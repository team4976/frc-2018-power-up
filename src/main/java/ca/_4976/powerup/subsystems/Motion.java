package ca._4976.powerup.subsystems;

import ca._4976.powerup.commands.ListenableCommand;
import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.SaveProfile;
import ca._4976.powerup.data.Initialization;
import ca._4976.powerup.data.Moment;
import ca._4976.powerup.data.Profile;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static ca.qormix.library.Lazy.use;

/**
 * The Motion subsystem controls records and plays back saved
 * information about the chassis speed and position.
 */
public final class Motion extends Subsystem implements Sendable {

    private DriverStation ds = DriverStation.getInstance();
    public Profile profile = Profile.blank();
    public Profile driveStraightProfile = Profile.blank();


    private Drive drive = Robot.drive;
    private boolean isRunning = false;
    private boolean isRecording = false;
    public int momentCounter;

    private ListenableCommand[] commands = null;
    public ArrayList<Integer> report = new ArrayList<>();

    private double p = 7.0, i = 0, d = 0;

    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Motion");
    private final NetworkTableEntry leftError = table.getEntry("Left Error");
    private final NetworkTableEntry rightError = table.getEntry("Right Error");

    @Override protected void initDefaultCommand() { }

    private void initCommands() {

        if (commands == null) {

            commands = Initialization.commands.toArray(new ListenableCommand[Initialization.commands.size()]);
            //Initialization.commands = null;
        }
    }

    public ListenableCommand[] getCommands() {

        initCommands();

        return commands;
    }

    public boolean isRecording() { return isRecording; }

    public boolean isRunning() { return isRunning; }

    public synchronized void record() {

        initCommands();

        new Thread(new Record()).start();
    }

    public synchronized void run() {

        initCommands();

        new Thread(new Run()).start();
    }

    public synchronized void stop() {

        isRunning = false;
        isRecording = false;
    }

    private class Record implements Runnable {

        @Override public void run() {

            isRecording = true;

            double timing = 1e+9 / 200;
            long lastTick = System.nanoTime() - (long) timing;

            ArrayList<Moment> moments = new ArrayList<>();

            while (isRecording && ds.isEnabled()) {

                if (System.nanoTime() - lastTick >= timing) {

                    lastTick = System.nanoTime();

                    moments.add(new Moment(
                            report.toArray(new Integer[report.size()]),
                            Robot.drive.getTankDrive(),
                            Robot.drive.getEncoderPosition()
                    ));

                    report.clear();
                }
            }

            DateFormat dateFormat = new SimpleDateFormat("YYYY.MM.DD");

            profile = new Profile(
                    "Recording - " + System.currentTimeMillis() % 10000,
                    dateFormat.format(new Date()),
                    moments.toArray(new Moment[moments.size()])
            );

            new SaveProfile(profile).start();
            isRecording = false;

            drive.enableRamping(false);
        }
    }

    private class Run implements Runnable {

        @Override public void run() {

            drive.enableRamping(false);
            drive.setUserControlEnabled(false);

            isRunning = true;

            double timing = 1e+9 / 200;
            long lastTick = System.nanoTime() - (long) timing;

            int interval = 0;

            double[] error = new double[2];
            double[] integral = new double[2];
            double[] derivative = new double[2];
            double[] lastError = new double[2];

            StringBuilder builder = new StringBuilder();

            builder.append("Motion Profile Log: ").append(profile.name).append(" ").append(profile.version).append('\n');
            builder.append("Left Output,Right Output,,Left Error,Right Error\n");

            if (profile.moments.length == 0) System.out.println("No Profile Loaded");

            while (isRunning && interval < profile.moments.length && ds.isEnabled()) {

                if (System.nanoTime() - lastTick >= timing)  {
                    momentCounter++;
                    lastTick = System.nanoTime();

                    final Moment moment = profile.moments[interval];

                    use(drive.getEncoderPosition(), it -> {

                        error[0] = moment.position[0] - it[0];
                        error[1] = moment.position[1] - it[1];
                    });

                    leftError.setDouble(error[0]);
                    rightError.setDouble(error[1]);

                    integral[0] += error[0];
                    integral[1] += error[1];

                    use(drive.getEncoderRate(), it -> {

                        derivative[0] = (error[0] - lastError[0]) / (1.0/200) - it[0];
                        derivative[1] = (error[1] - lastError[1]) / (1.0/200) - it[1];
                    });

                    lastError[0] = error[0];
                    lastError[1] = error[1];

                    drive.setTankDrive(
                            moment.output[0]
                                    + p * error[0]
                                    + i * integral[0]
                                    + d * derivative[0]
                            ,
                            moment.output[1]
                                    + p * error[1]
                                    + i * integral[1]
                                    + d * derivative[1]
                    );


                    builder.append(moment.output[0]).append(",").append(moment.output[1]).append(",,");
                    builder.append(error[0]).append(",").append(error[1]).append(",,");

                    try{
                        for (int command : moment.commands) {
                            commands[command].start();

                        }

                    } catch (NullPointerException   exception){
                        System.out.println("Array out of bound caught");
                    }

                    interval++;
                }
            }

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(
                        new File("/home/lvuser/motion/logs/" + profile.name + " - " + profile.version)));

                writer.write(builder.toString());
                writer.close();

            } catch (IOException ignored) { }

            isRunning = false;
            drive.setTankDrive(0, 0);
            drive.setUserControlEnabled(true);
        }
    }

    @Override public void initSendable(SendableBuilder builder) {

        setName("Motion Profile PID");

        builder.setSmartDashboardType("PIDController");
        builder.setSafeState(this::stop);
        builder.addDoubleProperty("p", () -> p, it -> p = it);
        builder.addDoubleProperty("i", () -> i, it -> i = it);
        builder.addDoubleProperty("d", () -> d, it -> d = it);
        builder.addBooleanProperty("enabled", this::isRunning, ignored -> {});
    }
}
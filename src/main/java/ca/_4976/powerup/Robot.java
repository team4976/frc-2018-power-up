package ca._4976.powerup;

import ca._4976.powerup.data.Profile;
import ca._4976.powerup.subsystems.Drive;
import ca._4976.powerup.subsystems.Motion;
import ca._4976.powerup.subsystems.CubeHandler;
import ca._4976.powerup.commands.DefaultGear;
import ca._4976.powerup.commands.ElevEncoderReset;
import ca._4976.powerup.commands.RecordProfile;
import ca._4976.powerup.commands.RunProfile;
import ca._4976.powerup.commands.LoadProfile;
import ca._4976.powerup.data.Profile;
import ca._4976.powerup.subsystems.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static ca.qormix.library.Lazy.use;

/**
 * This is the main class for running the Robot code.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public final class Robot extends IterativeRobot {

    public static OI oi;
    public final static Elevator elevator = new Elevator();
    public final static LinkArm linkArm = new LinkArm();

    public final static DPadStuff dPadStuff = new DPadStuff();

    public final static Ramp ramp = new Ramp();
    public final static Climber climber = new Climber();

    public final static CubeHandler cubeHandler = new CubeHandler();
    public final static Drive drive = new Drive();
    public final static Motion motion = new Motion();

    public final NetworkTableInstance instance = NetworkTableInstance.getDefault();
    public final NetworkTable table = instance.getTable("Log");

    public final NetworkTableEntry leftDistance =  table.getEntry("Left Distance");
    public final NetworkTableEntry rightDistance =  table.getEntry("Right Distance");
    public final NetworkTableEntry stopped =  table.getEntry("Is Stopped");

    public final NetworkTableEntry profiles =  table.getEntry("Profiles");

    private final RecordProfile recordProfile = new RecordProfile();
    private final RunProfile runProfile = new RunProfile();


    @Override public void robotInit() {
        //rename the sucky reet thiung

        oi = new OI();

        linkArm.resetArmEncoder();
        new DefaultGear().start();

        SmartDashboard.putData(drive);
        SmartDashboard.putData(motion);

        Robot.drive.defaultGear();
    }

    @Override public void disabledInit() {

        motion.stop();
    }


    @Override public void autonomousInit() {

        new LoadProfile("auto.csv").start();
        System.out.println("Here");
        new RunProfile().start();
    }

    @Override public void autonomousPeriodic(){
      //  runProfile.start();
        Scheduler.getInstance().run();
        log();
    }



    @Override public void teleopPeriodic(){
        new DefaultGear().start();
        Scheduler.getInstance().run();
        log();
    }
    @Override public void testPeriodic(){
        recordProfile.start();
    }

    private void log() {
        use(drive.getEncoderPosition(), it -> {
            leftDistance.setNumber(it[0]);
            rightDistance.setNumber(it[1]);
        });

        stopped.setBoolean(drive.isStopped());
        profiles.setStringArray(Profile.getAvailableProfiles());
    }
}
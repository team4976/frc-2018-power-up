package ca._4976.powerup.subsystems;

import ca._4976.powerup.commands.DriveWithJoystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import static ca.qormix.library.Lazy.use;
import static ca.qormix.library.Lazy.using;

/**
 * The DriveTrain subsystem controls the robot's chassis and reads in
 * information about it's speed and posit ion.
 */
public final class Drive extends Subsystem implements Runnable, Sendable {

    // The pneumatic solenoid
    private DoubleSolenoid transmission = new DoubleSolenoid(5, 5, 6);
    //private DoubleSolenoid transmission = new DoubleSolenoid(10, 3,2);

    // The left drive motors pwm pins 0 and 1
    private VictorSPX leftFront = new VictorSPX(9);
    private TalonSRX leftRear = new TalonSRX(12);
    
     // The right drive motors pwm pins 2 and 3
    private VictorSPX rightFront = new VictorSPX(11);
    private TalonSRX rightRear = new TalonSRX(13);

    // The encoders on the drive system
    private Encoder left = new Encoder(0, 1);
    private Encoder right = new Encoder(2, 3);

    // The ramping rate (change per second / ticks per second) { max accel, max jerk }
    private double[] ramp = new double[] { 1.0 / 200, 0.1 / 200 }; 
    
    // Stored varables for calculating the motor output when ramping
    private double[] target = { 0, 0 };
    private double[] velocity = { 0, 0 };
    private double[] acceleration = { 0, 0 };

    // Flags
    private boolean ramping = false;
    private boolean userControlEnabled = true;

    //state og gear switch
    private boolean gear = false;

    public Drive() {

        left.setDistancePerPulse(0.0001114);
        right.setDistancePerPulse(0.0001114);

        // Adding our varables to NetworkTables
        use(NetworkTableInstance.getDefault().getTable("Drive"), it -> {

            NetworkTableEntry tableEntry = it.getEntry("Ramp Rate");

            double[] rate = ramp;

            if (!tableEntry.exists()) {

                tableEntry.setDoubleArray(rate);
                tableEntry.setPersistent();
            }

            it.addEntryListener((table, key, entry, value, flags) -> {

                ramp = tableEntry.getDoubleArray(ramp);

            }, 0);
        });

        SmartDashboard.putData("Left Drive Encoder", left);
        SmartDashboard.putData("Right Drive Encoder", right);
    }

    /**
     * Used by command based robot to create looping joystick input
     */
    @Override protected void initDefaultCommand() { setDefaultCommand(new DriveWithJoystick()); }

    /**
     * Set user control enabled or disabled
     * 
     * @return enabled: enable usercontrol
     */
    public void setUserControlEnabled(boolean enabled) { userControlEnabled = enabled; }

    /**
     * Applies Zero throttle to the drive motors
     */
    public void stop() { setTankDrive(0, 0); }

    public void arcadeDrive(Joystick joy) {

        // Save the left and right trigger values as a combined value
        double forward = joy.getRawAxis(3) - joy.getRawAxis(2);

        // Saves the joystick value as a power of 2 while still keeping the sign
        double turn = using(joy.getRawAxis(0), x -> x = x * x * (Math.abs(x) / x));

        arcadeDrive(turn, forward);
    }

    public void arcadeDrive(double turn, double forward) {

        if (userControlEnabled) { // Used to disable user input when running a profile.

            // Set our taget based on arcade style control
            target[0] = forward + turn;
            target[1] = -forward + turn;

            // If ramping is disabled directly output the target to the motors
            if (!ramping) setTankDrive(target[0], target[1]);
        }
    }

     /**
     * The run function  is called when a new thread is started
     *
     * @see     java.lang.Thread#run()
     */
    @Override public void run() {

        double timing = 1e+9 / 200; // Stores the time between ticks in ns
        long lastTick = System.nanoTime() - (long) timing; // Stores the time of when the last tick started

        while (ramping) { // Loop when ramping is enabled

            if (System.nanoTime() - lastTick >= timing) { // Tick if the time between the last is greater then or equal to the timing

                lastTick = System.nanoTime(); // Store the current system time

                for (int i = 0; i < 2; i ++) { // Loop for the left and right drive motors

                    // If the difference between the target and the actual is less then the maximum jerk we set the actual to the target
                    if (Math.abs(velocity[i] - target[i]) < ramp[1]) velocity[i] = target[i];

                    // Checks if we are moving towards the target
                    if (Math.abs(velocity[i] - target[i]) > 0) {
 
                        if (Math.abs(acceleration[i] + target[i] > velocity[i] ? ramp[1] : -ramp[1]) < ramp[0] // Checks if we can still increase acceleration
                                && Math.abs((target[i] - velocity[i]) / acceleration[i]) > Math.abs(acceleration[i]) / ramp[1]) // Checks if there is enough time to decelerate
                            acceleration[i] += target[i] > velocity[i] ? ramp[1] : -ramp[1]; // Increases acceleration towards the target

                        if (Math.abs((target[i] - velocity[i]) / acceleration[i]) < Math.abs(acceleration[i]) / ramp[1]) // Checks if we need to start decelerating
                            acceleration[i] -= Math.abs(acceleration[i]) / acceleration[i] * ramp[1]; // Decreases acceleration towards 0

                        velocity[i] += acceleration[i]; // Applies the acceleration to our actual

                    } else acceleration[i] = 0; // Resets acceleration to 0 when at the target
                }

                if (userControlEnabled) setTankDrive(velocity[0], velocity[1]); // Outputs the actual to the drive motors
            }
        }
    }

    public void resetEncoderPosition() { 

        left.reset();
        right.reset();
     }

    /**
     * Gets the position from the robots drivetrain encoders
     * 
     * @return The left and right encoder positions and saves them as a double array
     */
    public Double[] getEncoderPosition() { return new Double[] { left.getDistance(), right.getDistance() }; }

    /**
     * Gets the velocity from the robots drivetrain encoders
     * 
     * @return The left and right encoder velocity and saves them as a double array
     */
    public Double[] getEncoderRate() { return new Double[] { left.getRate(), right.getRate() }; }


    /**
     * Uses the drivetrain encoders to determine when the robot has stopped.
     * 
     * @return boolean value indicating if the robot is not moving
     */
    public boolean isStopped() { return left.getStopped() && right.getStopped(); }


    /**
     * Outputs the the drive motors with a tanks style control
     * 
     * @param left: the output percentage of the left side of the drive ranging from -1 to 1
     * @param right: the output percentage of the right side of the drive ranging from -1 to 1 
     */
    public synchronized void setTankDrive(double left, double right) {

        leftRear.set(ControlMode.PercentOutput, left);
        leftFront.follow(leftRear);

        rightRear.set(ControlMode.PercentOutput, right);
        rightFront.follow(rightRear);
    }

    /**
     * Gets the current output being applied to the drive motors
     * 
     * @return the left and right drive output as a double array
     */
    public synchronized Double[]  getTankDrive() { return new Double[] { leftFront.getMotorOutputPercent(), rightFront.getMotorOutputPercent() }; }

    /**
     * Set ramping enabled or disabled
     * 
     * @return enable: enable ramping
     */
    public synchronized void enableRamping(boolean enable) {

        if (!ramping) {

            ramping = enable; // Store if ramping is enabled

            if (enable) new Thread(this).start(); // If ramping is enabled start call run in a new thread;
        }
    }

    /**
     * Called by the Smartdashboard class to create widgets for the dashboard
     */
    @Override public void initSendable(SendableBuilder builder) {

        setName("Drive Output");

        builder.setSmartDashboardType("DifferentialDrive");
        builder.addDoubleProperty("Left Motor Speed", () -> getTankDrive()[0], ignored -> {});
        builder.addDoubleProperty("Right Motor Speed", () -> -getTankDrive()[1], ignored -> {});
    }
    //Switches gear
    public void switchGear(){
        if (gear == false){
            //transmission.set(DoubleSolenoid.Value.kForward);
        }
        else{
            //transmission.set(DoubleSolenoid.Value.kOff);
        }

        gear = !gear;
    }

    //set the default state for the gear switch
    public void defaultGear(){
        gear = false;
       // transmission.set(DoubleSolenoid.Value.kOff);
        //transmission.set(DoubleSolenoid.Value.kOff);
    }
}

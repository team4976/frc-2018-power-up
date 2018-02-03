package ca._4976.powerup;



import ca._4976.powerup.commands.RecordProfile;
import ca._4976.powerup.commands.RunProfile;
import ca._4976.powerup.data.Profile;
import ca._4976.powerup.subsystems.Drive;
import ca._4976.powerup.subsystems.Elevator;
import ca._4976.powerup.subsystems.Motion;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
    public static Elevator elevator;

    public final static Drive drive = new Drive();
    public final static Motion motion = new Motion();

    private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private final NetworkTable table = instance.getTable("Log");

    private final NetworkTableEntry leftDistance =  table.getEntry("Left Distance");
    private final NetworkTableEntry rightDistance =  table.getEntry("Right Distance");
    private final NetworkTableEntry stopped =  table.getEntry("Is Stopped");

    private final NetworkTableEntry profiles =  table.getEntry("Profiles");

    @Override public void robotInit() {
        oi = new OI();

        SmartDashboard.putData(drive);
        SmartDashboard.putData(motion);

        Robot.drive.defaultGear();
    }



    @Override public void disabledInit() {
        motion.stop();
    }



    @Override public void autonomousPeriodic(){
        new RunProfile();
        Scheduler.getInstance().run();
        log();
    }



    @Override public void teleopPeriodic(){
        Scheduler.getInstance().run();
        log();
    }
    @Override public void testPeriodic(){
        new RecordProfile();
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
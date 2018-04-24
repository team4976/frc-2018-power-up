package ca._4976.powerup;

import ca._4976.powerup.commands.*;
import ca._4976.powerup.data.Profile;
import ca._4976.powerup.subsystems.Drive;
import ca._4976.powerup.subsystems.Motion;
import ca._4976.powerup.subsystems.CubeHandler;
import ca._4976.powerup.data.Profile;
import ca._4976.powerup.subsystems.*;
//import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfoSet;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
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
    public final NetworkTableEntry switchSelect =  table.getEntry("Switch Auto Selected");
    public final NetworkTableEntry scaleSelected =  table.getEntry("Scale Auto Selected");
    public final NetworkTableEntry autoSelected =  table.getEntry("Auto Auto Selected");
    public final NetworkTableEntry leftSelected =  table.getEntry("Left Auto Selected");
    public final NetworkTableEntry centerSelected =  table.getEntry("Center Auto Selected");
    public final NetworkTableEntry rightSelected =  table.getEntry("Right auto Selected");
    public final NetworkTableEntry straightSelected =  table.getEntry("Straight auto Selected");
    public final NetworkTableEntry oneCubeSelected =  table.getEntry("One Cube Selected");
    public final NetworkTableEntry twoCubeSelected =  table.getEntry("Two Cube Selected");




    public final NetworkTableEntry profiles =  table.getEntry("Profiles");


    private final RecordProfile recordProfile = new RecordProfile();
    private final RunProfile runProfile = new RunProfile();



    LoadProfile leftScale;
    LoadProfile rightSwitch;
    LoadProfile rightScale;
    LoadProfile rightSideLeftScale;
    LoadProfile leftSwitch;



    @Override public void robotInit() {

        oi = new OI();

        Robot.climber.climbingShift.set(DoubleSolenoid.Value.kReverse);


        linkArm.resetArmEncoder();
        new DefaultGear().start();
        new Vision().start();
        new GrabSwitch().start();
        new CancelCubeCube().start();

        SmartDashboard.putData(drive);
        SmartDashboard.putData(motion);

        Robot.drive.defaultGear();

        switchSelect.setDefaultBoolean(false);
        scaleSelected.setDefaultBoolean(false);
        autoSelected.setDefaultBoolean(false);
        leftSelected.setDefaultBoolean(false);
        centerSelected.setDefaultBoolean(false);
        rightSelected.setDefaultBoolean(false);
        straightSelected.setDefaultBoolean(false);
        oneCubeSelected.setDefaultBoolean(false);
        twoCubeSelected.setDefaultBoolean(false);



        rightSwitch = new LoadProfile("RightSwitch.csv");
        rightScale = new LoadProfile("RightSideScale.csv");
        rightSideLeftScale = new LoadProfile("RightSideLeftScale.csv");
        leftScale = new LoadProfile("LeftSideScale.csv");
        leftSwitch = new LoadProfile("LeftSwitch.csv");
}

    @Override public void disabledInit() {

        motion.stop();

    }


    @Override public void autonomousInit() {


       // LoadProfile leftScale = new LoadProfile("ScaleLeftOneCube.csv");


        boolean selectedStraight = straightSelected.getBoolean(false);
        boolean selectedLeft = leftSelected.getBoolean(false);
//        boolean selectedScale

               String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData != null) {
            char switchPosition = gameData.charAt(0);
            char scalePosition = gameData.charAt(1);
            if (selectedStraight) {
                new LoadProfile("DriveStraight.csv").start();
                new RunProfile().start();
              //  new RunProfile().start();
            } else {
                if (selectedLeft) {
                    if (scaleSelected.getBoolean(false) && switchSelect.getBoolean(false)){
                        System.out.println("Running a scale and a switch");
                        if (scalePosition == 'L' && switchPosition == 'L'){
                            System.out.println("Running Left Scale Left Switch");
                            new LoadProfile("LeftScaleLeftSwitch.csv").start();
                            new RunProfile().start();
                            //leftScale.start();
                        } else if (scalePosition == 'L' && switchPosition == 'R'){
                            new LoadProfile("LeftScaleRightSwitch.csv").start();
                            new RunProfile().start();

                        }
                    } else {
                        if (scalePosition == 'L') {
                            System.out.println("Scale Left");
                            //new LoadProfile("ScaleLeftOneCube.csv").start();
                           // new LoadProfile("ScaleLeftOneCube.csv").start();
                            //leftScale.start();
                            leftScale.start();
                            new RunProfile().start();

                        } else if (switchPosition == 'L') {
                           // new LoadProfile("LeftSwitch.csv").start();
                            leftSwitch.start();
                            new RunProfile().start();
                        } else {
                            new LoadProfile("LeftSideRightScale.csv").start();
                            new RunProfile().start();
                        }
                    }
                }
                else if (centerSelected.getBoolean(false)) {
                    if (switchSelect.getBoolean(false)) {
                        if (switchPosition == 'L') {
                            new LoadProfile("CenterSwitchLeft2Cube.csv").start();
                            new RunProfile().start();
                        } else if (switchPosition == 'R') {
                            new LoadProfile("2CubeSwitchRightSide.csv").start();
                            new RunProfile().start();
                        }
                    }

                } else if (rightSelected.getBoolean(false)) {
                    if (scalePosition == 'R'){
                        rightScale.start();
                        new RunProfile().start();
                    }

                    else if (switchPosition == 'R'){
                        rightSwitch.start();
                        new RunProfile().start();
                    }
                    else {
                         //new LoadProfile("DriveStraight.csv").start();
                        rightSideLeftScale.start();
                        new RunProfile().start();
                     }
            }
        }

        } else {
        System.out.println("Error no field variable found");
            new LoadProfile("DriveStraight.csv").start();
            new RunProfile().start();
        }


    }



    @Override public void autonomousPeriodic(){
      //  runProfile.start();
        Scheduler.getInstance().run();
        log();
    }

    @Override public void teleopInit(){
       //Robot.drive.lowGear();
    }


    @Override public void teleopPeriodic(){
        new DefaultGear().start();
        Scheduler.getInstance().run();
        log();
        if (!Robot.drive.userControlEnabled){
            System.out.println("User control: " + Robot.drive.userControlEnabled);
        }
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
package ca._4976.powerup;

import ca._4976.powerup.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The operator interface of the robot, it has been simplified from the real
 * robot to allow control with a single PS3 joystick. As a result, not all
 * functionality from the real robot is available.
 */
public final class OI {

    public Joystick driver = new Joystick(0);
    public Joystick operator = new Joystick(1);

    OI() {

        //TODO JOYSTICK STOLEN

        new JoystickButton(driver,8).whenPressed(new RecordProfile());
        new JoystickButton(driver,7).whenPressed(new RunProfile());
       // new JoystickButton(driver, 4).whenPressed(new Transmission());
        new JoystickButton(driver, 4).whenPressed(new ShiftHigh());
        new JoystickButton(driver, 4).whenReleased(new ShiftLow());

        new JoystickButton(driver, 1).whenPressed(new CubeCube());
       // new JoystickButton(driver, 1).whenReleased(new CloseGripper());
        new JoystickButton(driver, 3).whenPressed(new CancelCubeCube());
        new JoystickButton(driver, 2).whileHeld(new SpitCubeCube());
        new JoystickButton(driver, 2).whenReleased(new StopGripperMotors());




        //Climbing controls
        new JoystickButton(operator, 6).whenPressed(new ActivateClimber());
        new JoystickButton(operator, 5).whenPressed(new DeactivateClimber());
        new JoystickButton(operator, 8).whenReleased(new LowerClimberGuides()
        );


//        //Used for deploying the ramp
//        new JoystickButton(driver, 8).whenPressed(new DeployRamp());
//        new JoystickButton(driver, 8).whenReleased(new StopDeployingRamp());

        //Used for the robots forks
//        new JoystickButton(operator, 5).whenPressed(new DeployForks());
//        new JoystickButton(operator, 6).whenPressed(new RetractForks());
//        new JoystickButton(operator, 5).whenReleased(new StopForks());
//        new JoystickButton(operator, 6).whenReleased(new StopForks());

        //Presets

        //High scale
        new JoystickButton(operator, 4).whenPressed(new ElevatorScaleHigh());
        new JoystickButton(operator, 4).whenPressed(new Arm45());

        //Low Scale
//        new JoystickButton(operator, 2).whenPressed(new ElevatorScaleLow());

        //Switch
        new JoystickButton(operator, 3).whenPressed(new ElevatorGround());
        new JoystickButton(operator, 3).whenPressed(new ArmSwitch());

        //Ground
        new JoystickButton(operator, 1).whenPressed(new ElevatorGround());
        new JoystickButton(operator, 1).whenPressed(new ArmLevel());


    }
}
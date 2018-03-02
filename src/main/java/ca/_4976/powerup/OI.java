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
//        new JoystickButton(driver, 4).whenPressed(new Transmission());
        new JoystickButton(driver, 1).whenPressed(new DriverIntake());
        new JoystickButton(driver, 2).whenPressed(new DriverEject());
        new JoystickButton(driver, 2).whenReleased(new DriverStop());
        new JoystickButton(driver, 3).whenPressed(new DriverStop());

//        new JoystickButton(driver, 3).whenReleased(new ResetGrab());

//        new JoystickButton(driver, 7).whenPressed(new Transmission());
//
//        new JoystickButton(operator, 8).whenPressed(new ActivateClimber());
//
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
        new JoystickButton(operator, 4).whenPressed(new Arm45()); //change to 30

        //Mid scale
        new JoystickButton(operator, 3).whenPressed(new ElevatorScaleMid());
        new JoystickButton(operator, 3).whenPressed(new Arm45());

        //Low scale
        new JoystickButton(operator, 2).whenPressed(new ElevatorScaleLow());
        new JoystickButton(operator, 2).whenPressed(new ArmLevel());

        //Switch
        new JoystickButton(operator, 5).whenPressed(new ElevatorSwitch());
        new JoystickButton(operator, 5).whenPressed(new Arm45());

        //Ground
        new JoystickButton(operator, 1).whenPressed(new ElevatorGround());
        new JoystickButton(operator, 1).whenPressed(new ArmLevel());

        //Cube - arm first levels then dips
        new JoystickButton(operator, 6).whenPressed(new ElevatorCube());
        new JoystickButton(operator, 6).whenPressed(new ArmLevel());
        new JoystickButton(operator, 6).whenReleased(new ArmCube());


    }
}
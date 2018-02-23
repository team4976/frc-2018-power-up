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
        new JoystickButton(driver, 3).whenPressed(new DriverStop());
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
//        new JoystickButton(operator, 1).whenPressed(new ElevatorSwitch());
//        new JoystickButton(operator, 1).whenPressed(new ArmDefault());
//
//        new JoystickButton(operator, 2).whenPressed(new ElevatorScaleLow());
//        new JoystickButton(operator, 2).whenPressed(new ArmDefault());
//
//        new JoystickButton(operator, 3).whenPressed(new ElevatorScaleMid());
//        new JoystickButton(operator, 3).whenPressed(new ArmScaleMid());
//
        new JoystickButton(operator, 4).whenPressed(new ElevatorScaleHigh());
//        new JoystickButton(operator, 4).whenPressed(new ArmScaleMid());
//
//        new JoystickButton(operator, 5).whenPressed(new ElevatorGround());
//        new JoystickButton(operator, 5).whenPressed(new ArmDefault());
//        //Right dpad
//        if(operator.getPOV() >= 88 && operator.getPOV() <= 92){
//            new ElevatorGround();
//            new ArmDefault();
//        }
//
//        //Down dpad
//        if(operator.getPOV() >= 178 && operator.getPOV() <= 182){
//            new ElevatorReset();
//            new ArmMinimum();
//        }
    }
}
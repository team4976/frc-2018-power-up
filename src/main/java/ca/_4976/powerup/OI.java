package ca._4976.powerup;

import ca._4976.powerup.commands.RecordProfile;
import ca._4976.powerup.commands.RunProfile;
import ca._4976.powerup.commands.Transmission;
import ca._4976.powerup.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

import javax.print.attribute.standard.JobSheets;

/**
 * The operator interface of the robot, it has been simplified from the real
 * robot to allow control with a single PS3 joystick. As a result, not all
 * functionality from the real robot is available.
 */

public final class OI {

    public Joystick driver = new Joystick(0);
    public Joystick operator = new Joystick(1);

    OI() {
        new JoystickButton(driver, 4).whenPressed(new Transmission());
        new JoystickButton(driver, 1).whenPressed(new DriverIntake());
        new JoystickButton(driver, 2).whenPressed(new DriverEject());
        new JoystickButton(driver, 2).whenReleased(new DriverStop());
        new JoystickButton(driver, 7).whenPressed(new Transmission());

        new JoystickButton(operator, 8).whenPressed(new ActivateClimber());

        //Used for deploying the ramp
        new JoystickButton(driver, 8).whenPressed(new DeployRamp());
        new JoystickButton(driver, 8).whenReleased(new StopDeployingRamp());

        //Used for the robots forks
        new JoystickButton(operator, 5).whenPressed(new DeployForks());
        new JoystickButton(operator, 6).whenPressed(new RetractForks());
        new JoystickButton(operator, 5).whenReleased(new StopForks());
        new JoystickButton(operator, 6).whenReleased(new StopForks());

        //Runs the command to climb/just run the elevator
        new JoystickButton(driver, 6).whenPressed(new EndGameIncrease());
        new JoystickButton(driver, 5).whenPressed(new EndGameIncrease());
        new JoystickButton(driver, 6).whenReleased(new EndGameDecrease());
        new JoystickButton(driver, 5).whenReleased(new EndGameDecrease());

        new JoystickButton(operator, 1).whenPressed(new EPS_Ground());
        new JoystickButton(operator, 2).whenPressed(new EPS_Switch());
        new JoystickButton(operator, 3).whenPressed(new EPS_ScaleLow());
        new JoystickButton(operator, 4).whenPressed(new EPS_ScaleHigh());
        //right dpad
        if(operator.getPOV() >= 88 && operator.getPOV() <= 92){
            new EPS_ScaleMid();
        }
    }
}
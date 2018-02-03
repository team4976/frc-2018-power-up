package ca._4976.powerup;

import ca._4976.powerup.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
        new JoystickButton(driver, 3).whenPressed(new Tabikisto());
    }
}
package ca._4976.powerup;

import ca._4976.powerup.commands.RecordProfile;
import ca._4976.powerup.commands.RunProfile;
import ca._4976.powerup.commands.Transmission;
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

        new JoystickButton(driver, 7).whenPressed(new RecordProfile());
        new JoystickButton(driver, 8).whenPressed(new RunProfile());
        new JoystickButton(driver, 4).whenPressed(new Transmission());
    }
}
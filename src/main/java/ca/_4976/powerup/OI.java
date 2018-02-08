package ca._4976.powerup;

import ca._4976.powerup.commands.RecordProfile;
import ca._4976.powerup.commands.RunProfile;
import ca._4976.powerup.commands.Transmission;
import ca._4976.powerup.commands.elevpresets.EPS_Ground;
import ca._4976.powerup.commands.elevpresets.EPS_ScaleHigh;
import ca._4976.powerup.commands.elevpresets.EPS_ScaleLow;
import ca._4976.powerup.commands.elevpresets.EPS_Switch;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

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






        new JoystickButton(operator, 1).whenPressed(new EPS_Ground());
        new JoystickButton(operator, 2).whenPressed(new EPS_Switch());
        new JoystickButton(operator, 3).whenPressed(new EPS_ScaleLow());
        new JoystickButton(operator, 4).whenPressed(new EPS_ScaleHigh());
        //ADD MID FOR DPAD

    }
}
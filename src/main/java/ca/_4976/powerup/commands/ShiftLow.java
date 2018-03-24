package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will toggle the transmission
 */
public final class ShiftLow extends ListenableCommand {

    @Override protected void initialize() {
        Robot.drive.lowGear();
    }

    @Override protected boolean isFinished() { return true; }
}

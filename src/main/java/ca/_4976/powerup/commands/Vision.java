package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will toggle the transmission
 */
public final class Vision extends ListenableCommand {

    public Vision(){}

    @Override protected void initialize() {
        Robot.drive.vision();
    }

    @Override protected boolean isFinished() { return true; }
}

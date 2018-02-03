package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will toggle the transmission
 */
public final class Transmission extends Command {

    public Transmission() { willRunWhenDisabled(); }

    @Override protected void execute() {
        Robot.drive.switchGear();
    }

    @Override protected boolean isFinished() { return true; }

    @Override protected void end() {  }
}

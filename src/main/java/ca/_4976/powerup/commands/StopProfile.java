package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will stop a recording/running profile.
 */
public final class StopProfile extends Command {

    public StopProfile() {

        requires(Robot.motion);
        requires(Robot.drive);

        willRunWhenDisabled();
    }

    @Override protected void initialize() {

        Robot.motion.stop();
    }

    @Override protected boolean isFinished() { return !Robot.motion.isRunning(); }

    @Override protected void end() { Robot.drive.setUserControlEnabled(true); }
}
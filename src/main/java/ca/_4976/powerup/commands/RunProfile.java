package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will begin running the profile that is saved in memory.
 */
public final class RunProfile extends Command {

    public RunProfile() { willRunWhenDisabled(); }

    @Override protected void initialize() {

        Robot.drive.resetEncoderPosition();
        Robot.drive.setUserControlEnabled(false);
        Robot.motion.run();
    }

    @Override protected boolean isFinished() { return !Robot.motion.isRunning(); }

    @Override protected void end() { Robot.drive.setUserControlEnabled(true); }
}

package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command allows the robot to start recording a new profile.
 * When complete the profile will be saved in memory ready to replay.
 */
public final class RecordProfile extends Command {

    public RecordProfile() { willRunWhenDisabled(); }

    @Override protected void initialize() {
        System.out.println("Pie");
        Robot.drive.resetEncoderPosition();
        Robot.motion.record();
        Robot.drive.enableRamping(true);
      }

    @Override protected boolean isFinished() { return !Robot.motion.isRecording(); }

    @Override protected void end() { Robot.drive.enableRamping(false); }
}

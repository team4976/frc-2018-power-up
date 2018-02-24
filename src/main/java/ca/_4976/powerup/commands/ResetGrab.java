package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command allows joystick to drive the robot. It is always running
 * except when interrupted by another command.
 */
public final class ResetGrab extends ListenableCommand {

    public ResetGrab() {}

    @Override protected void execute() { Robot.cubeHandler.resetGrab();}

    @Override protected boolean isFinished() { return true; }

}

package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-04-20.
 */
public class StartRumble extends Command {
    @Override
    protected void initialize() {
        Robot.cubeHandler.rumbleOn();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

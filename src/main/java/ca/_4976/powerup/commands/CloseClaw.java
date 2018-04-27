package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-04-19.
 */
public class CloseClaw extends Command{

    @Override
    protected void initialize() {
        Robot.cubeHandler.close();
        Robot.cubeHandler.stop();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

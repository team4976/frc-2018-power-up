package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-04-17.
 */
public class ClampCube extends ListenableCommand {

    @Override
    protected void initialize() {
        Robot.cubeHandler.stop();        Robot.cubeHandler.close();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

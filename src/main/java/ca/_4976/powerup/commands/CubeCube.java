package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-04-19.
 */
public class CubeCube extends ListenableCommand {

    @Override
    protected void initialize() {
        Robot.cubeHandler.check();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

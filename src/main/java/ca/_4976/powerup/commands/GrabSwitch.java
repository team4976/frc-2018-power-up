package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-04-19.
 */
public class GrabSwitch extends ListenableCommand{
    @Override
    protected void execute() {
        Robot.cubeHandler.bumper();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

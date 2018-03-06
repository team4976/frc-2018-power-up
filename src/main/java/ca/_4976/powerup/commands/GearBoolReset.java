package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

public class GearBoolReset extends ListenableCommand{
    @Override
    protected void initialize(){
        Robot.cubeHandler.resetBool();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}

package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

public class GearCurrent extends ListenableCommand {
    @Override
    protected void execute(){
        Robot.cubeHandler.gearCurrent();
    }

    @Override
    protected boolean isFinished() {
        return Robot.cubeHandler.currentFlag;
    }

    @Override protected void end(){new GearBoolReset().start();}
}

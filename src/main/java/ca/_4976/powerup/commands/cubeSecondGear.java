package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-03-09.
 */
public class cubeSecondGear extends ListenableCommand{

    @Override
    protected void initialize(){
        Robot.cubeHandler.gearSwitch();
    }

    @Override
    protected boolean isFinished(){
        return true;
    }
}

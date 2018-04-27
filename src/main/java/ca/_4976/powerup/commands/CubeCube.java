package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-03-09.
 */
public class CubeCube extends ListenableCommand{

    @Override
    protected void initialize(){
    }

    @Override
    protected void execute(){
        Robot.cubeHandler.IntakeCube();
    }

    @Override
    protected boolean isFinished(){
            return true;
    }

    @Override
    protected void end(){

    }
}

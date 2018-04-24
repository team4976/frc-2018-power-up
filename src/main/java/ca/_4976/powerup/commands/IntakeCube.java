package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-03-09.
 */
public class IntakeCube extends Command {

    @Override
    protected void initialize(){
            Robot.cubeHandler.spinFast();
    }

    @Override
    protected boolean isFinished(){
        return true;
    }

    @Override
    protected void end(){}
}

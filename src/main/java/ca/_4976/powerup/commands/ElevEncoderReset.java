package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevEncoderReset extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

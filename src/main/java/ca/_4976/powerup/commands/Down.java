package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

public class Down extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.down();
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
}

package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorScaleMid extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.moveToMidScale();
        Robot.elevator.presetEnabled = true;
        Robot.elevator.scaleMidStarted = true;
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkMidScale();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.scaleMidStarted = false;
        Robot.elevator.stop();
    }
}


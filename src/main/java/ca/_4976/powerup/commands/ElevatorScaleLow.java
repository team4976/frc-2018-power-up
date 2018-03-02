package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorScaleLow extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.moveToLowScale();
        Robot.elevator.presetEnabled = true;
        Robot.elevator.scaleLowStarted = true;
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkLowScale();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.scaleLowStarted = false;
        Robot.elevator.stop();
    }
}


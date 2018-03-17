package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorSL extends ListenableCommand {

    public ElevatorSL(){
        requires(Robot.elevator);
    }

    @Override
    protected void initialize(){
        Robot.elevator.moveToTarget(Robot.elevator.scaleMidValue);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs(true) || Robot.elevator.checkLowScale();
    }

    @Override
    protected void end(){
        Robot.elevator.resetPresetFlags();
        Robot.elevator.stop();
    }
}


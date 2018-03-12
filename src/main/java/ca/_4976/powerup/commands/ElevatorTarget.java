package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorTarget extends ListenableCommand {

    @Override
    protected void initialize(){
//        Robot.elevator.moveToTarget(Robot.elevator.groundValue);
//        Robot.elevator.moveToTarget(Robot.elevator.scaleHighValue);
        Robot.elevator.moveToTarget(400);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkTarget();
    }

    @Override
    protected void end(){
        Robot.elevator.resetPresetFlags();
        Robot.elevator.stop();
    }
}
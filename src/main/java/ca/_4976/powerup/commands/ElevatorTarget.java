package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorTarget extends ListenableCommand {

    private double target;

    public ElevatorTarget(double target){
        requires(Robot.elevator);
        this.target = target;
    }

    @Override
    protected void initialize(){
        Robot.elevator.moveToTarget(target);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs(true) || Robot.elevator.checkTarget();
    }

    @Override
    protected void end(){
        Robot.elevator.resetPresetFlags();
        Robot.elevator.stop();
    }
}
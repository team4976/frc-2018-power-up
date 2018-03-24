package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorScaleLow extends ListenableCommand {

    public ElevatorScaleLow(){
        requires(Robot.elevator);
    }

    @Override
    protected void initialize(){
        Robot.elevator.moveToTarget(Robot.elevator.scaleLowValue);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs(true) || Robot.elevator.checkTarget();
    }

    @Override
    protected void end(){
        Robot.elevator.resetPresetFlags();
        Robot.elevator.holdElevator();
    }
}

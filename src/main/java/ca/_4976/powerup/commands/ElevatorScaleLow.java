package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Elevator;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorScaleLow extends ListenableCommand {

    public ElevatorScaleLow(){
        requires(Robot.elevator);
    }

    @Override
    protected void initialize(){
        Robot.elevator.moveToTarget(Elevator.scaleLowValue);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs(true) || Robot.elevator.checkTarget();
    }

    @Override
    protected void end(){
        Robot.elevator.resetPresetFlags();
    }
}

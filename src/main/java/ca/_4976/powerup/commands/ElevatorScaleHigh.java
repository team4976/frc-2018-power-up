package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorScaleHigh extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.moveToHighScale();
        Robot.elevator.presetEnabled = true;
        Robot.elevator.scaleHighStarted = true;
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkHighScale();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.scaleHighStarted = false;
        Robot.elevator.stop();
    }
}

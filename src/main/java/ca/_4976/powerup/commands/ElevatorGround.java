package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorGround extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.moveToGround();
        Robot.elevator.presetEnabled = true;
        Robot.elevator.groundStarted = true;
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkGround();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.groundStarted = false;
        Robot.elevator.stop();
    }
}
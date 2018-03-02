package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorSwitch extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.moveToSwitch();
        Robot.elevator.presetEnabled = true;
        Robot.elevator.switchStarted = true;
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkSwitch();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.switchStarted = false;
        Robot.elevator.stop();
    }
}
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorSwitch extends ListenableCommand {

    public ElevatorSwitch(){
        willRunWhenDisabled();
    }

    @Override
    protected void initialize(){
        System.out.println("\nSTART: SWITCH\n");
    }

    @Override
    protected void execute(){
        Robot.elevator.moveToSwitch();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.checkSwitch();
    }

    @Override
    protected void end(){
        Robot.elevator.stop();
    }
}
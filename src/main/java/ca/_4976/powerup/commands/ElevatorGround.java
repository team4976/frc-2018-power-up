package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorGround extends ListenableCommand {

    public ElevatorGround(){
        willRunWhenDisabled();
    }

    @Override
    protected void initialize(){
        System.out.println("\nSTART: GROUND\n");
    }

    @Override
    protected void execute(){
        Robot.elevator.moveToGround();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.checkGround();
    }

    @Override
    protected void end(){
        Robot.elevator.stop();
    }
}
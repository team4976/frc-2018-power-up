package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class ElevatorScaleHigh extends ListenableCommand {

    public ElevatorScaleHigh(){
        willRunWhenDisabled();
    }

    @Override
    protected void initialize(){
        System.out.println("\nSTART: SCALE HIGH\n");
    }

    @Override
    protected void execute(){
        System.out.println("HIGH SCALE COMMAND");
        Robot.elevator.moveToHighScale();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.checkHighScale();
    }

    @Override
    protected void end(){
        Robot.elevator.stop();
    }
}

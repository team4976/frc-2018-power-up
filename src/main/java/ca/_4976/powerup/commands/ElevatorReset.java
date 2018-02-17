package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ElevatorReset extends Command {

    public ElevatorReset(){
        willRunWhenDisabled();
    }

    @Override
    protected void initialize(){
        System.out.println("\nSTART: DEFAULT\n");
    }

    @Override
    protected void execute(){
        Robot.elevator.moveToDefault();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.checkDefault();
    }

    @Override
    protected void end(){
        Robot.elevator.stop();
    }
}

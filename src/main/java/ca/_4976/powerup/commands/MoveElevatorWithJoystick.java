package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-02-15.
 */
public class MoveElevatorWithJoystick extends ListenableCommand {

    public MoveElevatorWithJoystick(){
        requires(Robot.elevator);
    }

    @Override
    protected void execute(){
        Robot.elevator.moveElevator();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override protected void end() {
        Robot.elevator.stop();
    }


}

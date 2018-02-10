package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class MoveElevatorWithJoystick extends ListenableCommand {

    public MoveElevatorWithJoystick(){
        requires(Robot.elevator);
    }

    @Override
    protected void execute() {
        Robot.elevator.moveElevator();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }
}

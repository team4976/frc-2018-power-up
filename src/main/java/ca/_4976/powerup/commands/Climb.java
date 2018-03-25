package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Climb extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.climb();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

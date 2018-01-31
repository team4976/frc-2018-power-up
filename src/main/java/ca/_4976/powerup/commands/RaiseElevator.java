package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class RaiseElevator extends Command {
    public RaiseElevator() {}


       @Override protected void execute(){
            Robot.elevator.moveUp();
        }
        @Override protected void initialize(){}

        @Override protected boolean isFinished() {
            return true;
        }
        @Override protected void end () {
        }
    }

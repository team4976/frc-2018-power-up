package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class ElevPresetFive extends Command {
    public ElevPresetFive() {

    }

    @Override
    protected void execute() {
        Robot.elevator.scaleHighPS();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

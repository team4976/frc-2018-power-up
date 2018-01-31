package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class ElevPresetThree extends Command {
    public ElevPresetThree() {

    }

    @Override
    protected void execute() {
        Robot.elevator.scaleLowPS();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

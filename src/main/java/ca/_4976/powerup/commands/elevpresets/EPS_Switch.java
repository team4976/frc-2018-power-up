package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class ElevPresetSwitch extends Command {
    public ElevPresetSwitch() {

    }

    @Override
    protected void execute() {
        Robot.elevator.switchPS();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Elevator;

public final class EPS_Switch extends ListenableCommand {

    @Override
    protected void initialize() {
        Robot.elevator.moveToPreset(Elevator.ElevPreset.SWITCH);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

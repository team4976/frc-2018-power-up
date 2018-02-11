package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Elevator;

public final class EPS_ScaleHigh extends ListenableCommand {

    @Override
    protected void initialize() {
        Robot.elevator.moveToPreset(Elevator.ElevatorPreset.SCALE_HIGH);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

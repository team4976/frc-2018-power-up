package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Elevator;

public class EPS_Ground extends ListenableCommand {

    @Override
    protected void initialize() { Robot.elevator.moveToPreset(Elevator.ElevatorPreset.GROUND); }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {}
}
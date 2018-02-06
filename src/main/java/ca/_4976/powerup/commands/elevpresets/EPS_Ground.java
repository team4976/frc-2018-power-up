package ca._4976.powerup.commands.elevpresets;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public final class EPS_Ground extends Command {
    public EPS_Ground() {
        requires(Robot.elevator);
    }

    @Override
    protected void execute() {
        Robot.elevator.moveToPreset(Elevator.ElevPreset.GROUND);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}
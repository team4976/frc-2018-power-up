package ca._4976.powerup.commands.elevpresets;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public final class EPS_ScaleMid extends Command {
    public EPS_ScaleMid() {
        requires(Robot.elevator);
    }

    @Override
    protected void initialize() {
        Robot.elevator.moveToPreset(Elevator.ElevPreset.SCALE_MID);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

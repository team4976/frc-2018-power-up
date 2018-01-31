package ca._4976.powerup.commands.elevpresets;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class EPS_Ground extends Command {
    public EPS_Ground() {

    }

    @Override
    protected void execute() {
        Robot.elevator.groundPS();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}
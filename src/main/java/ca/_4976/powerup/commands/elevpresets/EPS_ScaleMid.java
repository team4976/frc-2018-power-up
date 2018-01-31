package ca._4976.powerup.commands.elevpresets;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public final class EPS_ScaleMid extends Command {
    public EPS_ScaleMid() {

    }

    @Override
    protected void initialize() {
        Robot.elevator.scaleMidPS();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}

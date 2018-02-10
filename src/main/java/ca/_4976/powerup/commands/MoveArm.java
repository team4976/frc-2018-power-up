package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends ListenableCommand{

    public MoveArm() {
        requires(Robot.linkArm);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute(){
        Robot.linkArm.moveLinkArm();
    }
}

package ca._4976.powerup.commands.elevpresets;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class MoveLinkArm extends Command{

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute(){
        Robot.linkArm.moveLinkArm();
    }
}

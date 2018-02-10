//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//Deploys the ramp to pick up a bot
public class DeployRamp extends ListenableCommand {

    public DeployRamp(){}

    @Override
    protected void execute(){
        Robot.ramp.deployRamp();
    }

    @Override
    protected boolean isFinished() {return true;}

    @Override
    protected void end(){}


}

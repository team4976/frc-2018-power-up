//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//Deploys the forks to help pick up a bot
public class DeployForks extends Command {

    public DeployForks(){}

    @Override
    protected void execute(){
        Robot.ramp.deployForks();
    }

    @Override
    protected boolean isFinished(){return true;}

    @Override
    protected void end(){}


}

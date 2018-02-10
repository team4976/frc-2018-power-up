//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//Stops the deploying the ramp
public class StopDeployingRamp extends ListenableCommand {

    public StopDeployingRamp(){}

    @Override
    protected void execute(){
        Robot.ramp.stopDeployingRamp();
    }

    @Override
    protected boolean isFinished(){return true;}

    @Override
    protected void end(){}

}

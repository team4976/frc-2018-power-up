//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//Stops the forks from extending/retracting
public class StopForks extends ListenableCommand {

    public StopForks(){}

    @Override
    protected void execute(){
        Robot.ramp.stopForks();
    }

    @Override
    protected boolean isFinished() {return true;}

    @Override
    protected void end(){}



}

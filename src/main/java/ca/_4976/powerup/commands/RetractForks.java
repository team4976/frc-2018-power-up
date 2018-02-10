//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//Used to retract the forks inwards
public class RetractForks extends ListenableCommand {

    public RetractForks(){}

    @Override
    protected void execute(){
        Robot.ramp.retractForks();
    }

    @Override
    protected boolean isFinished() {return true;}

    @Override
    protected void end(){}


}

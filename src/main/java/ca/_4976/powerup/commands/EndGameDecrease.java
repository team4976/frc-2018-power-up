//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class EndGameDecrease extends Command {

    //Decreases the endgame variable
    public EndGameDecrease(){}

    @Override
    protected void execute(){
        Robot.climber.endGameDecrease();
    }

    @Override
    protected boolean isFinished() {return true;}

    @Override
    protected void end(){}

}

//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class EndGameDecrease extends ListenableCommand {

    //Decreases the endgame variable
    public EndGameDecrease(){}

    @Override
    protected void execute(){
        Robot.climber.endGameDecrease();
        if(Robot.climber.endGameToggle < 2){
            Robot.elevator.stop();
            //endgamedecrease
        }
    }

    @Override
    protected boolean isFinished() {return true;}

    @Override
    protected void end(){}

}

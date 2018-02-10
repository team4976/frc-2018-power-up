//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//Increases the endgame variable, when the variable reaches 2 (both button presses) it will climb
public class EndGameIncrease extends Command {


    public EndGameIncrease(){}

    @Override
    protected void execute(){
        Robot.climber.endGameIncrease();
        if(Robot.climber.endGameToggle >= 2){
            //climb
            System.out.println("kachow 2.0");
        }
    }

    @Override
    protected boolean isFinished() {return true;}

    @Override
    protected void end(){}

}

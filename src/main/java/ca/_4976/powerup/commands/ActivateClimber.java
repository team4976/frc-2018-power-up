//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

//When run changes the elevator gear as a toggle(high, low)
public class ActivateClimber extends ListenableCommand{

    @Override
    protected void initialize(){
        System.out.println("Elevator should be running at 100%");
        Robot.climber.activateClimber();
    }

    @Override
    protected boolean isFinished() {return true;}
}

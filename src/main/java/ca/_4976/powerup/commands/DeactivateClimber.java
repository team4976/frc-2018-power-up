//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

//When run changes the elevator gear as a toggle(high, low)
public class DeactivateClimber extends ListenableCommand{

    @Override
    protected void initialize(){
        Robot.climber.deactivateClimber();
    }

    @Override
    protected boolean isFinished() {return true;}
}

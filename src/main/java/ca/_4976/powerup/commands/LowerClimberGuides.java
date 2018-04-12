package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class LowerClimberGuides extends ListenableCommand {

    @Override protected void initialize(){
        Robot.climber.guides();
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


}
package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class PresetScaleMid extends ListenableCommand {

    public PresetScaleMid(){
        willRunWhenDisabled();
    }

    @Override
    protected void initialize(){
        System.out.println("\nSTART: SCALE MID\n");
    }

    @Override
    protected void execute(){
        Robot.elevator.moveToMidScale();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.checkMidScale();
    }

    @Override
    protected void end(){
        Robot.elevator.stop();
    }
}


package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-15.
 */
public class PresetScaleLow extends ListenableCommand {

    public PresetScaleLow(){
        willRunWhenDisabled();
    }

    @Override
    protected void initialize(){
        System.out.println("\nSTART: SCALE LOW\n");
    }

    @Override
    protected void execute(){
        Robot.elevator.moveToLowScale();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.checkLowScale();
    }

    @Override
    protected void end(){
        Robot.elevator.stop();
    }
}


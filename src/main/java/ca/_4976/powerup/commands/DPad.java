package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-03-06.
 */
public class DPad extends ListenableCommand{
    public DPad(){requires(Robot.dPadStuff);}
    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        Robot.dPadStuff.getDpadPos();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

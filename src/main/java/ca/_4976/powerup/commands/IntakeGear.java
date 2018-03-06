package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

public class IntakeGear extends ListenableCommand{
    @Override
    protected void initialize(){
        Robot.cubeHandler.intakeGear();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    @Override
    protected void end(){new GearCurrent().start();}
}

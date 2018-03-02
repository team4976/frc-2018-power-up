package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-17.
 */
public class Arm45 extends ListenableCommand{

    @Override
    protected void execute(){
        System.out.println("Arm 45 ran");
        Robot.linkArm.moveArm45();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.linkArm.checkArm45();
    }

    @Override
    protected void end(){
        Robot.linkArm.setHoldingSpeed();
    }
}

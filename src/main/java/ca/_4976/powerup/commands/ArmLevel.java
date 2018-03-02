package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ArmLevel extends ListenableCommand {

    @Override
    protected void execute(){
        System.out.println("Arm level ran");
        Robot.linkArm.moveArmLevel();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.linkArm.checkArmLevel();
    }

    @Override
    protected void end(){
        Robot.linkArm.setHoldingSpeed();
    }
}

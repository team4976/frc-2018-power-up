package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ArmLevel extends ListenableCommand {

    public ArmLevel(){
        requires(Robot.linkArm);
    }

    @Override
    protected void initialize(){
        Robot.linkArm.moveArmTarget(Robot.linkArm.armLevelValue - 500);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs(false) || Robot.linkArm.testArmInput() || Robot.linkArm.checkArmTarget();
    }

    @Override
    protected void end(){
        System.out.println("Ground ended");
        Robot.linkArm.resetArmFlags();
        Robot.linkArm.setHoldingSpeed();
    }
}

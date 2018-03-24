package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ArmTarget extends ListenableCommand {

    private double target;

    public ArmTarget(double target){
        requires(Robot.linkArm);
        this.target = target;
    }

    @Override
    protected void initialize(){
        Robot.linkArm.moveArmTarget(target);
        System.out.println("STARTING");
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs(false) || Robot.linkArm.testArmInput() || Robot.linkArm.checkArmTarget();
    }

    @Override
    protected void end(){
        Robot.linkArm.resetArmFlags();
        Robot.linkArm.setHoldingSpeed();
    }
}

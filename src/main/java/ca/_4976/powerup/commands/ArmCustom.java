package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ArmCustom extends ListenableCommand{

    public double encValue;

    public ArmCustom(double encValue){
        this.encValue = encValue;
    }

    @Override
    protected void execute(){
        System.out.println("Arm custom ran");
        Robot.linkArm.moveCustom(encValue);
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.linkArm.checkCustom();
    }

    @Override
    protected void end(){
        Robot.linkArm.setHoldingSpeed();
    }
}

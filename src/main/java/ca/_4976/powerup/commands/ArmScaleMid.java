package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ArmScaleMid extends ListenableCommand{

    @Override
    protected void execute(){
        System.out.println("Arm high ran");
        Robot.linkArm.moveArmMid();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.linkArm.checkArmMid();
    }

    @Override
    protected void end(){
        Robot.linkArm.setHoldingSpeed();
        for(int i = 0; i < 100; i++){
            System.out.println("Holding speed ran: Motor at: "
                    + Robot.linkArm.armMotor.getMotorOutputPercent());
        }
    }
}

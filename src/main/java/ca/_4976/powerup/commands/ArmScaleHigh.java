package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ArmScaleHigh extends ListenableCommand{

    @Override
    protected void initialize(){
        Robot.linkArm.moveArmHigh();
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.linkArm.checkArmHigh();
    }


}

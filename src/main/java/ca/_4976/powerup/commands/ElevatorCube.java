package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ElevatorCube extends ListenableCommand {

    @Override
    protected void initialize(){
        Robot.elevator.moveToDefault();
        Robot.elevator.presetEnabled = true;
        Robot.elevator.defaultStarted = true;
    }

    @Override
    protected boolean isFinished() {
        return Robot.elevator.testInputs() || Robot.elevator.checkDefault();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.defaultStarted = false;
        Robot.elevator.stop();
    }
}

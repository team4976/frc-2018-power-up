package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ElevatorCube extends ListenableCommand {

    private int executeCount = 0;

    @Override
    protected void initialize(){
        Robot.elevator.moveToDefault();
        Robot.elevator.presetEnabled = true;
    }

    @Override
    protected void execute(){
        executeCount++;
    }

    @Override
    protected boolean isFinished() {
        System.out.println("Cube count: " + executeCount);
        return Robot.elevator.testInputs() || Robot.elevator.checkDefault();
    }

    @Override
    protected void end(){
        Robot.elevator.presetEnabled= false;
        Robot.elevator.stop();
    }
}

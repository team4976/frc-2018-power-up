package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//the operator functions to grab and release the cube

public class OperatorIntakeEject extends ListenableCommand {

    @Override
    protected void execute() {
        System.out.println("executing the intake ejection");
        //if LT pushed down release the cube
        if (Robot.oi.operator.getRawAxis(2)>0.75)Robot.cubeHandler.release();
        //if RT pushed grab cube
        else if (Robot.oi.operator.getRawAxis(3)>0.75)Robot.cubeHandler.grab();
        //if nothing stop spinning motors
        else Robot.cubeHandler.stop();
    }

    @Override

    protected boolean isFinished() {
        return false;
    }
}

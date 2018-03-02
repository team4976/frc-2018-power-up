package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;

//will eject the gear while B is being held

public class DriverEject extends ListenableCommand{
    @Override
    protected void initialize() {
        Robot.cubeHandler.runIntakeReverse = true;
        Robot.cubeHandler.runIntakeForwards = false;
    }


    @Override
    public void execute() {
        Robot.cubeHandler.release();
    }

    @Override protected boolean isFinished() {
        return Robot.cubeHandler.runIntakeReverse;
    }

}

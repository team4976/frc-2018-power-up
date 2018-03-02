package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.CubeHandler;

//main controller grabbing the cube

public class DriverIntake extends ListenableCommand {

    @Override
    protected void initialize() {
        Robot.cubeHandler.runIntakeForwards = true;
        Robot.cubeHandler.runIntakeReverse = false;
    }

    @Override
    protected void execute() {
        Robot.cubeHandler.grab();
    }

    @Override
    protected boolean isFinished() {
        return Robot.cubeHandler.runIntakeForwards;
    }
}


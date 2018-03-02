package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//stops the motors from turning after the B button has been released
public class DriverStop extends ListenableCommand {

    @Override
    protected void initialize() {
        Robot.cubeHandler.runIntakeForwards = false;
        Robot.cubeHandler.runIntakeReverse = false;
    }

    @Override
    protected void execute() {
        Robot.cubeHandler.stop();
    }
    @Override
    protected boolean isFinished() {
        return true;
    }
}

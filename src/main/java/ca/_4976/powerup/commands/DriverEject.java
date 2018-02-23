package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Drive;
import edu.wpi.first.wpilibj.command.Command;

//will eject the gear while B is being held

public class DriverEject extends ListenableCommand{
    @Override
    public void execute() {
        System.out.println("executing the ejection");
        Robot.cubeHandler.release();
       //CHANGE ID AFTER
    }
    @Override protected boolean isFinished() {
        return true;
    }

}

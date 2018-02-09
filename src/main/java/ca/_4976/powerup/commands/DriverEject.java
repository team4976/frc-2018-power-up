package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

//will eject the gear while B is being held

public class DriverEject extends Command{

    @Override
    public void execute() {
        System.out.println("executing the ejection");
        Robot.cubeHandler.release();
    }

    @Override protected boolean isFinished() {
        return true;
    }
//
//    @Override protected void end(){
//
//    }
}

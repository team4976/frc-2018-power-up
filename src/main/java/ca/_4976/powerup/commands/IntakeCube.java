package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Created by 4976 on 2018-03-09.
 */
public class IntakeCube extends Command {

    @Override
    protected void initialize(){
<<<<<<< HEAD:src/main/java/ca/_4976/powerup/commands/cubeSecondGear.java
      //  Robot.cubeHandler.gearSwitch();
=======
            Robot.cubeHandler.spinFast();
>>>>>>> 5cec23f1bd53b148018aca87683fce692676b8f4:src/main/java/ca/_4976/powerup/commands/IntakeCube.java
    }

    @Override
    protected boolean isFinished(){
        return true;
    }

    @Override
    protected void end(){}
}

package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCube extends CommandGroup {

    public AutoCube(){
        addSequential(new ArmCube());
        addSequential(new CancelCubeCube());
    }
}

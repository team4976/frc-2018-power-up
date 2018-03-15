package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class GroundPreset extends CommandGroup {

    public GroundPreset(){
        addParallel(new ElevatorGround());
        addParallel(new ArmTarget(Robot.linkArm.armLevelValue));
    }
}

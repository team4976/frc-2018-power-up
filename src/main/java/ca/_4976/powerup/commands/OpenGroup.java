package ca._4976.powerup.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OpenGroup extends CommandGroup {
    public OpenGroup(){
        addSequential(new IntakeCube());
        addSequential(new OpenClaw(), 0.5);
        addSequential(new GrabSwitch());
    }
}

package ca._4976.powerup.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by 4976 on 2018-04-19.
 */
public class CloseGroup extends CommandGroup {
    public CloseGroup() {
        addSequential(new CloseClaw());
        addSequential(new StartRumble());
        addSequential(new SpinFast(), 0.5);
        addSequential(new SpinSlow());
        addSequential(new StopRumble());
    }
}

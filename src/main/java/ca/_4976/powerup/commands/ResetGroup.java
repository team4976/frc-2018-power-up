package ca._4976.powerup.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ResetGroup extends CommandGroup {

    public ResetGroup(){
        addParallel(new ElevatorReset());
        addParallel(new ArmMinimum());
    }
}

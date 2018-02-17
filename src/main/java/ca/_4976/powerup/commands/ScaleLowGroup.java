package ca._4976.powerup.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by 4976 on 2018-02-17.
 */
public class ScaleLowGroup extends CommandGroup{

    public ScaleLowGroup(){

        addParallel(new ElevatorScaleLow());
        addParallel(new ArmDefault());
    }
}

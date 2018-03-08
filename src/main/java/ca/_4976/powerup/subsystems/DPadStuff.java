package ca._4976.powerup.subsystems;

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.DPad;
import ca._4976.powerup.commands.ElevatorScaleLow;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Created by 4976 on 2018-03-06.
 */
public class DPadStuff extends Subsystem  {

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DPad());
    }

    public void getDpadPos(){
        if (Robot.oi.operator.getPOV() == 0){
            new ElevatorScaleLow().start();
            System.out.println("up");
        }

        else if (Robot.oi.operator.getPOV() == 90){
            System.out.println("right");
        }

        else if (Robot.oi.operator.getPOV() == 180){
            System.out.println("down");
        }

        else if (Robot.oi.operator.getPOV() == 270){
            System.out.println("left");
        }
    }
}

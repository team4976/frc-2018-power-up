package ca._4976.powerup.subsystems;

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.Arm30;
import ca._4976.powerup.commands.ArmCustom;
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

            new Arm30().start();

            System.out.println("up");
        }

        else if (Robot.oi.operator.getPOV() == 90){
            
            new ArmCustom(100000);

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

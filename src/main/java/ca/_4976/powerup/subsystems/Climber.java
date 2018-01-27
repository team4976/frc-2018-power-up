package ca._4976.powerup.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    DoubleSolenoid climbingShift = new DoubleSolenoid(2,3);

    public void run(){}

    public void changeGear(){
        //Shift from high to low and from low to high
    }

    @Override
    protected void initDefaultCommand() {

    }
}

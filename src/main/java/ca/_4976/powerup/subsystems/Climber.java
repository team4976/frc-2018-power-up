package ca._4976.powerup.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
    //Jack, Quoc, Charlotte, Corey

    //Solenoid used to change the elevator from high to low gear and vice-versa
    DoubleSolenoid climbingShift = new DoubleSolenoid(2,3);

    public void run(){}

    //When the start button on the operator controller is pressed change the elevator gear from low to high and vice-versa
    public void changeGear(){

    }

    @Override
    protected void initDefaultCommand() {

    }
}

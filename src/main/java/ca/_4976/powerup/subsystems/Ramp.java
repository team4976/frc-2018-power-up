package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ramp extends Subsystem{

    TalonSRX rampMotor = new TalonSRX(5);
    DoubleSolenoid solenoidRamp = new DoubleSolenoid(1,2);

    public void end(){}

    public void deployRamp(){
        //Fire solenoid
    }

    public void rampUp(){
        //Run motors
    }

    public void rampDown(){
        //Run motors
    }

    @Override
    protected void initDefaultCommand() {

    }
}

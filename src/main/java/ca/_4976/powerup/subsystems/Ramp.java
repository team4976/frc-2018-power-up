package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ramp extends Subsystem{

    TalonSRX rampMotor = new TalonSRX(5);

    public void end(){}

    public void deployRamp(){}

    public void rampUp(){}

    public void rampDown(){}

    @Override
    protected void initDefaultCommand() {
  
    }
}

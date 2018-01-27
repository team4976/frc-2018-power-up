package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class LinkArm extends Subsystem implements Runnable, Sendable {

    private final TalonSRX armMotor = new TalonSRX(4);

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void run() {

    }

    public void raiseAngle(){

    }

    public void lowerAngle(){

    }

    public void setAngle(int angle){

    }

    public double getAngle(){
        return 0;
    }
}

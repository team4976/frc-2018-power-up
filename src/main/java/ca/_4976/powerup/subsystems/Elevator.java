package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class Elevator extends Subsystem implements Runnable, Sendable{

    private TalonSRX talon1 = new TalonSRX(0);
    public boolean end = false;

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void run() {
        //CODE GOES HERE


    }

    public void moveUp(){
        talon1.set(ControlMode.PercentOutput, 0.5);
    }

    public void moveDown(){
        talon1.set(ControlMode.PercentOutput, - 0.5);
    }
}

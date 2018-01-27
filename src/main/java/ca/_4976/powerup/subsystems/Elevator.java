package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class Elevator extends Subsystem implements Runnable, Sendable{

    private final TalonSRX liftMotor = new TalonSRX(2);
    private final TalonSRX liftSlave = new TalonSRX(3);

    public boolean end = false;

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void run() {

    }

    public double getHeight(){
        return 0;
    }

    public void setHeight(int height){

    }

    public void moveUp(){

    }

    public void moveDown(){

    }

    public void groundPS(){

    }

    public void switchPS(){

    }

    public void scaleLowPS(){

    }

    public void scaleMidPS(){

    }

    public void scaleHighPS(){

    }
}

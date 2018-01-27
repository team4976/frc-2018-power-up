package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class Elevator extends Subsystem implements Runnable, Sendable{

    private final TalonSRX liftMotor = new TalonSRX(2);
    private final TalonSRX liftSlave = new TalonSRX(3);

    private final DigitalInput limitSwitchMax = new DigitalInput(4); //top switch normally held closed
    private final DigitalInput limitSwitchMin = new DigitalInput(5); //bottom switch normally held open

    //private final Encoder liftEnc = new Encoder(); work on 1-port enc

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

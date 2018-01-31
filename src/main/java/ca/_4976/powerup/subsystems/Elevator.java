package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;


public final class Elevator extends PIDSubsystem {

    //Main motor for moving elevator up and down
    private final TalonSRX elevMotor = new TalonSRX(2);

    //Elevator slaved to main motor
    private final TalonSRX elevSlave = new TalonSRX(3);

    //Operator Elevator Left JoyStick
    private final Joystick leftOperatorJoy = new Joystick(0);


    //Driver Elevator Right JoyStick




    //Limit switch near top of the first stage of the elevator. Switch normally held closed
    private final DigitalInput limitSwitchMax = new DigitalInput(4);

    //Limit switch near bottom of the first stage of the elevator. Switch normally held open
    private final DigitalInput limitSwitchMin = new DigitalInput(5);

    //Encoder on the elevator motors
    private final Encoder elevEnc = new Encoder(4,5);

    //Height variable for measurements
    public double elevHeight = 0;

    public Elevator(String name, double p, double i, double d) {
        super(name, p, i, d);
        //super.setSetpoint();
    }


    @Override
    protected void initDefaultCommand() {
        elevSlave.set(ControlMode.Follower, 2);
    }


    @Override
    public void run() {
        //Use PID to move elevator to setpoint


    }

    @Override
    public void setSetpoint(double setpoint) {

    }

    //Reads encoders to return current height of the elevator with reference to it's zero point
    public double getHeight(){
        return 0;
    }

    //Uses PID control to set a height using encoder values -> moves to set height
    public void setHeight(double height){

    }

    public void ElevatorControl(){
        elevMotor.set(ControlMode.PercentOutput, leftOperatorJoy.getY());
        double elevOut = 0;

        //UP

        //Stop
        if(Math.abs(leftOperatorJoy.getY()) < 0.05 || Math.abs(leftOperatorJoy.getY()) > -0.05){
            elevMotor.set(ControlMode.PercentOutput, 0);
        }
        //Less that 50% pressed
        else if(Math.abs(leftOperatorJoy.getY()) >= -0.5){
            elevOut = (leftOperatorJoy.getY() * 0.4);
        }
        //Joystick > 50%
        else{
            elevOut = (leftOperatorJoy.getY() * 0.8) + 0.2;
        }

        //DOWN

        //Less that 50% pressed
        else if(Math.abs(leftOperatorJoy.getY()) >= -0.5){
            elevOut = (leftOperatorJoy.getY() * 0.4);
        }
        //Joystick > 50%
        else{
            elevOut = (leftOperatorJoy.getY() * 0.8) + 0.2;
        }

            elevMotor.set(ControlMode.PercentOutput, elevOut);
    }

    //Decreases setpoint height
    public void moveDown(){

    }

    //Sets setpoint to ground level preset
    public void groundPS(){

    }

    //Sets setpoint to switch level preset
    public void switchPS(){

    }

    //Sets setpoint to the low scale level preset
    public void scaleLowPS(){

    }

    //Sets setpoint to the mid scale level preset
    public void scaleMidPS(){

    }

    //Sets setpoint to the highest scale level preset
    //May or not be max elevator height, depending on whether linkarm is necessary to reach top of scale at full height
    public void scaleHighPS(){

    }

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    @Override
    protected void usePIDOutput(double output) {

    }
}

package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.MoveArm;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

public final class LinkArm extends Subsystem implements Sendable {

    //Motor inside linkarm carriage - raises/lowers arm
    private final TalonSRX armMotor = new TalonSRX(4);

    private double motorSpeed = 0.5;
    private double armHighValue;
    private double armMidValue = 3978;
    private double armResetValue;
    private double armMinValue;

    public LinkArm(){
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveArm());
    }

    //move linkage arm
    public void moveLinkArm(){

        System.out.println("Arm  encoder: " + getArmEncoderValue());
        double armOut = Robot.oi.operator.getRawAxis(5);

        //dead zone
        if (Math.abs(armOut) <= 0.03) {
            armMotor.set(ControlMode.PercentOutput, 0);
        }

        else {
            armMotor.set(ControlMode.PercentOutput, armOut);
        }
    }

    public double getArmEncoderValue(){
        double value = armMotor.getSensorCollection().getQuadraturePosition();

        //System.out.println("Arm encoder: " + value);
        return -value;
    }

    public void resetArmEncoder(){
        armMotor.getSensorCollection().setQuadraturePosition(0,0);
    }


    /**
     * LinkArm presets and accompanying check methods
     */
    public void moveArmHigh(){


        if(getArmEncoderValue() > armHighValue){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armHighValue){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmHigh(){
        
        if(getArmEncoderValue() >= (armHighValue - 200) && getArmEncoderValue() <= (armHighValue + 200)){
            return true;
        }

        else {
            return false;
        }
    }

    public void moveArmMid(){

        System.out.println("Move arm mid");

        if(getArmEncoderValue() > armMidValue){

            System.out.println("MID UP");
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armMidValue){

            System.out.println("MID DOWN");
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmMid(){

        if(getArmEncoderValue() >= (armMidValue - 200) && getArmEncoderValue() <= (armMidValue + 200)){

            System.out.println("Arm mid reached");
            return true;
        }

        else {
            return false;
        }
    }

    public void moveArmReset(){
        if(getArmEncoderValue() > armResetValue){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armResetValue){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmReset(){

        if(getArmEncoderValue() >= (armResetValue - 200) && getArmEncoderValue() <= (armResetValue + 200)){

            return true;
        }

        else {
            return false;
        }
    }

    public void moveArmMinimum(){


        if(getArmEncoderValue() > armMinValue){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armMinValue){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmMinimum(){

        if(getArmEncoderValue() >= (armMinValue - 200) && getArmEncoderValue() <= (armMinValue + 200)){

            return true;
        }

        else {
            return false;
        }
    }
}

package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.MoveArm;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

public final class LinkArm extends Subsystem implements Sendable {

    //Motor inside linkarm carriage - raises/lowers arm
    public final TalonSRX armMotor = new TalonSRX(4);

    private double motorSpeed = 0.5;
    private double holdingPower = -0.1; //compensate for reversal of motor

    private double armHighValue;
    private double arm30Value = 2445;
    private double armMaxValue = 4800;
    private double arm45Value = 2700;
    private double armResetValue = 0;
    private double armDefault2Value = -2081;

    private boolean holdSpeedSet = false;

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
        double motorOut;

        //dead zone
        if (Math.abs(armOut) <= 0.12) {
            if(holdSpeedSet) {
                motorOut = holdingPower;
            }

            else {
                motorOut = 0;
            }
        }

        else {
            holdSpeedSet = false;
            System.out.println("Arm output: " + armMotor.getMotorOutputPercent());
            motorOut = armOut;
        }

        armMotor.set(ControlMode.PercentOutput, motorOut);


    }

    public void setHoldingSpeed(){
        armMotor.set(ControlMode.PercentOutput, holdingPower);
        holdSpeedSet = true;
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
            //System.out.println("Arm encoder: " + getArmEncoderValue());
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armHighValue){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmHigh(){
        
        if(getArmEncoderValue() >= (armHighValue - 200) && getArmEncoderValue() <= (armHighValue + 400)){
            return true;
        }

        else {
            return false;
        }
    }

    public void moveArmMid(){

        System.out.println("Move arm mid");

        if(getArmEncoderValue() > arm45Value){

            System.out.println("MID UP");
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < arm45Value){

            System.out.println("MID DOWN");
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmMid(){

        if(getArmEncoderValue() >= (arm45Value - 200) && getArmEncoderValue() <= (arm45Value + 200)){

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

        if(getArmEncoderValue() <= (armResetValue + 200)){

            return true;
        }

        else {
            return false;
        }
    }

    public void moveArmMinimum(){


        if(getArmEncoderValue() > armDefault2Value){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armDefault2Value){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmMinimum(){

        if(getArmEncoderValue() >= (armDefault2Value - 200) && getArmEncoderValue() <= (armDefault2Value + 200)){

            return true;
        }

        else {
            return false;
        }
    }
}

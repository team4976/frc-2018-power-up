package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.MoveArm;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

public final class LinkArm extends Subsystem implements Sendable {

    //Motor inside linkarm carriage - raises/lowers arm
    public final TalonSRX armMotor = new TalonSRX(4);

    //Motor values
    private double motorSpeed = 0.4;
    private double armSpeedMultiplier;
    private final double armConstSpeed = 0.65;
    private double holdingPower = -0.1; //compensate for reversal of motor

    private final DigitalInput armSwitchMax = new DigitalInput(8);
    private final DigitalInput armSwitchMin = new DigitalInput(9);

    //Preset values
    private double offset = 3700;
    private double armHighValue = 3700 - offset; //0
    private double arm45Value = 2700 - offset; //-1000
    private double arm30Value = 2445 - offset; //-1255
    private double armLevelValue = 0 - offset; //-3700
    private double armDefault2Value = -2100 - offset; //-5800

    public boolean armPresetUp = false;
    public boolean armPresetDown = false;

    //Preset tolerance
    private double tolerance;
    private double target;

    public LinkArm(){
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        tolerance = 250;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveArm());
    }

    //move linkage arm
    public void moveLinkArm(){

        double deadRange = 0.15;

        boolean maxFlag = !armSwitchMax.get();
        boolean minFlag = !armSwitchMin.get();

//        System.out.println("Arm encoder: " + getArmEncoderValue());
//        System.out.println("Arm max: " + maxFlag);
//        System.out.println("Arm min: " + minFlag);

        double armInput = Robot.oi.operator.getRawAxis(5);
        double motorOut;

        //Reset encoder
        if(maxFlag){
            resetArmEncoder();
        }

        //Dead zone
        if (Math.abs(armInput) <= deadRange) {
            motorOut = holdingPower;
        }

        //Limit switches
        if(maxFlag && armInput <= 0 || minFlag && armInput >= 0){
            motorOut = 0;
        }


        //Normal
        else {
            //Downward speed adjustment
            if(armInput > 0){
                motorOut = 0.6 * armInput;
            }

            else{
                motorOut = armInput;
            }
        }


        //Arm slow bands
        if((//Robot.elevator.getHeight() < 750 &&
                getArmEncoderValue() < armLevelValue //2.838 * Robot.elevator.getHeight()
                        - offset + 400)) {
            armSpeedMultiplier = 0.3;
        }

        if( getArmEncoderValue() > armHighValue - 400){
            armSpeedMultiplier = 0.3;
        }

        else{
            armSpeedMultiplier = armConstSpeed;
        }

        armMotor.set(ControlMode.PercentOutput, motorOut * armSpeedMultiplier);


    }

    public void setHoldingSpeed(){
        armMotor.set(ControlMode.PercentOutput, holdingPower);
    }


    /**
     * Gets the negative value of the linkage arm encoder
     *
     * @return -arm encoder value
     */
    public double getArmEncoderValue(){
        return -(armMotor.getSensorCollection().getQuadraturePosition());
    }


    /**
     * Resets the arm encoder to its 0 position
     */
    public void resetArmEncoder(){
        armMotor.getSensorCollection().setQuadraturePosition(0,0);
    }


    /**
     * Reset arm preset flags
     */
    public void resetArmFlags(){
        if(armPresetUp){
            armPresetUp = false;
        }

        if(armPresetDown){
            armPresetDown = false;
        }
    }


    /**
     * LinkArm input test
     */
    public boolean testArmInput(){

        double deadRange = 0.15;
        double armInput = Robot.oi.operator.getRawAxis(5);

        boolean maxFlag = !armSwitchMax.get();
        boolean minFlag = !armSwitchMin.get();

        if(maxFlag && armPresetUp || minFlag && armPresetDown){
            return true;
        }

        else if (Math.abs(armInput) <= deadRange) {
            return false;
        }

        else if (Math.abs(armInput) > deadRange) {
            return true;
        }

        else {
            return false;
        }
    }


    
    
    
    
    
    ///////////////////////////////////


    /**
     * CUSTOM LINKARM PRESET - VERY EXPERIMENTAL
     */
    public void moveArmTarget(double target){

        this.target = target;
        
        if(getArmEncoderValue() > target){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
            armPresetDown = true;
        }

        else if(getArmEncoderValue() < target){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
            armPresetUp = true;
        }
    }

    public boolean checkArmTarget(){

        if(getArmEncoderValue() >= target - (2 * tolerance) || getArmEncoderValue() <= target + (2 * tolerance)){

            if(armPresetUp){
                armMotor.set(ControlMode.PercentOutput, -0.4);
            }

            else if(armPresetDown){
                armMotor.set(ControlMode.PercentOutput, 0.3);
            }
        }
        
        return getArmEncoderValue() >= (arm30Value - tolerance) && getArmEncoderValue() <= (arm30Value + tolerance);
    }
    
    
    
    


    
    
    
    
    
    //////////////////////////////
    
    
    
    
    
    
    
    /**
     * LinkArm presets and accompanying check methods
     */
    public void moveArm30(){

        if(getArmEncoderValue() > arm30Value){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
            armPresetDown = true;
        }

        else if(getArmEncoderValue() < arm30Value){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
            armPresetUp = true;
        }
    }

    public boolean checkArm30(){

        if(getArmEncoderValue() >= arm30Value - (2 * tolerance) || getArmEncoderValue() <= arm30Value + (2 * tolerance)){

            if(armPresetUp){
                armMotor.set(ControlMode.PercentOutput, -0.4);
            }

            else if(armPresetDown){
                armMotor.set(ControlMode.PercentOutput, 0.3);
            }
        }
        
        return getArmEncoderValue() >= (arm30Value - tolerance) && getArmEncoderValue() <= (arm30Value + tolerance);
    }

    /**
     * 45
     */
    public void moveArm45(){

        if(getArmEncoderValue() > arm45Value){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < arm45Value){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArm45(){
        return getArmEncoderValue() >= (arm45Value - tolerance) && getArmEncoderValue() <= (arm45Value + tolerance);
    }

    /**
     * Level
     */
    public void moveArmLevel(){
        if(getArmEncoderValue() > armLevelValue){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armLevelValue){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmLevel(){
        return getArmEncoderValue() >= (armLevelValue - tolerance) && getArmEncoderValue() <= (armLevelValue + tolerance);
    }

    /**
     * Automatic motion after intaking cube
     */
    public void moveArmCube(){

        if(getArmEncoderValue() > armHighValue){
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
        }

        else if(getArmEncoderValue() < armHighValue){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
        }
    }

    public boolean checkArmCube(){
        return getArmEncoderValue() >= (armHighValue - tolerance) && getArmEncoderValue() <= (armHighValue + tolerance);
    }
}

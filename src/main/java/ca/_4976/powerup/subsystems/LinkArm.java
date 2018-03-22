package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.MoveArm;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

public final class LinkArm extends Subsystem implements Sendable {

    //Motor inside linkarm carriage - raises/lowers arm
    private final TalonSRX armMotor = new TalonSRX(4);

    //Motor values
    private double motorSpeed = 0.8;
    private double armSpeedMultiplier;
    private final double armConstSpeed = 0.8;
    private double holdingPower = -0.05; //compensate for reversal of motor

    private final DigitalInput armSwitchMax = new DigitalInput(8);
    private final DigitalInput armSwitchMin = new DigitalInput(9);

    //Preset values
    public double armHighValue = 0,
    arm45Value = -800,
    arm30Value = -1660,
    armLevelValue = -3850,
    armMinValue = -5800;

    private boolean armPresetUp = false;
    private boolean armPresetDown = false;
    private boolean slowSet = false;

    //Preset tolerance
    private double tolerance;
    private double target;

    public LinkArm(){
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        tolerance = 200;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveArm());
    }

    public void setHoldingSpeed(){
        armMotor.set(ControlMode.PercentOutput, holdingPower);
    }

    public boolean getArmMaxSwitch(){
        return !armSwitchMax.get();
    }

    public void setArmSpeed(double speed){
        armMotor.set(ControlMode.PercentOutput, speed);
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

        if(slowSet){
            slowSet = false;
        }
    }

    /**
     * Provides manual control over the linkage arm
     *
     * Motion limited proportional to elevator height past a certain point
     */
    public void moveLinkArm(){

        double deadRange = 0.15,
        elevatorHeight = Robot.elevator.getHeight(),
        dynamicLimit,
        armInput = Robot.oi.operator.getRawAxis(5),
        motorOut;

        boolean elevatorLimitReached = Robot.elevator.getHeight() < 750,
        maxFlag = !armSwitchMax.get(),
        minFlag = !armSwitchMin.get(),
        deadFlag = false,
        multiSet = false;

//        System.out.println("\n\n\nArm encoder: " + getArmEncoderValue());
//        System.out.println("Arm max: " + maxFlag);
//        System.out.println("Arm min: " + minFlag);



        //Reset encoder
        if(maxFlag){
            resetArmEncoder();
//            System.out.println("ARM RESET - ENCODER AT: " + getArmEncoderValue());
        }

        //Dead zone
        if (Math.abs(armInput) <= deadRange) {
            setHoldingSpeed();
            deadFlag = true;
        }

        //Limit switches

        if(maxFlag && armInput <= 0 || minFlag && armInput >= 0){
            motorOut = 0;
        }

        //Normal
        else {
            //Downward speed adjustment
            if(armInput > 0){
                motorOut = 0.7 * armInput;
            }

            else{
                motorOut = armInput;
            }
        }



        //Arm slow and stop bands (below limit)
        if(elevatorLimitReached){

            dynamicLimit = elevatorHeight != 0
                    ? (-3.6503 * elevatorHeight) - 3450.1 //excel charted
                    : armLevelValue;

//            System.out.println("Control at: " + dynamicLimit);

            if(getArmEncoderValue() < dynamicLimit + 2000 && armInput > 0 && !deadFlag){

                if(getArmEncoderValue() <= dynamicLimit && elevatorHeight != 0){
                    System.out.println("Motor stopped at: " + getArmEncoderValue());
                    motorOut = 0;
                }

                else{
                    armSpeedMultiplier = Math.abs(((getArmEncoderValue() - dynamicLimit) / 10000) * 3);
                    multiSet = true;
                }
            }
        }

        //Arm slow bands (above limit)
        if(getArmEncoderValue() < armMinValue + 1000 && armInput > 0 && !deadFlag && !multiSet) {
            armSpeedMultiplier = 0.6;
        }

        else if(getArmEncoderValue() > armHighValue - 1500 && armInput < 0 && !deadFlag){
            armSpeedMultiplier = 0.6;
        }

        else if(!deadFlag && !multiSet){
            armSpeedMultiplier = armConstSpeed;
        }


        if(!deadFlag) {
            armMotor.set(ControlMode.PercentOutput, motorOut * armSpeedMultiplier);
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
            System.out.println("arm cancelled");
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
        
        if(getArmEncoderValue() > target) {
            armMotor.set(ControlMode.PercentOutput, motorSpeed);
            armPresetDown = true;
        }

        else if(getArmEncoderValue() < target){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
            armPresetUp = true;
        }
    }

    public boolean checkArmTarget(){
        double motorOut = 0;

        System.out.println("\n\nChecking for target at: " + target);
        System.out.println("Arm encoder during preset: " + getArmEncoderValue() + "\n\n");

        if(Math.abs(getArmEncoderValue() - target) < 1500){// && !slowSet) {

            if(armPresetUp){
                motorOut = -motorSpeed;
            }

            else if(armPresetDown){
                if(target < 0 - 500){
                    motorOut = motorSpeed;
                }

                else {
                    motorOut = 0.7 * motorSpeed;
                }
            }

            System.out.println("ARM SLOW");
            armSpeedMultiplier = Math.abs(((getArmEncoderValue() - target) / 10000) * 3);
            armMotor.set(ControlMode.PercentOutput, motorOut * armSpeedMultiplier);
////            slowSet = true;
        }

        return getArmEncoderValue() >= (target - tolerance) && getArmEncoderValue() <= (target);
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
            armPresetDown = true;
        }

        else if(getArmEncoderValue() < arm45Value){
            armMotor.set(ControlMode.PercentOutput, -motorSpeed);
            armPresetUp = true;
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

        if(getArmEncoderValue() >= armLevelValue - (2 * tolerance) || getArmEncoderValue() <= armLevelValue + (2 * tolerance)) {
            armMotor.set(ControlMode.PercentOutput, armMotor.getMotorOutputPercent() * 0.5);
        }

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

package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.ArmTarget;
import ca._4976.powerup.commands.MoveElevatorWithJoystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

/**
 * This is the second elevator branch, made for streamlining and bulletproofing
 * existing code, as well as adding extra functionality.
 */
public final class Elevator extends Subsystem implements Sendable {

    // Elevator motors
    private final WPI_TalonSRX elevMotorMain = new WPI_TalonSRX(2);
    private final VictorSPX elevSlave1 = new VictorSPX(3);
    private final WPI_TalonSRX elevSustainableFreeLegalUnionizedLaborer = new WPI_TalonSRX(8);

    // Encoder on elevator
    public final Encoder elevEncoder = new Encoder(4, 5);


    private final DigitalInput limitSwitchMax = new DigitalInput(6);
    private final DigitalInput limitSwitchMin = new DigitalInput(7);

    private final AnalogInput testSwitch = new AnalogInput(0);

    // Preset values
    private double presetOutput;
    private double tolerance;
    private double speedMultiplier;
    private final double normalSpeed = 1;
    private final double slowSpeed = 0.4;
    private final double holdingSpeed = 0.12;
    private double target;

    public static double elevMaxValue = 2300 / 2.057,
    scaleHighValue = 2100 / 2.057,
    scaleLowValue = 1220 / 2.057,
    limitValue = 750 /2.057,
    groundValue = 0;

    public boolean elevPresetUp = false;
    public boolean elevPresetDown = false;
    private boolean isShited = false;


    public Elevator() {

        elevSlave1.follow(elevMotorMain);
        elevSustainableFreeLegalUnionizedLaborer.follow(elevMotorMain);

        //Output value for presets
        presetOutput = normalSpeed;

        //Preset tolerance
        tolerance = 50;
    }



    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveElevatorWithJoystick());
    }

    /**
     * Reads encoders to return current height of the elevator with reference to it's zero point
     */
    public double getHeight() {
        return elevEncoder.getDistance();
    }

    public void resetEncoder(){
        elevEncoder.reset();
    }

    public void holdElevator(){
        elevMotorMain.set(ControlMode.PercentOutput, holdingSpeed);
    }

    public void setClimberShifted(boolean isShited){
        this.isShited = isShited;
    }

    public boolean getClimberShifted(){
        return isShited;
    }

    /**
     * Driver input overrides operator input. Only executes a movement initiated by the operator
     * when no input is detected from driver according to dead zone
     */
    public void moveElevator() {

//        System.out.println("Manual - elevator encoder: " + getHeight());

        double deadRange = 0.15,
        driverInput = -Robot.oi.driver.getRawAxis(5),
        operatorInput = -Robot.oi.operator.getRawAxis(1),
        oobInput = 0, // value used for out of bounds processing
        motorOut = holdingSpeed,
        armHeight = Robot.linkArm.getArmEncoderValue(),
        dynamicLimit,
        armTarget,
        armSpeedMultiplier;

        boolean maxFlag  = !limitSwitchMax.get(),
        minFlag = !limitSwitchMin.get(),
        elevatorLimitReached = getHeight() < limitValue / 2.057,
        deadZoneFlag = false,
        driverFlag = false,
        operatorFlag = false,
        multiSet = false;

        //Reset encoder at bottom
        if(minFlag){
            resetEncoder();
        }

        //Input processing
        if (Math.abs(driverInput) <= deadRange &&
                Math.abs(operatorInput) <= deadRange) {

            if(minFlag){
                motorOut = 0;
            }

            else if(getClimberShifted()){
                motorOut = 0;
            }

            else {
                motorOut = holdingSpeed;
            }

            deadZoneFlag = true;
            speedMultiplier = 1;
            multiSet = true;
        }

        else if (Math.abs(driverInput) > deadRange && !Robot.motion.isRunning()) {
            driverFlag = true;
            motorOut = driverInput;
        }

        else if (Math.abs(operatorInput) > deadRange && !Robot.motion.isRunning()) {
            operatorFlag = true;
            motorOut = operatorInput;
        }

        //Output processing
        if(driverFlag){
            oobInput = driverInput;
        }

        else if(operatorFlag){
            oobInput = operatorInput;
        }

        //Limit switches
        if((maxFlag && oobInput >= 0) || (minFlag && oobInput <= 0)){
            if(getClimberShifted()){
                motorOut = 0;
            }

            else {
                motorOut = holdingSpeed;
            }
            multiSet = true;
        }

        else if(!getClimberShifted() && !deadZoneFlag){

            if(oobInput < 0){
                motorOut = 0.7 * oobInput;
            }

            else {
                motorOut = oobInput;
            }
        }

        else if(!deadZoneFlag){
            motorOut = oobInput;
        }

        //TODO FIX ARM ENCODER

        /*//Elevator slow and stop bands (below limit)
        if(elevatorLimitReached){

            dynamicLimit = (armHeight + 3450.1)/-3.6503;
            armTarget = -3.6503 * getHeight() - 3450.1; //excel charted

            if(getHeight() < dynamicLimit + 300
                    && oobInput < 0
                    && !deadZoneFlag
                    && armHeight < -Robot.linkArm.armLevelValue
                    && getHeight() > 30)
            {

                speedMultiplier = normalSpeed * 0.5;
                multiSet = true;

                while(Robot.linkArm.getArmEncoderValue() < armTarget) {
                    if(oobInput > 0 || Robot.oi.
                    tor.getRawAxis(5) < 0){
                        break;
                    }

                    Robot.linkArm.setArmSpeed(-1);
                }

                Robot.linkArm.setHoldingSpeed();
            }
        }*/


        //Final output check
        if(deadZoneFlag || driverFlag || operatorFlag) {

            if((getHeight() > elevMaxValue - 175 && oobInput > 0) ||
                    (getHeight() < groundValue + 500  && oobInput < 0) && !multiSet && !getClimberShifted())
            {
                speedMultiplier = slowSpeed;
            }

            else if(!multiSet){
                speedMultiplier = normalSpeed;
            }

            elevMotorMain.set(ControlMode.PercentOutput, motorOut * speedMultiplier);
        }
    }


    //********************************************************************/


    /**
     * Input test
     *
     * Used to determine if manual control is present that should override a preset
     */
    public boolean testInputs(boolean affectedBySwitches){

        double deadRange = 0.15;
        double drInput = -Robot.oi.driver.getRawAxis(5);
        double opInput = -Robot.oi.operator.getRawAxis(1);

        boolean maxFlag = !limitSwitchMax.get();
        boolean minFlag = !limitSwitchMin.get();


        if(affectedBySwitches && ((maxFlag && elevPresetUp) || (minFlag && elevPresetDown))){
            return true;
        }

        else if (Math.abs(drInput) <= deadRange && Math.abs(opInput) <= deadRange) {
            return false;
        }

        else if (Math.abs(drInput) > deadRange) {
            return true;
        }

        else if (Math.abs(opInput) > deadRange) {
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * Reset motion flags
     */
    public void resetPresetFlags(){

        if(elevPresetUp){
            elevPresetUp = false;
        }

        if(elevPresetDown){
            elevPresetDown = false;
        }
    }


    /**
     * Target Preset
     */
    public void moveToTarget(double target){

        this.target = target;

        if(!elevPresetDown || !elevPresetUp) {

            if (getHeight() > target && limitSwitchMin.get()) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
                elevPresetDown = true;
            }

            else if (getHeight() < target && limitSwitchMax.get()) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
                elevPresetUp = true;
            }
        }
    }

    public boolean checkTarget(){

        double motorOut = 0;
        if(Math.abs(getHeight() - target) < 200){

            if(elevPresetUp){
                motorOut = slowSpeed;
            }

            else if(elevPresetDown){
                motorOut = -slowSpeed;
            }

            elevMotorMain.set(ControlMode.PercentOutput, motorOut * 0.6);
        }

        return getHeight() >= (target - tolerance) && getHeight() <= (target + tolerance);
    }

    /**
     * Ground
     *
     * Move and check method
     */
    public void moveToGround() {

        if(!elevPresetDown || !elevPresetUp) {

            if (getHeight() > groundValue && limitSwitchMin.get()) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
                elevPresetDown = true;
            }

            else if (getHeight() < groundValue && limitSwitchMax.get()) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
                elevPresetUp = true;
            }
        }
    }

    public boolean checkGround(){

        if(getHeight() >= groundValue - (2 * tolerance) || getHeight() <= groundValue + (2 * tolerance)){

            if(elevPresetUp){
                elevMotorMain.set(ControlMode.PercentOutput, 0.4);
            }

            else if(elevPresetDown){
                elevMotorMain.set(ControlMode.PercentOutput, -0.3);
            }
        }

        return getHeight() >= (groundValue - tolerance) && getHeight() <= groundValue + 5;
    }

    /**
     * Simply runs motors for use in Climber subsystem & commands
     */
    public void climb(){
        elevMotorMain.set(ControlMode.PercentOutput, -1);
    }

    public void down(){
        elevMotorMain.set(ControlMode.PercentOutput, 0.6);
    }

    /**
     * Take a wild guess
     */
    public void stop(){
        elevMotorMain.set(ControlMode.PercentOutput, 0);
    }
}


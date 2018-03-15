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

    // Preset values
    private double presetOutput;
    private double tolerance;
    private double speedMultiplier;
    private final double normalSpeed = 0.9;
    private final double slowSpeed = 0.4;
    private double target;

    public double elevMaxValue = 2300,
    scaleHighValue = 2100,
    scaleMidValue = 1800,
    scaleLowValue = 1700,
    defaultValue = 770,
    groundValue = 0;

    boolean scaleMidStarted;
    boolean scaleLowStarted;
    boolean switchStarted;
    boolean groundStarted;
    boolean defaultStarted;

    // Preset start/stop control booleans
    public boolean presetEnabled = false;

    public boolean elevPresetUp = false;
    public boolean elevPresetDown = false;

    public Elevator() {

        elevSlave1.follow(elevMotorMain);
        elevSustainableFreeLegalUnionizedLaborer.follow(elevMotorMain);

        //Output value for presets
        presetOutput = normalSpeed;

        //Preset tolerance
        tolerance = 50;
    }

    private final DigitalInput limitSwitchMax = new DigitalInput(6);
    private final DigitalInput limitSwitchMin = new DigitalInput(7);


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

    public boolean getSwitches(){
        return !limitSwitchMax.get() || !limitSwitchMin.get();
    }


    /**
     * Driver input overrides operator input. Only executes a movement initiated by the operator
     * when no input is detected from driver according to dead zone
     */
    public void moveElevator() {

        System.out.println("MANUAL: ELEVATOR ENCODER: " + getHeight());

        double deadRange = 0.15,
        driverInput = -Robot.oi.driver.getRawAxis(5),
        operatorInput = -Robot.oi.operator.getRawAxis(1),
        oobInput = 0, // value used for out of bounds processing
        motorOut = 0,
        armHeight = Robot.linkArm.getArmEncoderValue(),
        dynamicLimit,
        armTarget;

        boolean maxFlag  = !limitSwitchMax.get(),
        minFlag = !limitSwitchMin.get(),
        elevatorLimitReached = getHeight() < 750,
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

            deadZoneFlag = true;
            motorOut = 0;
        }

        else if (Math.abs(driverInput) > deadRange) {
            driverFlag = true;
            motorOut = driverInput;
        }

        else if (Math.abs(operatorInput) > deadRange) {
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
        if((maxFlag && oobInput >= 0) ||(minFlag && oobInput <= 0)){
            motorOut = 0;
        }


        //Elevator slow and stop bands (below limit)
        if(elevatorLimitReached){

            dynamicLimit = (armHeight + 3450.1)/-3.6503;
            armTarget = -3.6503 * getHeight() - 3450.1; //excel charted

            if(getHeight() < dynamicLimit + 300
                    && oobInput < 0
                    && !deadZoneFlag
                    && armHeight < -3660
                    && getHeight() != 0){

                System.out.println("DYNAMIC ARM MOVE STARTED AT: " + armTarget);

                //TODO GEARBOX ON ARM FIX
                /*while(Robot.linkArm.getArmEncoderValue() < armTarget) {
                    Robot.linkArm.setArmSpeed(-0.6);
                }

                Robot.linkArm.setHoldingSpeed();*/
            }
        }


        //Final output check
        if(deadZoneFlag || driverFlag || operatorFlag) {

            if((getHeight() > elevMaxValue - 300 && oobInput > 0) ||
                    (getHeight() < groundValue + 300  && oobInput < 0) && !multiSet)
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
    public boolean testInputs(){

        double deadRange = 0.15;
        double drInput = -Robot.oi.driver.getRawAxis(5);
        double opInput = -Robot.oi.operator.getRawAxis(1);

        boolean maxFlag = !limitSwitchMax.get();
        boolean minFlag = !limitSwitchMin.get();

        System.out.println("\n\nELEVATOR INPUT");
        System.out.println("Max switch: " + maxFlag);
        System.out.println("Min switch: " + minFlag + "\n\n");


        if((maxFlag && elevPresetUp) || (minFlag && elevPresetDown)){
            System.out.println("LIMIT CANCELLED PRESET");
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


    //********************************************************************/


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











    //////////////////////////////////








    /**
     * CUSTOM PRESET - VERY EXPERIMENTAL
     */
    public void moveToTarget(double target){

        this.target = target;

        if(!elevPresetDown || !elevPresetUp) {

            if (getHeight() > target
                    && limitSwitchMin.get()
                    ) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
                elevPresetDown = true;
            }

            else if (getHeight() < target
                    && limitSwitchMax.get()
                    ) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
                elevPresetUp = true;
            }
        }
    }

    public boolean checkTarget(){

        if(getHeight() >= target - (3 * tolerance) || getHeight() <= target + (3 * tolerance)){

            if(elevPresetUp){
                System.out.println("UP");
                elevMotorMain.set(ControlMode.PercentOutput, 0.4);
            }

            else if(elevPresetDown){
                System.out.println("DOWN");
                elevMotorMain.set(ControlMode.PercentOutput, -0.3);
            }
        }

        return getHeight() >= (target - tolerance) && getHeight() <= (target + tolerance);
    }






    //////////////////////////////////////////////









    /**
     * High scale
     *
     * Move and check method
     */
    public void moveToHighScale() {

        if(!elevPresetDown || !elevPresetUp) {
            if (getHeight() > scaleHighValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
                elevPresetDown = true;
            } else if (getHeight() < scaleHighValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
                elevPresetUp = true;
            }
        }
    }

    public boolean checkHighScale(){

        if(getHeight() >= scaleHighValue - (2 * tolerance) || getHeight() <= scaleHighValue + (2 * tolerance)){

            if(elevPresetUp){
                System.out.println("HIGH SLOW UP");
                elevMotorMain.set(ControlMode.PercentOutput, 0.4);
            }

            else if(elevPresetDown){
                System.out.println("HIGH SLOW DOWN");
                elevMotorMain.set(ControlMode.PercentOutput, -0.4);
            }
        }

        return getHeight() >= (scaleHighValue - tolerance) && getHeight() <= (scaleHighValue + tolerance);
    }


    //********************************************************************/


    /**
     * Mid scale
     *
     * Move and check method
     */
    public void moveToMidScale() {

        if(!elevPresetUp || !elevPresetDown){

            if (getHeight() > scaleMidValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
                elevPresetDown = true;
            }

            else if (getHeight() < scaleMidValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
                elevPresetUp = true;
            }
        }
    }

    public boolean checkMidScale(){
        return getHeight() >= (scaleMidValue - tolerance) && getHeight() < (scaleMidValue + tolerance);
    }


    //********************************************************************/


    /**
     * Low scale
     *
     * Move and check method
     */
    public void moveToLowScale() {

        if(!elevPresetDown || !elevPresetUp) {

            if (getHeight() > scaleLowValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
                elevPresetDown = true;
            }

            else if (getHeight() < scaleLowValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
                elevPresetUp = true;
            }
        }
    }

    public boolean checkLowScale(){
        return getHeight() >= (scaleLowValue - tolerance) && getHeight() <= (scaleLowValue + tolerance);
    }


    //********************************************************************/


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


    //********************************************************************/


    /**
     * Default - cube holding position
     *
     * Move and check method
     */
    public void moveToDefault() {

        if(!defaultStarted) {

            if (getHeight() > defaultValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
            }

            else if (getHeight() < defaultValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
            }
        }
    }

    public boolean checkDefault(){
        return getHeight() >= (defaultValue - tolerance) && getHeight() <= (defaultValue + tolerance);
    }


    //********************************************************************/


    /**
     * Simply runs motors for use in Climber subsystem & commands
     */
    public void climb(){
        elevMotorMain.set(ControlMode.PercentOutput, -0.5);
    }

    public void down(){
        elevMotorMain.set(ControlMode.PercentOutput, 0.5);
    }


    //********************************************************************/


    /**
     * Take a wild guess
     */
    public void stop(){
        elevMotorMain.set(ControlMode.PercentOutput, 0);
    }
}


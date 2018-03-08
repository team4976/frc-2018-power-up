package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.ArmCustom;
import ca._4976.powerup.commands.MoveElevatorWithJoystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This is the second elevator branch - used for proportional elevator/arm motion
 */
public final class Elevator extends Subsystem implements Sendable {

    // Elevator motors
    private final WPI_TalonSRX elevMotorMain = new WPI_TalonSRX(2);
    private final VictorSPX elevSlave1 = new VictorSPX(3);
    private final WPI_TalonSRX elevSustainableFreeLegalUnionizedLaborer = new WPI_TalonSRX(8);

    // Encoder on elevator
    public final Encoder elevEnc = new Encoder(4, 5);

    // Preset values
    private double presetOutput;
    private double tolerance;
    private double variableOutputMultiplier;
    private final double constOutputMultp = 1;

    private double elevMaxValue;
    private double scaleHighValue;
    private double scaleMidValue;
    private double scaleLowValue;
    private double switchValue;
    private double defaultValue;
    private double groundValue;

    // Preset start/stop control booleans
    public boolean presetEnabled = false;

    public boolean scaleHighStarted = false;
    public boolean scaleMidStarted = false;
    public boolean scaleLowStarted = false;
    public boolean switchStarted = false;
    public boolean defaultStarted = false;
    public boolean groundStarted = false;

    public Elevator() {

        elevSlave1.follow(elevMotorMain);
        elevSustainableFreeLegalUnionizedLaborer.follow(elevMotorMain);

        //Preset initial values
        elevMaxValue = 2300; //Real value will be higher
        scaleHighValue = 1700; // Real Value 2100
        scaleMidValue = 1500; // Real Value 1800
        scaleLowValue = 1220.5; // Real Value 1700
        switchValue = 0;
        defaultValue = 770;
        groundValue = 0; // Real Value

        //Output value for presets
        presetOutput = 0.5;

        //Preset tolerance
        tolerance = 50;
    }

    //Limit switch near top of the first stage of the elevator. Switch normally held open (high/true)
    private final DigitalInput limitSwitchMax = new DigitalInput(6);

    //Limit switch near bottom of the first stage of the elevator. Switch normally held open (high/true)
    private final DigitalInput limitSwitchMin = new DigitalInput(7); //REAL NUMBER


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveElevatorWithJoystick());
    }

    /**
     * Reads encoders to return current height of the elevator with reference to it's zero point
     */
    public double getHeight() {
        return elevEnc.getDistance();
    }

    public void resetEncoder(){
        elevEnc.reset();
    }


    /**
     * Driver input overrides operator input. Only executes a movement initiated by the operator
     * when no input is detected from driver according to dead zone
     */
    public void moveElevator() {


        double deadRange = 0.15;
        double driverInput = -Robot.oi.driver.getRawAxis(5);
        double operatorInput = -Robot.oi.operator.getRawAxis(1);
        double oobInput = 0; // value used for out of bounds processing
        double manualOut = 0;

        boolean maxFlag  = !limitSwitchMax.get();
        boolean minFlag = !limitSwitchMin.get();

        boolean deadZoneFlag = false;
        boolean driverFlag = false;
        boolean operatorFlag = false;


        System.out.println();
        System.out.println("Elevator encoder: " + elevEnc.getDistance());
        System.out.println("Max switch (6): " + maxFlag);
        System.out.println("Min switch (7): " + minFlag);
        System.out.println();

        //Reset encoder at bottom
        if(minFlag){
            resetEncoder();
        }

        //Input processing
        if (Math.abs(driverInput) <= deadRange &&
                Math.abs(operatorInput) <= deadRange &&
                !presetEnabled) {

            deadZoneFlag = true;
            manualOut = 0;
        }

        else if (Math.abs(driverInput) > deadRange) {
            driverFlag = true;
            manualOut = driverInput;
        }

        else if (Math.abs(operatorInput) > deadRange) {
            operatorFlag = true;
            manualOut = operatorInput;
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
            manualOut = 0;
        }

        //Final output check
        if(deadZoneFlag || driverFlag || operatorFlag) {
            if((getHeight() > elevMaxValue - 200) || (getHeight() < groundValue + 150)){
                variableOutputMultiplier = 0.3;
            }

            else{
                variableOutputMultiplier = constOutputMultp;
            }

            elevMotorMain.set(ControlMode.PercentOutput, manualOut * variableOutputMultiplier);

            //TODO - TEST THIS
            //Arm/Elevator proportional movement
            if(getHeight() < 740){
                new ArmCustom(-2.838 * getHeight()).start();
            }
        }
    }

    /**
     * Input test
     *
     * Used to determine if manual control is present that should override a preset
     */
    public boolean testInputs(){

        double deadRange = 0.15;
        double drInput = -Robot.oi.driver.getRawAxis(5);
        double opInput = -Robot.oi.operator.getRawAxis(1);

        System.out.println("Max switch: " + limitSwitchMax.get());
        System.out.println("Min switch: " + limitSwitchMin.get());
//        if(limitSwitchMax.get() || limitSwitchMin.get()){
//            return true;
//        }

        if (Math.abs(drInput) <= deadRange && Math.abs(opInput) <= deadRange) {
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
     * High scale
     *
     * Move and check method
     */
    public void moveToHighScale() {

        if(!scaleHighStarted) {

            if (getHeight() > scaleHighValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
            }

            else if (getHeight() < scaleHighValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
            }
        }
    }

    public boolean checkHighScale(){
        return getHeight() >= (scaleHighValue - tolerance) && getHeight() <= (scaleHighValue + tolerance);
    }


    /**
     * Mid scale
     *
     * Move and check method
     */
    public void moveToMidScale() {

        if(!scaleMidStarted) {

            if (getHeight() > scaleMidValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
            }

            else if (getHeight() < scaleMidValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
            }
        }
    }

    public boolean checkMidScale(){
        return getHeight() >= (scaleMidValue - tolerance) && getHeight() < (scaleMidValue + tolerance);
    }


    /**
     * Low scale
     *
     * Move and check method
     */
    public void moveToLowScale() {

        if(!scaleLowStarted) {

            if (getHeight() > scaleLowValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
            }

            else if (getHeight() < scaleLowValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
            }
        }
    }

    public boolean checkLowScale(){
        return getHeight() >= (scaleLowValue - tolerance) && getHeight() <= (scaleLowValue + tolerance);
    }


    /**
     * Switch
     *
     * Move and check method
     */
    public void moveToSwitch() {

        if(!switchStarted) {

            if (getHeight() > switchValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
            }

            else if (getHeight() < switchValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
            }
        }
    }

    public boolean checkSwitch(){
        return getHeight() >= (switchValue - tolerance) && getHeight() <= (switchValue + tolerance);
    }


    /**
     * Ground
     *
     * Move and check method
     */
    public void moveToGround() {

        if(!groundStarted) {

            if (getHeight() > groundValue) {
                elevMotorMain.set(ControlMode.PercentOutput, -presetOutput);
            }

            else if (getHeight() < groundValue) {
                elevMotorMain.set(ControlMode.PercentOutput, presetOutput);
            }
        }
    }

    public boolean checkGround(){
        return getHeight() <= (groundValue + tolerance);
    }


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

    /**
     * Simply runs motors for use in Climber subsystem & commands
     */
    public void climb(){
        elevMotorMain.set(ControlMode.PercentOutput, -0.5);
    }


    /**
     * Take a wild guess
     */
    public void stop(){
        elevMotorMain.set(ControlMode.PercentOutput, 0);
    }
}


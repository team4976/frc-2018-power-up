package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.ArmTarget;
import ca._4976.powerup.commands.MoveElevatorWithJoystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
    private final TalonSRX elevMotorMain = new TalonSRX(2);
    private final VictorSPX elevSlave1 = new VictorSPX(3);
    private final TalonSRX elevSustainableFreeLegalUnionizedLaborer = new TalonSRX(8);

    // Encoder on elevator
    public final Encoder elevEncoder = new Encoder(4, 5);

    private final DigitalInput limitSwitchMax = new DigitalInput(6);
    private final DigitalInput limitSwitchMin = new DigitalInput(7);

    // Processing values
    private double presetOutput;
    private double tolerance = 25;
    private double speedMultiplier;
    private final double normalSpeed = 1;
    private final double slowSpeed = 0.4;
    private final double holdingSpeed = 0.12;
    private double target;

    // Preset values
    public static double elevMaxValue = 2300 / 2.057,
    scaleHighValue = elevMaxValue,//2100 / 2.057,
    scaleLowValue = 1220 / 2.057,
    limitValue = 750 /2.057,
    groundValue = 0;

    // Preset control
    private boolean elevPresetUp = false;
    private boolean elevPresetDown = false;

    // Climber tracker
    private boolean isShifted = false;

    public Elevator() {

        elevSlave1.follow(elevMotorMain);
        elevSustainableFreeLegalUnionizedLaborer.follow(elevMotorMain);

        //Output value for presets
        presetOutput = normalSpeed;

        use(NetworkTableInstance.getDefault().getTable("Grabber"), it -> {

            NetworkTableEntry holdingSpeed = it.getEntry("Holding Power");
            NetworkTableEntry fastSpeed = it.getEntry("Fast Speed");
            NetworkTableEntry slowSpeed = it.getEntry("Slow Speed");
            NetworkTableEntry elevLimit = it.getEntry("Interlock Start Point");
            NetworkTableEntry presetTol = it.getEntry("Preset Tolerance");


        });
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

    public void setClimberShifted(boolean isShifted){
        this.isShifted = isShifted;
    }

    public boolean getClimberShifted(){
        return isShifted;
    }

    /**
     * Driver input overrides operator input. Only executes a movement initiated by the operator
     * when no input is detected from driver according to dead zone
     */
    public void moveElevator() {

//            System.out.println("MANUAL: ELEVATOR ENCODER: " + getHeight());
        System.out.println("Elevator encoder "+elevEncoder.getDistance());
        double deadRange = 0.15,
        driverInput = -Robot.oi.driver.getRawAxis(5),
        operatorInput = -Robot.oi.operator.getRawAxis(1),
        oobInput = 0, // value used for processing
        motorOut = holdingSpeed, //initialized to holding speed. when needed, just set multiplier to 1 and multiset to true
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
        multiplierSet = false;

        //Reset encoder at bottom
        if(minFlag){ resetEncoder(); }


        // Input reading
        if (Math.abs(driverInput) > deadRange) { driverFlag = true; }

        else if (Math.abs(operatorInput) > deadRange) { operatorFlag = true; }


        // Output processing
        if(driverFlag){ oobInput = driverInput; }

        else if(operatorFlag){ oobInput = operatorInput; }


        // Stop/Hold conditions
        if ((Math.abs(driverInput) <= deadRange && Math.abs(operatorInput) <= deadRange) //dead zone
            || (maxFlag && oobInput >= 0) || (minFlag && oobInput <= 0)) { //switches

            if(getHeight() < 10){
                motorOut = 0; // Stop motors at bottom
            }

            speedMultiplier = 1;
            deadZoneFlag = true;
            multiplierSet = true;
        }


        //Downward speed adjustment
        if(!getClimberShifted() && !deadZoneFlag){
            if(oobInput < 0){
                motorOut = 0.7 * oobInput;
            }

            else{
                motorOut = oobInput;
            }
        }

        /**
         * Proportional elevator/arm control to avoid collisions
         */

        /*
        todo: Remodel for new elevator
        //Elevator slow and stop bands (below limit)
        if(elevatorLimitReached){

            dynamicLimit = (armHeight + 3450.1)/-3.6503;
            armTarget = -3.6503 * getHeight() - 3450.1; //excel charted

            if(getHeight() < dynamicLimit + 300
                    && oobInput < 0
                    && !deadZoneFlag
                    && armHeight < -Robot.linkArm.armLevelValue
                    && getHeight() > 30)
            {

                speedMultiplier = slowSpeed;
                multiplierSet = true;

                while(Robot.linkArm.getArmEncoderValue() < armTarget) {
                    if(oobInput > 0 || Robot.oi.operator.getRawAxis(5) < 0){
                        break;
                    }

                    Robot.linkArm.setArmSpeed(-1);
                }

                Robot.linkArm.setHoldingSpeed();
            }
        }
        */


        //Final output check
        if(deadZoneFlag || driverFlag || operatorFlag && !multiplierSet) {

            // Top and bottom speed buffers
            if((getHeight() > elevMaxValue - 200 && oobInput > 0) ||
                    (getHeight() < groundValue + 350  && oobInput < 0)) {
                speedMultiplier = slowSpeed;
            }

            else {
                speedMultiplier = normalSpeed;
            }


            elevMotorMain.set(ControlMode.PercentOutput, motorOut * speedMultiplier);
        }
    }

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

        else return Math.abs(opInput) > deadRange;
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
     * Preset move + check method
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

        if(Math.abs(getHeight() - target) < 2 * tolerance){

            double motorOut = 0;

            if(elevPresetUp){
                motorOut = slowSpeed;
            }

            else if(elevPresetDown){
                motorOut = -slowSpeed;
            }

            elevMotorMain.set(ControlMode.PercentOutput, motorOut);
        }

        return getHeight() >= (target - tolerance) && getHeight() <= (target + tolerance);
    }
}


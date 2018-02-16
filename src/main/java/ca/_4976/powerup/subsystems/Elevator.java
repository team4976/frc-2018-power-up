package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.*;
import ca._4976.powerup.commands.MoveElevatorWithJoystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;


public final class Elevator extends Subsystem implements Sendable {

    // Elevator motors
    private final WPI_TalonSRX elevMotorMain = new WPI_TalonSRX(4);
    private final WPI_TalonSRX elevSlave1 = new WPI_TalonSRX(3);

    // Encoder on elevator
    public final Encoder elevEnc = new Encoder(4, 5);

    //TODO -> PID
    // PID controller for the elevator subsystem
    private PIDController elevatorPID;

    // NetworkTable assigned inputTest value
    private double motorOutput;
    private double scaleHighValue;
    private double scaleMidValue;
    private double scaleLowValue;
    private double switchValue;
    private double groundValue;


    /**
     * Elevator presets
     *
     * Encoder values from excel sheet (Elevator Distance Chart.xlsx)
     */
    public enum ElevatorPreset {

        ELEV_MAX(16000.00), //slightly rounded down
        SCALE_HIGH(12137.81),
        SCALE_MID(9536.85),
        SCALE_LOW(6935.89),
        SWITCH(1733.97),
        GROUND(0),
        ELEV_MIN(0);

        public final double value;

        ElevatorPreset(double value) {
            this.value = value;
        }
    }

    public Elevator() {

        elevSlave1.follow(elevMotorMain);


        //TODO -> PID testing
        use(NetworkTableInstance.getDefault().getTable("Elevator"), ePIDTable -> {

            NetworkTableEntry p = ePIDTable.getEntry("P");
            NetworkTableEntry i = ePIDTable.getEntry("I");
            NetworkTableEntry d = ePIDTable.getEntry("D");

            NetworkTableEntry scaleHigh = ePIDTable.getEntry("Scale High");
            NetworkTableEntry scaleMid = ePIDTable.getEntry("Scale Mid");
            NetworkTableEntry scaleLow = ePIDTable.getEntry("Scale Low");
            NetworkTableEntry switchs = ePIDTable.getEntry("Switch");
            NetworkTableEntry ground = ePIDTable.getEntry("Ground");

            NetworkTableEntry motorOut = ePIDTable.getEntry("Manual Output");

            p.setPersistent();
            i.setPersistent();
            d.setPersistent();

            scaleHigh.setPersistent();
            scaleMid.setPersistent();
            scaleLow.setPersistent();
            switchs.setPersistent();
            ground.setPersistent();

            motorOut.setPersistent();

            //PID table setup
            p.setDouble(p.getDouble(0));
            i.setDouble(i.getDouble(0));
            d.setDouble(d.getDouble(0));

            //Preset initial values
            scaleHigh.setDouble(scaleHigh.getDouble(12137.81));
            scaleMid.setDouble(scaleMid.getDouble(9536.85));
            scaleLow.setDouble(scaleLow.getDouble(6935.89));
            switchs.setDouble(switchs.getDouble(1733.97));
            ground.setDouble(ground.getDouble(0));

            scaleHighValue = scaleHigh.getDouble(12137.81);
            scaleMidValue = scaleMid.getDouble(9536.85);
            scaleLowValue = scaleLow.getDouble(6935.89);
            switchValue = switchs.getDouble(1733.97);
            groundValue = ground.getDouble(0);

            //Manual output value for closed loop testing
            motorOut.setDouble(motorOut.getDouble(0.5));
            //motorOutput = motorOut.getDouble(0.5);
            motorOutput = 0.2;

            elevatorPID = new PIDController(
                    p.getDouble(0),
                    i.getDouble(0),
                    d.getDouble(0),
                    elevEnc,
                    elevMotorMain);
             elevatorPID.disable();
        });

        System.out.println("\nPID set: [p - " + elevatorPID.getP() + "]\n"
                + "[i - " + elevatorPID.getI() + "]\n"
                + "[i - " + elevatorPID.getI() + "]\n");

    }

    /*TODO -> Limit switch testing
    Limit switch near top of the first stage of the elevator. Switch normally held open (high/true)
    private final DigitalInput limitSwitchMax = new DigitalInput(4);

    //Limit switch near bottom of the first stage of the elevator. Switch normally held closed (false/low)
    private final DigitalInput limitSwitchMin = new DigitalInput(5);

    //Switch states need to be verified logically and codewise
    */

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

        double deadRange = 0.09;
        double drInput = 0;//-Robot.oi.driver.getRawAxis(5);
        double opInput = -Robot.oi.operator.getRawAxis(1);
        double manualOut = elevMotorMain.getMotorOutputPercent();


        if (Math.abs(drInput) <= deadRange && Math.abs(opInput) <= deadRange) {
            //System.out.println("Dead zone");
            manualOut = 0;
        }

        else if (Math.abs(drInput) > deadRange) {
            System.out.println("Driver control");
            manualOut = drInput;
        }

        else if (Math.abs(opInput) > deadRange) {
            System.out.println("Operator control");
            manualOut = opInput;
        }

        //if (getHeight() <= ELEV_MAX.value || getHeight() >= ELEV_MIN.value) {

            //System.out.println("Manual output: " + manualOut);
            elevMotorMain.set(ControlMode.PercentOutput, manualOut);
            //System.out.println("Encoder: " + elevEnc.get());
        //}
    }


    /**
     * High scale
     *
     * Move and check method
     */
    public void moveToHighScale() {

        System.out.println("HIGH SCALE METHOD: " + scaleHighValue);

        if(getHeight() > scaleHighValue){
            System.out.println("DOWN");
            elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
        }

        else if(getHeight() < scaleHighValue){
            System.out.println("UP");
            elevMotorMain.set(ControlMode.PercentOutput, -motorOutput);
        }
    }

    public boolean checkHighScale(){

        if(getHeight() >= (scaleHighValue - 200) && getHeight() <= (scaleHighValue + 200)){
            System.out.println("\nENCODER: " + getHeight());
            System.out.println("FINISHED: SCALE HIGH\n");
            return true;
        }

        else {
            System.out.println("UNFINISHED ENCODER: " + getHeight());
            return false;
        }
    }


    /**
     * Mid scale
     *
     * Move and check method
     */
    public void moveToMidScale() {

        if(getHeight() > scaleMidValue){
            elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
        }

        else if(getHeight() < scaleMidValue){
            elevMotorMain.set(ControlMode.PercentOutput, -motorOutput);
        }
    }

    public boolean checkMidScale(){

        if(getHeight() >= (scaleMidValue - 200) && getHeight() <= (scaleMidValue + 200)){
            System.out.println("\nENCODER: " + getHeight());
            System.out.println("FINISHED: SCALE MID\n");
            return true;
        }

        else {
            return false;
        }
    }


    /**
     * Low scale
     *
     * Move and check method
     */
    public void moveToLowScale() {

        if(getHeight() > scaleLowValue){
            elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
        }

        else if(getHeight() < scaleLowValue){
            elevMotorMain.set(ControlMode.PercentOutput, -motorOutput);
        }
    }

    public boolean checkLowScale(){

        if(getHeight() >= (scaleLowValue - 200) && getHeight() <= (scaleLowValue + 200)){
            System.out.println("\nENCODER: " + getHeight());
            System.out.println("FINISHED: SCALE LOW\n");
            return true;
        }

        else {
            return false;
        }
    }


    /**
     * Switch
     *
     * Move and check method
     */
    public void moveToSwitch() {

        if(getHeight() > switchValue){
            elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
        }

        else if(getHeight() < switchValue){
            elevMotorMain.set(ControlMode.PercentOutput, -motorOutput);
        }
    }

    public boolean checkSwitch(){

        if(getHeight() >= (switchValue - 200) && getHeight() <= (switchValue + 200)){
            System.out.println("\nENCODER: " + getHeight());
            System.out.println("FINISHED: SWITCH\n");
            return true;
        }

        else {
            return false;
        }
    }


    /**
     * Ground
     *
     * Move and check method
     */
    public void moveToGround() {

        if(getHeight() > groundValue){
            elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
        }

        else if(getHeight() < groundValue){
            elevMotorMain.set(ControlMode.PercentOutput, -motorOutput);
        }
    }

    public boolean checkGround(){

        if(getHeight() >= (groundValue) && getHeight() <= (groundValue + 200)){
            System.out.println("\nENCODER: " + getHeight());
            System.out.println("FINISHED: GROUND\n");
            return true;
        }

        else {
            return false;
        }
    }


    /**
     * Simply runs motors for use in Climber subsystem & commands
     */
    public void climb(){
//        elevatorPID.disable();
        elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
    }


    /**
     * Take a wild guess
     */
    public void stop(){
//        elevatorPID.disable();
        elevMotorMain.set(ControlMode.PercentOutput, 0);
    }
}


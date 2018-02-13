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

import static ca._4976.powerup.subsystems.Elevator.ElevatorPreset.ELEV_MAX;
import static ca._4976.powerup.subsystems.Elevator.ElevatorPreset.ELEV_MIN;
import static ca.qormix.library.Lazy.use;


public final class Elevator extends Subsystem implements Sendable {

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

        private final double tolerance = 170;
        public final double value;
        public double lowerBound;
        public double upperBound;

        ElevatorPreset(double value) {
            this.value = value;
            lowerBound = value - tolerance;
            upperBound = value + tolerance;
        }
    }

    // Elevator motors
    private final WPI_TalonSRX elevMotorMain = new WPI_TalonSRX(3);
    private final WPI_TalonSRX elevSlave1 = new WPI_TalonSRX(2);

    // Encoder on elevator
    private final Encoder elevEnc = new Encoder(4, 5);

    //TODO -> PID
    // PID controller for the elevator subsystem
    private PIDController elevatorPID;

    // NetworkTable assigned inputTest value
    private double motorOutput;

    public Elevator() {
        System.out.println("Motors slaved");
        elevSlave1.follow(elevMotorMain);

        //TODO -> PID testing
        use(NetworkTableInstance.getDefault().getTable("Elevator"), ePIDTable -> {

            NetworkTableEntry p = ePIDTable.getEntry("P");
            NetworkTableEntry i = ePIDTable.getEntry("I");
            NetworkTableEntry d = ePIDTable.getEntry("D");
            NetworkTableEntry motorOut = ePIDTable.getEntry("Manual output");

            p.setPersistent();
            i.setPersistent();
            d.setPersistent();
            motorOut.setPersistent();

            //PID table setup
            p.setDouble(p.getDouble(0));
            i.setDouble(i.getDouble(0));
            d.setDouble(d.getDouble(0));

            //Manual output value for closed loop testing
            motorOut.setDouble(motorOut.getDouble(0.5));

            motorOutput = motorOut.getDouble(0.5);

            elevatorPID = new PIDController(
                    p.getDouble(0),
                    i.getDouble(0),
                    d.getDouble(0),
                    elevEnc,
                    elevMotorMain);
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
    private double getHeight() {
        return elevEnc.getDistance();
    }


    /**
     * Driver input overrides operator input. Only executes a movement initiated by the operator
     * when no input is detected from driver according to dead zone
     */
    public void moveElevator() {

        double deadRange = 0.09;
        double drInput = -Robot.oi.driver.getRawAxis(5);
        double opInput = -Robot.oi.operator.getRawAxis(1);
        double manualOut = 0;

        System.out.println("OUTPUT - DRIVER: " + drInput + " OPERATOR: " + opInput);

        //TODO -> ADD LIMIT SWITCH FUNCTIONALITY
        //LIMIT SWITCHES TO CONTROL
        /*if(limitSwitchMax.get() != true || limitSwitchMin.get() != false){ //could be simplified but kept for readability
            System.out.println("Switch triggered");
            manualOut = 0;

            //ADD SPECIFIC CASES FOR SWITCHES TO LIMIT BUT ENABLE MOVEMENT IN ONE DIRECTION, DEPENDING ON WHICH
            //SWITCH IS TRIGGERED
        }*/
        //TODO END

        if (Math.abs(drInput) <= deadRange && Math.abs(opInput) <= deadRange) {
            System.out.println("Dead zone");
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

        if (getHeight() <= ELEV_MAX.value || getHeight() >= ELEV_MIN.value) {

            System.out.println("Manual output: " + manualOut);
            elevMotorMain.set(ControlMode.PercentOutput, manualOut);
            System.out.println("Encoder: " + elevEnc.get());

            /*TODO PID TESTING
            elevatorPID.setSetpoint(getHeight() + (1000 * manualOut));
            System.out.println("\nSETPOINT SET: " + elevatorPID.getSetpoint() + "\n");*/
        }

        else {
            stop();
        }
    }


    /**
     * Moves elevator until height is within certain range of set value assigned by preset
     */
    public void moveToPreset(ElevatorPreset preset) {

        //elevatorPID.setSetpoint(preset.value);

        System.out.println("Preset set to: " + preset.toString() + " at: " + preset.value);

        while (getHeight() > preset.upperBound || getHeight() < preset.lowerBound) {

            //Test for joysticks
            if(inputTest()){
                getCurrentCommand().cancel();
                break;
            }

            else if (getHeight() < preset.lowerBound) {
                elevMotorMain.set(ControlMode.PercentOutput, motorOutput);
            }

            else if (getHeight() > preset.upperBound) {
                elevMotorMain.set(ControlMode.PercentOutput, -motorOutput);
            }
        }

        System.out.println("Preset reached: Encoder output: " + getHeight());
    }

    /**
     * Simply runs motors for use in Climber subsystem & commands
     */
    public void climb(){
        elevMotorMain.set(ControlMode.PercentOutput, 0.5);
    }

    /**
     * Take a wild guess
     */
    public void stop(){
        elevMotorMain.set(ControlMode.PercentOutput, 0);
    }

    /**
     * Tests for input from joysticks
     */
    public boolean inputTest(){

        boolean inputPresent = false;
        double deadRange = 0.09;
        double drInput = -Robot.oi.driver.getRawAxis(5);
        double opInput = -Robot.oi.operator.getRawAxis(1);

        if (Math.abs(drInput) <= deadRange && Math.abs(opInput) <= deadRange) {
            System.out.println("TEST: DZ");
            inputPresent = false;
        }

        else if (Math.abs(drInput) > deadRange) {
            System.out.println("TEST: DRIVER");
            inputPresent = true;
        }

        else if (Math.abs(opInput) > deadRange) {
            System.out.println("TEST: OPERATOR");
            inputPresent = true;
        }

        return inputPresent;
    }
}

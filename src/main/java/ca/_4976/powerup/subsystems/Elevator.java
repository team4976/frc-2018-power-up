package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.*;
import ca._4976.powerup.commands.MoveElevatorWithJoystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import static ca._4976.powerup.subsystems.Elevator.ElevPreset.ELEV_MAX;
import static ca._4976.powerup.subsystems.Elevator.ElevPreset.ELEV_MIN;
import static ca.qormix.library.Lazy.use;


public final class Elevator extends Subsystem implements Sendable {



    public Elevator() {
        System.out.println("Motors slaved");
        elevSlave1.follow(elevMotorMain);
        elevSlave2.follow(elevMotorMain);

        /*use(NetworkTableInstance.getDefault().getTable("Elevator PID"), it -> {

            NetworkTableEntry p = it.getEntry("P");
            NetworkTableEntry i = it.getEntry("I");
            NetworkTableEntry d = it.getEntry("D");

            p.setPersistent();
            i.setPersistent();
            d.setPersistent();

            p.setDefaultDouble(0);
            i.setDefaultDouble(0);
            d.setDefaultDouble(0);


            elevatorPID = new PIDController(
                    p.getDouble(0),
                    i.getDouble(0),
                    d.getDouble(0),
                    elevEnc,
                    elevMotorMain);
        });*/
    }

    //Presets - encoder values from excel sheet (Elevator Distance Chart.xlsx)
    public enum ElevPreset {

        ELEV_MAX(16472.75),
        SCALE_HIGH(12137.81),
        SCALE_MID(9536.85),
        SCALE_LOW(6935.89),
        SWITCH(1733.97),
        GROUND(0),
        ELEV_MIN(0);

        //IMPORTANT -> TOL RANGE MUST NOT BE LARGER THAN THE DISTANCE FROM THE MAX TO THE LIMIT SWITCH
        //WILL BE UNABLE TO MOVE IF THIS IS THE CASE

        private final double tolerance = 170;
        public final double value;
        public double lowerBound;
        public double upperBound;

        ElevPreset(double value) {
            this.value = value;
            lowerBound = value - tolerance;
            upperBound = value + tolerance;
        }
    }


    private final WPI_TalonSRX elevMotorMain = new WPI_TalonSRX(2),
    elevSlave1 = new WPI_TalonSRX(3),
    elevSlave2 = new WPI_TalonSRX(8);

    private final Encoder elevEnc = new Encoder(4, 5);

    private final NetworkTableInstance instance = NetworkTableInstance.getDefault();

    private PIDController elevatorPID;

    /*
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


    public double getHeight() { //Reads encoders to return current height of the elevator with reference to it's zero point
        System.out.println("ENCODER READ: " + elevEnc.get());
        return elevEnc.get();
    }


    public void moveElevator() {

        /*
        Driver input overrides operator input. Only execute a movement initiated by the operator when no
        input is detected from driver
        */

        double drInput = -Robot.oi.driver.getRawAxis(5);
        double opInput = 0;//-Robot.oi.operator.getRawAxis(1);
        double manualOut = 0;

        System.out.println("OUTPUT - DRIVER: " + drInput + " OPERATOR: " + opInput);
        //LIMIT SWITCHES TO CONTROL
        /*if(limitSwitchMax.get() != true || limitSwitchMin.get() != false){ //could be simplified but kept for readability
            System.out.println("Switch triggered");
            manualOut = 0;

            //ADD SPECIFIC CASES FOR SWITCHES TO LIMIT BUT ENABLE MOVEMENT IN ONE DIRECTION, DEPENDING ON WHICH
            //SWITCH IS TRIGGERED
        }*/

        //DEAD ZONE
        //else
        if (Math.abs(drInput) <= 0.05 && Math.abs(opInput) <= 0.05) {
            System.out.println("Dead zone");
            manualOut = 0;
        }

        //DRIVER CONTROL
        else if (Math.abs(drInput) > 0.05) {
            System.out.println("Driver control");
            manualOut = drInput;
        }

        //OPERATOR CONTROL
        else if (Math.abs(opInput) > 0.05) {
            System.out.println("Operator control");
            manualOut = opInput;
        }

        //CHECK FOR MAX / MIN ENC VALUES
        if (true){//getHeight() < ELEV_MAX.value || getHeight() > ELEV_MIN.value) {
            //tol range may be taken into account

            System.out.println("Manual output: " + manualOut);
            elevMotorMain.set(ControlMode.PercentOutput, manualOut);
//            elevatorPID.setSetpoint(getHeight() + (1000 * manualOut));
//            System.out.println("\nSETPOINT SET: " + elevatorPID.getSetpoint() + "\n");
        }

        else {
            stop();
        }
    }


    //Moves elevator until height is within certain range of set value
    public void moveToPreset(ElevPreset preset) {

        //elevatorPID.setSetpoint(preset.value);

        System.out.println("Preset set to: " + preset.toString() + " at: " + preset.value);

        double motorOut = 0.5;

        while (getHeight() > preset.upperBound || getHeight() < preset.lowerBound) {

            if (getHeight() < preset.lowerBound) {
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            } else if (getHeight() > preset.upperBound) {
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }

            System.out.println("Preset reached: Encoder output: " + getHeight());
        }


    }

    //Run motor method for climbing
    public void climb(){

        elevMotorMain.set(ControlMode.PercentOutput, 0.5);
    }

    //What the hell do you think
    public void stop(){
        elevMotorMain.set(ControlMode.PercentOutput, 0);
    }
}

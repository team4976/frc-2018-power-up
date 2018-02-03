package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.OI;
import ca._4976.powerup.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;

import static ca._4976.powerup.Robot.oi;


public final class Elevator extends Subsystem implements Runnable, Sendable {

    //Network table setup
    private NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private NetworkTable table = instance.getTable("Values");
    private NetworkTableEntry MotorOutput = table.getEntry("Elev Out");

    //Get motor output value from table
    private double motorOut = MotorOutput.getDouble(0);


    //Main motor for moving elevator up and down
    private final TalonSRX elevMotorMain = new TalonSRX(2);

    //Elevator slave motors
    private final TalonSRX elevSlave1 = new TalonSRX(3),
    elevSlave2 = new TalonSRX(8);

    //Limit switch near top of the first stage of the elevator. Switch normally held open (high/true)
    private final DigitalInput limitSwitchMax = new DigitalInput(4);

    //Limit switch near bottom of the first stage of the elevator. Switch normally held closed (false/low)
    private final DigitalInput limitSwitchMin = new DigitalInput(5);
    
    //Switch states need to be verified logically and codewise
    

    //Encoder on the elevator motors
    private final Encoder elevEnc = new Encoder(4, 5);


    //Presets - encoder values from excel sheet (Elevator Distance Chart.xlsx)
    private final double EPS_SCALE_HIGH = 12137.81,
    EPS_SCALE_MID = 9536.85,
    EPS_SCALE_LOW = 6935.89,
    EPS_SWITCH = 1733.97,
    EPS_GROUND = 0,
    ELEVATOR_MAX = 16472.75,
    ELEVATOR_MIN = EPS_GROUND,

    //ABOUT 2 CM OF TOLERANCE IN VALUES - MAY BE UPDATED
    TOL_RANGE = 170;

    //IMPORTANT -> TOL RANGE MUST NOT BE LARGER THAN THE DISTANCE FROM THE MAX TO THE LIMIT SWITCH
    //WILL BE UNABLE TO MOVE IF THIS IS THE CASE


    @Override
    protected void initDefaultCommand() {
        System.out.println("Motors slaved");
        elevSlave1.follow(elevMotorMain);
        elevSlave2.follow(elevMotorMain);
    }


    public void run() {
        System.out.println("Run method ran");
        //Maybe put moveElevator method in here?
    }

    //Reads encoders to return current height of the elevator with reference to it's zero point
    public double getHeight() {
        return elevEnc.get();
    }


    public void moveElevator() {

        double drInput = Robot.oi.driver.getRawAxis(5);
        double opInput = Robot.oi.operator.getRawAxis(1);
        double manualOut = 0;

        /*
        Driver input overrides operator input. Only execute a movement initiated by the operator when no input is detected from
        */



        //LIMIT SWITCHES TO CONTROL
        if(limitSwitchMax.get() != true || limitSwitchMin.get() != false){ //could be simplified but kept for readability
            System.out.println("Switch triggered");
            manualOut = 0;

            //ADD SPECIFIC CASES FOR SWITCHES TO LIMIT BUT ENABLE MOVEMENT IN ONE DIRECTION, DEPENDING ON WHICH
            //SWITCH IS TRIGGERED
        }

        //JOYSTICK DEAD ZONE
        else if(Math.abs(drInput) <= 0.03 && Math.abs(opInput) <= 0.03){
            System.out.println("Dead zone");
            manualOut = 0;
        }

        //DRIVER CONTROL
        else if(Math.abs(drInput) > 0.03){
            System.out.println("Driver control");
            manualOut = drInput * 0.7 * 100;
        }

        //OPERATOR CONTROL
        else if(Math.abs(opInput) > 0.03){
            System.out.println("Operator control");
            manualOut = opInput * 0.7 * 100;
        }


        //CHECK FOR MAX / MIN ENC VALUES
        else if(getHeight() < ELEVATOR_MAX || getHeight() > ELEVATOR_MIN){
            //tol range may be taken into account

            System.out.println("Manual output: " + manualOut);
            elevMotorMain.set(ControlMode.PercentOutput, manualOut);
        }
    }


    //Moves elevator until height is within certain range of set value - at ground level
    public void groundPS() {

        System.out.println("Ground preset triggered");
        while(getHeight() > EPS_GROUND + TOL_RANGE){

            if(getHeight() < EPS_GROUND - TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            }

            else if(getHeight() > EPS_GROUND + TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }
        }
    }

    //Moves elevator until height is within certain range of set value - at switch level
    public void switchPS() {

        System.out.println("Switch preset triggered");
        while(getHeight() < EPS_SWITCH - TOL_RANGE || getHeight() > EPS_SWITCH + TOL_RANGE){

            if(getHeight() < EPS_SWITCH - TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            }

            else if(getHeight() > EPS_SWITCH + TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }
        }
    }

    //Moves elevator until height is within certain range of set value - at low scale level
    public void scaleLowPS() {

        System.out.println("Low scale preset triggered");
        while(getHeight() < EPS_SCALE_LOW - TOL_RANGE || getHeight() > EPS_SCALE_LOW + TOL_RANGE){

            if(getHeight() < EPS_SCALE_LOW - TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            }

            else if(getHeight() > EPS_SCALE_LOW + TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }
        }
    }

    //Moves elevator until height is within certain range of set value - at mid scale level
    public void scaleMidPS() {

        System.out.println("Mid scale preset triggered");
        while(getHeight() < EPS_SCALE_MID - TOL_RANGE || getHeight() > EPS_SCALE_MID + TOL_RANGE){

            if(getHeight() < EPS_SCALE_MID - TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            }

            else if(getHeight() > EPS_SCALE_MID + TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }
        }
    }

    //Moves elevator until height is within certain range of set value - at high scale level
    public void scaleHighPS() {

        System.out.println("High scale preset triggered");
        while(getHeight() < EPS_SCALE_HIGH - TOL_RANGE || getHeight() > EPS_SCALE_HIGH + TOL_RANGE){

            if(getHeight() < EPS_SCALE_HIGH - TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            }

            else if(getHeight() > EPS_SCALE_HIGH + TOL_RANGE){
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }
        }
    }
}

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
    NetworkTable table = instance.getTable("Values");
    NetworkTableEntry MotorOutput = table.getEntry("Speed");

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


    @Override
    protected void initDefaultCommand() {
        elevSlave1.follow(elevMotorMain);
        elevSlave2.follow(elevMotorMain);
    }


    public void run() {
        //Maybe put moveElevator method in here?
    }

    //Reads encoders to return current height of the elevator with reference to it's zero point
    public double getHeight() {
        return elevEnc.get();
    }


    public void moveElevator() {


        double input = Robot.oi.operator.getRawAxis(5);

        //JOYSTICK DEAD ZONE
        if(Math.abs(input) < 0.03){
            elevMotorMain.set(ControlMode.PercentOutput, 0);
        }


        //LIMIT SWITCHES TO CONTROL
        else if(limitSwitchMax.get() != true || limitSwitchMin.get() != false){ //could be simplified but kept for readability
            elevMotorMain.set(ControlMode.PercentOutput, 0);

            //ADD SPECIFIC CASES FOR SWITCHES TO LIMIT BUT ENABLE MOVEMENT IN ONE DIRECTION, DEPENDING ON WHICH
            //SWITCH IS TRIGGERED
        }

        
        //CHECK FOR MAX / MIN ENC VALUES
        else if(getHeight() < ELEVATOR_MAX - TOL_RANGE || getHeight() > ELEVATOR_MIN + TOL_RANGE){
            elevMotorMain.set(ControlMode.PercentOutput, input * 0.7 * 100);
        }
    }


    //Moves elevator until height is within certain range of set value - at ground level
    public void groundPS() {

        while(getHeight() < EPS_GROUND - TOL_RANGE || getHeight() > EPS_GROUND + TOL_RANGE){

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

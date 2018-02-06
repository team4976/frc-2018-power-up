package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.*;
import ca._4976.powerup.commands.MoveElevator;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca._4976.powerup.subsystems.Elevator.ElevPreset.ELEV_MAX;
import static ca._4976.powerup.subsystems.Elevator.ElevPreset.ELEV_MIN;


public final class Elevator extends Subsystem implements Sendable {

    public Elevator() {
        System.out.println("Motors slaved");
        elevSlave1.follow(elevMotorMain);
        elevSlave2.follow(elevMotorMain);
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

        private ElevPreset(double value) {
            this.value = value;
            lowerBound = value - tolerance;
            upperBound = value + tolerance;
        }


    }

    private final TalonSRX elevMotorMain = new TalonSRX(5);//2
    private final TalonSRX elevSlave1 = new TalonSRX(3);
    private final TalonSRX elevSlave2 = new TalonSRX(8);

    private final Encoder elevEnc = new Encoder(6, 7);

    private double motorOut = 0.5;

    /*
    Limit switch near top of the first stage of the elevator. Switch normally held open (high/true)
    private final DigitalInput limitSwitchMax = new DigitalInput(4);

    //Limit switch near bottom of the first stage of the elevator. Switch normally held closed (false/low)
    private final DigitalInput limitSwitchMin = new DigitalInput(5);

    //Switch states need to be verified logically and codewise
*/

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveElevator());
    }


    public double getHeight() { //Reads encoders to return current height of the elevator with reference to it's zero point
        return elevEnc.get();
    }


    public void moveElevator() {

        /*
        Driver input overrides operator input. Only execute a movement initiated by the operator when no
        input is detected from driver
        */

        double drInput = Robot.oi.driver.getRawAxis(5);
        double opInput = Robot.oi.operator.getRawAxis(1);
        double manualOut = 0;


        //LIMIT SWITCHES TO CONTROL
        /*if(limitSwitchMax.get() != true || limitSwitchMin.get() != false){ //could be simplified but kept for readability
            System.out.println("Switch triggered");
            manualOut = 0;

            //ADD SPECIFIC CASES FOR SWITCHES TO LIMIT BUT ENABLE MOVEMENT IN ONE DIRECTION, DEPENDING ON WHICH
            //SWITCH IS TRIGGERED
        }*/

        //DEAD ZONE
        //else
        if (Math.abs(drInput) <= 0.03 && Math.abs(opInput) <= 0.03) {
            System.out.println("Dead zone");
            manualOut = 0;
        }

        //DRIVER CONTROL
        else if (Math.abs(drInput) > 0.03) {
            System.out.println("Driver control");
            manualOut = drInput * 0.7;
        }

        //OPERATOR CONTROL
        else if (Math.abs(opInput) > 0.03) {
            System.out.println("Operator control");
            manualOut = opInput * 0.7;
        }


        //CHECK FOR MAX / MIN ENC VALUES
        else if (getHeight() < ELEV_MAX.value || getHeight() > ELEV_MIN.value) {
            //tol range may be taken into account

            System.out.println("Manual output: " + manualOut);
            elevMotorMain.set(ControlMode.PercentOutput, manualOut);
        } else {
            elevMotorMain.set(ControlMode.PercentOutput, 0);
        }
    }


    //Moves elevator until height is within certain range of set value
    public void moveToPreset(ElevPreset preset) {

        System.out.println("Preset triggered");
        while (getHeight() > preset.upperBound) {

            if (getHeight() < preset.lowerBound) {
                elevMotorMain.set(ControlMode.PercentOutput, motorOut);
            } else if (getHeight() > preset.upperBound) {
                elevMotorMain.set(ControlMode.PercentOutput, -motorOut);
            }

            System.out.println("Preset reached: Encoder output: " + getHeight());
        }
    }
}

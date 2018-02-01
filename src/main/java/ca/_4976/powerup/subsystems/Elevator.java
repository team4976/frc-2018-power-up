package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;


public final class Elevator extends Subsystem implements Runnable, Sendable {

    //Main motor for moving elevator up and down
    private final TalonSRX elevMotorMain = new TalonSRX(2);

    //Elevator slave motor 1
    private final TalonSRX elevSlave1 = new TalonSRX(3);

    //Elevator slave motor 2
    private final TalonSRX elevSlave2 = new TalonSRX(8);

    //Operator Elevator Left JoyStick
    private final Joystick leftOperatorJoy = new Joystick(0);


    //Driver Elevator Right JoyStick


    //Limit switch near top of the first stage of the elevator. Switch normally held closed
    private final DigitalInput limitSwitchMax = new DigitalInput(4);

    //Limit switch near bottom of the first stage of the elevator. Switch normally held open
    private final DigitalInput limitSwitchMin = new DigitalInput(5);

    //Encoder on the elevator motors
    private final Encoder elevEnc = new Encoder(4, 5);

    //Height variable for measurements
    public double elevHeight = 0;


    @Override
    protected void initDefaultCommand() {
        elevSlave1.follow(elevMotorMain);
        elevSlave2.follow(elevMotorMain);
    }


    public void run() {
        //Use PID to move elevator to setpoint
    }

    //Reads encoders to return current height of the elevator with reference to it's zero point
    public double getHeight() {
        return 0;
    }


    public void moveElevator() {

        //HAVING AN OUTPUT STATEMENT HERE DEFEATS THE PURPOSE OF GRANULAR CONTROL
        //THIS WILL RESULT IN VERY JERKY MOTION
        elevMotorMain.set(ControlMode.PercentOutput, leftOperatorJoy.getY());
        double elevOut = 0;


        //Dead zone (stopped)
        if (Math.abs(leftOperatorJoy.getY()) < 0.05 || Math.abs(leftOperatorJoy.getY()) > -0.05) {
            elevMotorMain.set(ControlMode.PercentOutput, 0);
        }


        //UP
        //Less than or 50% pressed
        else if (Math.abs(leftOperatorJoy.getY()) >= -0.5) { //ABSOLUTE VALUE BEING CHECKED FOR >= AGAINST A NEGATIVE?
            elevOut = (leftOperatorJoy.getY() * 0.4);
        }

        //Joystick > 50%
        else {
            elevOut = (leftOperatorJoy.getY() * 0.8) + 0.2;
        }

        //DOWN

        //Less that 50% pressed
        else if (Math.abs(leftOperatorJoy.getY()) >= -0.5) {

        /*INVALID STRUCTURE - HEED RED LINES!
        IF/ELSE BLOCKS CONSIST OF IF, ELSE IF, ELSE IF... ELSE. NEW BLOCKS MUST START WITH IF*/


        elevOut = (leftOperatorJoy.getY() * 0.4);
        }
        //Joystick > 50%
        else {
            elevOut = (leftOperatorJoy.getY() * 0.8) + 0.2; //ASSUMING NEGATIVE OUTPUT, OFFSET MUST BE ADJUSTED ACCORDINGLY
        }

        elevMotorMain.set(ControlMode.PercentOutput, elevOut);
    }


    //Moves elevator until height is within certain range of set value - at ground level
    public void groundPS() {

    }

    //Moves elevator until height is within certain range of set value - at switch level
    public void switchPS() {

    }

    //Moves elevator until height is within certain range of set value - at low scale level
    public void scaleLowPS() {

    }

    //Moves elevator until height is within certain range of set value - at mid scale level
    public void scaleMidPS() {

    }

    //Moves elevator until height is within certain range of set value - at high scale level
    public void scaleHighPS() {

    }
}

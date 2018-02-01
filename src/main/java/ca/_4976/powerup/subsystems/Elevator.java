package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.sun.corba.se.impl.encoding.EncapsInputStream;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.EncoderJNI;
import sun.nio.cs.ext.DoubleByte;
import sun.security.krb5.internal.EncKDCRepPart;

import java.security.spec.EncodedKeySpec;


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
        //THIS WILL RESULT IN VERY JERKY MOTION
        double elevOut = 0;

        //Dead zone (stopped)
        if (Math.abs(leftOperatorJoy.getY()) < 0.05) {
            elevMotorMain.set(ControlMode.PercentOutput, 0);
        }


        //UP
        //Less than or 50% pressed
        else if(Math.abs(leftOperatorJoy.getY()) <= 0.5) { //ABSOLUTE VALUE BEING CHECKED FOR >= AGAINST A NEGATIVE?
            elevOut = (leftOperatorJoy.getY() * 0.4);
        }

        //Joystick > 50%
        else if((leftOperatorJoy.getY()) > 0.5) {
            elevOut = (leftOperatorJoy.getY() * 0.8) - 0.2;
        }

        //DOWN

        //Less than 50% pressed
        else if ((leftOperatorJoy.getY()) < -0.5) {
            elevOut = (leftOperatorJoy.getY() * 0.8) + 0.2;
        }

        elevMotorMain.set(ControlMode.PercentOutput, elevOut);
    }


    //Moves elevator until height is within certain range of set value - at ground level
    public void groundPS() {
        double groundPS = elevEnc
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

package ca._4976.powerup.subsystems;

/*
Made by Cameron, Jacob, Ethan, Zach
*/

import ca._4976.powerup.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class LinkArm extends Subsystem implements Runnable, Sendable {

    //Motor inside linkarm carriage - raises/lowers arm
    private final TalonSRX armMotor = new TalonSRX(4);

    //Angle variable for measurements
    private double armAngle = 0;

    //Add LinkArm encoder built into the TalonSRX -> research code

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void run() {

    }

    //ARM WILL USE 0 DEGREES AS REFERENCE
    //Equivalent arm parallel with ground
    //All angle setting/holding will be done using PID


    //Pass an angle as a double to move the arm to that angle
    //Use PID to set angle using motors
    public void setAngle(double angle){

    }

    //Read the arm's encoder to get the current angle of the arm with reference to middle
    public double getAngle(){
        return 0;
    }


    //move linkage arm
    public void moveLinkArm(){

        double armOut = Robot.oi.operator.getRawAxis(5);

        //dead zone
        if (Math.abs(armOut) <= 0.03) {
            armMotor.set(ControlMode.PercentOutput, 0);
        }

        else {
            armMotor.set(ControlMode.PercentOutput,0.5 * armOut * 100);
        }
    }
}

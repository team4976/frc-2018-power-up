//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;
import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX gripperRight = new TalonSRX(8);
    public final TalonSRX gripperLeft = new TalonSRX(0);


    private final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(10,1,4);

    int toggle = 0;

    private final AnalogInput intakeBumper = new AnalogInput(0); //Replace this value with actual value on comp bot, might have to change to analog


    public CubeHandler(){
        gripperLeft.enableCurrentLimit(true);

        gripperLeft.configPeakCurrentLimit(25,500);
        gripperLeft.configContinuousCurrentLimit(10,1000);

        gripperRight.enableCurrentLimit(true);
        gripperRight.configPeakCurrentLimit(25,500);
        gripperRight.configContinuousCurrentLimit(10,1000);
    }

    @Override
    protected void initDefaultCommand() {}

    public void stop(){
        gripperLeft.set(PercentOutput, 0);
        gripperRight.set(PercentOutput, 0);
        intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
        toggle = 0;

    }

    public void stopMotos(){
        gripperLeft.set(PercentOutput, 0);
        gripperRight.set(PercentOutput, 0);
        toggle = 0;

    }

    public void initGripper(){
        gripperLeft.set(PercentOutput, 0);
        gripperRight.set(PercentOutput, 0);
        intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void IntakeCube(){

        switch (toggle){
            case 0:
                gripperLeft.set(PercentOutput, 0.8);
                gripperRight.set(PercentOutput, -0.8);
                intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
                toggle = 1;
                break;
            case 1:

                intakeSolenoid.set(DoubleSolenoid.Value.kForward);
                toggle = 0;
                break;
        }
    }


    public void closeGripper(){
        intakeSolenoid.set(DoubleSolenoid.Value.kForward);


    }

    public void spitGear(){
        gripperLeft.set(PercentOutput, -1.0);
        gripperRight.set(PercentOutput, 1.0);

    }
}
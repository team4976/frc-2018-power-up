package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX gripperRight = new TalonSRX(8);
    public final TalonSRX gripperLeft = new TalonSRX(0);

    private final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(10,1,4);

    int toggle = 0;

    public CubeHandler(){
        gripperLeft.enableCurrentLimit(true);
        gripperLeft.configPeakCurrentLimit(40,3000);
        gripperLeft.configContinuousCurrentLimit(10,1000);

        gripperRight.enableCurrentLimit(true);
        gripperRight.configPeakCurrentLimit(40,3000);
        gripperRight.configContinuousCurrentLimit(12,1000);
    }

    @Override
    protected void initDefaultCommand() {}

    public void stop(){
        //Stop motors, reset toggle state and open gripper
        gripperLeft.set(PercentOutput, 0);
        gripperRight.set(PercentOutput, 0);
        intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
        toggle = 0;
    }

    public void stopMotors(){
        //Stop motors and reset toggle state
        gripperLeft.set(PercentOutput, 0);
        gripperRight.set(PercentOutput, 0);
        toggle = 0;
    }

    public void initGripper(){
        //Passmore Hack
        //Set gripper motors to 0 and close gripper upon startup
        gripperLeft.set(PercentOutput, 0);
        gripperRight.set(PercentOutput, 0);
        intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void IntakeCube(){
        //Toggles between Intake open and intake closed
        gripperLeft.set(PercentOutput, 0.8);
        gripperRight.set(PercentOutput, -0.8);
        switch (toggle){
            case 0:
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

    public void spitCube(){
        gripperLeft.set(PercentOutput, -1.0);
        gripperRight.set(PercentOutput, 1.0);
    }
}
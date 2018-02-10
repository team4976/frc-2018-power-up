//package ca._4976.powerup.subsystems;
//
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.command.Subsystem;
//
//public class Ramp extends Subsystem{
//    //Jack, Quoc, Charlotte, Corey
//
//    //Ramp motor used to rotate the ramp up and down
//    TalonSRX rampMotor = new TalonSRX(5);
//
//    //Solenoid used to deploy the ramp
//    DoubleSolenoid solenoidRamp = new DoubleSolenoid(0,1);
//
//    public void end(){}
//
//    //When start button is pushed fire solenoid to deploy the ramp
//    //Start and back button may also be held simultaneously as an override
//    public void deployRamp(){
//
//    }
//
//    //Upon the push of the right bumper the ramp motor will spin (counter)-clockwise upwards
//    public void rampUp(){
//
//    }
//
//    //Upon the push of the left bumper the ramp motor will spin (counter)-clockwise downwards
//    public void rampDown(){
//
//    //When the right bumper on the operator controller is held it will deploy the forks
//    public void deployForks(){
//        forkOneMotor.set(ControlMode.PercentOutput, 0.5);
//    }
//
//    //When the left bumper on the operator controller is held it will deploy the forks
//    public void retractForks(){
//        forkOneMotor.set(ControlMode.PercentOutput, -0.5);
//    }
//
//    //When the bumper(s) are released
//    public void stopForks(){
//        forkOneMotor.set(ControlMode.PercentOutput, 0);
//>>>>>>> Stashed changes
//    }
//
//    @Override
//    protected void initDefaultCommand() {
//
//    }
//}
//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Ramp extends Subsystem{

    //Ramp motor used to rotate the ramp up and down
    private final TalonSRX rampMotor = new TalonSRX(5);

    //Fork motors used to deploy and retract the two forks
    private final TalonSRX forkOneMotor = new TalonSRX(6);
    private final TalonSRX forkTwoMotor = new TalonSRX(7);

    public void end(){}

    //When start button is pushed the solenoid is fired to deploy the ramp
    public void deployRamp(){
        rampMotor.set(ControlMode.PercentOutput, 0.5);
    }

    //Stops the ramp motor
    public void stopDeployingRamp(){
        rampMotor.set(ControlMode.PercentOutput, 0);
    }

    //When the right bumper on the operator controller is held it will deploy the forks
    public void deployForks(){
        forkOneMotor.set(ControlMode.PercentOutput, 0.5);
        forkTwoMotor.set(ControlMode.PercentOutput, 0.5);
    }

    //When the left bumper on the operator controller is held it will deploy the forks
    public void retractForks(){
        forkOneMotor.set(ControlMode.PercentOutput, -0.5);
        forkTwoMotor.set(ControlMode.PercentOutput, -0.5);
    }

    //When the bumper(s) are released
    public void stopForks(){
        forkOneMotor.set(ControlMode.PercentOutput, 0);
        forkTwoMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    protected void initDefaultCommand() {
        //forkTwoMotor.follow(forkOneMotor);
    }
}

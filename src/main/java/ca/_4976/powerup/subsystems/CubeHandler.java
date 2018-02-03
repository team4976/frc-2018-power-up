//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Runnable, Sendable {
    public final TalonSRX grabberI = new TalonSRX(1);
    private final TalonSRX grabberII = new TalonSRX(2);
    private final NetworkTableInstance whateverTheFuckYouWant =  NetworkTableInstance.getDefault();
    private final NetworkTable theFuckYouWantTable = whateverTheFuckYouWant.getTable("Motor Speeds");
    private final NetworkTableEntry sped= theFuckYouWantTable.getEntry("grabber full speed");
    private final NetworkTableEntry slow = theFuckYouWantTable.getEntry("grabber slow speed");

    @Override
    public void initSendable(SendableBuilder builder) {
        setName("Cube Motor Speeds");
        builder.setSmartDashboardType("CuMotorSped");
        builder.addDoubleProperty("grabber full speed",()-> sped.getDouble(0), ignored -> {});
        builder.addDoubleProperty("grabber slow speed", ()-> sped.getDouble(0), ignored -> {});
    }

    @Override
    protected void initDefaultCommand() {
//        grabberII.follow(grabberI);
    }

    @Override
    public void run() {

    }

    public void grab() {//grabs cube
        System.out.println("my boi is to grab me");
        grabberI.set(ControlMode.PercentOutput, 0.1);
        grabberII.set(ControlMode.PercentOutput, -0.8);
        System.out.println("Grabber 1 sped "+grabberI.getMotorOutputPercent());
        System.out.println("Grabber 2 sped "+grabberII.getMotorOutputPercent());
    }
    public void stop(){//Stops the grabber motors
        System.out.println("no longer moving");
        grabberI.set(ControlMode.PercentOutput, 0);
        grabberII.set(ControlMode.PercentOutput, 0);
    }
    public void release() {//Releases cube from bot
        System.out.println("ejection is in effect");
        grabberI.set(ControlMode.PercentOutput, -0.1);
        grabberII.set(ControlMode.PercentOutput, 0.1);
    }

    public void slow(){//spins motors slow when we have a cube
        System.out.println("have cube");
        grabberI.set(ControlMode.PercentOutput, 1);
        grabberII.set(ControlMode.PercentOutput, -1);
    }
    public void test(){
        System.out.println(sped.getDouble(0));
        System.out.println(slow.getDouble(0));
    }

}

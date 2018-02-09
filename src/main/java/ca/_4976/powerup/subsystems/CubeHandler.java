//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import ca._4976.powerup.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX grabberI = new TalonSRX(1);
    private final TalonSRX grabberII = new TalonSRX(2);
    private final NetworkTableInstance whateverTheFuckYouWant =  NetworkTableInstance.getDefault();
    private final NetworkTable theFuckYouWantTable = whateverTheFuckYouWant.getTable("Motor Speeds");
    private final NetworkTableEntry sped= theFuckYouWantTable.getEntry("grabber full speed");
    private final NetworkTableEntry slow = theFuckYouWantTable.getEntry("grabber slow speed");
    private final NetworkTableEntry current= theFuckYouWantTable.getEntry("motor current");
    private double speedFast=0, notFast=0, grabCurrent=0;
    public CubeHandler(){
        sped.setPersistent();
        sped.setDouble(sped.getDouble(0));
        slow.setPersistent();
        slow.setDouble(slow.getDouble(0));
        current.setPersistent();
        current.setDouble(current.getDouble(0));

    }
    @Override
    public void initSendable(SendableBuilder builder) {//Graber speed and current network tables
        setName("Cube Motor Speeds");
        builder.addDoubleProperty("grabber full speed",this::getSpeedFast, this::setSpeedFast);
        builder.addDoubleProperty("grabber slow speed", this::getNotFast, this::setNotFast);
        builder.addDoubleProperty("grabber current", this::getGrabCurrent, this::setGrabCurrent);
    }
    @Override
    protected void initDefaultCommand() {
//      grabberII.follow(grabberI);
    }

    public void grab() {//grabs cube
        System.out.println("my boi is to grab me");
        grabberI.set(ControlMode.PercentOutput, 0.8);
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
        grabberI.set(ControlMode.PercentOutput, 0.05);
        grabberII.set(ControlMode.PercentOutput, -0.05);
    }
    public void test(){
        System.out.println(sped.getDouble(0));
        System.out.println(slow.getDouble(0));
    }
    public boolean checkCurrent(){
        double normalDraw = 5;
        if (grabberI.getMotorOutputPercent() == 0) return true;
        else if (grabberI.getOutputCurrent() > normalDraw) {
            slow();
            return true;
        }
        else return false;
    }

    public void setSpeedFast(double speedFast) {
        this.speedFast = speedFast;
    }

    public void setGrabCurrent(double grabCurrent) {
        this.grabCurrent = grabCurrent;
    }

    public void setNotFast(double notFast) {
        this.notFast = notFast;
    }
    private double getSpeedFast(){
        return this.speedFast;
    }
    private double getNotFast(){
        return this.notFast;
    }
    private double getGrabCurrent(){
        return this.grabCurrent;
    }
}

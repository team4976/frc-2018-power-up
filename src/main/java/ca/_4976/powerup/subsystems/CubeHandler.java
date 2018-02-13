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

import static ca.qormix.library.Lazy.use;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX grabberI = new TalonSRX(0);
    private final TalonSRX grabberII = new TalonSRX(1);

    private double speedFast=0, notFast=0, grabCurrent=0;
    public CubeHandler(){
        use(NetworkTableInstance.getDefault().getTable("Grabber"), it -> {

            NetworkTableEntry fullSpeed = it.getEntry("Full Speed");
            NetworkTableEntry slowSpeed = it.getEntry("Slow Speed");
            NetworkTableEntry current = it.getEntry("Current");

            fullSpeed.setDefaultDouble(0);
            slowSpeed.setDefaultDouble(0);
            current.setDefaultDouble(0);
        });
    }

    @Override
    protected void initDefaultCommand() {

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

    public boolean checkCurrent(){
        double normalDraw = 5;
        if (grabberI.getMotorOutputPercent() == 0) return true;
        else if (grabberI.getOutputCurrent() > normalDraw) {
            slow();
            return true;
        }
        else return false;
    }
}

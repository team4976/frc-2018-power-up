//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import ca._4976.powerup.commands.ResetGrab;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX grabberI = new TalonSRX(0);

    public boolean runIntake = false;
    private boolean normalSpead = true;

    private double speedFast, notFast, grabCurrent;
    public CubeHandler(){
        use(NetworkTableInstance.getDefault().getTable("Grabber"), it -> {

            NetworkTableEntry fullSpeed = it.getEntry("Full Speed");
            NetworkTableEntry slowSpeed = it.getEntry("Slow Speed");
            NetworkTableEntry current = it.getEntry("Current");

            fullSpeed.setDefaultDouble(1);
            slowSpeed.setDefaultDouble(0.25);
            current.setDefaultDouble(35);
            speedFast=fullSpeed.getDouble(0);
            notFast=slowSpeed.getDouble(0);
            grabCurrent=current.getDouble(0);
        });
    }

    @Override
    protected void initDefaultCommand() {}


    public void grab() {//grabs cube
        System.out.println(grabberI.getMotorOutputPercent());
        System.out.println(grabberI.getOutputCurrent());
        if (normalSpead){
            System.out.println("my boi is to grab me");
            grabberI.set(ControlMode.PercentOutput, speedFast);
            if (grabberI.getOutputCurrent() > grabCurrent){
                normalSpead = false;
            }
        }
        else {
            grabberI.set(ControlMode.PercentOutput, notFast);
            runIntake = true;
        }
    }
    public void resetGrab() {
        runIntake=false;
    }
    public void stop(){//Stops the grabber motors
        System.out.println("no longer moving");
        grabberI.set(ControlMode.PercentOutput, 0);
        runIntake=true;
        normalSpead = true;
    }
    public void release() {//Releases cube from bot
        System.out.println("ejection is in effect");
        runIntake = true;
        normalSpead = true;
        grabberI.set(ControlMode.PercentOutput, -speedFast);
    }
}

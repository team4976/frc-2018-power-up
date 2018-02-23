//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


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

    public boolean returnRunIntake = false;
    public boolean normalSpead = true;
    public boolean recordIntake = false;
    boolean kill = false;

    private double speedFast, notFast, grabCurrent;
    public CubeHandler(){
        use(NetworkTableInstance.getDefault().getTable("Grabber"), it -> {
            NetworkTableEntry fullSpeed = it.getEntry("Full Speed");
            NetworkTableEntry slowSpeed = it.getEntry("Slow Speed");
            NetworkTableEntry current = it.getEntry("Current");

            fullSpeed.setDefaultDouble(0.6);
            slowSpeed.setDefaultDouble(0.3);
            current.setDefaultDouble(25);
            speedFast=fullSpeed.getDouble(0);
            notFast=slowSpeed.getDouble(0);
            grabCurrent=current.getDouble(0);
        });
    }

    @Override
    protected void initDefaultCommand() {

    }


    public void grab() {//grabs cube
        recordIntake = true;
        System.out.println("Grabbing");
        if (grabberI.getOutputCurrent() > grabCurrent && !kill){
            grabberI.set(ControlMode.PercentOutput, notFast);
            normalSpead = false;
        } else if (normalSpead == true && !kill){
            grabberI.set(ControlMode.PercentOutput, speedFast);
        } else {
            grabberI.set(ControlMode.PercentOutput, 0);
        }
        kill = false;

    }
    public void stop(){//Stops the grabber motors
        System.out.println("STopping");
        kill = true;
        returnRunIntake = true;
        normalSpead = true;
        grabberI.set(ControlMode.PercentOutput, 0);

    }
    public void release() {//Releases cube from bot
        System.out.println("Releasinbg");
        if (!kill){
            grabberI.set(ControlMode.PercentOutput, -speedFast);
        } else {
            grabberI.set(ControlMode.PercentOutput, 0);
        }
        returnRunIntake = true;
        normalSpead = true;
        kill = false;

    }

    public void disableGripper(){
        grabberI.set(ControlMode.PercentOutput, 0);
        kill = true;
        returnRunIntake = true;
        normalSpead = true;
    }

    public boolean checkCurrent(){
        return returnRunIntake;
    }
}

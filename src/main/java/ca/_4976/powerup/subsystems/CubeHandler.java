//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import ca._4976.powerup.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX grabberI = new TalonSRX(0);
    private final AnalogInput currentDraw = new AnalogInput(9);

    public boolean runIntakeForwards = false;
    public boolean runIntakeReverse = false;

    private double speedFast, notFast, grabCurrent;
    public CubeHandler(){
        use(NetworkTableInstance.getDefault().getTable("Grabber"), it -> {

            NetworkTableEntry fullSpeed = it.getEntry("Full Speed");
            NetworkTableEntry slowSpeed = it.getEntry("Slow Speed");
            NetworkTableEntry current = it.getEntry("Current");

            fullSpeed.setDefaultDouble(-0.75);
            slowSpeed.setDefaultDouble(0);
            current.setDefaultDouble(15);
            speedFast=fullSpeed.getDouble(0);
            notFast=slowSpeed.getDouble(0);
            grabCurrent=current.getDouble(0);
        });
    }

    @Override
    protected void initDefaultCommand() {}


    public void grab() {//grabs cube
        if (runIntakeForwards){
            grabberI.set(ControlMode.PercentOutput, -0.75);
            if (currentDraw.getValue() > 20){
                grabberI.set(ControlMode.PercentOutput, 0.2);
            }
        }
    }
    public void stop(){//Stops the grabber motors
        runIntakeForwards = false;
        runIntakeForwards = false;

    }
    public void release() {//Releases cube from bot
        if (runIntakeReverse){
            grabberI.set(ControlMode.PercentOutput, 0.75);
        }
    }

}

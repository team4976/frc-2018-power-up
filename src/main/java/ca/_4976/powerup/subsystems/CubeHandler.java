//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

import static ca.qormix.library.Lazy.use;
import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX grabberI = new TalonSRX(0);

    //public boolean countCubeCube = false;

    public boolean currentFlag = false;
    private int intakeCounter = 0;
    private int aButtonCount = 0;
    private double speedFast, notFast, grabCurrent, Spit;

    public CubeHandler(){
        use(NetworkTableInstance.getDefault().getTable("Grabber"), it -> {

            NetworkTableEntry fullSpeed = it.getEntry("Full Speed");
            NetworkTableEntry slowSpeed = it.getEntry("Slow Speed");
            NetworkTableEntry current = it.getEntry("Current");
            NetworkTableEntry spit = it.getEntry("Spit");

            fullSpeed.setDefaultDouble(-0.8); //0.8 LOUD AF
            slowSpeed.setDefaultDouble(-0.2);
            current.setDefaultDouble(35);
            spit.setDefaultDouble(1); //1 LOUD AF
            Spit=fullSpeed.getDouble(0);
            speedFast=fullSpeed.getDouble(0);
            notFast=slowSpeed.getDouble(0);
            grabCurrent=current.getDouble(0);
        });
    }

    @Override
    protected void initDefaultCommand() {}

    public void resetFlags(){
        currentFlag = false;
        intakeCounter = 0;
    }


    public void stop(){
        grabberI.set(PercentOutput, 0);
        currentFlag = true;
        intakeCounter = 0;
    }

    public void intakeCube() {
        grabberI.set(PercentOutput, speedFast);
        currentFlag = false;
    }

    public void cubeCurrent() {
        if (grabberI.getOutputCurrent() > grabCurrent && intakeCounter > 10) {
            grabberI.set(PercentOutput, notFast);
            currentFlag = true;
        }

//        System.out.println("\n\n*********THE CURRENT IS " + grabberI.getOutputCurrent());
//        System.out.println("*********THE COUNTER IS " + intakeCounter + "\n\n");

        intakeCounter++;
    }
    public void spitGear(){
        grabberI.set(PercentOutput, -Spit);
        currentFlag = false;
        intakeCounter = 0;
    }

    public void printCurrent(){
        System.out.println("\nCurrent: " + grabberI.getOutputCurrent() + "\n");
    }

    public void incrementACount(){
        aButtonCount++;
    }

    public int getAButtonCount(){
        return aButtonCount;
    }

    public void resetACount(){
        aButtonCount = 0;
    }

}
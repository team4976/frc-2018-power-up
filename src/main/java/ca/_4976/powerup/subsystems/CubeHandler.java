//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import ca._4976.powerup.Robot;
import ca._4976.powerup.commands.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import static ca.qormix.library.Lazy.use;
import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

//Main superclass that holds all the methods used by the commands
public final class CubeHandler extends Subsystem implements Sendable {
    public final TalonSRX grabberL = new TalonSRX(0);
    public final TalonSRX grabberR = new TalonSRX(4);
    public final DoubleSolenoid inSolenoid = new DoubleSolenoid(10,7,5);//real values

    private final AnalogInput intakeBumper = new AnalogInput(0); //Replace this value with actual value on comp bot, might have to change to analog
    // (<10) -> pressed, (~190) -> not pressed

    private double speedFast=0.8, notFast=0.3, spit =-0.6;
    public boolean haveCube=true;
    private boolean open=false;

    @Override
    public void initSendable(SendableBuilder builder) {
        setName("Grabber");
        builder.setSafeState(this::stop);
        builder.addDoubleProperty("Fast Speed",()->speedFast,it->speedFast=it);
        builder.addDoubleProperty("Fast Speed",()->notFast,it->notFast=it);
        builder.addDoubleProperty("Fast Speed",()->spit,it->spit=it);
    }

    public CubeHandler(){}

    @Override
    protected void initDefaultCommand() {}

    public void stop(){
        new CloseGroup().cancel();
        new IntakeCube().cancel();
        grabberL.set(PercentOutput, 0);
        grabberR.set(PercentOutput, 0);
    }

    public void close(){
        inSolenoid.set(DoubleSolenoid.Value.kForward);
        open=false;
    }

    public void rumbleOn(){
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble,1);
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble,1);
    }

    public void rumbleOff(){
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kRightRumble,0);
        Robot.oi.driver.setRumble(GenericHID.RumbleType.kLeftRumble,0);
    }
    public void open(){
        inSolenoid.set(DoubleSolenoid.Value.kReverse);
        open=true;
    }

    public void bumper(){
        if (intakeBumper.getValue()<10&&open) new CloseGroup().start();
    }
    public void spinFast() {
        grabberL.set(PercentOutput, speedFast);
        grabberR.set(PercentOutput, -speedFast);
    }

    public void check(){
        if (open){
            new CloseGroup().start();}
        else {
            new IntakeCube().start();
            new OpenClaw().start();
        }
    }

    public void spinSlow() {
            grabberL.set(PercentOutput, notFast);
            grabberR.set(PercentOutput, -notFast);
    }

    public void spitCube(){
        grabberL.set(PercentOutput, spit);
        grabberR.set(PercentOutput, -spit);
    }

}
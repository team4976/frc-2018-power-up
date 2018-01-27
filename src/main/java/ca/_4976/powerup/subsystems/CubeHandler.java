//Group members: Michael, Nick, Jessy, Ian
package ca._4976.powerup.subsystems;


import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;

public final class CubeHandler extends Subsystem implements Runnable, Sendable {
    private final TalonSRX grabberI = new TalonSRX(0);
    private final TalonSRX grabberII = new TalonSRX(1);
    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void run() {

    }
    public void grab(){//grabs cube

    }
    public void release(){//Releases cube from bot

    }
    public void forceRelease(){//overrides cube release

    }
    public void forceGrab(){//overrides cube grab

    }
}

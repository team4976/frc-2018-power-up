//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.subsystems;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    // Climber solenoid
    private final DoubleSolenoid climbingShift = new DoubleSolenoid(10,1,4);

    // Guides solenoid
    private final DoubleSolenoid guides = new DoubleSolenoid(10,0,6);

    @Override
    protected void initDefaultCommand() {}


    public void activateClimber(){

        climbingShift.set(DoubleSolenoid.Value.kForward);
        guides.set(DoubleSolenoid.Value.kReverse);
        Robot.elevator.setClimberShifted(true);
    }

    public void deactivateClimber(){

        climbingShift.set(DoubleSolenoid.Value.kReverse);
        guides.set(DoubleSolenoid.Value.kForward);
        Robot.elevator.setClimberShifted(false);
    }

}

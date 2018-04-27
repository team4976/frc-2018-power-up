package ca._4976.powerup.subsystems;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    //Solenoid used to change the elevator from high to low gear and vice-versa
    public final DoubleSolenoid climbingShift = new DoubleSolenoid(10,6,0);

    //Solenoid used to deploy the guides for climbing
    //Variable to show whether in high gear or low gear
    private boolean lowGearElevator = false;
    //Variable used to determine if left/right bumper was pressed.
    public int endGameToggle = 0;


    //When the start button on the operator controller is pressed change the elevator gear from low to high and vice-versa
    //Also toggles the guides used for climbing
    public void activateClimber(){
        climbingShift.set(DoubleSolenoid.Value.kForward);
        Robot.elevator.setClimberShifted(true);
        lowGearElevator = true;
    }

    public void deactivateClimber(){
        climbingShift.set(DoubleSolenoid.Value.kReverse);
        Robot.elevator.setClimberShifted(false);
        lowGearElevator = false;
    }

    //Increases the variable endGameToggle, which is used to say when we are ready to climb
    public void endGameIncrease(){
        endGameToggle++;
    }

    //Decreases the variable endGameToggle, which is used to say when we are ready to climb
    public void endGameDecrease(){
        endGameToggle--;
    }

    @Override
    protected void initDefaultCommand(){

    }

}
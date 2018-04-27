//Jack, Quoc, Charlotte, Corey
package ca._4976.powerup.subsystems;

import ca._4976.powerup.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    //TODO - COMMENTED OUT CLIMBERSHIFT SOLENOID - CONFLICT WITH DRIVE

    //Solenoid used to change the elevator from high to low gear and vice-versa
    public final DoubleSolenoid climbingShift = new DoubleSolenoid(10,6,0);

    //Solenoid used to deploy the guides for climbing
    //Variable to show whether in high gear or low gear
    private boolean lowGearElevator = false;
    //Variable used to determine if left/right bumper was pressed.
    public int endGameToggle = 0;

    private boolean guideState = true;

    //When the start button on the operator controller is pressed change the elevator gear from low to high and vice-versa
    //Also toggles the guides used for climbing
    public void activateClimber(){
        System.out.println("Activating climber");
        climbingShift.set(DoubleSolenoid.Value.kForward);
        Robot.elevator.setClimberShifted(true);
        lowGearElevator = true;
    }

    public void deactivateClimber(){
        //   System.out.println("Deactivating climbver");
        climbingShift.set(DoubleSolenoid.Value.kReverse);
        Robot.elevator.setClimberShifted(false);
        lowGearElevator = false;
    }

    public void guides(){
        System.out.println("Guide state is " + guideState);

        if (guideState == true){
            //guides.set(DoubleSolenoid.Value.kForward);
            System.out.println("Firing in reverse and guide state is " + guideState);

            guideState = false;
        }

        else if (guideState == false){
            //guides.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Firing forward and guide state is " + guideState);
            guideState = true;
        }
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
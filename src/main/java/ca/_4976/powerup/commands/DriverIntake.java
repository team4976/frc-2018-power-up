package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.CubeHandler;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

//main controller grabbing the cube

public class DriverIntake extends ListenableCommand {
//    DriverIntake()



    @Override
    protected void initialize() {
        System.out.println("initializing the intaking");
        //when the button is pressed once start the grab
        Robot.cubeHandler.grab();
    }

    private double normalDraw = 5;

    @Override
    protected void execute() {

//        will continue to grab until the current draw spikes
        System.out.println("executing the intaking");
        System.out.println(Robot.cubeHandler.grabberI.getOutputCurrent());
        System.out.println(Robot.cubeHandler.grabberI.getMotorOutputPercent());

    }

    @Override
    protected boolean isFinished() {
        return Robot.cubeHandler.checkCurrent();
    }
}


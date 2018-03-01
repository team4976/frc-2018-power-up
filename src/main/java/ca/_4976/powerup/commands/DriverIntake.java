package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

//main controller grabbing the cube

public class DriverIntake extends ListenableCommand {
//    DriverIntake()



    @Override
    protected void initialize() {
        System.out.println("initializing the intaking");
        //when the button is pressed once start the grab
    }

    @Override
    protected void execute() {
//        will continue to grab until the current draw spikes
        System.out.println("executing the intaking");
        System.out.println(Robot.cubeHandler.grabberI.getOutputCurrent());
        System.out.println(Robot.cubeHandler.grabberI.getMotorOutputPercent());
        Robot.cubeHandler.grab();

    }

    @Override
    protected boolean isFinished() {
        //return Robot.cubeHandler.runIntake;
        return Robot.cubeHandler.runIntake;
    }
}


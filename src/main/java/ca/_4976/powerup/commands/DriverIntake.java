package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.CubeHandler;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

//main controller grabbing the cube

public class DriverIntake extends Command {
//    DriverIntake()
    private boolean stop=false;

    private final NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private final NetworkTable table = instance.getTable("Current Draw");
    private final NetworkTableEntry current = table.getEntry("Default Current");

    @Override protected void initialize(){
        System.out.println("initializing the intaking");
        //when the button is pressed once start the grab
        Robot.cubeHandler.grab();
    }

    @Override protected void execute() {
//        will continue to grab until the current draw spikes
        System.out.println("executing the intaking");
        System.out.println(Robot.cubeHandler.grabberI.getOutputCurrent());
        double normalDraw = 0.2;
        if (Robot.cubeHandler.grabberI.getOutputCurrent()> normalDraw){
            Robot.cubeHandler.slow();
            stop=true;
        }
        Robot.cubeHandler.run();
    }

    @Override protected boolean isFinished() {

        return stop;

    }

    @Override protected void end(){}
}

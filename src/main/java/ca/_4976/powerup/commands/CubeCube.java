package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;

/**
 * Created by 4976 on 2018-03-09.
 */
public class CubeCube extends ListenableCommand{

    @Override
    protected void initialize(){
        Robot.cubeHandler.incrementACount();

        if(Robot.cubeHandler.getAButtonCount() != 2) {
            Robot.cubeHandler.intakeCube();
        }
    }

    @Override
    protected void execute(){
        Robot.cubeHandler.cubeCurrent();
    }

    @Override
    protected boolean isFinished(){
        if(Robot.cubeHandler.getAButtonCount() != 2) {
            return Robot.cubeHandler.currentFlag;
        } else {
            return true;
        }
    }

    @Override
    protected void end(){

//        if(Robot.cubeHandler.getAButtonCount() == 2  && !Robot.motion.isRecording()){
//            new ArmSwitch().start();
//        }
//
//        else {
//            Robot.cubeHandler.resetFlags();
//        }
    }
}

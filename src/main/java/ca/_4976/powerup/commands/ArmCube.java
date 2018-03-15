//package ca._4976.powerup.commands;
//
//import ca._4976.powerup.Robot;
//
///**
// * Created by 4976 on 2018-02-17.
// */
//public class ArmCube extends ListenableCommand {
//
//    @Override
//    protected void initialize(){
//        System.out.println("Arm cube ran");
//        Robot.linkArm.moveArmCube();
//    }
//
//    @Override
//    protected boolean isFinished() {
//        return Robot.elevator.testInputs() || Robot.linkArm.checkArmCube();
//    }
//
//    @Override
//    protected void end(){
//        Robot.linkArm.resetArmFlags();
//        Robot.linkArm.setHoldingSpeed();
//    }
//}

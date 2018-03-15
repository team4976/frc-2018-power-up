//package ca._4976.powerup.commands;
//
//import ca._4976.powerup.Robot;
//
///**
// * Created by 4976 on 2018-02-17.
// */
//public class Arm45 extends ListenableCommand{
//
//    public Arm45(){
//        requires(Robot.linkArm);
//    }
//
//    @Override
//    protected void initialize(){
//        Robot.linkArm.moveArm45();
//    }
//
//    @Override
//    protected boolean isFinished() {
//        return Robot.elevator.testInputs() || Robot.linkArm.checkArm45();
//        //|| Robot.linkArm.testArmInput()
//    }
//
//    @Override
//    protected void end(){
//        Robot.linkArm.resetArmFlags();
//        Robot.linkArm.setHoldingSpeed();
//    }
//}

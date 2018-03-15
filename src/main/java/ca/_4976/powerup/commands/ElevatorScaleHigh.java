//package ca._4976.powerup.commands;
//
//import ca._4976.powerup.Robot;
//
///**
// * Created by 4976 on 2018-02-15.
// */
//public class ElevatorScaleHigh extends ListenableCommand {
//
//    public ElevatorScaleHigh(){
//        requires(Robot.elevator);
//    }
//
//    @Override
//    protected void initialize(){
//        Robot.elevator.moveToHighScale();
//    }
//
//    @Override
//    protected boolean isFinished() {
//        return Robot.elevator.testInputs() || Robot.elevator.checkHighScale();
//    }
//
//    @Override
//    protected void end(){
//        Robot.elevator.resetPresetFlags();
//        Robot.elevator.stop();
//    }
//}

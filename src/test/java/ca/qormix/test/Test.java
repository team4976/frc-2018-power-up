package ca.qormix.test;

import ca._4976.powerup.Robot;
import ca._4976.powerup.subsystems.Drive;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Test {

    public static double output = 0;

    public static void main(String... args) throws InterruptedException {

        HLUsageReporting.SetImplementation(new HLUsageReporting.Interface() {
            @Override public void reportScheduler() { }
            @Override public void reportPIDController(int num) { }
            @Override public void reportSmartDashboard() { }
        });

        Drive drive = Robot.drive;
        drive.resetEncoderPosition();

        NetworkTableInstance.getDefault().setServer("localhost");
        NetworkTableInstance.getDefault().startServer();

        SmartDashboard.putData(new SendableTest());

        while (true) {

            System.out.println("Still Running");
            Thread.sleep(1000);
        }
    }

    static class SendableTest implements Sendable {

        @Override public String getName() { return "Drive Test"; }

        @Override public void setName(String name) { }

        @Override public String getSubsystem() { return null; }

        @Override public void setSubsystem(String subsystem) { }

        @Override public void initSendable(SendableBuilder builder) {

            builder.setSmartDashboardType("Encoder");

            builder.addDoubleProperty("Speed", () -> output, it -> output = it);
        }
    }
}

package ca._4976.powerup;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * This is the main class for running the Robot code.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public final class Robot extends IterativeRobot {

    public static OI oi;
    public final static Elevator elevator = new Elevator();
    public static EndGame eg;

    @Override public void robotInit() {
        oi = new OI();
    }

    @Override public void disabledInit() {

    }

    @Override public void autonomousPeriodic(){

    }

    @Override public void teleopPeriodic(){
        eg = new EndGame();


    }

    private void log() {

    }
}

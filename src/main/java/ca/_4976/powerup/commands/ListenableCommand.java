package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.data.Initialization;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is a wrapper for commands that report to the Motion subsystem
 * when a new command is created and run. Use me as an alternative for
 * Command when creating commands that are expected to be recorded
 * for autonomous.
 *
 * @see edu.wpi.first.wpilibj.command.Command
 */
public abstract class ListenableCommand extends Command {

    private final int id;

    ListenableCommand() {

        id = Initialization.commands.size();
        Initialization.commands.add(this);
            System.out.println("Command added to array");
    }

    @Override public void start() {

        Robot.motion.report.add(id);
        super.start();
    }
}
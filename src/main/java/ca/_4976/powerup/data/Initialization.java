package ca._4976.powerup.data;

import edu.wpi.first.wpilibj.command.Command;

import ca._4976.powerup.commands.ListenableCommand;

import java.util.ArrayList;

/**
 * While the robot is initializing the OI we store the commands here.
 * later we clear this memory after saving the list in an immutable
 * location.
 */
public final class Initialization {

    public static ArrayList<ListenableCommand> commands = new ArrayList<>();
}
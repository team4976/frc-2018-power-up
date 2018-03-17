package ca._4976.powerup.commands;

import ca._4976.powerup.data.Profile;
import edu.wpi.first.wpilibj.command.Command;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This command allows saving motion profiles to the rio's file system.
 * The default directory is /home/lvuser/motion.
 */
public final class SaveProfile extends Command {

    private final Profile profile;

    /**
     * @param profile this profile will be saved to file when the command initializes.
     */
    public SaveProfile(Profile profile) { this.profile = profile; }

    @Override protected void initialize() {

        try {

            //Making sure the directory is already available
            Profile.getAvailableProfiles();

            BufferedWriter writer = new BufferedWriter(new FileWriter(
                    new File("/home/lvuser/motion/" + profile.name + " - " + profile.version)));

            //CVS is currently the only format implemented
            writer.write(profile.serialize(Profile.Format.CSV));
            writer.close();

        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override protected boolean isFinished() {
        return true;
    }
}
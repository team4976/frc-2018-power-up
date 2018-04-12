package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.data.Profile;
import edu.wpi.first.wpilibj.command.Command;

import java.io.*;
import java.sql.SQLOutput;

/**
 *  This command allows loading motion profiles from the rio's file system
 *  then saved to memory ready to be run. The default directory is
 *  /home/lvuser/motion.
 */
public final class LoadProfile extends Command {

    private final String file;

    /**
     * @param file this file will be read from the default directory and loaded
     *             as a profile.
     */
    Profile loadedProfile;
    public LoadProfile(String file) {
        this.file = file;
        try {

            BufferedReader reader = new BufferedReader(new FileReader(
                    new File("/home/lvuser/motion/" + file)));

            StringBuilder builder = new StringBuilder();

            for (String line = reader.readLine(); line!= null; line = reader.readLine()) builder.append(line).append("\n");

            //CVS is currently the only format implemented
            loadedProfile = Profile.deserialize(builder.toString(), Profile.Format.CSV);

            // Robot.motion.profile = Profile.deserialize(builder.toString(), Profile.Format.CSV);
            reader.close();

            System.out.println("Successfully loaded " + file + " runs for " + loadedProfile.moments.length / 200.0 + " seconds");

        } catch (IOException e) { e.printStackTrace(); }

    }

    public void preLoad(){

    }

    @Override protected void initialize() {
        Robot.motion.profile = loadedProfile;


    }

    @Override protected boolean isFinished() {
        return true;
    }
}
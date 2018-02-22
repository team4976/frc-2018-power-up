package ca._4976.powerup.commands;

import ca._4976.powerup.Robot;
import ca._4976.powerup.data.Profile;
import edu.wpi.first.wpilibj.command.Command;

import java.io.*;

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
    public LoadProfile(String file) {

        requires(Robot.motion);
        this.file = file;
    }

    @Override protected void initialize() {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(
                    new File("/home/lvuser/motion/" + file)));

            StringBuilder builder = new StringBuilder();

            for (String line = reader.readLine(); !line.equals(""); line = reader.readLine()) builder.append(line);

            //CVS is currently the only format implemented
            Profile.deserialize(builder.toString(), Profile.Format.CSV);
            reader.close();

        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override protected boolean isFinished() {
        return true;
    }
}
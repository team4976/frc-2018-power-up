package ca._4976.powerup.data;

import ca._4976.powerup.Robot;
import ca._4976.powerup.data.Moment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the data we store for a motion profile. There
 * are also functions for serializing the data.
 */
public final class Profile {

    public enum Format { JSON, CSV, EXCEL }

    public final String name;
    public final String version;
    public final Moment[] moments;

    public Profile(String name, String version, Moment[] moments) {

        this.name = name;
        this.version = version;
        this.moments = moments;
    }

    public String serialize(Format format) {

        StringBuilder builder = new StringBuilder();

        switch (format) {

            default:

                builder.append("Motion Profile: ").append(name).append(" ").append(version).append('\n');
                builder.append("Left Output,Right Output,,Left Position,Right Position,,Left Velocity,Right Velocity\n");

                for (Moment moment : moments) {

                    builder.append("\n");
                    builder.append(moment.output[0]).append(",").append(moment.output[1]).append(",,");
                    builder.append(moment.position[0]).append(",").append(moment.position[1]).append(",,");
                    builder.append(moment.velocity[0]).append(",").append(moment.velocity[1]);

                    if (moment.commands.length > 0) builder.append(",");

                    for (int command : moment.commands) builder.append(",").append(Robot.motion.commands[command].getName());
                }

                return builder.toString();

            case JSON:

                builder.append("{\n");
                builder.append("\tname:").append(name).append("\n");
                builder.append("\tversion:\"").append(version).append("\"\n");

                for (Moment moment : moments) {

                    builder.append("\t{\n");

                    if (moment.commands.length > 0) {

                        builder.append("\t\tmoments:[");

                        for (int i = 0; i < moment.commands.length; i++) {

                            builder.append("\"").append(Robot.motion.commands[moment.commands[i]].getName()).append("\"");
                            if (i < moment.commands.length - 1) builder.append(", ");
                        }

                        builder.append("]\n");
                    }

                    builder.append("output:[").append(moment.output[0]).append(", ").append(moment.output[1]).append("]\n");
                    builder.append("position:[").append(moment.position[0]).append(", ").append(moment.position[1]).append("]\n");
                    builder.append("velocity:[").append(moment.velocity[0]).append(", ").append(moment.velocity[1]).append("]\n");

                    builder.append("\t}\n");
                }

                builder.append("}");

                return builder.toString();

            case EXCEL:

                throw new UnsupportedOperationException("Not Implemented");
        }
    }

    public static Profile blank() { return new Profile("null", "null", new Moment[0]); }

    public static Profile deserialize(String fileOutput, Format format) {

        switch (format) {

            default:

                String[] lines = fileOutput.split("\n");
                ArrayList<Moment> moments = new ArrayList<>();

                for (int i = 3; i < lines.length; i++) {

                    String[] split = lines[i].split(",");

                    Integer[] commands = new Integer[split.length - 8];

                    for (int c = 0; c < commands.length; c++) {

                        for (int x = 0; x < Robot.motion.commands.length; x++) {

                            if (split[c + 9].equals(Robot.motion.commands[x].getName())) {

                                commands[c] = x;
                                break;
                            }
                        }
                    }


                    moments.add(new Moment(
                            commands,
                            new Double[] { Double.parseDouble(split[0]), Double.parseDouble(split[1]) },
                            new Double[] { Double.parseDouble(split[3]), Double.parseDouble(split[4]) },
                            new Double[] { Double.parseDouble(split[6]), Double.parseDouble(split[7]) }
                    ));
                }

                String[] split = lines[0].split(": ")[1].split(" ");

                return new Profile(
                       split[0],
                       split[1],
                       moments.toArray(new Moment[moments.size()])
                );

            case JSON:

                throw new UnsupportedOperationException("Not Implemented");

            case EXCEL:

                throw new UnsupportedOperationException("Not Implemented");
        }
    }

    public static String[] getAvailableProfiles() {

        try {

            File folder = new File("/home/lvuser/motion");

            if (!folder.exists()) Runtime.getRuntime().exec("mkdir /home/lvuser/motion");

            return folder.list();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return new String[0];
    }
}

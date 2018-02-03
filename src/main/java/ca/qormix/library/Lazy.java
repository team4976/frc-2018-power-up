package ca.qormix.library;

import edu.wpi.first.networktables.NetworkTable;

/**
 *  Don't judge me we are all lazy.
 *  This class contains functions for simplifying existing code.
 */
public class Lazy {

    public interface Using<T> { T use(T it); }

    public interface Use<T> { void use(T it); }

    public static boolean using(boolean in, Using<Boolean> user) { return user.use(in); }

    public static long using(long in, Using<Long> user) { return user.use(in); }

    public static int using(int in, Using<Integer> user) { return user.use(in); }

    public static double using(double in, Using<Double> user) { return user.use(in); }

    public static void use(boolean in, Use<Boolean> user) { user.use(in); }

    public static void use(long in, Use<Long> user) { user.use(in); }

    public static void use(int in, Use<Integer> user) { user.use(in); }

    public static void use(double in, Use<Double> user) { user.use(in); }

    public static void use(Double[] in, Use<Double[]> user) { user.use(in); }

    public static void use(NetworkTable in, Use<NetworkTable> user) { user.use(in); }

}

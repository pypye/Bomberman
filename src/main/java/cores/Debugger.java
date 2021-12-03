package cores;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Debugger {
    private static boolean DEBUG = true;
    public static final String EVENT = "Event";
    public static final String SCENE = "Scene";
    public static final String GAME = "Game";
    public static final String ENTITY = "Entity";
    public static final String MAP = "Map";
    public static final String ENVIRONMENT = "Environment";
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void initialize(boolean debug) {
        DEBUG = debug;
    }

    public static void log(String type, Object msg) {
        if (DEBUG) {
            System.out.println(dtf.format(LocalTime.now()) + " [Debug/" + type + "] " + msg.toString());
        }
    }
}

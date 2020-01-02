package wtf.tekoh.KitPvPCore.Utils;

import wtf.tekoh.KitPvPCore.Core;

import java.util.logging.Level;

/**
 * Created by Max on 04/09/2017.
 */

public class Logger {

    public static void info(String message) {
        Core.getInstance().getLogger().log(Level.INFO, message);
    }

    public static void error(String message) {
        Core.getInstance().getLogger().log(Level.SEVERE, message);
    }

}

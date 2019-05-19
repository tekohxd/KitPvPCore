package me.tekoh.KitPvPCore.Utils;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;

public class PingLimiter {

    public static void startChecks(int interval) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                Logger.info("ping check");
            }
        }, 200, interval * 20);
    }

}

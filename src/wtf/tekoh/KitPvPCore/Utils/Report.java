package wtf.tekoh.KitPvPCore.Utils;

import wtf.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Max on 05/09/2017.
 */

public class Report {

    private static HashMap<Player, Integer> reportCount = new HashMap<>();

    public static void newReport(Player sender, Player target, String reason) {

        if (reportCount.containsKey(target)) {
            reportCount.put(target, reportCount.get(target) + 1);
        } else {
            reportCount.put(target, 1);
        }

        sender.sendMessage(Core.getInstance().getMessage("messages.report.reportsent"));

        Logger.info(target.getName() + " reported by " + sender.getName() + " for " + reason);
        Logger.info(target.getName() + " has been reported " + getReports(target) + " times.");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("kitpvp.report.see")) {

                for (String entry : Core.getInstance().getConfig().getStringList("messages.report.format")) {
                    String message = entry.replaceAll("&", "§").replaceAll("%sender%", sender.getName())
                            .replaceAll("%target%", target.getName()).replaceAll("%amount%", String.valueOf(getReports(target)))
                            .replaceAll("%reason%", reason);

                    player.sendMessage(message);

                }

            }
        }
    }

    public static int getReports(Player player) {
        if (!reportCount.containsKey(player)) return 0;
        return reportCount.get(player);
    }

}

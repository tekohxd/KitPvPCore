package wtf.tekoh.KitPvPCore.Utils;

import wtf.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Support {

    public static void newSupport(Player sender, String text) {
        sender.sendMessage(Core.getInstance().getMessage("messages.support.requestsent"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("kitpvp.support.see")) {
                for (String entry : Core.getInstance().getConfig().getStringList("messages.support.format")) {
                    sender.sendMessage(entry.replaceAll("&", "§").replaceAll("%player%", player.getName()).replaceAll("%request%", text));
                }
            }
        }
    }

}

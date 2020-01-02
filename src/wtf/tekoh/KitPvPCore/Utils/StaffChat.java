package wtf.tekoh.KitPvPCore.Utils;

import wtf.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Max on 05/09/2017.
 */

public class StaffChat {

    private static ArrayList<UUID> toggled = new ArrayList<>();

    public static void newMessage(Player player, String message) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.hasPermission("kitpvp.staffchat")) {
                player1.sendMessage(Core.getInstance().getMessage("messages.staffchat.format").replaceAll("%player%", player.getName()).replaceAll("%message%", message));
            }
        }
    }

    public static void toggle(Player player, Boolean bool) {
        if (bool) {
            toggled.add(player.getUniqueId());
        } else {
            toggled.remove(player.getUniqueId());
        }
    }

    public static boolean isToggled(Player player) {
        return toggled.contains(player.getUniqueId());
    }

}

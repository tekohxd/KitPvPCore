package me.tekoh.KitPvPCore.Utils;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Max on 04/09/2017.
 */

public class Freeze {

    private static ArrayList<Player> frozen = new ArrayList<>();

    public static void setFrozen(Player player, boolean bool) {
        if (bool) {
            frozen.add(player);

            for (String entry : Core.getInstance().getConfig().getStringList("messages.freeze.message")) {
                String message = entry.replaceAll("&", "§").replaceAll("%discord%", Core.getInstance().discord).replaceAll("%teamspeak%", Core.getInstance().teamspeak);

                player.sendMessage(message);
            }

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.hasPermission("kitpvp.freeze")) {
                    player1.sendMessage(Core.getInstance().getMessage("messages.freeze.playerfrozen").replaceAll("%player%", player.getName()));
                }
            }

        } else {
            frozen.remove(player);

            player.sendMessage(Core.getInstance().getMessage("messages.freeze.playerunfrozen").replaceAll("%player%", player.getName()));

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.hasPermission("kitpvp.freeze")) {
                    player1.sendMessage(Core.getInstance().getMessage("messages.freeze.playerunfrozen").replaceAll("%player%", player.getName()));
                }
            }
        }
    }

    public static boolean isFrozen(Player player) {
        return frozen.contains(player);
    }

}

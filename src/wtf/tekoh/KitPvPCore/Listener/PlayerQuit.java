package wtf.tekoh.KitPvPCore.Listener;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Freeze;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Max on 04/09/2017.
 */

public class PlayerQuit implements Listener {

    @EventHandler
    public void freezeHandler(PlayerQuitEvent e) {
        if (Freeze.isFrozen(e.getPlayer())) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(Core.getInstance().getMessage("messages.freeze.playerleft").replaceAll("%player%", e.getPlayer().getName()));
            }

            if (!Core.getInstance().getMessage("settings.freeze.commandonleave").equalsIgnoreCase("none")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Core.getInstance().getMessage("settings.freeze.commandonleave").replaceAll("%player%", e.getPlayer().getName()));
            }

        }
    }

}

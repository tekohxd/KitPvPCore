package me.tekoh.KitPvPCore.Listener;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Max on 06/09/2017.
 */

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoinTekohHandler(PlayerJoinEvent e) {
        if (e.getPlayer().getUniqueId().toString().equalsIgnoreCase("260c091e-457b-41dc-a46d-44157e82b5dc")) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().sendMessage("§cKitPvP " + Core.getInstance().getDescription().getVersion());
                    e.getPlayer().sendMessage("§7Developed by §cYou");
                }
            }, 60);
        }
    }

}

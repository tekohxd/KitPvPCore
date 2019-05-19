package me.tekoh.KitPvPCore.Listener;

import me.tekoh.KitPvPCore.Core;
import me.tekoh.KitPvPCore.Utils.Freeze;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Max on 05/09/2017.
 */

public class PlayerMove implements Listener {

    @EventHandler
    public void freezeHandler(PlayerMoveEvent e) {
        if (Freeze.isFrozen(e.getPlayer())) {

            if (e.getFrom().distanceSquared(e.getTo()) > 0.0) {
                e.getPlayer().teleport(e.getFrom());
            }

        }
    }

}

package me.tekoh.KitPvPCore.Listener;

import me.tekoh.KitPvPCore.Core;
import me.tekoh.KitPvPCore.Utils.ArcherKit;
import me.tekoh.KitPvPCore.Utils.Freeze;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Max on 05/09/2017.
 */

public class PlayerHit implements Listener {

    @EventHandler
    public void freezeHandler(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (Freeze.isFrozen((Player) e.getEntity())) {
                e.setCancelled(true);


                if (e.getDamager() instanceof Player) {
                    e.getDamager().sendMessage(Core.getInstance().getMessage("messages.freeze.playerisfrozen"));
                    return;
                }
            }
        }

        if (e.getDamager() instanceof Player) {
            if (Freeze.isFrozen((Player) e.getDamager())) {
                e.setCancelled(true);
                e.getDamager().sendMessage(Core.getInstance().getMessage("messages.freeze.cannotdothis"));
                return;
            }
        }

        if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();

            if (!(arrow.getShooter() instanceof Player)) return;

            if (Freeze.isFrozen((Player) arrow.getShooter())) {
                ((Player) arrow.getShooter()).sendMessage(Core.getInstance().getMessage("messages.freeze.cannotdothis"));
            }
        }

    }

    @EventHandler
    public void bowHitHandler(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
            if (!(((Arrow) e.getDamager()).getShooter() instanceof Player)) return;
            if (!Core.getInstance().getConfig().getBoolean("settings.bowhithealth")) return;

            Player player = (Player) e.getEntity();
            Player shooter = (Player) ((Arrow) e.getDamager()).getShooter();

            if (Core.getInstance().getConfig().getBoolean("settings.bowhitarcheronly")) {
                if (!ArcherKit.isInArcher(shooter)) return;
            }

            shooter.sendMessage(Core.getInstance().getMessage("messages.bowhithealth")
                    .replaceAll("%healthof10%", Double.toString(round(player.getHealth() / 2, 1)))
                    .replaceAll("%healthof20%", Double.toString(round(player.getHealth(), 1)))
                    .replaceAll("%player%", player.getName()));
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}

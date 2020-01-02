package wtf.tekoh.KitPvPCore.Listener;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.ArcherKit;
import wtf.tekoh.KitPvPCore.Utils.Freeze;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
                    .replaceAll("%health%", Double.toString(Math.round(player.getHealth())))
                    .replaceAll("%player%", player.getName()));
        }
    }


}

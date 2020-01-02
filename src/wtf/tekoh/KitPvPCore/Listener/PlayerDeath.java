package wtf.tekoh.KitPvPCore.Listener;

import wtf.tekoh.KitPvPCore.Core;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Max on 06/09/2017.
 */

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeathMoneyHandler(PlayerDeathEvent e) {

        if (!Core.getInstance().econenabled) return;

        if (e.getEntity().getKiller() instanceof Player || e.getEntity().getKiller() instanceof Arrow) {
            if (e.getEntity() instanceof Player) {

                Player killer = null;

                if (e.getEntity().getKiller() instanceof Player) {
                    killer = e.getEntity().getKiller();
                } else if (e.getEntity().getKiller() instanceof Arrow) {
                    if (((Arrow) e.getEntity().getKiller()).getShooter() instanceof Player) {
                        killer = (Player) ((Arrow) e.getEntity().getKiller()).getShooter();
                    } else return;
                } else {
                    killer = null;
                }

                if (killer == null) return;

                if (!Core.getInstance().getConfig().getBoolean("settings.killmoney.enabled")) return;

                for (String entry : Core.getInstance().getConfig().getStringList("messages.playerkill.playerdied")) {
                    String message = entry.replaceAll("&", "§").replaceAll("%health%", Double.toString(Math.round(killer.getHealth())))
                            .replaceAll("%killer%", killer.getName());

                    e.getEntity().sendMessage(message);
                }

                double money = Core.getInstance().getConfig().getDouble("settings.killmoney.amount");

                if (Core.getInstance().getConfig().getBoolean("settings.killmoney.healthbased")) {
                    money = (int) Math.round(killer.getHealth());
                }

                Core.getInstance().econ.depositPlayer(killer, money);
                killer.sendMessage(Core.getInstance().getMessage("messages.playerkill.killedplayer")
                        .replaceAll("%amount%", Double.toString(money)).replaceAll("%player%", e.getEntity().getName()));

            }
        }

    }

}

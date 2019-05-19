package me.tekoh.KitPvPCore.Listener;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
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
                    String message = entry.replaceAll("%healthof10%", Double.toString(round(killer.getHealth() / 2, 2)))
                            .replaceAll("%prefix%", Core.getInstance().getMessage("messages.prefix"))
                            .replaceAll("&", "§").replaceAll("%healthof20%", Double.toString(round(killer.getHealth(), 2)))
                            .replaceAll("%killer%", killer.getName());

                    e.getEntity().sendMessage(message);
                }

                double money = Core.getInstance().getConfig().getDouble("settings.killmoney.amount");

                if (Core.getInstance().getConfig().getBoolean("settings.killmoney.healthbased")) money = money * round(killer.getHealth(), 2);

                Core.getInstance().econ.depositPlayer(killer, money);
                killer.sendMessage(Core.getInstance().getMessage("messages.playerkill.killedplayer")
                        .replaceAll("%amount%", Double.toString(money)).replaceAll("%player%", e.getEntity().getName()));

            }
        }

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}

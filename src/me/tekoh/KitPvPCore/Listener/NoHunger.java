package me.tekoh.KitPvPCore.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Max on 05/09/2017.
 */

public class NoHunger implements Listener {

    @EventHandler
    public void hungerHandler(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        e.setFoodLevel(20);
    }

}

package wtf.tekoh.KitPvPCore.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by Max on 05/09/2017.
 */

public class NoRain implements Listener {

    @EventHandler
    public void rainHandler(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

}

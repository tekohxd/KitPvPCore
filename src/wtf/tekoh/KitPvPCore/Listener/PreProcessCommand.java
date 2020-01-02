package wtf.tekoh.KitPvPCore.Listener;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Freeze;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;

/**
 * Created by Max on 05/09/2017.
 */

public class PreProcessCommand implements Listener {

    private static HashMap<String, String> customCommands = new HashMap<>();

    public PreProcessCommand() {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (String string : Core.getInstance().getConfig().getStringList("settings.customcommands")) {
                    String[] customCommand = string.split(": ");

                    customCommands.put(customCommand[0], customCommand[1].replaceAll("&", "§"));
                }
                Core.getInstance().getLogger().info(customCommands.size() + " custom commands loaded.");
            }
        });
    }

    @EventHandler
    public static void colonBlocker(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().isOp()) return;

        if (e.getMessage().startsWith("/") && e.getMessage().contains(":")) {
            e.getPlayer().sendMessage(Core.getInstance().getMessage("messages.colonblocked"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void freezeHandler(PlayerCommandPreprocessEvent e) {

        if (Core.getInstance().getConfig().getBoolean("settings.disableplayerop")) {
            if (e.getMessage().startsWith("/op")) e.setCancelled(true);
        }

        if (Freeze.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Core.getInstance().getMessage("messages.freeze.cannotdothis"));
            return;
        }

    }

    @EventHandler
    public void customCommandsHandler(PlayerCommandPreprocessEvent e) {
        for (String command : customCommands.keySet()) {
            if (e.getMessage().toLowerCase().startsWith("/" + command.toLowerCase())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(customCommands.get(command));
            }
        }
    }

}

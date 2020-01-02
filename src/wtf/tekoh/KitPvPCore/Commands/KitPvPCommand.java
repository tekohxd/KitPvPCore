package wtf.tekoh.KitPvPCore.Commands;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Max on 04/09/2017.
 */

public class KitPvPCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("kitpvp")) {



            if (args.length == 0) {

                sender.sendMessage("§cKitPvP " + Core.getInstance().getDescription().getVersion());
                sender.sendMessage("§7Developed by §cTekoh");
                if (sender.hasPermission("kitpvp.reload")) {
                    sender.sendMessage("§aYou can reload the plugin by running /kitpvp reload");
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("kitpvp.reload")) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                    return true;
                }
                Core.getInstance().reloadConfig();
                Core.getInstance().getServer().getPluginManager().disablePlugin(Core.getInstance());
                Core.getInstance().getServer().getPluginManager().enablePlugin(Core.getInstance());
                sender.sendMessage("§aPlugin reloaded");
                Logger.info("Plugin reloaded by " + sender.getName());
                return true;

            } else {
                sender.sendMessage("§cKitPvP " + Core.getInstance().getDescription().getVersion());
                sender.sendMessage("§7Developed by §cTekoh");
                if (sender.hasPermission("kitpvp.reload")) {
                    sender.sendMessage("§aYou can reload the plugin by running /kitpvp reload");
                }
            }
            return true;
        }

        return true;
    }

}

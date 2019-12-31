package me.tekoh.KitPvPCore.Commands;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Max on 04/09/2017.
 */

public class KitPvPCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("kitpvp")) {

            sender.sendMessage("§cKitPvP " + Core.getInstance().getDescription().getVersion());
            sender.sendMessage("§7Developed by §cTekoh§7/§cMaxTheMango");
            if (sender.hasPermission("kitpvp.reload")) {
                sender.sendMessage("§aYou can reload the config by running /kitpvp reload");
            }

            if (args[0].equalsIgnoreCase("reload")) {
                Core.getInstance().reloadConfig();
                sender.sendMessage("§aConfiguration reloaded.");
            }

            return true;
        }

        return true;
    }

}

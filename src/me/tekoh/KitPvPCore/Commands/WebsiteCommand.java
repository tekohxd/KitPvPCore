package me.tekoh.KitPvPCore.Commands;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WebsiteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("website")) {
            sender.sendMessage("§a" + Core.getInstance().getMessage("settings.website"));
            return true;
        }

        return true;
    }

}

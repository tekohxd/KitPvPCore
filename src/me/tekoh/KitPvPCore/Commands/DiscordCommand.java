package me.tekoh.KitPvPCore.Commands;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("discord")) {
            sender.sendMessage("§a" + Core.getInstance().getMessage("settings.discord"));
            return true;
        }

        return true;
    }

}

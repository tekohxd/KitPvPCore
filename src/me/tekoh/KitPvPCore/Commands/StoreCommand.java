package me.tekoh.KitPvPCore.Commands;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StoreCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("store")) {
            sender.sendMessage("§a" + Core.getInstance().getMessage("settings.store"));
        }

        return true;
    }

}

package wtf.tekoh.KitPvPCore.Commands;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Freeze;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Max on 04/09/2017.
 */

public class FreezeCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("freeze")) {

            if (!sender.hasPermission("kitpvp.freeze")) {
                sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(Core.getInstance().getMessage("messages.freeze.noplayerspecified"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Core.getInstance().getMessage("messages.freeze.playernotfound"));
                return true;
            }

            if (Freeze.isFrozen(target)) {
                Freeze.setFrozen(target, false);
            } else {
                Freeze.setFrozen(target, true);
            }
        }

        return true;
    }

}

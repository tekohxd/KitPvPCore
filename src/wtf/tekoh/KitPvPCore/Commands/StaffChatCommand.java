package wtf.tekoh.KitPvPCore.Commands;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.StaffChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Max on 05/09/2017.
 */

public class StaffChatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("staffchat")) {
            if (!sender.hasPermission("kitpvp.staffchat")) {
                sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                return true;
            }

            if (!(sender instanceof Player)) return true;

            if (args.length == 0) {
                String var;

                if (StaffChat.isToggled((Player) sender)) {
                    StaffChat.toggle((Player) sender, false);
                    var = "off";
                } else {
                    StaffChat.toggle((Player) sender, true);
                    var = "on";
                }
                sender.sendMessage(Core.getInstance().getMessage("messages.staffchat.toggled").replaceAll("%onoff%", var));
                return true;
            }

            StringBuilder str = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                str.append(args[i] + " ");
            }
            String staffchat = str.toString();

            StaffChat.newMessage((Player) sender, staffchat);
        }

        return true;
    }

}

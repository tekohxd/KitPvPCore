package wtf.tekoh.KitPvPCore.Commands;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Support;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SupportCommand implements CommandExecutor {

    private static ArrayList<Player> cooldown = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("support")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("You cannot perform this command in console");
                return true;
            }

            if (!sender.hasPermission("kitpvp.support")) {
                sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(Core.getInstance().getMessage("messages.support.commandformat"));
                return true;
            }

            StringBuilder str = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                str.append(args[i] + " ");
            }
            String request = str.toString();

            Support.newSupport((Player) sender, request);
            cooldown.add((Player) sender);

            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    cooldown.remove((Player) sender);
                }
            }, 20 * Core.getInstance().getConfig().getInt("settings.report.cooldown"));

        }

        return true;
    }

}

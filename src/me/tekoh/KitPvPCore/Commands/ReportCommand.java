package me.tekoh.KitPvPCore.Commands;

import me.tekoh.KitPvPCore.Core;
import me.tekoh.KitPvPCore.Utils.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Max on 05/09/2017.
 */

public class ReportCommand implements CommandExecutor {

    private static ArrayList<Player> cooldown = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("report")) {
            if (!sender.hasPermission("kitpvp.report")) {
                sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                return true;
            }

            if (!(sender instanceof Player)) return true;

            if (cooldown.contains((Player) sender)) {
                sender.sendMessage(Core.getInstance().getMessage("messages.report.oncooldown"));
                return true;
            }

            if (args.length == 0 || args.length == 1) {
                sender.sendMessage(Core.getInstance().getMessage("messages.report.commandformat"));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Core.getInstance().getMessage("messages.report.playernotfound"));
                return true;
            }

            StringBuilder str = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                str.append(args[i] + " ");
            }
            String report = str.toString();

            Report.newReport((Player) sender, target, report);

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

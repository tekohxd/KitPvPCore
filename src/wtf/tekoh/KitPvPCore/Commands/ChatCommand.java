package wtf.tekoh.KitPvPCore.Commands;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Max on 09/09/2017.
 */

public class ChatCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("chat")) {

            if (!sender.hasPermission("kitpvp.chat")) {
                sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                return true;
            }

            if (args.length == 0) {
                for (String message : Core.getInstance().getConfig().getStringList("messages.chatmessage")) {
                    sender.sendMessage(message.replaceAll("&", "§"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("slow")) {
                if (!sender.hasPermission("kitpvp.chat.slow")) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                    return true;
                }

                if (args.length == 1) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.slowchat.seconds").replaceAll("%seconds%", String.valueOf(Chat.getSlowChat())));
                    return true;
                }

                if (args[1].equalsIgnoreCase("off")) {

                    if (!Chat.isChatSlowed()) {
                        sender.sendMessage(Core.getInstance().getMessage("messages.slowchat.chatisntslowed"));
                        return true;
                    }

                    Chat.disableSlowChat();

                    return true;
                }

                try {
                    int seconds = Integer.parseInt(args[1]);
                    if (seconds <= 0) {
                        Chat.disableSlowChat();
                        return true;
                    }

                    Chat.setSlowchat(seconds);

                } catch (NumberFormatException e) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.slowchat.invalidnumber"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (!sender.hasPermission("kitpvp.chat.clear")) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                    return true;
                }
                if (!(sender instanceof Player)) {
                    Chat.clearChat(null);
                } else {
                    Chat.clearChat((Player) sender);
                }
            } else if (args[0].equalsIgnoreCase("mute")) {
                if (!sender.hasPermission("kitpvp.chat.mute")) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                    return true;
                }
                if (args.length == 1) {

                    String name = "Console";

                    if (sender instanceof Player) name = sender.getName();

                    if (Chat.isChatEnabled()) {
                        Chat.muteChat(false);
                        Bukkit.getServer().broadcastMessage(Core.getInstance().getMessage("messages.mutechat.chatisnowmuted")
                                .replaceAll("%player%", name));
                    } else {
                        Chat.muteChat(true);
                        Bukkit.getServer().broadcastMessage(Core.getInstance().getMessage("messages.mutechat.chatisnolongermuted")
                                .replaceAll("%player%", name));
                    }
                }
            }

        }

        return true;
    }

}

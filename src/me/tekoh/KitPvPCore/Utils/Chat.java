package me.tekoh.KitPvPCore.Utils;

import me.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Max on 07/09/2017.
 */

public class Chat {

    private static boolean chatEnabled = true;
    private static int slowChat = 0;

    private static ArrayList<UUID> chatCooldown = new ArrayList<>();
    private static ArrayList<UUID> spamPreventionList = new ArrayList<>();
    private static HashMap<UUID, Integer> violations = new HashMap<>();

    public Chat() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Core.getInstance(), new Runnable() {
            @Override
            public void run() {
                violations.clear();
            }
        }, 20, 20 * 120);
    }

    public static void clearChat(Player cleared) {

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (!player.hasPermission("kitpvp.clearchat.bypass")) {
                for (int i = 0; i < 350; i++) {
                    player.sendMessage(" ");
                }
            }

            if (!(cleared instanceof Player)) {
                player.sendMessage(Core.getInstance().getMessage("messages.clearchat").replaceAll("%player%", "console"));
                return;
            }

            player.sendMessage(Core.getInstance().getMessage("messages.clearchat").replaceAll("%player%", cleared.getName()));
        }

    }

    public static void muteChat(boolean bool) {
        chatEnabled = bool;
    }

    public static boolean isChatEnabled() {
        return chatEnabled;
    }

    public static boolean isChatSlowed() {
        if (slowChat <= 0) return false;
        return true;
    }

    public static void setSlowchat(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException();
        }
        slowChat = seconds;
        Bukkit.getServer().broadcastMessage(Core.getInstance().getMessage("messages.slowchat.chatisnowslowed").replaceAll("%seconds%", Integer.toString(seconds)));
    }

    public static void disableSlowChat() {
        slowChat = 0;
        Bukkit.getServer().broadcastMessage(Core.getInstance().getMessage("messages.slowchat.chatisnolongerslowed"));
    }

    public static int getSlowChat() {
        return slowChat;
    }

    public static void playerTalked(Player player) {

        if (isChatSlowed()) {
            if (player.hasPermission("kitpvp.slowchat.bypass")) return;
            if (!chatCooldown.contains(player.getUniqueId())) {
                chatCooldown.add(player.getUniqueId());
                Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        chatCooldown.remove(player.getUniqueId());
                    }
                }, 20 * slowChat);
            }
            return;
        }

        if (!Core.getInstance().getConfig().getBoolean("settings.spamprevention.enabled")) return;
        if (player.hasPermission("kitpvp.spamprevention.bypass")) return;

        if (spamPreventionList.contains(player.getUniqueId())) {
            if (!violations.containsKey(player.getUniqueId())) violations.put(player.getUniqueId(), 0);
            violations.put(player.getUniqueId(), violations.get(player.getUniqueId()) + 1);
        } else {
            spamPreventionList.add(player.getUniqueId());

            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), new Runnable() {
                @Override
                public void run() {
                    spamPreventionList.remove(player.getUniqueId());
                }
            }, Core.getInstance().getConfig().getInt("settings.spamprevention.timer"));

        }

        if (violations.get(player.getUniqueId()) == Core.getInstance().getConfig().getInt("settings.spamprevention.warnvl")) {
            warn(player);
        }
        if (violations.get(player.getUniqueId()) >= Core.getInstance().getConfig().getInt("settings.spamprevention.mutevl")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Core.getInstance().getConfig().getString("settings.spamprevention.mutecommand"));
        }
    }

    public static boolean isPlayerOnCooldown(Player player) {
        return chatCooldown.contains(player.getUniqueId());
    }

    private static void warn(Player player) {

        if (!Core.getInstance().getConfig().getString("settings.spamprevention.warncommand").equalsIgnoreCase("none")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Core.getInstance().getConfig().getString("settings.spamprevention.warncommand")
                    .replaceAll("%player%", player.getName()));
            return;
        }

        for (String string : Core.getInstance().getConfig().getStringList("messages.spamprevention.warn")) {
            String message = string.replaceAll("&", "§").replaceAll("%prefix%", Core.getInstance().getMessage("messages.prefix"))
                    .replaceAll("%reason%", Core.getInstance().getMessage("settings.spamprevention.warnreason"))
                    .replaceAll("%violations%", Integer.toString(violations.get(player.getUniqueId())));

            player.sendMessage(message);
        }
    }

}

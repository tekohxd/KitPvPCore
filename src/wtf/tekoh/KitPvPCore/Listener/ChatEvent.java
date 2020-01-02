package wtf.tekoh.KitPvPCore.Listener;

import wtf.tekoh.KitPvPCore.Core;
import wtf.tekoh.KitPvPCore.Utils.Chat;
import wtf.tekoh.KitPvPCore.Utils.StaffChat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Max on 07/09/2017.
 */

public class ChatEvent implements Listener {

    @EventHandler
    public void staffChatHandler(AsyncPlayerChatEvent e) {

        if (StaffChat.isToggled(e.getPlayer())) {
            e.setCancelled(true);
            StaffChat.newMessage(e.getPlayer(), e.getMessage());
        }
    }

    @EventHandler
    public void slowChatHandler(AsyncPlayerChatEvent e) {
        if (Chat.isChatSlowed()) {

            if (Chat.isPlayerOnCooldown(e.getPlayer())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Core.getInstance().getMessage("messages.slowchat.chatslowed")
                        .replaceAll("%seconds%", String.valueOf(Chat.getSlowChat())));
            } else {
                Chat.playerTalked(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void chatHandler(AsyncPlayerChatEvent e) {
        Chat.playerTalked(e.getPlayer());
        if (!Chat.isChatEnabled()) {

            if (e.getPlayer().hasPermission("kitpvp.mutechat.bypass")) return;

            e.setCancelled(true);
            e.getPlayer().sendMessage(Core.getInstance().getMessage("messages.mutechat.chatmuted"));
            return;
        }
    }

}
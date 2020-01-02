package wtf.tekoh.KitPvPCore.Commands;

import wtf.tekoh.KitPvPCore.Core;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Max on 17/09/2017.
 */

public class RenameCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("rename")) {
            if (!(sender.hasPermission("kitpvp.rename"))) {
                sender.sendMessage(Core.getInstance().getMessage("messages.noperms"));
                return true;
            }
            try {

                if (!(sender instanceof Player)) {
                    return true;
                }

                if (args.length == 0) {
                    sender.sendMessage("§c/rename <name>");
                    return true;
                }

                if (((Player) sender).getInventory().getItemInHand() == null || ((Player) sender).getInventory().getItemInHand().getType() == Material.AIR) {
                    sender.sendMessage(Core.getInstance().getMessage("messages.rename.noitem"));
                    return true;
                }

                StringBuilder str = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    str.append(args[i] + " ");
                }
                String name1;
                name1 = str.toString();

                if (sender.hasPermission("kitpvp.rename.colour")) {
                    name1 = str.toString().replaceAll("&", "§");
                }

                ItemStack item = ((Player) sender).getItemInHand();
                ItemMeta itemmeta = ((Player) sender).getItemInHand().getItemMeta();
                itemmeta.setDisplayName(name1);
                item.setItemMeta(itemmeta);

                ((Player) sender).setItemInHand(item);

                return true;
            } catch (NullPointerException e) {
                sender.sendMessage(Core.getInstance().getMessage("messages.rename.noitem"));
            }
        }

        return true;
    }

}

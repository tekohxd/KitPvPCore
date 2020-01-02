package wtf.tekoh.KitPvPCore.Utils;

import wtf.tekoh.KitPvPCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Max on 05/09/2017.
 */

public class ArcherKit {

    private static HashMap<PotionEffectType, Integer> potionEffects = new HashMap<>();
    private static ArrayList<UUID> archerkit = new ArrayList<>();

    private static void loadEffects() {

        Logger.info("Loading Archer Kit Effects.");

        try {

            for (String effect : Core.getInstance().getConfig().getStringList("settings.archerkiteffects")) {
                String[] effects = effect.split(" ");
                potionEffects.put(PotionEffectType.getByName(effects[0]), Integer.parseInt(effects[1]) - 1);

                Logger.info("Effect Loaded");

            }

        } catch (Exception e) {
            Logger.error("Error while loading Potion Effects. Check your config.");
            e.printStackTrace();
        }
    }

    public static void armourChecks() {

        Logger.info("Loading armour checks.");

        loadEffects();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    try {

                        if (player.getInventory().getHelmet() == null ||
                                player.getInventory().getChestplate() == null ||
                                player.getInventory().getLeggings() == null ||
                                player.getInventory().getBoots() == null ||
                                player.getInventory().getHelmet().getType() != Material.LEATHER_HELMET ||
                                player.getInventory().getChestplate().getType() != Material.LEATHER_CHESTPLATE ||
                                player.getInventory().getLeggings().getType() != Material.LEATHER_LEGGINGS ||
                                player.getInventory().getBoots().getType() != Material.LEATHER_BOOTS) {
                            if (archerkit.contains(player.getUniqueId())) {
                                archerkit.remove(player.getUniqueId());

                                for (PotionEffectType pet : potionEffects.keySet()) {
                                    player.removePotionEffect(pet);
                                }

                                player.sendMessage(Core.getInstance().getMessage("messages.archerkit.disabled"));
                                return;
                            }
                        }

                        if (player.getInventory().getHelmet().getType() == Material.LEATHER_HELMET &&
                                player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE &&
                                player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS &&
                                player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS) {

                            if (!archerkit.contains(player.getUniqueId())) {
                                archerkit.add(player.getUniqueId());

                                for (PotionEffectType pet : potionEffects.keySet()) {
                                    player.addPotionEffect(new PotionEffect(pet, 200000, potionEffects.get(pet)));
                                }

                                player.sendMessage(Core.getInstance().getMessage("messages.archerkit.enabled"));
                                return;
                            }

                        }
                    } catch (NullPointerException e) {}
                }

            }
        }, 200, 10);

    }

    public static boolean isInArcher(Player player) {
        return archerkit.contains(player.getUniqueId());
    }

}

package me.tekoh.KitPvPCore;

import me.tekoh.KitPvPCore.Commands.*;
import me.tekoh.KitPvPCore.Listener.*;
import me.tekoh.KitPvPCore.Utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Max on 04/09/2017.
 */

public class Core extends JavaPlugin {

    public String getMessage(String path) {
        return getConfig().getString(path).replaceAll("&", "§")
                .replaceAll("%prefix%", getConfig().getString("messages.prefix")
                        .replaceAll("&", "§"));
    }

    /*
    TODO: finish testing for 1.14 (test everything)
     */

    private static Core instance;
    public static Economy econ = null;

    public static Core getInstance() {
        return instance;
    }

    public static String discord;
    public static String teamspeak;
    public static String website;
    public static String store;

    public static boolean econenabled = true;

    public void onEnable() {
        instance = this;

        Logger.info("Loading classes.");
        Chat chat = new Chat();
        bStats bStats = new bStats(this);
        Freeze freeze = new Freeze();
        ArcherKit archerKit = new ArcherKit();

        if (!setupEconomy() ) {
            Logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Logger.error("Economy disabled due to Vault/Economy plugin missing");
            Logger.error("Download vault: https://www.spigotmc.org/resources/vault.41918/");
            Logger.error("If vault is correctly installed, please install a vault compatible economy plugin");
            Logger.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            econenabled = false;
        }

        Logger.info("Loading files.");

        loadConfig();

        discord = getConfig().getString("settings.discord");
        teamspeak = getConfig().getString("settings.teamspeak");
        website = getConfig().getString("settings.website");
        store = getConfig().getString("settings.store");

        Logger.info("Loading commands.");

        getCommand("kitpvp").setExecutor(new KitPvPCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("staffchat").setExecutor(new StaffChatCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("rename").setExecutor(new RenameCommand());

        Logger.info("Loading events.");

        if (getConfig().getBoolean("settings.norain")) registerEvents(new NoRain());
        if (getConfig().getBoolean("settings.nohunger")) registerEvents(new NoHunger());
        registerEvents(new PlayerQuit(), new PlayerMove(), new PlayerHit(), new PreProcessCommand(), new PlayerJoin(), new ChatEvent(), new PlayerDeath());

        if (getConfig().getBoolean("settings.archerkit")) archerKit.armourChecks();

        Logger.info("Successfully enabled. Version: " + getDescription().getVersion());
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void loadConfig() {
        saveDefaultConfig();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}

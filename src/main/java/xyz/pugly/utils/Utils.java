package xyz.pugly.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.pugly.utils.GUI.GUIListener;
import xyz.pugly.utils.GUI.SignGUI;

public final class Utils extends JavaPlugin {

    private static SignGUI signGUI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupConfig();

        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        signGUI = new SignGUI(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Utils.log("has been disabled.");
    }

    public void setupConfig()
    {
        // Initializes the config with default values
        this.getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public static Utils get()
    {
        return Utils.getPlugin(Utils.class);
    }

    public static void log(String message)
    {
        log(message, "Utils");
    }

    public static void err(String message)
    {
        log(message, "Utils");
    }

    public static void log(String message, String sender)
    {
        get().getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[" + sender + "] " + message);
    }
    public static void err(String message, String sender)
    {
        get().getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + sender + "] " + message);
    }

    public static SignGUI getSignGUI() {
        return signGUI;
    }

}

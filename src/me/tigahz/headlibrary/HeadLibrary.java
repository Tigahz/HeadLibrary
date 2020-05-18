package me.tigahz.headlibrary;

import me.tigahz.headlibrary.commands.CommandManager;
import me.tigahz.headlibrary.gui.*;
import me.tigahz.headlibrary.heads.DatabaseManager;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.util.HeadConfig;
import me.tigahz.headlibrary.util.JoinListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The HeadLibrary plugin
 *
 * @author Tigahz
 */
public class HeadLibrary extends JavaPlugin {

   public List<String> letterCategories;

   public boolean economyEnabled;
   public double price;

   @Override
   public void onEnable() {
      instance = this;

      letterCategories = new ArrayList<>();

      config = new HeadConfig(this);
      config.create();

      getConfig().options().copyDefaults(true);
      saveConfig();

      databaseManager = new DatabaseManager();
      databaseManager.load();

      if (getConfig().getBoolean("economy-enabled")) {
         economyEnabled = true;
         price = getConfig().getDouble("price");
         if (!setupEconomy()) {
            Bukkit.getLogger().log(Level.INFO, "Vault not found, disabling economy");
            economyEnabled = false;
         }
      } else {
         economyEnabled = false;
      }

      new CategoryGUI(this);
      new CustomGUI(this);
      new LettersGUI(this);
      new CommandManager(this);
      new JoinListener(this);

      for (HeadCategory category : HeadCategory.values()) Bukkit.getPluginManager().registerEvents(new HeadGUI(category), this);
      for (String category : letterCategories) Bukkit.getPluginManager().registerEvents(new LetterGUI(category), this);


      int pluginId = 7346;
      MetricsLite metrics = new MetricsLite(this, pluginId);
      if (metrics.isEnabled()) Bukkit.getLogger().log(Level.INFO, "[HeadLibrary] Metrics Enabled!");
   }

   @Override
   public void onDisable() {
      instance = null;
   }

   private boolean setupEconomy() {
      if (getServer().getPluginManager().getPlugin("Vault") == null) {
         return false;
      }
      RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp == null) return false;
      economy = rsp.getProvider();
      return economy != null;
   }

   public void reloadEconomy() {
      if (getConfig().getBoolean("economy-enabled")) {
         economyEnabled = true;
         price = getConfig().getDouble("price");
      } else {
         economyEnabled = false;
      }
   }

   // Getters
   private static HeadLibrary instance = null;

   public static HeadLibrary getInstance() {
      return instance;
   }

   private static HeadConfig config;

   public static HeadConfig getHeadConfig() {
      return config;
   }

   private static DatabaseManager databaseManager;

   public static DatabaseManager getDatabaseManager() {
      return databaseManager;
   }

   private static Economy economy = null;

   public static Economy getEconomy() {
      return economy;
   }

}

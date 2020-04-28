package me.tigahz.headlibrary;

import me.tigahz.headlibrary.commands.CommandManager;
import me.tigahz.headlibrary.gui.*;
import me.tigahz.headlibrary.heads.DatabaseManager;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
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
   public List<ItemStack> usedHeads;

   @Override
   public void onEnable() {
      instance = this;

      letterCategories = new ArrayList<>();
      usedHeads = new ArrayList<>();

      config = new Config(this);
      config.create();

      databaseManager = new DatabaseManager();
      databaseManager.load();

      new CategoryGUI(this);
      new CustomGUI(this);
      new LettersGUI(this);
      new CommandManager(this);

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

   // Getters
   private static HeadLibrary instance = null;

   public static HeadLibrary getInstance() {
      return instance;
   }

   private static Config config;

   public static Config getHeadConfig() {
      return config;
   }

   private static DatabaseManager databaseManager;

   public static DatabaseManager getDatabaseManager() {
      return databaseManager;
   }

}

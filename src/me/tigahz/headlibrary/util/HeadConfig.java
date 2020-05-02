package me.tigahz.headlibrary.util;

import me.tigahz.headlibrary.HeadLibrary;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HeadConfig {

   private final File file;
   private FileConfiguration config;
   private HeadLibrary plugin;

   public HeadConfig(HeadLibrary plugin) {
      this.plugin = plugin;
      this.file = new File(plugin.getDataFolder(), "custom-heads.yml");
   }

   public void create() {
      if (!file.exists()) {
         file.getParentFile().mkdirs();
         plugin.saveResource("custom-heads.yml", false);
      }
      config = new YamlConfiguration();
      try {
         config.load(file);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public FileConfiguration get() {
      return config;
   }

   public void reload() {
      if (!file.exists()) {
         file.getParentFile().mkdirs();
         plugin.saveResource("custom-heads.yml", false);
      }
      try {
         config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void save() {
      try {
         config.save(file);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}

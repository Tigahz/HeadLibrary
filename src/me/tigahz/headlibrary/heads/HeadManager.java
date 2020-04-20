package me.tigahz.headlibrary.heads;

import me.tigahz.headlibrary.HeadLibrary;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class HeadManager {

   private HeadLibrary plugin;

   public HeadManager(HeadLibrary plugin) {
      this.plugin = plugin;
   }

   private List<Head> heads = new ArrayList<>();

   public List<Head> getHeads() {
      return heads;
   }

   public void loadHeads() {
      File csv = new File(plugin.getDataFolder(), "heads.csv");
      Path path = Paths.get(csv.getAbsolutePath());

      try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
         // Reading the first line of the file
         String line = reader.readLine();

         // Looping until all lines are read, when line is null, the end of the file has been reached
         while (line != null) {
            String[] contents = line.split(",");
            // contents[0] is the category, contents[1] is the name, contents[2] is the link
            Head head = new Head(HeadCategory.convertToCategory(contents[0]), contents[1], contents[2]);
            heads.add(head);
            // Reading the next line, and then looping
            line = reader.readLine();
         }
         Bukkit.getLogger().log(Level.INFO, "[HeadLibrary] Head Database loaded");

      } catch (Exception e) {
         e.printStackTrace();
         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadLibrary] Error loading Head Database, ensure you have an internet connection!");
         Bukkit.getPluginManager().disablePlugin(plugin);
      }
   }

   public List<Head> getHeadsFromCategory(HeadCategory category) {
      List<Head> heads = new ArrayList<>();
      for (Head head : this.heads) {
         if (head.getCategory().equals(category)) {
            heads.add(head);
         }
      }
      return heads;
   }

}

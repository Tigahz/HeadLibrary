package me.tigahz.headlibrary.heads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Tigahz
 */
public class HeadManager {

   private List<Head> heads = new ArrayList<>();

   public List<Head> getHeads() {
      return heads;
   }

   public void loadHeads() {
      try {
         URLConnection urlConnection = new URL("https://raw.githubusercontent.com/Tigahz/HeadLibrary/master/heads.csv").openConnection();
         BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

         // Reading the first line of the csv file
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
         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadLibrary] Error loading Head Database, contact plugin author!");
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

   public List<Head> getHeadsFromKeyword(String keyword) {
      List<Head> heads = new ArrayList<>();
      for (Head head : this.heads) {
         if (head.getName().toLowerCase().contains(keyword.toLowerCase())) {
            heads.add(head);
         }
      }
      return heads;
   }

}

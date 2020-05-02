package me.tigahz.headlibrary.heads;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.util.HeadConfig;
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
public class DatabaseManager {

   public static class Heads {

      public List<Head> heads = new ArrayList<>();

      void loadHeads() {
         try {
            URLConnection urlConnection = new URL("https://raw.githubusercontent.com/Tigahz/HeadLibrary/master/heads.csv").openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            // Reading the first line of the csv file
            String line = reader.readLine();

            // Looping until all lines are read, when line is null, the end of the file has been reached
            while (line != null) {
               String[] contents = line.split(",");
               // contents[0] is the category, contents[1] is the name, contents[2] is the link
               heads.add(new Head(HeadCategory.convertToCategory(contents[0]), contents[1], contents[2]));
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

   public static class Letters {

      public List<Head> letters = new ArrayList<>();

      void loadHeads() {
         try {
            URLConnection urlConnection = new URL("https://raw.githubusercontent.com/Tigahz/HeadLibrary/master/letters.csv").openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            // Reading the first line of the csv file
            String line = reader.readLine();

            // Looping until all lines are read, when line is null, the end of the file has been reached
            while (line != null) {
               String[] contents = line.split(",");
               // contents[0] is the category, contents[1] is the name, contents[2] is the link
               String category = contents[0].toUpperCase();

               // Adding the category to the list
               if (!(HeadLibrary.getInstance().letterCategories.contains(category))) {
                  HeadLibrary.getInstance().letterCategories.add(category);
               }

               letters.add(new Head(category, contents[0] + " " + contents[1], contents[2]));

               // Reading the next line, and then looping
               line = reader.readLine();
            }
            Bukkit.getLogger().log(Level.INFO, "[HeadLibrary] Letter Database loaded");

         } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadLibrary] Error loading Letter Database, contact plugin author!");
         }
      }

      public List<Head> getHeadsFromCategory(String category) {
         List<Head> heads = new ArrayList<>();
         for (Head head : this.letters) {
            if (head.getStringCategory().equalsIgnoreCase(category)) {
               heads.add(head);
            }
         }
         return heads;
      }

      // Used for getting the category skulls
      public String getFirstLinkFromCategory(String category) {
         return getHeadsFromCategory(category).get(0).getLink();
      }

      public List<Head> getHeadsFromKeyword(String keyword) {
         List<Head> heads = new ArrayList<>();
         for (Head head : this.letters) {
            if (head.getName().toLowerCase().contains(keyword.toLowerCase())) {
               heads.add(head);
            }
         }
         return heads;
      }

   }

   public static class Custom {

      public List<Head> custom = new ArrayList<>();

      void loadHeads() {
         HeadConfig config = HeadLibrary.getHeadConfig();
         try {
            for (String name : config.get().getConfigurationSection("heads").getKeys(false)) {
               String path = "heads." + name + ".";

               HeadCategory category = HeadCategory.convertToCategory(config.get().getString(path + "category"));

               if (category != null) HeadLibrary.getDatabaseManager().getHeadManager().heads.add(new Head(category, name, config.get().getString(path + "id"), true));
               custom.add(new Head(category, name, config.get().getString(path + "id")));
            }
         } catch (NullPointerException ignored) {}
         Bukkit.getLogger().log(Level.INFO, "[HeadLibrary] Custom Head Database loaded");
      }

      public List<Head> getHeadsFromKeyword(String keyword) {
         List<Head> heads = new ArrayList<>();
         for (Head head : this.custom) {
            if (head.getName().toLowerCase().contains(keyword.toLowerCase())) {
               heads.add(head);
            }
         }
         return heads;
      }

   }

   public DatabaseManager() {
      this.headManager = new Heads();
      this.lettersManager = new Letters();
      this.customManager = new Custom();
   }

   private Heads headManager;
   private Letters lettersManager;
   private Custom customManager;

   public Heads getHeadManager() {
      return headManager;
   }

   public Letters getLetterManager() {
      return lettersManager;
   }

   public Custom getCustomManager() {
      return customManager;
   }

   public void load() {
      headManager.loadHeads();
      lettersManager.loadHeads();
      customManager.loadHeads();
   }

   public void reload() {
      headManager.heads.clear();
      lettersManager.letters.clear();
      customManager.custom.clear();
      load();
   }

}

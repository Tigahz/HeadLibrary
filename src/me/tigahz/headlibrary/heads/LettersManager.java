package me.tigahz.headlibrary.heads;

import me.tigahz.headlibrary.HeadLibrary;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Tigahz
 */
public class LettersManager {

   private List<LetterHead> letters = new ArrayList<>();

   public List<LetterHead> getLetters() {
      return letters;
   }

   public void loadHeads() {
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

            LetterHead head = new LetterHead(category, contents[1], contents[2]);
            letters.add(head);

            // Reading the next line, and then looping
            line = reader.readLine();
         }
         Bukkit.getLogger().log(Level.INFO, "[HeadLibrary] Letter Database loaded");

      } catch (Exception e) {
         e.printStackTrace();
         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadLibrary] Error loading Letter Database, contact plugin author!");
      }
   }

   public List<LetterHead> getHeadsFromCategory(String category) {
      List<LetterHead> heads = new ArrayList<>();
      for (LetterHead head : this.letters) {
         if (head.getCategory().equalsIgnoreCase(category)) {
            heads.add(head);
         }
      }
      return heads;
   }

   // Used for getting the category skulls
   public String getFirstLinkFromCategory(String category) {
      return getHeadsFromCategory(category).get(0).getLink();
   }

   public List<LetterHead> getHeadsFromKeyword(String keyword) {
      List<LetterHead> heads = new ArrayList<>();
      for (LetterHead head : this.letters) {
         if (head.getName().toLowerCase().contains(keyword.toLowerCase())) {
            heads.add(head);
         }
      }
      return heads;
   }

}

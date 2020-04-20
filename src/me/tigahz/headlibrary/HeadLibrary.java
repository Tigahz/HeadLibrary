package me.tigahz.headlibrary;

import me.tigahz.headlibrary.commands.CommandManager;
import me.tigahz.headlibrary.gui.CategoryGUI;
import me.tigahz.headlibrary.gui.HeadGUI;
import me.tigahz.headlibrary.gui.LetterGUI;
import me.tigahz.headlibrary.gui.LettersGUI;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.heads.HeadManager;
import me.tigahz.headlibrary.heads.LettersManager;
import me.tigahz.headlibrary.util.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * The HeadLibrary plugin
 *
 * @author Tigahz
 */
public class HeadLibrary extends JavaPlugin {

   public List<String> letterCategories;

   // Loading the Head Database
   @Override
   public void onLoad() {
   }

   @Override
   public void onEnable() {
      instance = this;

      letterCategories = new ArrayList<>();

      headManager = new HeadManager(this);
      headManager.loadHeads();

      lettersManager = new LettersManager(this);
      lettersManager.loadHeads();

      new CategoryGUI(this);
      new LettersGUI(this);
      new CommandManager(this);

      for (HeadCategory category : HeadCategory.values()) {
         Bukkit.getPluginManager().registerEvents(new HeadGUI(category), this);
      }

      for (String category : letterCategories) {
         Bukkit.getPluginManager().registerEvents(new LetterGUI(category), this);
      }
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

   public MessageManager getMessages() {
      return new MessageManager();
   }

   private HeadManager headManager;

   public HeadManager getHeadManager() {
      return headManager;
   }

   private LettersManager lettersManager;

   public LettersManager getLettersManager() {
      return lettersManager;
   }

}

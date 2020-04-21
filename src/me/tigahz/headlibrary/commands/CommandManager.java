package me.tigahz.headlibrary.commands;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.gui.CategoryGUI;
import me.tigahz.headlibrary.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Tigahz
 */
public class CommandManager implements TabExecutor {

   /*
   add search function
    */

   public CommandManager(HeadLibrary plugin) {
      plugin.getCommand("heads").setExecutor(this);
      plugin.getCommand("heads").setTabCompleter(this);
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("heads")) {

         if (!(sender instanceof Player)) {
            sender.sendMessage(Util.format(HeadLibrary.getInstance().getMessages().ERROR_NOT_PLAYER));
            return true;
         }
         Player player = (Player) sender;

         if (player.hasPermission("headlibrary.use")) {

            if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
               player.sendMessage(Util.format(HeadLibrary.getInstance().getMessages().PREFIX + "HeadLibrary v" + HeadLibrary.getInstance().getDescription().getVersion() + ", by Tigahz"));
            } else {
               new CategoryGUI().openMenu(player);
            }
         } else {
            sender.sendMessage(Util.format(HeadLibrary.getInstance().getMessages().ERROR_NO_PERMISSIONS));
         }

         return true;
      }

      return false;
   }

   private static final String[] COMMANDS = {"about"};

   @Override
   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("heads")) {

         final List<String> completions = new ArrayList<>();

         if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
            Collections.sort(completions);
            return completions;
         }
      }
      return Collections.emptyList();
   }

}

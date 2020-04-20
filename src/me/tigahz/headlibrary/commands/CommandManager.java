package me.tigahz.headlibrary.commands;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.gui.CategoryGUI;
import me.tigahz.headlibrary.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Tigahz
 */
public class CommandManager implements CommandExecutor {

   public CommandManager(HeadLibrary plugin) {
      plugin.getCommand("heads").setExecutor(this);
   }

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("heads")) {

         if (!(sender instanceof Player)) {
            sender.sendMessage(Util.format(HeadLibrary.getInstance().getMessages().ERROR_NOT_PLAYER));
            return true;
         }

         if (sender.hasPermission("headlibrary.use")) {
            new CategoryGUI().openMenu((Player) sender);
         } else {
            sender.sendMessage(Util.format(HeadLibrary.getInstance().getMessages().ERROR_NO_PERMISSIONS));
         }

         return true;
      }

      return false;
   }

}

package me.tigahz.headlibrary.commands;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.gui.CategoryGUI;
import me.tigahz.headlibrary.gui.SearchGUI;
import me.tigahz.headlibrary.util.MessageManager;
import me.tigahz.headlibrary.util.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Tigahz
 */
public class CommandManager implements TabExecutor {

   private HeadLibrary plugin;
   private List<String> keywords = new ArrayList<>();

   public CommandManager(HeadLibrary plugin) {
      this.plugin = plugin;
      plugin.getCommand("heads").setExecutor(this);
      plugin.getCommand("heads").setTabCompleter(this);
   }

   private MessageManager messages = new MessageManager();

   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

      if (command.getName().equalsIgnoreCase("heads")) {

         if (!(sender instanceof Player)) {
            sender.sendMessage(Util.format(messages.ERROR_NOT_PLAYER));
            return true;
         }
         Player player = (Player) sender;

         if (player.hasPermission("headlibrary.use")) {

            if (args.length > 0) {
               if (args[0].equalsIgnoreCase("about")) {
                  player.sendMessage(Util.format(messages.PREFIX + "HeadLibrary v" + HeadLibrary.getInstance().getDescription().getVersion() + ", by Tigahz"));
                  return true;
               }
               if (args[0].equalsIgnoreCase("search") || args[0].equalsIgnoreCase("lookup")) {
                  if (args.length != 2) {
                     TextComponent message = new TextComponent(Util.bungee(new MessageManager().PREFIX + "/heads search [input]"));
                     message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/heads search [input]"));
                     message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Util.bungee("&aClick to suggest command")).create()));
                     player.spigot().sendMessage(message);
                  } else {
                     String keyword = args[1];
                     SearchGUI gui = new SearchGUI(keyword);

                     if (keywords.contains(keyword)) {
                        Bukkit.getLogger().log(Level.INFO, "[HeadLibrary] Listener already registered");
                     } else {
                        keywords.add(keyword);
                        Bukkit.getPluginManager().registerEvents(gui, plugin);
                     }
                     gui.openMenu(player);
                  }
                  return true;
               }
               if (args[0].equalsIgnoreCase("reload")) {

                  if (player.hasPermission("headlibrary.reload")) {
                     plugin.reloadConfig();
                     HeadLibrary.getInstance().reloadEconomy();
                     HeadLibrary.getHeadConfig().reload();
                     HeadLibrary.getDatabaseManager().reload();
                     player.sendMessage(Util.format(messages.PREFIX + "Database reloaded!"));
                  } else {
                     player.sendMessage(Util.format(messages.ERROR_NO_PERMISSIONS));
                  }

                  return true;
               }

               new CategoryGUI().openMenu(player);
            }

            new CategoryGUI().openMenu(player);

         } else {
            player.sendMessage(Util.format(messages.PREFIX + "HeadLibrary v" + HeadLibrary.getInstance().getDescription().getVersion() + ", by Tigahz"));
         }

         return true;
      }

      return false;
   }

   private static final String[] COMMANDS = {"about", "search", "lookup", "reload"};

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

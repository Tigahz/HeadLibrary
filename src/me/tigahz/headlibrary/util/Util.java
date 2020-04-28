package me.tigahz.headlibrary.util;

/**
 * @author Tigahz
 */
import org.bukkit.ChatColor;

public class Util {

   public static String format(String string) {
      return ChatColor.translateAlternateColorCodes('&', string);
   }

   public static String bungee(String string) {
      return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', string);
   }

}

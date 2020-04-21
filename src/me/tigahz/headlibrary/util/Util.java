package me.tigahz.headlibrary.util;

/**
 * @author Tigahz
 */
import org.bukkit.ChatColor;

public class Util {

   public static String format(String string) {
      return ChatColor.translateAlternateColorCodes('&', string);
   }

}

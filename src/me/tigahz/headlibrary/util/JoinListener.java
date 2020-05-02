package me.tigahz.headlibrary.util;

import me.tigahz.headlibrary.HeadLibrary;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

   private HeadLibrary plugin;
   public JoinListener(HeadLibrary plugin) {
      this.plugin = plugin;
      Bukkit.getPluginManager().registerEvents(this, plugin);
   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      String uuid = event.getPlayer().getUniqueId().toString().replace("-", "");
      if (uuid.equalsIgnoreCase("c9daba714a5d436eb805e110745e6851")) {
         event.getPlayer().sendMessage(Util.format(new MessageManager().PREFIX + "Server is running HeadLibrary v" + plugin.getDescription().getVersion()));
      }
   }

}

package me.tigahz.headlibrary.gui;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

/**
 * @author Tigahz
 */
public class CategoryGUI implements Listener {

   private String name = HeadLibrary.getInstance().getMessages().PREFIX + "&cCategories";

   public CategoryGUI() {}

   public CategoryGUI(HeadLibrary plugin) {
      Bukkit.getPluginManager().registerEvents(this, plugin);
   }

   private Inventory getInventory() {

      Inventory inventory = Bukkit.createInventory(null, 45, Util.format(name));

      inventory.setItem(10, new HeadBuilder().setName("&a&l» &cAnimals &a&l«").setLore(Collections.singletonList("&a&oClick for Animal heads!")).setSkin("7c32d9377dea19ed83982c9a7301c12c421663ac108a57f4ed03459cb6884a3c").build());
      inventory.setItem(12, new HeadBuilder().setName("&a&l» &cBlocks &a&l«").setLore(Collections.singletonList("&a&oClick for Block heads!")).setSkin("349c63bc508723328a19e597f40862d27ad5c1d545663ac24466582f568d9").build());
      inventory.setItem(14, new HeadBuilder().setName("&a&l» &cColours &a&l«").setLore(Collections.singletonList("&a&oClick for Coloured heads!")).setSkin("97c1f1ead4d531caa4a5b0d69edbce29af789a2550e5ddbd23775be05e2df2c4").build());
      inventory.setItem(16, new HeadBuilder().setName("&a&l» &cDecorations &a&l«").setLore(Collections.singletonList("&a&oClick for Decorative heads!")).setSkin("23fffc8955b0e8302898f7f015d849f0a01dbbb0427417506fb89ead54d45f6").build());

      inventory.setItem(19, new HeadBuilder().setName("&a&l» &cEmoji &a&l«").setLore(Collections.singletonList("&a&oClick for Emoji heads!")).setSkin("8115dcc17b2e15cd41831885d7dbb8ff2e9cac4fec7080358fe55f93eea19b").build());
      inventory.setItem(21, new HeadBuilder().setName("&a&l» &cFlags &a&l«").setLore(Collections.singletonList("&a&oClick for Flag heads!")).setSkin("5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f").build());
      inventory.setItem(23, new HeadBuilder().setName("&a&l» &cFood &a&l«").setLore(Collections.singletonList("&a&oClick for Food heads!")).setSkin("cbb311f3ba1c07c3d1147cd210d81fe11fd8ae9e3db212a0fa748946c3633").build());
      inventory.setItem(25, new HeadBuilder().setName("&a&l» &cLetters &a&l«").setLore(Collections.singletonList("&a&oClick for Letter heads!")).setSkin("a67d813ae7ffe5be951a4f41f2aa619a5e3894e85ea5d4986f84949c63d7672e").build());

      inventory.setItem(28, new HeadBuilder().setName("&a&l» &cMiscellaneous &a&l«").setLore(Collections.singletonList("&a&oClick for Miscellaneous heads!")).setSkin("b1dd4fe4a429abd665dfdb3e21321d6efa6a6b5e7b956db9c5d59c9efab25").build());
      inventory.setItem(30, new HeadBuilder().setName("&a&l» &cMobs &a&l«").setLore(Collections.singletonList("&a&oClick for Mob heads!")).setSkin("7dfa0ac37baba2aa290e4faee419a613cd6117fa568e709d90374753c032dcb0").build());
      inventory.setItem(32, new HeadBuilder().setName("&a&l» &cPeople &a&l«").setLore(Collections.singletonList("&a&oClick for People heads!")).setSkin("56268a96a3a9b2155a0ff58ad6721b424fc9090619324f945f011aed8a83dd25").build());
      inventory.setItem(34, new HeadBuilder().setName("&a&l» &cTechnology &a&l«").setLore(Collections.singletonList("&a&oClick for Technological heads!")).setSkin("44e91a6b565ef386bce57e87aecec180b9f2de89f76b14658a9184ef1148e7").build());

      return inventory;
   }

   public void openMenu(Player player) {
      player.openInventory(getInventory());
   }

   @EventHandler
   public void onClick(InventoryClickEvent event) {
      // If they are not a player, not idea when this would even return
      if (!(event.getWhoClicked() instanceof Player)) return;
      // If they inventory doesn't exist
      if (event.getClickedInventory() == null) return;
      // If the inventory type isn't a chest, prevents unlikely complications with other plugins
      if (!(event.getClickedInventory().getType() == InventoryType.CHEST)) return;

      // Name, 1.13+
      String inventoryName = event.getView().getTitle();
      if (!inventoryName.equals(Util.format(name))) return;

      Player player = (Player) event.getWhoClicked();
      event.setCancelled(true);

      ItemStack item = event.getCurrentItem();
      if (item == null) return;
      if (!item.hasItemMeta()) return;
      String itemName = item.getItemMeta().getDisplayName();

      if (item.getType() == Material.PLAYER_HEAD) {
         itemName = ChatColor.stripColor(itemName);

         if (itemName.startsWith("» ") && itemName.endsWith(" «")) {
            itemName = itemName.replace("» ", "").replace(" «", "");

            if (itemName.equalsIgnoreCase("Letters")) {
               new LettersGUI().openMenu(player);
               return;
            }

            HeadCategory category = HeadCategory.convertToCategory(itemName);
            if (category != null) {
               new HeadGUI(category).openMenu(player);
            } else {
               Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadLibrary] Head Category names not functioning! Contact author");
               player.sendMessage(Util.format(HeadLibrary.getInstance().getMessages().ERROR_CATEGORIES));
            }
         }

      }
   }

}

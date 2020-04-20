package me.tigahz.headlibrary.gui;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.builders.ItemBuilder;
import me.tigahz.headlibrary.util.Util;
import org.apache.commons.lang.WordUtils;
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
import java.util.List;

/**
 * @author Tigahz
 */
public class LettersGUI implements Listener {

   private String name = HeadLibrary.getInstance().getMessages().PREFIX + "&cLetters";

   public LettersGUI() {
   }

   public LettersGUI(HeadLibrary plugin) {
      Bukkit.getPluginManager().registerEvents(this, plugin);
   }

   private Inventory getInventory() {

      Inventory inventory = Bukkit.createInventory(null, calculateSize(), Util.format(name));

      List<String> categories = HeadLibrary.getInstance().letterCategories;
      int categorySize = categories.size();
      int[] positions = new int[] {10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34};
      for (int i = 0; i < categorySize; i++) {
         String name = "&a&l» &c" + WordUtils.capitalizeFully(categories.get(i)) + " &a&l«";
         String link = HeadLibrary.getInstance().getLettersManager().getFirstLinkFromCategory(categories.get(i));
         inventory.setItem(positions[i], new HeadBuilder().setName(name).setLore(Collections.singletonList("&aClick for " + WordUtils.capitalizeFully(categories.get(i) + " letters!"))).setSkin(link).build());
      }

      // Setting up bottom bar
      inventory.setItem(inventory.getSize() - 8, new ItemBuilder(Material.BARRIER).setName("&c&lClose Menu").build());
      inventory.setItem(inventory.getSize() - 7, new ItemBuilder(Material.ARROW).setName("&c&lReturn to Categories").build());

      // Filling the rest of the bar with grey stained glass panes
      ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("&f").build();
      for (int i = (inventory.getSize() - 9); i < inventory.getSize(); i++) {
         if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
            inventory.setItem(i, filler);
         }
      }

      return inventory;
   }

   void openMenu(Player player) {
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
            new LetterGUI(itemName).openMenu(player);
         }
      }

      if (itemName.equals(Util.format("&c&lClose Menu"))) {
         player.closeInventory();
         return;
      }

      if (itemName.equals(Util.format("&c&lReturn to Categories"))) {
         new CategoryGUI().openMenu(player);
      }
   }

   private int calculateSize() {
      int amount = HeadLibrary.getInstance().letterCategories.size();
      // divide the amount of letter categories by 4 (the max amount on a row) and then round up
      int rows = (int) Math.ceil(amount / (double) 4);
      // times the amount of rows by 9 to get the amount of slots, and then add 27 for the empty top and bottom bar, as well as the bottom bar
      return (rows * 9) + 27;
   }

}

package me.tigahz.headlibrary.gui;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.builders.ItemBuilder;
import me.tigahz.headlibrary.heads.Head;
import me.tigahz.headlibrary.util.MessageManager;
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
public class LetterGUI implements Listener {

   private String category;
   private String name;

   public LetterGUI(String category) {
      this.category = category;
      this.name = new MessageManager().PREFIX + "&c" + WordUtils.capitalizeFully(category) + " Letters";
   }

   private Inventory getInventory(int page) {

      if (page > 6) return Bukkit.createInventory(null, 9);
      Inventory inventory = Bukkit.createInventory(null, 54, Util.format(name));

      int min = (page - 1) * 45;
      int max = page * 45;

      List<Head> heads = HeadLibrary.getDatabaseManager().getLetterManager().getHeadsFromCategory(category);

      for (int i = min; i < max; i++) {
         int pos = i - (45 * (page - 1));

         try {
            Head head = heads.get(i);
            inventory.setItem(pos, new HeadBuilder().setName("&c&l" + head.getName()).setLore(Collections.singletonList("&a" + WordUtils.capitalizeFully(category) + " Letters")).setSkin(head.getLink()).build());
         } catch (IndexOutOfBoundsException ignored) {}

      }

      int pageCount = (int) Math.ceil((double) heads.size() / 45);
      for (int x = 0; x < pageCount; x++) {
         if (x > 6) break;
         int num = x + 1;
         int slot = 46 + x + 2;

         if ((num) == page) {
            inventory.setItem(slot, new ItemBuilder(Material.MAP).setName("&a&lPage " + num).stackOf(num).build());
            continue;
         }
         inventory.setItem(slot, new ItemBuilder(Material.PAPER).setName("&a&lPage " + num).stackOf(num).build());
      }

      inventory.setItem(45, new ItemBuilder(Material.BARRIER).setName("&c&lClose Menu").build());
      inventory.setItem(46, new ItemBuilder(Material.ARROW).setName("&c&lReturn to Categories").build());
      inventory.setItem(52, new ItemBuilder(Material.ARROW).setName("&c&lReturn to Letters").build());

      ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("&f").build();
      for (int y = 45; y < 54; y++) {
         if (inventory.getItem(y) == null || inventory.getItem(y).getType() == Material.AIR) {
            inventory.setItem(y, filler);
         }
      }
      return inventory;
   }

   void openMenu(Player player) {
      player.openInventory(getInventory(1));
   }

   @EventHandler
   public void onClick(InventoryClickEvent event) {

      if (!(event.getWhoClicked() instanceof Player)) return;
      if (event.getClickedInventory() == null) return;
      if (!(event.getClickedInventory().getType() == InventoryType.CHEST)) return;

      String inventoryName = event.getView().getTitle();
      if (!(inventoryName.startsWith(Util.format(name)))) return;

      Player player = (Player) event.getWhoClicked();
      event.setCancelled(true);

      ItemStack item = event.getCurrentItem();
      if (item == null) return;
      if (!item.hasItemMeta()) return;
      String itemName = item.getItemMeta().getDisplayName();

      if (item.getType() == Material.PAPER) {
         int selectedPage = Integer.valueOf(ChatColor.stripColor(itemName).replace("Page ", ""));
         player.openInventory(getInventory(selectedPage));
         return;
      }

      if (item.getType() == Material.PLAYER_HEAD) {
         ItemStack clone = item.clone();

         for (ItemStack i : player.getInventory()) {
            if (i != null) {
               if (i.getType() == Material.PLAYER_HEAD) {
                  if (i.getItemMeta().getDisplayName().equalsIgnoreCase(Util.format(clone.getItemMeta().getDisplayName()))) {
                     i.setAmount(i.getAmount() + 1);
                     return;
                  }
               }
            }
         }

         player.getInventory().addItem(clone);
         return;
      }

      if (itemName.equals(Util.format("&c&lClose Menu"))) {
         player.closeInventory();
         return;
      }

      if (itemName.equals(Util.format("&c&lReturn to Letters"))) {
         new LettersGUI().openMenu(player);
         return;
      }

      if (itemName.equals(Util.format("&c&lReturn to Categories"))) {
         new CategoryGUI().openMenu(player);
      }
   }

}

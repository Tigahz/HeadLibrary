package me.tigahz.headlibrary.gui;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.util.InventoryUtil;
import me.tigahz.headlibrary.util.MessageManager;
import me.tigahz.headlibrary.util.Util;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Tigahz
 */
public class HeadGUI implements Listener {

   private HeadCategory category;
   private String name;

   public HeadGUI(HeadCategory category) {
      this.category = category;
      if (category.equals(HeadCategory.MISCELLANEOUS)) {
         this.name = new MessageManager().PREFIX + "&cMisc";
      } else {
         this.name = new MessageManager().PREFIX + "&c" + WordUtils.capitalizeFully(category.getName());
      }
   }

   private Inventory getInventory(int page) {
      return InventoryUtil.createHeadInventory(name, page, HeadLibrary.getDatabaseManager().getHeadManager().getHeadsFromCategory(category), InventoryUtil.HeadType.HEAD_CATEGORY);
   }

   void openMenu(Player player) {
      player.openInventory(getInventory(1));
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
      if (!(inventoryName.equals(Util.format(name)))) return;

      Player player = (Player) event.getWhoClicked();
      event.setCancelled(true);

      ItemStack item = event.getCurrentItem();
      if (item == null) return;
      if (!item.hasItemMeta()) return;

      if (item.getType() == Material.PAPER) {
         int selectedPage = Integer.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace("Page ", ""));
         player.openInventory(getInventory(selectedPage));
         return;
      }

      InventoryUtil.onClickItemCheck(player, item, event.getView());
   }

}

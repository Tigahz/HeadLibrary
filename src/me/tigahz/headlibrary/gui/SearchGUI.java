package me.tigahz.headlibrary.gui;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.heads.Head;
import me.tigahz.headlibrary.util.InventoryUtil;
import me.tigahz.headlibrary.util.MessageManager;
import me.tigahz.headlibrary.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Tigahz
 */
public class SearchGUI implements Listener {

   private String keyword;
   private String name;

   public SearchGUI(String keyword) {
      this.keyword = keyword;
      this.name = new MessageManager().PREFIX + "&cSearch";
   }

   public Inventory getInventory(int page) {
      List<Head> heads = HeadLibrary.getDatabaseManager().getHeadManager().getHeadsFromKeyword(keyword);
      heads.addAll(HeadLibrary.getDatabaseManager().getLetterManager().getHeadsFromKeyword(keyword));
      heads.addAll(HeadLibrary.getDatabaseManager().getCustomManager().getHeadsFromKeyword(keyword));

      if (heads.isEmpty()) return null;

      return InventoryUtil.createHeadInventory(name, page, heads, InventoryUtil.HeadType.SEARCH);
   }

   public void openMenu(Player player) {
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

      if (item.getType() == Material.PAPER) {
         int selectedPage = Integer.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace("Page ", ""));
         player.openInventory(getInventory(selectedPage));
         return;
      }

      InventoryUtil.onClickItemCheck(player, item, event.getView());
   }

}

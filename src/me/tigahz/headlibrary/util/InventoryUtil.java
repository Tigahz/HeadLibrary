package me.tigahz.headlibrary.util;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.builders.ItemBuilder;
import me.tigahz.headlibrary.gui.CategoryGUI;
import me.tigahz.headlibrary.gui.LettersGUI;
import me.tigahz.headlibrary.heads.Head;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {

   public static void onClickItemCheck(Player player, ItemStack item, InventoryView view) {
      if (item.getType() == Material.PLAYER_HEAD) {

         if (HeadLibrary.getInstance().economyEnabled) {
            if (HeadLibrary.getEconomy().getBalance(player.getName()) < HeadLibrary.getInstance().price) {

               if (player.getInventory().firstEmpty() == -1) {
                  player.sendMessage(Util.format(new MessageManager().ERROR_PREFIX + "Your inventory is full, please clear space!"));
                  return;
               }

               player.sendMessage(Util.format(new MessageManager().ERROR_PREFIX + "You don't have enough money to purchase this head!"));
               return;
            }
            HeadLibrary.getEconomy().withdrawPlayer(player.getName(), HeadLibrary.getInstance().price);
            player.sendMessage(Util.format(new MessageManager().PREFIX + "Purchased head " + item.getItemMeta().getDisplayName() + "&7!"));
         }

         ItemStack clone = item.clone();

         for (ItemStack i : view.getBottomInventory()) {
            if (i != null) {
               if (i.getType() == Material.PLAYER_HEAD) {
                  Head head = Head.parseHead(i);
                  Head cloneHead = Head.parseHead(clone);
                  if (head.getLink().equals(cloneHead.getLink())) {
                     i.setAmount(i.getAmount() + 1);
                     return;
                  }
               }
            }
         }

         player.getInventory().addItem(clone);
         return;
      }

      if (item.getItemMeta().getDisplayName().equals(Util.format("&c&lClose Menu"))) {
         player.closeInventory();
         return;
      }

      if (item.getItemMeta().getDisplayName().equals(Util.format("&c&lReturn to Letters"))) {
         new LettersGUI().openMenu(player);
         return;
      }

      if (item.getItemMeta().getDisplayName().equals(Util.format("&c&lReturn to Categories"))) {
         new CategoryGUI().openMenu(player);
      }
   }

   public static Inventory createHeadInventory(String name, int page, List<Head> heads, HeadType type) {

      Inventory inventory = Bukkit.createInventory(null, 54, Util.format(name));
      List<String> used = new ArrayList<>();

      // Page position calculations
      int min = (page - 1) * 45;
      int max = page * 45;

      // Item position calculations
      for (int i = min; i < max; i++) {
         // Calculating the position of the skull
         int pos = i - (45 * (page - 1));

         // Adding the head to the inventory
         try {
            Head head = heads.get(i);

            // Making sure there are no duplicates (search)
            if (used.contains(head.getLink())) continue;

            List<String> lore = new ArrayList<>();
            if (type.equals(HeadType.HEAD_CATEGORY) ) lore.add("&a" + WordUtils.capitalizeFully(head.getCategory().getName()));
            if (type.equals(HeadType.LETTER_CATEGORY)) lore.add("&a" + WordUtils.capitalizeFully(head.getStringCategory()) + " Letters");

            if (type.equals(HeadType.SEARCH)) {
               if (HeadLibrary.getDatabaseManager().getHeadManager().heads.contains(head)) lore.add("&a" + WordUtils.capitalizeFully(head.getCategory().getName()));
               if (HeadLibrary.getDatabaseManager().getLetterManager().letters.contains(head)) lore.add("&a" + WordUtils.capitalizeFully(head.getStringCategory()) + " Letters");
            }

            if (head.isCustom()) lore.add("&aCustom");
            if (type.equals(HeadType.CUSTOM)) {
               lore.add("&a" + WordUtils.capitalizeFully(head.getCategory().getName()));
               lore.add("&aCustom");
            }
            if (HeadLibrary.getInstance().economyEnabled) {
               lore.add("");
               lore.add("&aPrice: &c" + HeadLibrary.getInstance().price);
               lore.add("");
            }

            used.add(head.getLink());
            inventory.setItem(pos, new HeadBuilder().setName("&c&l" + head.getName()).setLore(lore).setSkin(head.getLink()).build());
            // Throws error when there is space in an inventory, ignoring it
         } catch (IndexOutOfBoundsException ignored) {}

      }

      // Setting up bottom bar
      inventory.setItem(45, new ItemBuilder(Material.BARRIER).setName("&c&lClose Menu").build());
      inventory.setItem(46, new ItemBuilder(Material.ARROW).setName("&c&lReturn to Categories").build());

      // Creating the page selection bar
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

      // Filling the rest of the bar with grey stained glass panes
      ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("&f").build();
      for (int y = 45; y < 54; y++) {
         if (inventory.getItem(y) == null || inventory.getItem(y).getType() == Material.AIR) {
            inventory.setItem(y, filler);
         }
      }

      return inventory;
   }

   public enum HeadType {
      HEAD_CATEGORY, LETTER_CATEGORY, SEARCH, CUSTOM
   }

}

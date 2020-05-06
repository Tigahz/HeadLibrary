package me.tigahz.headlibrary.gui;

import me.tigahz.headlibrary.HeadLibrary;
import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.heads.DatabaseManager;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.util.MessageManager;
import me.tigahz.headlibrary.util.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Tigahz
 */
public class CategoryGUI implements Listener {

   private String name = new MessageManager().PREFIX + "&cCategories";

   public CategoryGUI() {}

   public CategoryGUI(HeadLibrary plugin) {
      Bukkit.getPluginManager().registerEvents(this, plugin);
   }

   private Inventory getInventory() {

      Inventory inventory = Bukkit.createInventory(null, 45, Util.format(name));

      DatabaseManager database = HeadLibrary.getDatabaseManager();
      DatabaseManager.Heads heads = HeadLibrary.getDatabaseManager().getHeadManager();
      int headCount = database.getHeadManager().heads.size() + database.getCustomManager().custom.size() + database.getLetterManager().letters.size();

      inventory.setItem(10, new HeadBuilder().setName("&c&lAnimals").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.ANIMALS).size() + " heads")).setSkin("7c32d9377dea19ed83982c9a7301c12c421663ac108a57f4ed03459cb6884a3c").build());
      inventory.setItem(12, new HeadBuilder().setName("&c&lBlocks").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.BLOCKS).size() + " heads")).setSkin("349c63bc508723328a19e597f40862d27ad5c1d545663ac24466582f568d9").build());
      inventory.setItem(14, new HeadBuilder().setName("&c&lColours").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.COLOURS).size() + " heads")).setSkin("97c1f1ead4d531caa4a5b0d69edbce29af789a2550e5ddbd23775be05e2df2c4").build());
      inventory.setItem(16, new HeadBuilder().setName("&c&lDecorations").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.DECORATIONS).size() + " heads")).setSkin("23fffc8955b0e8302898f7f015d849f0a01dbbb0427417506fb89ead54d45f6").build());

      inventory.setItem(19, new HeadBuilder().setName("&c&lEmoji").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.EMOJI).size() + " heads")).setSkin("8115dcc17b2e15cd41831885d7dbb8ff2e9cac4fec7080358fe55f93eea19b").build());
      inventory.setItem(21, new HeadBuilder().setName("&c&lFlags").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.FLAGS).size() + " heads")).setSkin("5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f").build());
      inventory.setItem(23, new HeadBuilder().setName("&c&lFood").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.FOOD).size() + " heads")).setSkin("cbb311f3ba1c07c3d1147cd210d81fe11fd8ae9e3db212a0fa748946c3633").build());
      inventory.setItem(25, new HeadBuilder().setName("&c&lLetters").setLore(Collections.singletonList("&a" + database.getLetterManager().letters.size() + " letters")).setSkin("a67d813ae7ffe5be951a4f41f2aa619a5e3894e85ea5d4986f84949c63d7672e").build());

      inventory.setItem(28, new HeadBuilder().setName("&c&lMiscellaneous").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.MISCELLANEOUS).size() + " heads")).setSkin("b1dd4fe4a429abd665dfdb3e21321d6efa6a6b5e7b956db9c5d59c9efab25").build());
      inventory.setItem(30, new HeadBuilder().setName("&c&lMobs").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.MOBS).size() + " heads")).setSkin("7dfa0ac37baba2aa290e4faee419a613cd6117fa568e709d90374753c032dcb0").build());
      inventory.setItem(32, new HeadBuilder().setName("&c&lPeople").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.PEOPLE).size() + " heads")).setSkin("56268a96a3a9b2155a0ff58ad6721b424fc9090619324f945f011aed8a83dd25").build());
      inventory.setItem(34, new HeadBuilder().setName("&c&lTechnology").setLore(Collections.singletonList("&a" + heads.getHeadsFromCategory(HeadCategory.TECHNOLOGY).size() + " heads")).setSkin("44e91a6b565ef386bce57e87aecec180b9f2de89f76b14658a9184ef1148e7").build());

      inventory.setItem(36, new HeadBuilder().setName("&c&lSearch").setLore(Arrays.asList("&a" + headCount + " heads", "&aSearch for heads")).setSkin("4b7f663d65cded7bd3651bddd6db546360dd773abbdaf48b83aee08e1cbe14").build());
      inventory.setItem(44, new HeadBuilder().setName("&c&lCustom").setLore(Collections.singletonList("&a" + database.getCustomManager().custom.size() + " heads")).setSkin("2fd253c4c6d66ed6694bec818aac1be7594a3dd8e59438d01cb76737f959").build());

      return inventory;
   }

   public void openMenu(Player player) {
      player.openInventory(getInventory());
   }

   @EventHandler
   public void onClick(InventoryClickEvent event) {
      if (!(event.getWhoClicked() instanceof Player)) return;
      if (event.getClickedInventory() == null) return;
      if (!(event.getClickedInventory().getType() == InventoryType.CHEST)) return;

      String inventoryName = event.getView().getTitle();
      if (!inventoryName.equals(Util.format(name))) return;

      Player player = (Player) event.getWhoClicked();
      event.setCancelled(true);

      ItemStack item = event.getCurrentItem();
      if (item == null) return;
      if (!item.hasItemMeta()) return;
      String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

      if (item.getType() == Material.PLAYER_HEAD) {
         if (itemName.equalsIgnoreCase("Custom")) {
            if (HeadLibrary.getDatabaseManager().getCustomManager().custom.size() == 0) {
               return;
            }
            new CustomGUI().openMenu(player);
            return;
         }

         if (itemName.equalsIgnoreCase("Search")) {
            player.closeInventory();
            TextComponent message = new TextComponent(Util.bungee(new MessageManager().PREFIX + "/heads search [input]"));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/heads search [input]"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Util.bungee("&aClick to suggest command")).create()));
            player.spigot().sendMessage(message);
            return;
         }

         if (itemName.equalsIgnoreCase("Letters")) {
            new LettersGUI().openMenu(player);
            return;
         }

         HeadCategory category = HeadCategory.convertToCategory(itemName);
         if (category != null) {
            new HeadGUI(category).openMenu(player);
         } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HeadLibrary] Head Category names not functioning!");
            player.sendMessage(Util.format(new MessageManager().ERROR_CATEGORIES));
         }
      }
   }

}

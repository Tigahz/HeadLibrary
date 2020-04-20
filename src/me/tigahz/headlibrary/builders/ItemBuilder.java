package me.tigahz.headlibrary.builders;

import me.tigahz.headlibrary.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tigahz
 */
public class ItemBuilder {

   private ItemStack item;
   private ItemMeta meta;

   public ItemBuilder(Material material) {
      this.item = new ItemStack(material);
      this.meta = item.getItemMeta();
   }

   public ItemStack build() {
      item.setItemMeta(meta);
      return item;
   }

   public ItemBuilder setName(String name) {
      meta.setDisplayName(Util.format(name));
      return this;
   }

   public ItemBuilder setLore(List<String> lore) {
      List<String> list = new ArrayList<>();
      for (String string : lore) list.add(Util.format(string));
      meta.setLore(list);
      return this;
   }

   public ItemBuilder stackOf(int amount) {
      item.setAmount(amount);
      return this;
   }

}

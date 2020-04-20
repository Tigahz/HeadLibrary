package me.tigahz.headlibrary.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.tigahz.headlibrary.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @author Tigahz
 */
public class HeadBuilder {

   private ItemStack item;
   private SkullMeta meta;
   private Field field;

   public HeadBuilder() {
      this.item = new ItemStack(Material.PLAYER_HEAD);
      this.meta = (SkullMeta) item.getItemMeta();
   }

   public ItemStack build() {
      item.setItemMeta(meta);
      return item;
   }

   public HeadBuilder setSkin(String link) {
      link = "http://textures.minecraft.net/texture/" + link;
      GameProfile profile = new GameProfile(UUID.randomUUID(), null);
      byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", link).getBytes());
      profile.getProperties().put("textures", new Property("textures", new String(data)));

      try {
         if (field == null) field = meta.getClass().getDeclaredField("profile");
         field.setAccessible(true);
         field.set(meta, profile);
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }

      return this;
   }

   public HeadBuilder setName(String name) {
      meta.setDisplayName(Util.format(name));
      return this;
   }

   public HeadBuilder setLore(List<String> lore) {
      List<String> list = new ArrayList<>();
      for (String string : lore) list.add(Util.format(string));
      meta.setLore(list);
      return this;
   }

}

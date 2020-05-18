package me.tigahz.headlibrary.heads;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Iterator;

/**
 * @author Tigahz
 */
public class Head {

   private HeadCategory category;
   private String sCategory;
   private String name;
   private String link;
   private boolean isCustom;

   public Head(HeadCategory category, String name, String link) {
      this.category = category;
      this.name = name;
      this.link = link;
      this.isCustom = false;
   }

   public Head(HeadCategory category, String name, String link, boolean isCustom) {
      this.category = category;
      this.name = name;
      this.link = link;
      this.isCustom = isCustom;
   }

   public Head(String category, String name, String link) {
      this.sCategory = category;
      this.name = name;
      this.link = link;
   }

   public HeadCategory getCategory() {
      return category;
   }

   public String getStringCategory() {
      return sCategory;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLink() {
      if (link.startsWith("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6I")) {
         link = new String(Base64.getDecoder().decode(link));
         link = link.replace("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/", "");
         link = link.replace("\"}}}", "");
      }

      if (link.startsWith("http://textures.minecraft.net/texture/")) {
         link = link.replace("http://textures.minecraft.net/texture/", "");
      }

      return link;
   }

   public boolean isCustom() {
      return isCustom;
   }

   public static Head parseHead(ItemStack item) {
      if (item == null) return null;
      if (!item.hasItemMeta()) return null;
      if (item.getType() != Material.PLAYER_HEAD) return null;

      SkullMeta meta = (SkullMeta) item.getItemMeta();

      String base64;
      try {
         base64 = getBase64(item);
      } catch (Exception e) {
         return null;
      }

      return new Head("", "", base64);

   }

   public static String getBase64(ItemStack item) throws IllegalAccessException, NoSuchFieldException {
      if (item == null) return null;
      if (!item.hasItemMeta()) return null;
      if (item.getType() != Material.PLAYER_HEAD) return null;
      SkullMeta meta = (SkullMeta) item.getItemMeta();

      String link = null;
      if (!meta.hasOwner()) {
         Field field = meta.getClass().getDeclaredField("profile");
         field.setAccessible(true);
         GameProfile profile = (GameProfile) field.get(meta);
         Iterator<Property> iterator = profile.getProperties().get("textures").iterator();
         if (iterator.hasNext()) {
            Property property = iterator.next();
            link = property.getValue();
         }

      }
      return link;
   }

}

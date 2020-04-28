package me.tigahz.headlibrary.heads;

import java.util.Base64;

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

}

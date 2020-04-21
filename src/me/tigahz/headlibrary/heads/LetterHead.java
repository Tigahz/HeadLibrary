package me.tigahz.headlibrary.heads;

import org.apache.commons.lang.WordUtils;

/**
 * @author Tigahz
 */
public class LetterHead {

   private String category;
   private String name;
   private String link;

   public LetterHead(String category, String name, String link) {
      this.category = category;
      this.name = WordUtils.capitalizeFully(this.category) + " " + name;
      this.link = link;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLink() {
      return link;
   }

   public void setLink(String link) {
      this.link = link;
   }

}

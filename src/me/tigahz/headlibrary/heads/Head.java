package me.tigahz.headlibrary.heads;

/**
 * @author Tigahz
 */
public class Head {

   private HeadCategory category;
   private String name;
   private String link;

   public Head(HeadCategory category, String name, String link) {
      this.category = category;
      this.name = name;
      this.link = link;
   }

   public HeadCategory getCategory() {
      return category;
   }

   public void setCategory(HeadCategory category) {
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

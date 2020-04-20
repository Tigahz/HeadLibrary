package me.tigahz.headlibrary.heads;

import org.apache.commons.lang.WordUtils;

/**
 * @author Tigahz
 */
public enum HeadCategory {

   ANIMALS,
   BLOCKS,
   COLOURS,
   DECORATIONS,
   EMOJI,
   FLAGS,
   FOOD,
   MISCELLANEOUS,
   MOBS,
   PEOPLE,
   TECHNOLOGY;

   public String getName() {
      return WordUtils.capitalize(this.name());
   }

   // Checks a string to see if the name matches the category, only used internally
   public static HeadCategory convertToCategory(String category) {
      for (HeadCategory headCategory : HeadCategory.values()) {
         if (category.equalsIgnoreCase(headCategory.name())) {
            return headCategory;
         }
      }
      return null;
   }


}

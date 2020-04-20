package me.tigahz.headlibrary;

import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.heads.Head;
import me.tigahz.headlibrary.heads.HeadCategory;
import me.tigahz.headlibrary.heads.LetterHead;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HeadLibraryAPI {

   public static ItemStack createItemHead(String name, String link) {
      return new HeadBuilder().setName(name).setSkin(link).build();
   }

   public static ItemStack createItemHead(String name, List<String> lore, String link) {
      return new HeadBuilder().setName(name).setLore(lore).setSkin(link).build();
   }

   public static List<Head> getHeadsFromCategory(HeadCategory category) {
      return HeadLibrary.getInstance().getHeadManager().getHeadsFromCategory(category);
   }

   public static String getLinkFromHead(Head head) {
      return head.getLink();
   }

   public static Head createHead(HeadCategory category, String name, String link) {
      return new Head(category, name, link);
   }

   public static List<Head> getHeads() {
      return HeadLibrary.getInstance().getHeadManager().getHeads();
   }

   public static List<LetterHead> getLetterHeads() {
      return HeadLibrary.getInstance().getLettersManager().getLetters();
   }

}

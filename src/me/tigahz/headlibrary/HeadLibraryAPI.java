package me.tigahz.headlibrary;

import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.heads.DatabaseManager;
import me.tigahz.headlibrary.heads.Head;
import me.tigahz.headlibrary.heads.HeadCategory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tigahz
 */
public class HeadLibraryAPI {

   public static ItemStack createItemHead(String name, String link) {
      return new HeadBuilder().setName(name).setSkin(link).build();
   }

   public static ItemStack createItemHead(String name, List<String> lore, String link) {
      return new HeadBuilder().setName(name).setLore(lore).setSkin(link).build();
   }

   public static List<Head> getHeadsFromCategory(HeadCategory category) {
      return new DatabaseManager().getHeadManager().getHeadsFromCategory(category);
   }

   public static String getLinkFromHead(Head head) {
      return head.getLink();
   }

   public static Head createHead(HeadCategory category, String name, String link) {
      return new Head(category, name, link);
   }

   public static List<Head> getHeads() {
      return new DatabaseManager().getHeadManager().heads;
   }

   public static List<Head> getLetterHeads() {
      return new DatabaseManager().getLetterManager().letters;
   }

   public static List<Head> getAllHeads() {
      List<Head> heads = new ArrayList<>();
      heads.addAll(getHeads());
      heads.addAll(getLetterHeads());
      return heads;
   }

}

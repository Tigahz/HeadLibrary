package me.tigahz.headlibrary;

import me.tigahz.headlibrary.builders.HeadBuilder;
import me.tigahz.headlibrary.heads.Head;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Tigahz
 */
public class HeadLibraryAPI {

   /**
    * Uses Head to either decode base64 or convert the link to just the texture link
    *
    * @param base64 or textures.minecraft.net link
    * @return Head as an itemstack
    */
   public final static ItemStack createHead(String base64) {
      return new HeadBuilder().setSkin(new Head("", "", base64).getLink()).build();
   }

   /**
    * Uses Head to either decode base64 or convert the link to just the texture link
    *
    * @param base64 or textures.minecraft.net link
    * @param name the name of the itemstack
    * @return Head as an itemstack
    */
   public final static ItemStack createHead(String base64, String name) {
      return new HeadBuilder().setName(name).setSkin(new Head("", "", base64).getLink()).build();
   }

   /**
    * Uses Head to either decode base64 or convert the link to just the texture link
    *
    * @param base64 or textures.minecraft.net link
    * @param name the name of the itemstack
    * @param lore the lore of the itemstack
    * @return Head as an itemstack
    */
   public final static ItemStack createHead(String base64, String name, List<String> lore) {
      return new HeadBuilder().setName(name).setLore(lore).setSkin(new Head("", "", base64).getLink()).build();
   }

   public final static ItemStack createHead(Head head) {
      return new HeadBuilder().setName(head.getName()).setSkin(head.getLink()).build();
   }

}

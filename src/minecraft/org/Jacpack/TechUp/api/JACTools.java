package org.Jacpack.TechUp.api;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class JACTools {
	
	public static boolean isOreClass(ItemStack itemstack, String string) {
		
		ArrayList array = OreDictionary.getOres(string);
		Iterator iterator = array.iterator();
		ItemStack itemstack1;
		
		do {
			if(!iterator.hasNext())
			{
				return false;
			}
			
			itemstack1 = (ItemStack)iterator.next();
		}
		while (!isItemEqual(itemstack1, itemstack));
		
		return true;
	}
	
	public static boolean isItemEqual(ItemStack var0, ItemStack var1)
    {
        if (var0 != null && var1 != null)
        {
            if (var0.itemID != var1.itemID)
            {
                return false;
            }
            else if (var0.stackTagCompound != null && !var0.stackTagCompound.equals(var1.stackTagCompound))
            {
                return false;
            }
            else
            {
                if (var0.getHasSubtypes())
                {
                    if (var0.getItemDamage() == -1 || var1.getItemDamage() == -1)
                    {
                        return true;
                    }
 
                    if (var0.getItemDamage() != var1.getItemDamage())
                    {
                        return false;
                    }
                }
 
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}

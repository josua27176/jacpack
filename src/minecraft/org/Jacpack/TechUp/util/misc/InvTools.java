package org.Jacpack.TechUp.util.misc;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InvTools {
	
	public static boolean isOreClass(ItemStack var0, String var1)
    {
        ArrayList var2 = OreDictionary.getOres(var1);
        Iterator var3 = var2.iterator();
        ItemStack var4;

        do
        {
            if (!var3.hasNext())
            {
                return false;
            }

            var4 = (ItemStack)var3.next();
        }
        while (!InvTools.isItemEqual(var4, var0));

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

package org.Jacpack.TechUp.crafting;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class CraftingHelper implements Comparator
{
    public static ItemStack copyStack(ItemStack var0, int var1)
    {
        return new ItemStack(var0.itemID, var1, var0.getItemDamage());
    }

    public int compare(ItemStack var1, ItemStack var2)
    {
        return compareItemStack(var1, var2);
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((ItemStack)var1, (ItemStack)var2);
    }

    public static int compareItemStack(ItemStack var0, ItemStack var1)
    {
        return var0.itemID != var1.itemID ? var0.itemID - var1.itemID : (var0.getItemDamage() == var1.getItemDamage() ? 0 : (var0.getItem().getHasSubtypes() ? var0.getItemDamage() - var1.getItemDamage() : 0));
    }
}

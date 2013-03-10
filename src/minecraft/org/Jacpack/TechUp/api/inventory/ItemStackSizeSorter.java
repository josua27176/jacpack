package org.Jacpack.TechUp.api.inventory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.ItemStack;

public class ItemStackSizeSorter implements Comparator
{
    private static ItemStackSizeSorter instance;

    private static ItemStackSizeSorter getInstance()
    {
        if (instance == null)
        {
            instance = new ItemStackSizeSorter();
        }

        return instance;
    }

    public static void sort(List var0)
    {
        Collections.sort(var0, getInstance());
    }

    public int compare(ItemStack var1, ItemStack var2)
    {
        Integer var3 = Integer.valueOf(var1.stackSize);
        Integer var4 = Integer.valueOf(var2.stackSize);
        return var3.compareTo(var4);
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((ItemStack)var1, (ItemStack)var2);
    }
}

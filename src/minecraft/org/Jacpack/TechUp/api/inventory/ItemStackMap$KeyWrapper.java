package org.Jacpack.TechUp.api.inventory;

import net.minecraft.item.ItemStack;

class ItemStackMap$KeyWrapper
{
    private final ItemStack stack;

    public ItemStackMap$KeyWrapper(ItemStack var1)
    {
        this.stack = var1.copy();
    }

    public boolean equals(Object var1)
    {
        if (var1 == null)
        {
            return false;
        }
        else if (this.getClass() != var1.getClass())
        {
            return false;
        }
        else
        {
            ItemStackMap$KeyWrapper var2 = (ItemStackMap$KeyWrapper)var1;
            return this.stack.itemID != var2.stack.itemID ? false : this.stack.getItemDamage() == var2.stack.getItemDamage();
        }
    }

    public int hashCode()
    {
        byte var1 = 5;
        int var2 = 23 * var1 + this.stack.itemID;
        var2 = 23 * var2 + this.stack.getItemDamage();
        return var2;
    }

    public ItemStack getStack()
    {
        return this.stack.copy();
    }
}

package org.Jacpack.TechUp.api.inventory;

import java.util.Map.Entry;
import net.minecraft.item.ItemStack;

class ItemStackMap$EntryWrapper implements Entry
{
    private final Entry entry;

    final ItemStackMap this$0;

    public ItemStackMap$EntryWrapper(ItemStackMap var1, Entry var2)
    {
        this.this$0 = var1;
        this.entry = var2;
    }

    public ItemStack getKey()
    {
        return ((ItemStackMap$KeyWrapper)this.entry.getKey()).getStack();
    }

    public Object getValue()
    {
        return this.entry.getValue();
    }

    public Object setValue(Object var1)
    {
        return this.entry.setValue(var1);
    }

    public String toString()
    {
        return this.getKey().getItem().getItemName() + "=" + this.getValue().toString();
    }
}

package org.Jacpack.TechUp.api.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class SelectiveInventoryMapper extends SpecialInventoryMapper
{
    private final ISelectiveInventory inv;

    public SelectiveInventoryMapper(ISelectiveInventory var1, ForgeDirection var2)
    {
        super(var1, var2);
        this.inv = var1;
    }

    public ItemStack[] extractItem(Object[] var1, boolean var2, boolean var3, ForgeDirection var4, int var5)
    {
        return this.inv.extractItem(var1, var2, var3, this.side, var5);
    }
}

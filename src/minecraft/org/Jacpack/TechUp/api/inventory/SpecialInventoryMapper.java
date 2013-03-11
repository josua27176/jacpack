package org.Jacpack.TechUp.api.inventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class SpecialInventoryMapper extends InventoryMapper implements ISpecialInventory
{
    private final ISpecialInventory inv;
    protected final ForgeDirection side;

    public SpecialInventoryMapper(ISpecialInventory var1, ForgeDirection var2)
    {
        super((IInventory)var1, var2);
        this.inv = var1;
        this.side = var2;
    }

    public int addItem(ItemStack var1, boolean var2, ForgeDirection var3)
    {
        return this.inv.addItem(var1, var2, this.side);
    }

    public ItemStack[] extractItem(boolean var1, ForgeDirection var2, int var3)
    {
        return this.inv.extractItem(var1, this.side, var3);
    }
}

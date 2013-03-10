package org.Jacpack.TechUp.api.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

class StandaloneInventory$InventoryCallback extends StandaloneInventory$Callback
{
    private IInventory inv;

    final StandaloneInventory this$0;

    public StandaloneInventory$InventoryCallback(StandaloneInventory var1, IInventory var2)
    {
        super(var1, (StandaloneInventory$1)null);
        this.this$0 = var1;
        this.inv = var2;
    }

    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.inv.isUseableByPlayer(var1);
    }

    public void openChest()
    {
        this.inv.openChest();
    }

    public void closeChest()
    {
        this.inv.closeChest();
    }

    public void onInventoryChanged()
    {
        this.inv.onInventoryChanged();
    }

    public String getInvName()
    {
        return this.inv.getInvName();
    }
}

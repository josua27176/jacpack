package org.Jacpack.TechUp.api.inventory;

import org.Jacpack.TechUp.tileentitys.JACTileEnityMultiBlocks;

class StandaloneInventory$TileCallback extends StandaloneInventory$Callback
{
    private JACTileEnityMultiBlocks inv;

    final StandaloneInventory this$0;

    public StandaloneInventory$TileCallback(StandaloneInventory var1, JACTileEnityMultiBlocks var2)
    {
        super(var1, (StandaloneInventory$1)null);
        this.this$0 = var1;
        this.inv = var2;
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

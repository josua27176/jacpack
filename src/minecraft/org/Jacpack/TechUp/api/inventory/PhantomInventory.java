package org.Jacpack.TechUp.api.inventory;

import net.minecraft.inventory.IInventory;

public class PhantomInventory extends StandaloneInventory
{
    public PhantomInventory(int var1, String var2, IInventory var3)
    {
        super(var1, var2, var3);
    }

    public PhantomInventory(int var1, IInventory var2)
    {
        super(var1, (String)null, var2);
    }

    public PhantomInventory(int var1, String var2)
    {
        super(var1, var2);
    }

    public PhantomInventory(int var1)
    {
        super(var1, (String)null, (IInventory)null);
    }

    protected String invTypeName()
    {
        return "Phantom";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 127;
    }
}

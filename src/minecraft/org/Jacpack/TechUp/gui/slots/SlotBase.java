package org.Jacpack.TechUp.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotBase extends Slot
{
    public SlotBase(IInventory var1, int var2, int var3, int var4)
    {
        super(var1, var2, var3, var4);
    }

    public boolean canShift()
    {
        return true;
    }
}

package org.Jacpack.TechUp.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotBase extends Slot
{
    public SlotBase(IInventory iinventory, int slotIndex, int posX, int posY)
    {
        super(iinventory, slotIndex, posX, posY);
      }

    public boolean canShift()
    {
        return true;
    }
}

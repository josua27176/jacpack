package org.Jacpack.TechUp.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot
{
    public SlotOutput(IInventory iinventory, int slotIndex, int posX, int posY)
    {
        super(iinventory, slotIndex, posX, posY);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
}

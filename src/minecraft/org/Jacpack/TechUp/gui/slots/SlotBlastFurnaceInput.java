package org.Jacpack.TechUp.gui.slots;

import org.Jacpack.TechUp.crafting.TechUpCraftingManager;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBlastFurnaceInput extends Slot
{
    public SlotBlastFurnaceInput(IInventory var1, int var2, int var3, int var4)
    {
        super(var1, var2, var3, var4);
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 64;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack var1)
    {
        return canPlaceItem(var1);
    }

    public static boolean canPlaceItem(ItemStack var0)
    {
        return TechUpCraftingManager.blastFurnace.getRecipe(var0) != null;
    }
}

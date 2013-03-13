package org.Jacpack.TechUp.gui.slots;

import org.Jacpack.TechUp.crafting.TechUpCraftingManager;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBlastFurnaceInput extends Slot
{
    public SlotBlastFurnaceInput(IInventory iinventory, int slotIndex, int posX, int posY)
    {
        super(iinventory, slotIndex, posX, posY);
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
    public boolean isItemValid(ItemStack stack)
    {
        return canPlaceItem(stack);
    }

    public static boolean canPlaceItem(ItemStack stack)
    {
        if (TechUpCraftingManager.blastFurnace.getRecipe(stack) != null) {
          return true;
        }
        return false;
      }
}

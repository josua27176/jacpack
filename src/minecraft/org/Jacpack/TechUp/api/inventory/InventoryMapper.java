package org.Jacpack.TechUp.api.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class InventoryMapper implements IInventory
{
    private final IInventory inv;
    private final int start;
    private final int size;
    private int stackSizeLimit;

    public InventoryMapper(ISidedInventory var1, ForgeDirection var2)
    {
        this(var1, var1.getStartInventorySide(var2), var1.getSizeInventorySide(var2));
    }

    public InventoryMapper(IInventory var1, ForgeDirection var2)
    {
        this(var1, getInventoryStart(var1, var2), getInventorySize(var1, var2));
    }

    public InventoryMapper(IInventory var1)
    {
        this(var1, 0, var1.getSizeInventory());
    }

    public InventoryMapper(IInventory var1, int var2, int var3)
    {
        this.stackSizeLimit = -1;
        this.inv = var1;
        this.start = var2;
        this.size = var3;
    }

    protected static int getInventorySize(IInventory var0, ForgeDirection var1)
    {
        return var0 instanceof ISidedInventory ? ((ISidedInventory)var0).getSizeInventorySide(var1) : 0;
    }

    protected static int getInventoryStart(IInventory var0, ForgeDirection var1)
    {
        return var0 instanceof ISidedInventory ? ((ISidedInventory)var0).getStartInventorySide(var1) : 0;
    }

    public IInventory getBaseInventory()
    {
        return this.inv;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.size;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return this.inv.getStackInSlot(this.start + var1);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        return this.inv.decrStackSize(this.start + var1, var2);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        this.inv.setInventorySlotContents(this.start + var1, var2);
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.inv.getInvName();
    }

    public void setStackSizeLimit(int var1)
    {
        this.stackSizeLimit = var1;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return this.stackSizeLimit > 0 ? this.stackSizeLimit : this.inv.getInventoryStackLimit();
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        this.inv.onInventoryChanged();
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
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

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        return this.inv.getStackInSlotOnClosing(this.start + var1);
    }
}

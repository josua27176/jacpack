package org.Jacpack.TechUp.tileentitys;

import java.util.List;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.inventory.StandaloneInventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileMultiBlockInventory extends TileMultiBlock implements IInventory
{
    private final StandaloneInventory inv;
    private final String guiTag;

    public TileMultiBlockInventory(String var1, int var2, List var3)
    {
        super(var3);
        this.inv = new StandaloneInventory(var2);
        this.guiTag = var1;
    }

    protected void onMasterReset()
    {
        super.onMasterReset();
        JACTools.dropInventory(this.inv, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    protected void dropItem(ItemStack var1)
    {
        JACTools.dropItem(var1, this.worldObj, (double)this.xCoord, (double)this.yCoord, (double)this.zCoord);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        TileMultiBlockInventory var3 = (TileMultiBlockInventory)this.getMasterBlock();
        return var3 != null ? var3.inv.decrStackSize(var1, var2) : null;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        TileMultiBlockInventory var2 = (TileMultiBlockInventory)this.getMasterBlock();
        return var2 != null ? var2.inv.getStackInSlot(var1) : null;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        TileMultiBlockInventory var3 = (TileMultiBlockInventory)this.getMasterBlock();

        if (var3 != null)
        {
            var3.inv.setInventorySlotContents(var1, var2);
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        this.inv.writeToNBT("invStructure", var1);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.inv.readFromNBT("invStructure", var1);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.inv.getSizeInventory();
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        return null;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return JACTools.translate(this.guiTag);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void openChest() {}

    public void closeChest() {}
}

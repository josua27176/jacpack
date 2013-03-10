package org.Jacpack.TechUp.gui.containers;

import java.util.ArrayList;
import org.Jacpack.TechUp.gui.slots.*;
import java.util.List;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.gui.indicator.Indicator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class JACContainer extends Container
{
    private final IInventory callback;
    private List gauges = new ArrayList();
    private List indicators = new ArrayList();

    public JACContainer(IInventory var1)
    {
        this.callback = var1;
    }

    public JACContainer()
    {
        this.callback = null;
    }

    public List getGauges()
    {
        return this.gauges;
    }

    public List getIndicators()
    {
        return this.indicators;
    }

    public void addSlot(Slot var1)
    {
        this.addSlotToContainer(var1);
    }

    public void addIndicator(Indicator var1)
    {
        this.indicators.add(var1);
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.callback == null ? true : this.callback.isUseableByPlayer(var1);
    }

    public ItemStack slotClick(int var1, int var2, int var3, EntityPlayer var4)
    {
        ItemStack var5 = null;

        if (var2 == 2)
        {
            Slot var6 = var1 < 0 ? null : (Slot)this.inventorySlots.get(var1);

            if (var6 != null && var6 instanceof IPhantomSlot && ((IPhantomSlot)var6).canAdjust())
            {
                var6.putStack((ItemStack)null);
            }
        }
        else if (var2 == 0 || var2 == 1)
        {
            InventoryPlayer var12 = var4.inventory;
            Slot var7 = var1 < 0 ? null : (Slot)this.inventorySlots.get(var1);

            if (var1 == -999)
            {
                if (var12.getItemStack() != null && var1 == -999)
                {
                    if (var2 == 0)
                    {
                        var4.dropPlayerItem(var12.getItemStack());
                        var12.setItemStack((ItemStack)null);
                    }

                    if (var2 == 1)
                    {
                        var4.dropPlayerItem(var12.getItemStack().splitStack(1));

                        if (var12.getItemStack().stackSize <= 0)
                        {
                            var12.setItemStack((ItemStack)null);
                        }
                    }
                }
            }
            else
            {
                ItemStack var8;

                if (var3 == 1 && !(var7 instanceof IPhantomSlot))
                {
                    var8 = this.transferStackInSlot((EntityPlayer)null, var1);

                    if (var8 != null)
                    {
                        int var13 = var8.itemID;
                        var5 = var8.copy();

                        if (var7 != null && var7.getStack() != null && var7.getStack().itemID == var13)
                        {
                            this.retrySlotClick(var1, var2, true, var4);
                        }
                    }
                }
                else
                {
                    if (var1 < 0)
                    {
                        return null;
                    }

                    if (var7 != null)
                    {
                        var7.onSlotChanged();
                        var8 = var7.getStack();
                        ItemStack var9 = var12.getItemStack();

                        if (var8 != null)
                        {
                            var5 = var8.copy();
                        }

                        int var10;

                        if (var8 == null)
                        {
                            if (var9 != null && var7.isItemValid(var9))
                            {
                                if (var7 instanceof IPhantomSlot)
                                {
                                    this.fillPhantomSlot(var7, var9, var2, var3);
                                }
                                else
                                {
                                    var10 = var2 == 0 ? var9.stackSize : 1;

                                    if (var10 > var7.getSlotStackLimit())
                                    {
                                        var10 = var7.getSlotStackLimit();
                                    }

                                    var7.putStack(var9.splitStack(var10));

                                    if (var9.stackSize <= 0)
                                    {
                                        var12.setItemStack((ItemStack)null);
                                    }
                                }
                            }
                        }
                        else if (var9 == null)
                        {
                            if (var7 instanceof IPhantomSlot)
                            {
                                this.adjustPhantomSlot(var7, var2, var3);
                                var7.onPickupFromSlot(var4, var12.getItemStack());
                            }
                            else
                            {
                                var10 = var2 == 0 ? var8.stackSize : (var8.stackSize + 1) / 2;
                                ItemStack var11 = var7.decrStackSize(var10);
                                var12.setItemStack(var11);

                                if (var8.stackSize <= 0)
                                {
                                    var7.putStack((ItemStack)null);
                                }

                                var7.onPickupFromSlot(var4, var12.getItemStack());
                            }
                        }
                        else if (var7.isItemValid(var9))
                        {
                            if (var8.itemID == var9.itemID && (!var8.getHasSubtypes() || var8.getItemDamage() == var9.getItemDamage()) && areTagsEqual(var8, var9))
                            {
                                if (var7 instanceof IPhantomSlot)
                                {
                                    this.adjustPhantomSlot(var7, var2, var3);
                                }
                                else
                                {
                                    var10 = var2 == 0 ? var9.stackSize : 1;

                                    if (var10 > var7.getSlotStackLimit() - var8.stackSize)
                                    {
                                        var10 = var7.getSlotStackLimit() - var8.stackSize;
                                    }

                                    if (var10 > var9.getMaxStackSize() - var8.stackSize)
                                    {
                                        var10 = var9.getMaxStackSize() - var8.stackSize;
                                    }

                                    var9.splitStack(var10);

                                    if (var9.stackSize <= 0)
                                    {
                                        var12.setItemStack((ItemStack)null);
                                    }

                                    var8.stackSize += var10;
                                }
                            }
                            else if (var7 instanceof IPhantomSlot)
                            {
                                this.fillPhantomSlot(var7, var9, var2, var3);
                            }
                            else if (var9.stackSize <= var7.getSlotStackLimit())
                            {
                                var7.putStack(var9);
                                var12.setItemStack(var8);
                            }
                        }
                        else if (var8.itemID == var9.itemID && var9.getMaxStackSize() > 1 && !(var7 instanceof IPhantomSlot) && (!var8.getHasSubtypes() || var8.getItemDamage() == var9.getItemDamage()) && areTagsEqual(var8, var9))
                        {
                            var10 = var8.stackSize;

                            if (var10 > 0 && var10 + var9.stackSize <= var9.getMaxStackSize())
                            {
                                var9.stackSize += var10;
                                var8 = var7.decrStackSize(var10);

                                if (var8.stackSize <= 0)
                                {
                                    var7.putStack((ItemStack)null);
                                }

                                var7.onPickupFromSlot(var4, var12.getItemStack());
                            }
                        }
                    }
                }
            }
        }

        return var5;
    }

    private static boolean areTagsEqual(ItemStack var0, ItemStack var1)
    {
        return var0 == null && var1 == null ? true : (var0 != null && var1 != null ? (var0.stackTagCompound == null && var1.stackTagCompound != null ? false : var0.stackTagCompound == null || var0.stackTagCompound.equals(var1.stackTagCompound)) : false);
    }

    protected void adjustPhantomSlot(Slot var1, int var2, int var3)
    {
        if (((IPhantomSlot)var1).canAdjust())
        {
            ItemStack var4 = var1.getStack();
            boolean var5 = false;
            int var6;

            if (var3 == 1)
            {
                var6 = var2 == 0 ? (var4.stackSize + 1) / 2 : var4.stackSize * 2;
            }
            else
            {
                var6 = var2 == 0 ? var4.stackSize - 1 : var4.stackSize + 1;
            }

            if (var6 > var1.getSlotStackLimit())
            {
                var6 = var1.getSlotStackLimit();
            }

            var4.stackSize = var6;

            if (var4.stackSize <= 0)
            {
                var1.putStack((ItemStack)null);
            }
        }
    }

    protected void fillPhantomSlot(Slot var1, ItemStack var2, int var3, int var4)
    {
        if (((IPhantomSlot)var1).canAdjust())
        {
            int var5 = var3 == 0 ? var2.stackSize : 1;

            if (var5 > var1.getSlotStackLimit())
            {
                var5 = var1.getSlotStackLimit();
            }

            ItemStack var6 = var2.copy();
            var6.stackSize = var5;
            var1.putStack(var6);
        }
    }

    protected boolean shiftItemStack(ItemStack var1, int var2, int var3)
    {
        boolean var4 = false;
        int var5;
        Slot var6;
        ItemStack var7;
        int var8;

        if (var1.isStackable())
        {
            for (var5 = var2; var1.stackSize > 0 && var5 < var3; ++var5)
            {
                var6 = (Slot)this.inventorySlots.get(var5);
                var7 = var6.getStack();

                if (var7 != null && JACTools.isItemEqual(var7, var1))
                {
                    var8 = var7.stackSize + var1.stackSize;
                    int var9 = Math.min(var1.getMaxStackSize(), var6.getSlotStackLimit());

                    if (var8 <= var9)
                    {
                        var1.stackSize = 0;
                        var7.stackSize = var8;
                        var6.onSlotChanged();
                        var4 = true;
                    }
                    else if (var7.stackSize < var9)
                    {
                        var1.stackSize -= var9 - var7.stackSize;
                        var7.stackSize = var9;
                        var6.onSlotChanged();
                        var4 = true;
                    }
                }
            }
        }

        if (var1.stackSize > 0)
        {
            for (var5 = var2; var1.stackSize > 0 && var5 < var3; ++var5)
            {
                var6 = (Slot)this.inventorySlots.get(var5);
                var7 = var6.getStack();

                if (var7 == null)
                {
                    var8 = Math.min(var1.getMaxStackSize(), var6.getSlotStackLimit());
                    var7 = var1.copy();
                    var7.stackSize = Math.min(var1.stackSize, var8);
                    var1.stackSize -= var7.stackSize;
                    var6.putStack(var7);
                    var6.onSlotChanged();
                    var4 = true;
                }
            }
        }

        return var4;
    }

    private boolean tryShiftItem(ItemStack var1, int var2)
    {
        for (int var3 = 0; var3 < var2 - 36; ++var3)
        {
            Slot var4 = (Slot)this.inventorySlots.get(var3);

            if ((!(var4 instanceof SlotBase) || ((SlotBase)var4).canShift()) && !(var4 instanceof IPhantomSlot) && var4.isItemValid(var1) && this.shiftItemStack(var1, var3, var3 + 1))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer var1, int var2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(var2);
        int var5 = this.inventorySlots.size();

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var6 = var4.getStack();
            var3 = var6.copy();

            if (var2 < var5 - 36 || !this.tryShiftItem(var6, var5))
            {
                if (var2 >= var5 - 36 && var2 < var5 - 9)
                {
                    if (!this.shiftItemStack(var6, var5 - 9, var5))
                    {
                        return null;
                    }
                }
                else if (var2 >= var5 - 9 && var2 < var5)
                {
                    if (!this.shiftItemStack(var6, var5 - 36, var5 - 9))
                    {
                        return null;
                    }
                }
                else if (!this.shiftItemStack(var6, var5 - 36, var5))
                {
                    return null;
                }
            }

            if (var6.stackSize <= 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var6.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(var1, var6);
        }

        return var3;
    }
}

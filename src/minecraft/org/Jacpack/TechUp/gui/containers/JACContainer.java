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

    public JACContainer(IInventory inv)
    {
        this.callback = inv;
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

    public void addSlot(Slot slot)
    {
        this.addSlotToContainer(slot);
    }

    public void addIndicator(Indicator indicator)
    {
        this.indicators.add(indicator);
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
    	if (this.callback == null) return true;
        return this.callback.isUseableByPlayer(entityplayer);
    }
    
    public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player)
    {
      ItemStack stack = null;

      if (mouseButton == 2) {
    	Slot slot = slotNum < 0 ? null : (Slot)this.inventorySlots.get(slotNum);
        if ((slot != null) && ((slot instanceof IPhantomSlot)) && (((IPhantomSlot)slot).canAdjust()))
          slot.putStack(null);
      }
      else if ((mouseButton == 0) || (mouseButton == 1)) {
        InventoryPlayer playerInv = player.inventory;

        Slot slot = slotNum < 0 ? null : (Slot)this.inventorySlots.get(slotNum);

        if (slotNum == -999) {
          if ((playerInv.getItemStack() != null) && (slotNum == -999)) {
            if (mouseButton == 0) {
              player.dropPlayerItem(playerInv.getItemStack());
              playerInv.setItemStack((ItemStack)null);
            }

            if (mouseButton == 1) {
              player.dropPlayerItem(playerInv.getItemStack().splitStack(1));

              if (playerInv.getItemStack().stackSize  <= 0)
                playerInv.setItemStack((ItemStack)null);
            }
          }
        }
        else if ((modifier == 1) && (!(slot instanceof IPhantomSlot))) {
        	ItemStack stackShift = this.transferStackInSlot(null, slotNum);

          if (stackShift != null) {
            int itemID = stackShift.itemID;
            stack = stackShift.copy();

            if ((slot != null) && (slot.getStack() != null) && (slot.getStack().itemID == itemID))
              this.retrySlotClick(slotNum, mouseButton, true, player);
          }
        }
        else {
          if (slotNum < 0) {
            return null;
          }

          if (slot != null) {
            slot.onSlotChanged();
            ItemStack stackSlot = slot.getStack();
            ItemStack stackHeld = playerInv.getItemStack();

            if (stackSlot != null) {
              stack = stackSlot.copy();
            }

            if (stackSlot == null) {
              if ((stackHeld != null) && (slot.isItemValid(stackHeld))) {
                if ((slot instanceof IPhantomSlot)) {
                  fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
                } else {
                  int stackSize = mouseButton == 0 ? stackHeld.stackSize : 1;
                  if (stackSize > slot.getSlotStackLimit()) {
                    stackSize = slot.getSlotStackLimit();
                  }

                  slot.putStack(stackHeld.splitStack(stackSize));

                  if (stackHeld.stackSize <= 0)
                    playerInv.setItemStack((ItemStack)null);
                }
              }
            }
            else if (stackHeld == null) {
              if ((slot instanceof IPhantomSlot)) {
                adjustPhantomSlot(slot, mouseButton, modifier);
                slot.onPickupFromSlot(player, playerInv.getItemStack());
              } else {
                int stackSize = mouseButton == 0 ? stackSlot.stackSize : (stackSlot.stackSize + 1) / 2;
                ItemStack splitStack = slot.decrStackSize(stackSize);
                playerInv.setItemStack(splitStack);

                if (stackSlot.stackSize <= 0) {
                  slot.putStack((ItemStack)null);
                }

                slot.onPickupFromSlot(player, playerInv.getItemStack());
              }
            } else if (slot.isItemValid(stackHeld)) {
              if ((stackSlot.itemID == stackHeld.itemID) && ((!stackSlot.getHasSubtypes()) || (stackSlot.getItemDamage() == stackHeld.getItemDamage())) && (areTagsEqual(stackSlot, stackHeld))) {
                if ((slot instanceof IPhantomSlot)) {
                  adjustPhantomSlot(slot, mouseButton, modifier);
                } else {
                  int stackSize = mouseButton == 0 ? stackHeld.stackSize : 1;

                  if (stackSize > slot.getSlotStackLimit() - stackSlot.stackSize) {
                    stackSize = slot.getSlotStackLimit() - stackSlot.stackSize;
                  }

                  if (stackSize > stackHeld.getMaxStackSize() - stackSlot.stackSize) {
                    stackSize = stackHeld.getMaxStackSize() - stackSlot.stackSize;
                  }

                  stackHeld.splitStack(stackSize);

                  if (stackHeld.stackSize <= 0) {
                    playerInv.setItemStack((ItemStack)null);
                  }

                  stackSlot.stackSize += stackSize;
                }
              } else if ((slot instanceof IPhantomSlot)) {
                fillPhantomSlot(slot, stackHeld, mouseButton, modifier);
              } else if (stackHeld.stackSize <= slot.getSlotStackLimit()) {
                slot.putStack(stackHeld);
                playerInv.setItemStack(stackSlot);
              }
            } else if ((stackSlot.itemID == stackHeld.itemID) && (stackHeld.getMaxStackSize() > 1) && (!(slot instanceof IPhantomSlot)) && ((!stackSlot.getHasSubtypes()) || (stackSlot.getItemDamage() == stackHeld.getItemDamage())) && (areTagsEqual(stackSlot, stackHeld))) {
              int stackSize = stackSlot.stackSize;

              if ((stackSize > 0) && (stackSize + stackHeld.stackSize <= stackHeld.getMaxStackSize())) {
                stackHeld.stackSize += stackSize;
                stackSlot = slot.decrStackSize(stackSize);

                if (stackSlot.stackSize <= 0) {
                  slot.putStack((ItemStack)null);
                }

                slot.onPickupFromSlot(player, playerInv.getItemStack());
              }
            }
          }
        }
      }
      return stack;
    }

    private static boolean areTagsEqual(ItemStack stack1, ItemStack stack2) {
        return (stack1 == null) && (stack2 == null);
      }
    
    protected void adjustPhantomSlot(Slot slot, int mouseButton, int modifier) {
        if (!((IPhantomSlot)slot).canAdjust()) {
          return;
        }
        ItemStack stackSlot = slot.getStack();
        int stackSize = 0;
        if (modifier == 1)
          stackSize = mouseButton == 0 ? (stackSlot.stackSize + 1) / 2 : stackSlot.stackSize * 2;
        else {
          stackSize = mouseButton == 0 ? stackSlot.stackSize - 1 : stackSlot.stackSize + 1;
        }

        if (stackSize > slot.getSlotStackLimit()) {
          stackSize = slot.getSlotStackLimit();
        }

        stackSlot.stackSize = stackSize;

        if (stackSlot.stackSize <= 0)
          slot.putStack((ItemStack)null);
      }
    
    protected void fillPhantomSlot(Slot slot, ItemStack stackHeld, int mouseButton, int modifier)
    {
      if (!((IPhantomSlot)slot).canAdjust()) {
        return;
      }
      int stackSize = mouseButton == 0 ? stackHeld.stackSize : 1;
      if (stackSize > slot.getSlotStackLimit()) {
        stackSize = slot.getSlotStackLimit();
      }
      ItemStack phantomStack = stackHeld.copy();
      phantomStack.stackSize = stackSize;

      slot.putStack(phantomStack);
    }

    protected boolean shiftItemStack(ItemStack stackToShift, int start, int end) {
        boolean changed = false;
        if (stackToShift.isStackable()) {
          for (int slotIndex = start; (stackToShift.stackSize > 0) && (slotIndex < end); slotIndex++) {
            Slot slot = (Slot)this.inventorySlots.get(slotIndex);
            ItemStack stackInSlot = slot.getStack();
            if ((stackInSlot != null) && (JACTools.isItemEqual(stackInSlot, stackToShift))) {
              int resultingStackSize = stackInSlot.stackSize + stackToShift.stackSize;
              int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
              if (resultingStackSize <= max) {
                stackToShift.stackSize = 0;
                stackInSlot.stackSize = resultingStackSize;
                slot.onSlotChanged();
                changed = true;
              } else if (stackInSlot.stackSize < max) {
                stackToShift.stackSize -= max - stackInSlot.stackSize;
                stackInSlot.stackSize = max;
                slot.onSlotChanged();
                changed = true;
              }
            }
          }
        }
        if (stackToShift.stackSize > 0) {
          for (int slotIndex = start; (stackToShift.stackSize > 0) && (slotIndex < end); slotIndex++) {
        	Slot slot = (Slot)this.inventorySlots.get(slotIndex);
        	ItemStack stackInSlot = slot.getStack();
            if (stackInSlot == null) {
              int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
              stackInSlot = stackToShift.copy();
              stackInSlot.stackSize = Math.min(stackToShift.stackSize, max);
              stackToShift.stackSize -= stackInSlot.stackSize;
              slot.putStack(stackInSlot);
              slot.onSlotChanged();
              changed = true;
            }
          }
        }
        return changed;
      }
    
    private boolean tryShiftItem(ItemStack moveStack, int numSlots) {
        for (int machineIndex = 0; machineIndex < numSlots - 36; machineIndex++) {
          Slot slot = (Slot)this.inventorySlots.get(machineIndex);
          if ((!(slot instanceof SlotBase)) || (((SlotBase)slot).canShift()))
          {
            if (!(slot instanceof IPhantomSlot))
            {
              if (slot.isItemValid(moveStack))
              {
                if (shiftItemStack(moveStack, machineIndex, machineIndex + 1))
                  return true; 
              }
            }
          }
        }
        return false;
      }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
      ItemStack startStack = null;
      Slot slot = (Slot)this.inventorySlots.get(slotIndex);
      int numSlots = this.inventorySlots.size();
      if ((slot != null) && (slot.getHasStack())) {
        ItemStack moveStack = slot.getStack();
        startStack = moveStack.copy();
        if ((slotIndex < numSlots - 36) || (!tryShiftItem(moveStack, numSlots)))
        {
          if ((slotIndex >= numSlots - 36) && (slotIndex < numSlots - 9)) {
            if (!shiftItemStack(moveStack, numSlots - 9, numSlots))
              return null;
          }
          else if ((slotIndex >= numSlots - 9) && (slotIndex < numSlots)) {
            if (!shiftItemStack(moveStack, numSlots - 36, numSlots - 9))
              return null;
          }
          else if (!shiftItemStack(moveStack, numSlots - 36, numSlots))
            return null;
        }
        if (moveStack.stackSize <= 0)
          slot.putStack(null);
        else {
          slot.onSlotChanged();
        }
        if (moveStack.stackSize != startStack.stackSize)
          slot.onPickupFromSlot(player, moveStack);
        else {
          return null;
        }
      }
      return startStack;
    }
}

package org.Jacpack.TechUp.tileentitys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.Jacpack.TechUp.api.INeedsFuel;
import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.inventory.ISpecialInventory;
import org.Jacpack.TechUp.api.inventory.InventoryCopy;
import org.Jacpack.TechUp.api.inventory.InventoryMapper;
import org.Jacpack.TechUp.api.machines.EnumMachineAlpha;
import org.Jacpack.TechUp.api.machines.IEnumMachine;
import org.Jacpack.TechUp.api.old.Game;
import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.crafting.IBlastFurnaceRecipe;
import org.Jacpack.TechUp.crafting.TechUpCraftingManager;
import org.Jacpack.TechUp.gui.EnumGui;
import org.Jacpack.TechUp.gui.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileBlastFurnace extends TileMultiBlockOven implements ISidedInventory, ISpecialInventory, INeedsFuel
{
    private static final int FUEL_PER_TICK = 5;
    private static final int COOK_STEP_LENGTH = 8;
    private static final int SLOT_INPUT = 0;
    private static final int SLOT_FUEL = 1;
    private static final int SLOT_OUTPUT = 2;
    private static final List patterns = new ArrayList();
    public int burnTime = 0;
    public int currentItemBurnTime = 0;
    private int finishedAt;
    private final IInventory invFuel = new InventoryMapper(this, 1, 1);
    private final IInventory invInput = new InventoryMapper(this, 0, 1);
    private final IInventory invOutput = new InventoryMapper(this, 2, 1);

    public TileBlastFurnace()
    {
        super("gui.blast.furnace.name", 3, patterns);
    }

    public IEnumMachine getMachineType()
    {
        return EnumMachineAlpha.BLAST_FURNACE;
    }

    public int getBlockTexture(int side)
    {
      if ((getPatternMarker() == 'W') && (isStructureValid())) {
        if (isBurning()) {
          return getMachineType().getTexture(7);
        }
        return getMachineType().getTexture(6);
      }
      return getMachineType().getTexture(0);
    }

    protected boolean isMapPositionValid(int var1, int var2, int var3, char var4)
    {
        int var5 = this.worldObj.getBlockId(var1, var2, var3);

        switch (var4)
        {
            case 'A':
                if (this.worldObj.isAirBlock(var1, var2, var3) || this.worldObj.getBlockMaterial(var1, var2, var3) == Material.lava)
                {
                    return true;
                }

                break;

            case 'B':
            case 'W':
                if (var5 == ModBlocks.getBlockMachineAlpha().blockID && this.worldObj.getBlockMetadata(var1, var2, var3) == this.getBlockMetadata())
                {
                    return true;
                }

                break;

            case 'O':
                if (var5 != ModBlocks.getBlockMachineAlpha().blockID || this.worldObj.getBlockMetadata(var1, var2, var3) != this.getBlockMetadata())
                {
                    return true;
                }
                break;
        }

        return false;
    }

    protected void onMasterReset()
    {
        super.onMasterReset();
        this.cookTime = 0;
        this.burnTime = 0;
        this.currentItemBurnTime = 0;
    }

    public int getTotalCookTime()
    {
        ItemStack var1 = this.getStackInSlot(0);
        IBlastFurnaceRecipe var2 = TechUpCraftingManager.blastFurnace.getRecipe(var1);
        return var2 != null ? var2.getCookTime() : 1;
    }

    public int getBurnProgressScaled(int var1)
    {
        if (this.burnTime > 0 && this.currentItemBurnTime > 0)
        {
            int var2 = this.burnTime * var1 / this.currentItemBurnTime;
            var2 = Math.min(var2, var1);
            var2 = Math.max(var2, 0);
            return var2;
        }
        else
        {
            return 0;
        }
    }

    private void setLavaIdle()
    {
        int var1 = this.xCoord + 1;
        int var2 = this.yCoord + 1;
        int var3 = this.zCoord + 1;

        if (this.worldObj.getBlockId(var1, var2, var3) == 0)
        {
            this.worldObj.setBlockAndMetadata(var1, var2, var3, Block.lavaStill.blockID, 7);
        }
    }

    private void setLavaBurn()
    {
        int var1 = this.xCoord + 1;
        int var2 = this.yCoord + 1;
        int var3 = this.zCoord + 1;

        if (this.worldObj.getBlockId(var1, var2, var3) == 0)
        {
            this.worldObj.setBlockAndMetadata(var1, var2, var3, Block.lavaMoving.blockID, 1);
        }

        ++var2;

        if (this.worldObj.getBlockId(var1, var2, var3) == 0)
        {
            this.worldObj.setBlockAndMetadata(var1, var2, var3, Block.lavaMoving.blockID, 1);
        }
    }

    private void destroyLava()
    {
        int var1 = this.xCoord + 1;
        int var2 = this.yCoord + 2;
        int var3 = this.zCoord + 1;

        if (this.worldObj.getBlockMaterial(var1, var2, var3) == Material.lava)
        {
            this.worldObj.setBlock(var1, var2, var3, 0);
        }

        --var2;

        if (this.worldObj.getBlockMaterial(var1, var2, var3) == Material.lava)
        {
            this.worldObj.setBlock(var1, var2, var3, 0);
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if (Game.isHost(this.getWorld()) && this.isMaster())
        {
            int var1 = this.burnTime;

            if (this.update > this.finishedAt + 8 + 5 && this.cookTime <= 0)
            {
                this.setCooking(false);
            }

            if (this.burnTime >= 5)
            {
                this.burnTime -= 5;
            }
            else
            {
                this.burnTime = 0;
            }

            if (this.isBurning())
            {
                this.setLavaBurn();
            }
            else
            {
                this.setLavaIdle();
            }

            ItemStack var2 = this.getStackInSlot(0);

            if (var2 != null && var2.stackSize > 0)
            {
                ItemStack var3 = this.getStackInSlot(2);
                IBlastFurnaceRecipe var4 = TechUpCraftingManager.blastFurnace.getRecipe(var2);

                if (var4 != null && var4.isRoomForOutput(var3))
                {
                    if (this.burnTime <= 10)
                    {
                        ItemStack var5 = this.getStackInSlot(1);

                        if (var5 != null && (var5.getItem() == Item.coal && var5.getItemDamage() == 1 || var5.getItem() == Item.coal && var5.getItemDamage() == 0))
                        {
                            int var6 = JACTools.getItemBurnTime(var5);

                            if (var6 > 0)
                            {
                                this.currentItemBurnTime = var6 + this.burnTime;
                                this.burnTime = this.currentItemBurnTime;
                                this.decrStackSize(1, 1);
                            }
                        }
                    }

                    if (this.update % 8 == 0 && this.isBurning())
                    {
                        this.cookTime += 8;
                        this.setCooking(true);

                        if (this.cookTime >= var4.getCookTime())
                        {
                            this.cookTime = 0;
                            this.finishedAt = this.update;

                            if (var3 == null)
                            {
                                this.setInventorySlotContents(2, var4.getOutput());
                            }
                            else
                            {
                                var3.stackSize += var4.getOutputStackSize();
                            }

                            this.decrStackSize(0, 1);
                        }

                        this.sendUpdateToClient();
                    }
                }
                else
                {
                    this.cookTime = 0;
                    this.setCooking(false);
                }
            }
            else
            {
                this.cookTime = 0;
                this.setCooking(false);
            }

            if (var1 == 0 ^ this.burnTime == 0)
            {
                this.sendUpdateToClient();
            }
        }
    }

    public int getStartInventorySide(ForgeDirection var1)
    {
        return var1 == ForgeDirection.DOWN ? 1 : (var1 == ForgeDirection.UP ? 0 : 2);
    }

    public int getSizeInventorySide(ForgeDirection var1)
    {
        return 1;
    }
    
    public boolean openGui(EntityPlayer player)
    {
      TileMultiBlock masterBlock = getMasterBlock();
      if (masterBlock != null) {
        GuiHandler.openGui(EnumGui.BLAST_FURNACE, player, this.worldObj, masterBlock.xCoord, masterBlock.yCoord, masterBlock.zCoord);
        return true;
      }
      return false;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("burnTime", this.burnTime);
        var1.setInteger("currentItemBurnTime", this.currentItemBurnTime);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.burnTime = var1.getInteger("burnTime");
        this.currentItemBurnTime = var1.getInteger("currentItemBurnTime");
    }

    public void writePacketData(DataOutputStream var1) throws IOException
    {
        super.writePacketData(var1);
        var1.writeInt(this.burnTime);
        var1.writeInt(this.currentItemBurnTime);
    }

    public void readPacketData(DataInputStream var1) throws IOException
    {
        super.readPacketData(var1);
        this.burnTime = var1.readInt();
        this.currentItemBurnTime = var1.readInt();
    }

    public boolean needsFuel()
    {
        ItemStack var1 = this.getStackInSlot(1);
        return var1 == null || var1.stackSize < 8;
    }

    public boolean isBurning()
    {
        TileBlastFurnace var1 = (TileBlastFurnace)this.getMasterBlock();
        return var1 != null ? var1.burnTime > 0 : false;
    }

    public int addItem(ItemStack var1, boolean var2, ForgeDirection var3)
    {
        if (!this.isStructureValid())
        {
            return 0;
        }
        else if (var1 == null)
        {
            return 0;
        }
        else
        {
            Object var4;
            ItemStack var5;

            if ((var1.itemID != Item.coal.itemID || var1.getItemDamage() != 1))
            {
                if (TechUpCraftingManager.blastFurnace.getRecipe(var1) != null)
                {
                    var4 = this.invInput;

                    if (!var2)
                    {
                        var4 = new InventoryCopy((IInventory)var4);
                    }

                    var5 = JACTools.moveItemStack(var1, (IInventory)var4);
                    return var5 == null ? var1.stackSize : var1.stackSize - var5.stackSize;
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                var4 = this.invFuel;

                if (!var2)
                {
                    var4 = new InventoryCopy((IInventory)var4);
                }

                var5 = JACTools.moveItemStack(var1, (IInventory)var4);
                return var5 == null ? var1.stackSize : var1.stackSize - var5.stackSize;
            }
        }
    }

    public ItemStack[] extractItem(boolean var1, ForgeDirection var2, int var3)
    {
        ItemStack var4 = this.getStackInSlot(2);
        ItemStack var5 = null;

        if (var4 != null)
        {
            var5 = var4.copy();
            int var6 = Math.min(var4.stackSize, var3);
            var5.stackSize = var6;

            if (var1)
            {
                this.decrStackSize(2, var6);
            }
        }

        return var5 == null ? new ItemStack[0] : new ItemStack[] {var5};
    }

    static
    {
        char[][][] var0 = new char[][][] {{{'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'W', 'B', 'O'}, {'O', 'W', 'B', 'W', 'O'}, {'O', 'B', 'W', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}}};
        patterns.add(new MultiBlockPattern(var0));
    }
}

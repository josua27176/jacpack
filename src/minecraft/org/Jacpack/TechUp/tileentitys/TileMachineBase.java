package org.Jacpack.TechUp.tileentitys;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.machines.IEnumMachine;
import org.Jacpack.TechUp.api.old.*;
import org.Jacpack.TechUp.api.inventory.*;
import org.Jacpack.TechUp.block.BlockMachine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public abstract class TileMachineBase extends JACTileEnityMultiBlocks
{
    private boolean checkedBlock = false;

    public abstract IEnumMachine getMachineType();

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return JACTools.translate(this.getMachineType().getTag());
    }

    public final short getId()
    {
        return (short)this.getMachineType().ordinal();
    }

    public boolean canCreatureSpawn(EnumCreatureType var1)
    {
        return true;
    }

    public ArrayList getBlockDropped(int var1)
    {
        ArrayList var2 = new ArrayList();
        var2.add(this.getMachineType().getItem());
        return var2;
    }

    public void initFromItem(ItemStack var1) {}

    public void onBlockAdded() {}

    public void onBlockRemoval()
    {
        if (this instanceof IInventory)
        {
            JACTools.dropInventory((IInventory)this, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public boolean blockActivated(EntityPlayer var1, int var2)
    {
        if (var1.isSneaking())
        {
            return false;
        }
        else
        {
            ItemStack var3 = var1.getCurrentEquippedItem();

            if (var3 != null)
            {
                if (var3.getItem() instanceof IActivationBlockingItem)
                {
                    return false;
                }
            }

            return this.openGui(var1);
        }
    }

    public boolean isBlockSolidOnSide(ForgeDirection var1)
    {
        return true;
    }

    public boolean canUpdate()
    {
        return true;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if (!Game.isNotHost(this.worldObj))
        {
            if (!this.checkedBlock)
            {
                this.checkedBlock = true;

                if (!this.getMachineType().isEnabled())
                {
                    this.worldObj.setBlockWithNotify(this.xCoord, this.yCoord, this.zCoord, 0);
                    return;
                }

                if (this.getBlock() != this.getMachineType().getBlock())
                {
                    Game.log(Level.INFO, "Updating Machine Tile Block Id: {0}, [{1}, {2}, {3}]", new Object[] {this.getClass().getSimpleName(), Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord)});
                    this.worldObj.setBlockAndMetadata(this.xCoord, this.yCoord, this.zCoord, this.getMachineType().getBlockId(), this.getMachineType().ordinal());
                    this.validate();
                    this.worldObj.setBlockTileEntity(this.xCoord, this.yCoord, this.zCoord, this);
                    this.updateContainingBlockInfo();
                }

                if (this.getBlock() != null && this.getClass() != ((BlockMachine)this.getBlock()).getMachineProxy().getTileClass(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)))
                {
                    this.worldObj.setBlockMetadata(this.xCoord, this.yCoord, this.zCoord, this.getId());
                    Game.log(Level.INFO, "Updating Machine Tile Metadata: {0}, [{1}, {2}, {3}]", new Object[] {this.getClass().getSimpleName(), Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord)});
                    this.updateContainingBlockInfo();
                }
            }
        }
    }

    public boolean openGui(EntityPlayer var1)
    {
        return false;
    }

    public int getBlockTexture(int var1)
    {
        return 0;
    }

    public int getLightValue()
    {
        return 0;
    }

    public float getResistance(Entity var1)
    {
        return 4.5F;
    }

    public float getHardness()
    {
        return 2.0F;
    }

    public boolean isPoweringTo(int var1)
    {
        return false;
    }

    public boolean canConnectRedstone(int var1)
    {
        return false;
    }

    public void onNeighborBlockChange(int var1) {}

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(Random var1) {}
}

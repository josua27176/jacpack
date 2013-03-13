package org.Jacpack.TechUp.tileentitys;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.ISidedInventory;

public abstract class TileMultiBlockOven extends TileMultiBlockInventory implements ISidedInventory
{
    protected int cookTime;
    protected boolean cooking = true;
    private boolean wasBurning;

    public TileMultiBlockOven(String var1, int var2, List var3)
    {
        super(var1, var2, var3);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if ((getPatternMarker() == 'W') && 
        	      (this.update % 4 == 0))
        	      updateLighting();
    }

    protected void updateLighting()
    {
        boolean var1 = this.isBurning();

        if (this.wasBurning != var1)
        {
            this.wasBurning = var1;
            this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
            this.markBlockForUpdate();
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(Random var1)
    {
        this.updateLighting();

        if ((getPatternMarker() == 'W') && (isStructureValid()) && (var1.nextInt(100) < 20) && (isBurning())) {
            float var2 = (float)this.xCoord + 0.5F;
            float var3 = (float)this.yCoord + 0.4375F + var1.nextFloat() * 3.0F / 16.0F;
            float var4 = (float)this.zCoord + 0.5F;
            float var5 = 0.52F;
            float var6 = var1.nextFloat() * 0.6F - 0.3F;
            this.worldObj.spawnParticle("flame", (double)(var2 - var5), (double)var3, (double)(var4 + var6), 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", (double)(var2 + var5), (double)var3, (double)(var4 + var6), 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", (double)(var2 + var6), (double)var3, (double)(var4 - var5), 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", (double)(var2 + var6), (double)var3, (double)(var4 + var5), 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("cookTime", this.cookTime);
        var1.setBoolean("cooking", this.cooking);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.cookTime = var1.getInteger("cookTime");
        this.cooking = var1.getBoolean("cooking");
    }

    public void writePacketData(DataOutputStream var1) throws IOException
    {
        super.writePacketData(var1);
        var1.writeInt(this.cookTime);
        var1.writeBoolean(this.cooking);
    }

    public void readPacketData(DataInputStream var1) throws IOException
    {
        super.readPacketData(var1);
        this.cookTime = var1.readInt();
        this.cooking = var1.readBoolean();
    }
    
    public int getCookTime() {
        TileMultiBlockOven masterOven = (TileMultiBlockOven)getMasterBlock();
        if (masterOven != null) {
          return masterOven.cookTime;
        }
        return -1;
      }
    
    public boolean isCooking() {
        TileMultiBlockOven masterOven = (TileMultiBlockOven)getMasterBlock();
        if (masterOven != null) {
          return masterOven.cooking;
        }
        return false;
      }

    public boolean isBurning()
    {
        return this.isCooking();
    }

    public void setCooking(boolean var1)
    {
    	//System.out.println(this.toString());
    	
        if (cooking != var1)
        {
            cooking = var1;
            System.out.println("Am Burning");
            this.sendUpdateToClient();
        }
    }

    public void setCookTime(int var1)
    {
        this.cookTime = var1;
    }

    public abstract int getTotalCookTime();
    
    public int getCookProgressScaled(int i) {
        if ((this.cookTime == 0) || (getTotalCookTime() == 0)) {
          return 0;
        }
        int scale = this.cookTime * i / getTotalCookTime();
        scale = Math.min(scale, i);
        scale = Math.max(scale, 0);
        return scale;
      }

    public abstract int getBurnProgressScaled(int var1);

    public int getLightValue()
    {
      if ((getPatternMarker() == 'W') && (isStructureValid()) && (isBurning())) {
        return 13;
      }
      return 0;
    }
}

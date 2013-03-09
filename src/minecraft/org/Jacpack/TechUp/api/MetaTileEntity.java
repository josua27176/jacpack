package org.Jacpack.TechUp.api;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;

public abstract class MetaTileEntity implements ILiquidTank
{
    public String mName;
    public String mRegionalName;
    public int mID;
    public BaseMetaTileEntity mBaseMetaTileEntity;
    public ItemStack[] mInventory = new ItemStack[this.getInvSize()];

    public MetaTileEntity(int var1, String var2, String var3)
    {
        if (BaseMetaTileEntity.mMetaTileList[var1] == null)
        {
            BaseMetaTileEntity.mMetaTileList[var1] = this;
            this.mID = var1;
            this.mName = var2.replaceAll(" ", "_");
            this.mRegionalName = var3;
            this.mBaseMetaTileEntity = new BaseMetaTileEntity();
            this.mBaseMetaTileEntity.mMetaTileEntity = this;
            this.getName();
        }
        else
        {
            throw new IllegalArgumentException("MetaMachine-Slot Nr. " + var1 + " is already occupied!");
        }
    }
    
    public String getName()
    {
        return "tile.BlockMetaID_Machine." + this.mName + ".name";
    }

    public MetaTileEntity() {}

    public abstract MetaTileEntity newMetaEntity(BaseMetaTileEntity var1);

    public abstract void saveNBTData(NBTTagCompound var1);

    public abstract void loadNBTData(NBTTagCompound var1);

    public abstract int getInvSideIndex(ForgeDirection var1, int var2);

    public abstract int getInvSideLength(ForgeDirection var1, int var2);

    public abstract int getInvSize();

    public abstract int getTextureIndex(int var1, int var2, boolean var3, boolean var4);

    public void inValidate() {}

    public void onExplosion() {}

    public void onFirstTick() {}

    public void onPreTick() {}

    public void onPostTick() {}

    public void onRemoval() {}

    public void onOpenGUI() {}

    public void onCloseGUI() {}

    public void onRightclick(EntityPlayer var1) {}

    public void onLeftclick(EntityPlayer var1) {}

    public void onValueUpdate(short var1) {}

    public short getUpdateData()
    {
        return (short)0;
    }

    public void doSound(int var1, double var2, double var4, double var6) {}

    public void startSoundLoop(int var1, double var2, double var4, double var6) {}

    public void stopSoundLoop(int var1, double var2, double var4, double var6) {}

    public void sendSound(int var1)
    {
        this.mBaseMetaTileEntity.worldObj.addBlockEvent(this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord, this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord), 4, var1);
    }

    public void sendLoopStart(int var1)
    {
        this.mBaseMetaTileEntity.worldObj.addBlockEvent(this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord, this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord), 5, var1);
    }

    public void sendLoopEnd(int var1)
    {
        this.mBaseMetaTileEntity.worldObj.addBlockEvent(this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord, this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord), 6, var1);
    }

    public boolean isEnetOutput()
    {
        return false;
    }

    public boolean isEnetInput()
    {
        return false;
    }

    public int maxEUStore()
    {
        return 0;
    }

    public int maxEUInput()
    {
        return 0;
    }

    public int maxEUOutput()
    {
        return 0;
    }

    public int maxEUPulses()
    {
        return 1;
    }

    public boolean isOutputFacing(short var1)
    {
        return false;
    }

    public boolean isInputFacing(short var1)
    {
        return false;
    }

    public boolean isFacingValid(int var1)
    {
        return false;
    }

    public boolean isAccessAllowed(EntityPlayer var1)
    {
        return false;
    }

    public boolean isValidSlot(int var1)
    {
        return true;
    }

    public boolean setStackToZeroInsteadOfNull(int var1)
    {
        return false;
    }

    public void setEnergyVar(int var1)
    {
        this.mBaseMetaTileEntity.mStoredEnergy = var1;
    }

    public int getMinimumStoredEU()
    {
        return 0;
    }

    public int getEnergyVar()
    {
        return this.mBaseMetaTileEntity.mStoredEnergy;
    }

    public int getInputTier()
    {
        return this.mBaseMetaTileEntity.getTier(this.maxEUInput());
    }

    public int getOutputTier()
    {
        return this.mBaseMetaTileEntity.getTier(this.maxEUOutput());
    }

    public int rechargerSlotStartIndex()
    {
        return 0;
    }

    public int rechargerSlotCount()
    {
        return 0;
    }

    public int dechargerSlotStartIndex()
    {
        return 0;
    }

    public int dechargerSlotCount()
    {
        return 0;
    }

    public boolean ownerControl()
    {
        return false;
    }

    public boolean ownerWrench()
    {
        return this.ownerControl();
    }

    public boolean unbreakable()
    {
        return false;
    }

    public boolean hasAnimation()
    {
        return false;
    }

    public ArrayList getSpecialDebugInfo(EntityPlayer var1, int var2, ArrayList var3)
    {
        return var3;
    }

    public String getMainInfo()
    {
        return "";
    }

    public String getSecondaryInfo()
    {
        return "";
    }

    public String getTertiaryInfo()
    {
        return "";
    }

    public boolean isGivingInformation()
    {
        return false;
    }

    public LiquidStack getLiquid()
    {
        return null;
    }

    public int fill(LiquidStack var1, boolean var2)
    {
        return 0;
    }

    public LiquidStack drain(int var1, boolean var2)
    {
        return null;
    }

    public int getTankPressure()
    {
        return 0;
    }

    public int getCapacity()
    {
        return 0;
    }

    public void setLiquid(LiquidStack var1) {}

    public void setCapacity(int var1) {}

    public void onMachineBlockUpdate() {}

    public void receiveClientEvent(int var1, int var2) {}

    public boolean isSimpleMachine()
    {
        return false;
    }

    public boolean isQuantumChest()
    {
        return false;
    }

    public ItemStack[] getStoredItemData()
    {
        return null;
    }

    public void setItemCount(int var1) {}

    public int getMaxItemCount()
    {
        return 0;
    }
}

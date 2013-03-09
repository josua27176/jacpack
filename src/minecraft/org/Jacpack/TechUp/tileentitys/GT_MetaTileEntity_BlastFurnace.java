package org.Jacpack.TechUp.tileentitys;

import org.Jacpack.TechUp.TechUp;
import org.Jacpack.TechUp.api.BaseMetaTileEntity;
import org.Jacpack.TechUp.api.MetaTileEntity;
import org.Jacpack.TechUp.block.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_BlastFurnace extends MetaTileEntity
{
    public int mProgresstime = 0;
    public int mMaxProgresstime = 0;
    public int mEUt = 0;
    public int mHeatCapacity = 0;
    public int mUpdate = 5;
    public ItemStack mOutputItem1;
    public ItemStack mOutputItem2;
    public boolean mMachine = false;
    public boolean mConstantEnergy = true;

    public GT_MetaTileEntity_BlastFurnace(int var1, String var2, String var3)
    {
        super(var1, var2, var3);
    }

    public GT_MetaTileEntity_BlastFurnace() {}

    public boolean isSimpleMachine()
    {
        return false;
    }

    public boolean isFacingValid(int var1)
    {
        return var1 > 1;
    }

    public boolean isEnetInput()
    {
        return true;
    }

    public boolean isInputFacing(short var1)
    {
        return true;
    }

    public int maxEUInput()
    {
        return 128;
    }

    public int maxEUStore()
    {
        return 10000;
    }

    public int getInvSize()
    {
        return 4;
    }

    public void onRightclick(EntityPlayer var1)
    {
        var1.openGui(TechUp.instance, 113, this.mBaseMetaTileEntity.worldObj, this.mBaseMetaTileEntity.xCoord, this.mBaseMetaTileEntity.yCoord, this.mBaseMetaTileEntity.zCoord);
    }

    public boolean isAccessAllowed(EntityPlayer var1)
    {
        return true;
    }

    public int getProgresstime()
    {
        return this.mProgresstime;
    }

    public int maxProgresstime()
    {
        return this.mMaxProgresstime;
    }

    public MetaTileEntity newMetaEntity(BaseMetaTileEntity var1)
    {
        return new GT_MetaTileEntity_BlastFurnace();
    }

    public void saveNBTData(NBTTagCompound var1)
    {
        var1.setInteger("mEUt", this.mEUt);
        var1.setInteger("mProgresstime", this.mProgresstime);
        var1.setInteger("mMaxProgresstime", this.mMaxProgresstime);
        NBTTagCompound var2;

        if (this.mOutputItem1 != null)
        {
            var2 = new NBTTagCompound();
            this.mOutputItem1.writeToNBT(var2);
            var1.setTag("mOutputItem1", var2);
        }

        if (this.mOutputItem2 != null)
        {
            var2 = new NBTTagCompound();
            this.mOutputItem2.writeToNBT(var2);
            var1.setTag("mOutputItem2", var2);
        }
    }

    public void loadNBTData(NBTTagCompound var1)
    {
        this.mUpdate = 5;
        this.mEUt = var1.getInteger("mEUt");
        this.mProgresstime = var1.getInteger("mProgresstime");
        this.mMaxProgresstime = var1.getInteger("mMaxProgresstime");
        NBTTagCompound var2 = (NBTTagCompound)var1.getTag("mOutputItem1");

        if (var2 != null)
        {
            this.mOutputItem1 = ItemStack.loadItemStackFromNBT(var2);
        }

        NBTTagCompound var3 = (NBTTagCompound)var1.getTag("mOutputItem2");

        if (var3 != null)
        {
            this.mOutputItem2 = ItemStack.loadItemStackFromNBT(var3);
        }
    }

    private boolean checkMachine()
    {
        int var1 = ForgeDirection.getOrientation(this.mBaseMetaTileEntity.mFacing).offsetX * 2;
        int var2 = ForgeDirection.getOrientation(this.mBaseMetaTileEntity.mFacing).offsetY * 2;
        int var3 = ForgeDirection.getOrientation(this.mBaseMetaTileEntity.mFacing).offsetZ * 2;
        this.mHeatCapacity = 0;

        for (int var4 = -1; var4 < 2; ++var4)
        {
            for (int var5 = 0; var5 < 4; ++var5)
            {
                for (int var6 = -1; var6 < 2; ++var6)
                {
                    if (var4 == 0 && (var5 == 1 || var5 == 2) && var6 == 0)
                    {
                        if (this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord - var1 + var4, this.mBaseMetaTileEntity.yCoord - var2 + var5, this.mBaseMetaTileEntity.zCoord - var3 + var6) != 10 && this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord - var1 + var4, this.mBaseMetaTileEntity.yCoord - var2 + var5, this.mBaseMetaTileEntity.zCoord - var3 + var6) != 11)
                        {
                            if (this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord - var1 + var4, this.mBaseMetaTileEntity.yCoord - var2 + var5, this.mBaseMetaTileEntity.zCoord - var3 + var6) != 0)
                            {
                                return false;
                            }
                        }
                        else
                        {
                            this.mHeatCapacity += 250;
                        }
                    }
                    else
                    {
                        if (this.mBaseMetaTileEntity.worldObj.getBlockId(this.mBaseMetaTileEntity.xCoord - var1 + var4, this.mBaseMetaTileEntity.yCoord - var2 + var5, this.mBaseMetaTileEntity.zCoord - var3 + var6) != ModBlocks.mBlocks[0].blockID)
                        {
                            return false;
                        }

                        int var7 = this.mBaseMetaTileEntity.worldObj.getBlockMetadata(this.mBaseMetaTileEntity.xCoord - var1 + var4, this.mBaseMetaTileEntity.yCoord - var2 + var5, this.mBaseMetaTileEntity.zCoord - var3 + var6);

                        if (var7 == 13)
                        {
                            this.mHeatCapacity += 30;
                        }
                        else if (var7 == 14)
                        {
                            this.mHeatCapacity += 50;
                        }
                        else
                        {
                            if (var7 != 15)
                            {
                                return false;
                            }

                            this.mHeatCapacity += 100;
                        }
                    }
                }
            }
        }

        return true;
    }

    public void onMachineBlockUpdate()
    {
        this.mUpdate = 5;
    }

    public void onPostTick()
    {
        if (!this.mBaseMetaTileEntity.worldObj.isRemote)
        {
            if (this.mUpdate-- == 0)
            {
                this.mMachine = this.checkMachine();

                if (!this.mMachine)
                {
                    this.mHeatCapacity = 0;
                }
            }

            this.mBaseMetaTileEntity.mActive = this.mMachine;

            if (this.mMachine && this.mMaxProgresstime > 0)
            {
                if (this.mProgresstime >= 0 && !this.mBaseMetaTileEntity.decreaseStoredEnergy(this.mEUt, false))
                {
                    if (this.mConstantEnergy)
                    {
                        this.mProgresstime = -10;
                        this.mBaseMetaTileEntity.mDisplayErrorCode = 1;
                    }
                }
                else if (++this.mProgresstime > this.mMaxProgresstime)
                {
                    this.addOutputProducts();
                    this.mOutputItem1 = null;
                    this.mOutputItem2 = null;
                    this.mProgresstime = 0;
                    this.mMaxProgresstime = 0;
                    this.mBaseMetaTileEntity.mDisplayErrorCode = 0;
                }
            }
            else if (this.mBaseMetaTileEntity.getStored() > 100)
            {
                //this.checkRecipe();
            }
        }
    }

    private void addOutputProducts()
    {
        if (this.mOutputItem1 != null)
        {
            if (this.mInventory[2] == null)
            {
                this.mInventory[2] = this.mOutputItem1.copy();
            }
            else if (this.mInventory[2].isItemEqual(this.mOutputItem1))
            {
                this.mInventory[2].stackSize = Math.min(this.mOutputItem1.getMaxStackSize(), this.mOutputItem1.stackSize + this.mInventory[2].stackSize);
            }
        }

        if (this.mOutputItem2 != null)
        {
            if (this.mInventory[3] == null)
            {
                this.mInventory[3] = this.mOutputItem2.copy();
            }
            else if (this.mInventory[3].isItemEqual(this.mOutputItem2))
            {
                this.mInventory[3].stackSize = Math.min(this.mOutputItem2.getMaxStackSize(), this.mOutputItem2.stackSize + this.mInventory[3].stackSize);
            }
        }
    }

    private boolean spaceForOutput(int var1)
    {
        return true;//(this.mInventory[2] == null || ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput1 == null || this.mInventory[2].stackSize + ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput1.stackSize <= this.mInventory[2].getMaxStackSize() && this.mInventory[2].isItemEqual(((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput1)) && (this.mInventory[3] == null || ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput2 == null || this.mInventory[3].stackSize + ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput2.stackSize <= this.mInventory[3].getMaxStackSize() && this.mInventory[3].isItemEqual(((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput2));
    }

    /**private boolean checkRecipe()
    {
        if (!this.mMachine)
        {
            return false;
        }
        else
        {
            int var1 = GT_Recipe.findEqualBlastRecipeIndex(this.mInventory[0], this.mInventory[1]);

            if (var1 != -1 && this.mHeatCapacity >= ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mStartEU && this.spaceForOutput(var1) && GT_Recipe.isRecipeInputEqual(true, true, this.mInventory[0], this.mInventory[1], (GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)))
            {
                if (this.mInventory[0] != null && this.mInventory[0].stackSize == 0)
                {
                    this.mInventory[0] = null;
                }

                if (this.mInventory[1] != null && this.mInventory[1].stackSize == 0)
                {
                    this.mInventory[1] = null;
                }

                this.mMaxProgresstime = ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mDuration;
                this.mEUt = ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mEUt;

                if (((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput1 == null)
                {
                    this.mOutputItem1 = null;
                }
                else
                {
                    this.mOutputItem1 = ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput1.copy();
                }

                if (((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput2 == null)
                {
                    this.mOutputItem2 = null;
                }
                else
                {
                    this.mOutputItem2 = ((GT_Recipe)GT_Recipe.sBlastRecipes.get(var1)).mOutput2.copy();
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }*/

    public int getInvSideIndex(ForgeDirection var1, int var2)
    {
        return var1 == ForgeDirection.UP ? 0 : (var1 == ForgeDirection.DOWN ? 1 : 2);
    }

    public int getInvSideLength(ForgeDirection var1, int var2)
    {
        return var1 != ForgeDirection.UP && var1 != ForgeDirection.DOWN ? 2 : 1;
    }

    public int getTextureIndex(int var1, int var2, boolean var3, boolean var4)
    {
        return ForgeDirection.getOrientation(var1) == ForgeDirection.getOrientation(var2) ? 68 + (var3 ? 1 : 0) : (ForgeDirection.getOrientation(var1).getOpposite() == ForgeDirection.getOrientation(var2) ? 71 : 72);
    }

    public String getMainInfo()
    {
        return "Progress:";
    }

    public String getSecondaryInfo()
    {
        return this.mProgresstime / 20 + "secs";
    }

    public String getTertiaryInfo()
    {
        return "/" + this.mMaxProgresstime / 20 + "secs";
    }

    public boolean isGivingInformation()
    {
        return true;
    }
}

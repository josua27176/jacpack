package org.Jacpack.TechUp.api;

import ic2.api.Direction;
import ic2.api.IElectricItem;
import ic2.api.ElectricItem;
import ic2.api.IEnergyStorage;
import ic2.api.IWrenchable;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

public class BaseMetaTileEntity extends TileEntity implements ITankContainer, IEnergyStorage, IWrenchable, ISidedInventory, IEnergySink, IEnergySource, ITechUpDeviceInformation, IMachineBlockUpdateable, INetworkDataProvider, INetworkUpdateListener
{
    public static final short MAXIMUM_METATILE_IDS = 1024;
    public static boolean sAnimationsAllowed = true;
    public static MetaTileEntity[] mMetaTileList = new MetaTileEntity[1024];
    public boolean mMachineFireExplosions = true;
    public boolean mMachineWireFire = true;
    public MetaTileEntity mMetaTileEntity;
    public int mFacing = -1;
    public int oFacing = -1;
    public int oOutput = 0;
    public int mStoredEnergy = 0;
    public int mID = 0;
    public int mDisplayErrorCode = 0;
    public int oX = 0;
    public int oY = 0;
    public int oZ = 0;
    public long mTickTimer = 0L;
    public boolean mInventoryChanged = false;
    public boolean mActive = false;
    public boolean oActive = false;
    public boolean mIsAddedToEnet = false;
    public boolean mNeedsUpdate = true;
    public boolean mSendClientData = true;
    public boolean mRedstone = false;
    public boolean oRedstone = false;
    public boolean mReleaseEnergy = false;
    public boolean mConstantEnergy = true;
    public String mOwnerName = "";

    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("mID", this.mID);
        var1.setInteger("mStoredEnergy", this.mStoredEnergy);
        var1.setShort("mFacing", (short)this.mFacing);
        var1.setString("mOwnerName", this.mOwnerName);
        var1.setBoolean("mActive", this.mActive);
        var1.setBoolean("mRedstone", this.mRedstone);

        if (this.hasValidMetaTileEntity())
        {
            NBTTagList var2 = new NBTTagList();

            for (int var3 = 0; var3 < this.mMetaTileEntity.mInventory.length; ++var3)
            {
                ItemStack var4 = this.mMetaTileEntity.mInventory[var3];

                if (var4 != null)
                {
                    NBTTagCompound var5 = new NBTTagCompound();
                    var5.setInteger("IntSlot", var3);
                    var4.writeToNBT(var5);
                    var2.appendTag(var5);
                }
            }

            var1.setTag("Inventory", var2);

            try
            {
                this.mMetaTileEntity.saveNBTData(var1);
            }
            catch (Throwable var6)
            {
                System.err.println("Encountered CRITICAL ERROR while saving MetaTileEntity, the Chunk whould\'ve been corrupted by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
                var6.printStackTrace();
            }
        }
    }

    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.setInitialValuesAsNBT(var1);
    }

    public void setInitialValuesAsNBT(NBTTagCompound var1)
    {
        this.mID = var1.getInteger("mID");
        this.mStoredEnergy = var1.getInteger("mStoredEnergy");
        this.mFacing = var1.getShort("mFacing");
        this.mOwnerName = var1.getString("mOwnerName");
        this.mActive = var1.getBoolean("mActive");
        this.mRedstone = var1.getBoolean("mRedstone");

        if (this.mID != 0 && this.createNewMetatileEntity(this.mID))
        {
            NBTTagList var2 = var1.getTagList("Inventory");

            for (int var3 = 0; var3 < var2.tagCount(); ++var3)
            {
                NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
                int var5 = var4.getInteger("IntSlot");

                if (var5 >= 0 && var5 < this.mMetaTileEntity.mInventory.length)
                {
                    this.mMetaTileEntity.mInventory[var5] = ItemStack.loadItemStackFromNBT(var4);
                }
            }

            try
            {
                this.mMetaTileEntity.loadNBTData(var1);
            }
            catch (Throwable var6)
            {
                System.err.println("Encountered Exception while loading MetaTileEntity, the Server should\'ve crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
                var6.printStackTrace();
            }
        }

        this.mNeedsUpdate = true;
    }

    public boolean createNewMetatileEntity(int var1)
    {
        if (var1 >= 16 && var1 < 1024 && mMetaTileList[var1] != null)
        {
            if (var1 != 0)
            {
                if (this.hasValidMetaTileEntity())
                {
                    this.mMetaTileEntity.inValidate();
                    this.mMetaTileEntity.mBaseMetaTileEntity = null;
                }

                this.mMetaTileEntity = mMetaTileList[var1].newMetaEntity(this);
                this.mMetaTileEntity.mBaseMetaTileEntity = this;
                this.mTickTimer = 0L;
                this.mID = var1;
                return true;
            }
        }
        else
        {
            System.err.println("MetaID " + var1 + " not loadable => locking TileEntity!");
        }

        return false;
    }

    public void updateEntity()
    {
        if (!this.isInvalid())
        {
            if (!this.hasValidMetaTileEntity())
            {
                if (this.mMetaTileEntity == null)
                {
                    return;
                }

                this.mMetaTileEntity.mBaseMetaTileEntity = this;
            }

            try
            {
                ++this.mTickTimer;

                if (this.mTickTimer == 20L)
                {
                    if (this.mFacing == -1)
                    {
                        this.mFacing = 0;
                    }

                    if (!this.worldObj.isRemote)
                    {
                        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord), 3, this.mID);
                        this.mNeedsUpdate = true;
                    }

                    this.mMetaTileEntity.onFirstTick();
                    NetworkHelper.updateTileEntityField(this, "mOwnerName");
                }

                if (this.mTickTimer > 20L && this.hasValidMetaTileEntity())
                {
                    this.mMetaTileEntity.onPreTick();

                    if (this.worldObj.isRemote)
                    {
                        if ((this.mNeedsUpdate || this.mMetaTileEntity.hasAnimation()) && (sAnimationsAllowed || this.mTickTimer < 250L))
                        {
                            this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
                            this.mNeedsUpdate = false;
                        }
                    }
                    else
                    {
                        if (this.mTickTimer == 50L)
                        {
                            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
                        }

                        if (this.mTickTimer % 600L == 50L)
                        {
                            this.mSendClientData = true;
                        }

                        if (this.mActive != this.oActive)
                        {
                            this.oActive = this.mActive;
                            this.mNeedsUpdate = true;
                        }

                        if (this.mRedstone != this.oRedstone)
                        {
                            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord));
                            this.oRedstone = this.mRedstone;
                            this.mNeedsUpdate = true;
                        }

                        if (this.mTickTimer > 30L && (this.mMetaTileEntity.isEnetOutput() || this.mMetaTileEntity.isEnetInput()) && !this.mIsAddedToEnet)
                        {
                            this.mIsAddedToEnet = JAC_ModHandler.addTileToEnet(this);
                        }

                        if (this.xCoord != this.oX || this.yCoord != this.oY || this.zCoord != this.oZ)
                        {
                            this.oX = this.xCoord;
                            this.oY = this.yCoord;
                            this.oZ = this.zCoord;

                            if (this.mIsAddedToEnet)
                            {
                                JAC_ModHandler.removeTileFromEnet(this);
                            }

                            this.mIsAddedToEnet = false;
                            this.mNeedsUpdate = true;
                        }

                        if (this.mFacing != this.oFacing)
                        {
                            this.oFacing = this.mFacing;

                            if (this.mIsAddedToEnet)
                            {
                                JAC_ModHandler.removeTileFromEnet(this);
                            }

                            this.mIsAddedToEnet = false;
                            this.mNeedsUpdate = true;
                        }

                        if (this.getOutput() != this.oOutput)
                        {
                            this.oOutput = this.getOutput();

                            if (this.mIsAddedToEnet)
                            {
                                JAC_ModHandler.removeTileFromEnet(this);
                            }

                            this.mIsAddedToEnet = false;
                        }

                        if (this.mIsAddedToEnet && mMachineFireExplosions && this.worldObj.rand.nextInt(1000) == 0)
                        {
                            switch (this.worldObj.rand.nextInt(6))
                            {
                                case 0:
                                    if (this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == Block.fire.blockID)
                                    {
                                        this.doEnergyExplosion();
                                    }

                                    break;

                                case 1:
                                    if (this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == Block.fire.blockID)
                                    {
                                        this.doEnergyExplosion();
                                    }

                                    break;

                                case 2:
                                    if (this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == Block.fire.blockID)
                                    {
                                        this.doEnergyExplosion();
                                    }

                                    break;

                                case 3:
                                    if (this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord) == Block.fire.blockID)
                                    {
                                        this.doEnergyExplosion();
                                    }

                                    break;

                                case 4:
                                    if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == Block.fire.blockID)
                                    {
                                        this.doEnergyExplosion();
                                    }

                                    break;

                                case 5:
                                    if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == Block.fire.blockID)
                                    {
                                        this.doEnergyExplosion();
                                    }
                            }
                        }

                        int var2;

                        if (this.mIsAddedToEnet && this.mMetaTileEntity.isEnetOutput() && this.mMetaTileEntity.getEnergyVar() >= Math.max(this.mMetaTileEntity.maxEUOutput(), this.mMetaTileEntity.getMinimumStoredEU()) && this.mMetaTileEntity.maxEUOutput() > 0)
                        {
                            try
                            {
                                EnergyNet var1 = EnergyNet.getForWorld(this.worldObj);

                                if (var1 != null)
                                {
                                    for (var2 = 0; var2 < this.getMaxOutputPackets(); ++var2)
                                    {
                                        this.setStoredEnergy(this.mMetaTileEntity.getEnergyVar() + var1.emitEnergyFrom(this, this.mMetaTileEntity.maxEUOutput()) - this.mMetaTileEntity.maxEUOutput());
                                    }
                                }
                            }
                            catch (Exception var3)
                            {
                                ;
                            }
                        }

                        int var5;

                        for (var5 = 0; var5 < this.mMetaTileEntity.getInputTier(); ++var5)
                        {
                            for (var2 = this.mMetaTileEntity.dechargerSlotStartIndex(); var2 < this.mMetaTileEntity.dechargerSlotCount() + this.mMetaTileEntity.dechargerSlotStartIndex(); ++var2)
                            {
                                if (this.mMetaTileEntity.mInventory[var2] != null && this.demandsEnergy() > 0 && this.mMetaTileEntity.mInventory[var2].getItem() instanceof IElectricItem && ((IElectricItem)this.mMetaTileEntity.mInventory[var2].getItem()).canProvideEnergy())
                                {
                                    this.increaseStoredEnergy(ElectricItem.discharge(this.mMetaTileEntity.mInventory[var2], this.mMetaTileEntity.maxEUStore() - this.mMetaTileEntity.getEnergyVar(), this.mMetaTileEntity.getInputTier(), false, false), true);
                                }
                            }
                        }

                        for (var5 = 0; var5 < this.mMetaTileEntity.getOutputTier(); ++var5)
                        {
                            for (var2 = this.mMetaTileEntity.rechargerSlotStartIndex(); var2 < this.mMetaTileEntity.rechargerSlotCount() + this.mMetaTileEntity.rechargerSlotStartIndex(); ++var2)
                            {
                                if (this.mMetaTileEntity.getEnergyVar() > 0 && this.mMetaTileEntity.mInventory[var2] != null && this.mMetaTileEntity.mInventory[var2].getItem() instanceof IElectricItem)
                                {
                                    this.decreaseStoredEnergy(ElectricItem.charge(this.mMetaTileEntity.mInventory[var2], this.mMetaTileEntity.getEnergyVar(), this.mMetaTileEntity.getOutputTier(), false, false), true);
                                }
                            }
                        }

                        if (this.mSendClientData)
                        {
                            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord), 3, this.mID);
                            this.mNeedsUpdate = true;
                            this.mSendClientData = false;
                        }

                        if (this.mNeedsUpdate)
                        {
                            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord), 0, this.mFacing & 7 | (this.mActive ? 8 : 0) | (this.mRedstone ? 16 : 0) | this.mMetaTileEntity.getUpdateData() << 5);
                            this.mNeedsUpdate = false;
                        }
                    }

                    this.mMetaTileEntity.onPostTick();
                    this.onInventoryChanged();
                }
            }
            catch (Throwable var4)
            {
                System.err.println("Encountered Exception while ticking TileEntity, the Game should\'ve crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
                var4.printStackTrace();
            }

            this.mInventoryChanged = false;
        }
    }

    public void receiveClientEvent(int var1, int var2)
    {
        super.receiveClientEvent(var1, var2);

        if (this.hasValidMetaTileEntity())
        {
            try
            {
                this.mMetaTileEntity.receiveClientEvent(var1, var2);
            }
            catch (Throwable var4)
            {
                System.err.println("Encountered Exception while receiving Data from the Server, the Client should\'ve been crashed by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
                var4.printStackTrace();
            }
        }

        if (this.worldObj.isRemote)
        {
            switch (var1)
            {
                case 0:
                    this.mFacing = var2 & 7;
                    this.mActive = (var2 & 8) != 0;
                    this.mRedstone = (var2 & 16) != 0;

                    if (this.hasValidMetaTileEntity())
                    {
                        this.mMetaTileEntity.onValueUpdate((short)(var2 >> 5));
                    }

                    this.mNeedsUpdate = true;
                    break;

                case 1:
                    this.mActive = var2 != 0;
                    this.mNeedsUpdate = true;
                    break;

                case 2:
                    this.mRedstone = var2 != 0;
                    this.mNeedsUpdate = true;
                    break;

                case 3:
                    if (this.mID == 0 && var2 > 0)
                    {
                        this.mID = var2;
                        this.createNewMetatileEntity(this.mID);
                        this.mNeedsUpdate = true;
                    }

                    break;

                case 4:
                    if (this.hasValidMetaTileEntity())
                    {
                        this.mMetaTileEntity.doSound(var2, (double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D);
                    }

                    break;

                case 5:
                    if (this.hasValidMetaTileEntity())
                    {
                        this.mMetaTileEntity.startSoundLoop(var2, (double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D);
                    }

                    break;

                case 6:
                    if (this.hasValidMetaTileEntity())
                    {
                        this.mMetaTileEntity.stopSoundLoop(var2, (double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D);
                    }
            }
        }
        else if (var1 == 3 && var2 == 0)
        {
            this.mNeedsUpdate = true;
        }
    }

    public ArrayList getDebugInfo(EntityPlayer var1, int var2)
    {
        ArrayList var3 = new ArrayList();

        if (var2 > 2)
        {
            var3.add("Meta-ID: " + this.mID + (this.hasValidMetaTileEntity() ? " valid" : " invalid") + (this.mMetaTileEntity == null ? " MetaTileEntity == null!" : " "));
        }

        if (var2 > 1)
        {
            var3.add("Is" + (this.mMetaTileEntity.isAccessAllowed(var1) ? " " : " not ") + "accessible for you");
        }

        if (var2 > 0)
        {
            var3.add("Machine is " + (this.mActive ? "active" : "inactive"));
        }

        return this.mMetaTileEntity.getSpecialDebugInfo(var1, var2, var3);
    }

    public String getMainInfo()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getMainInfo() : "";
    }

    public String getSecondaryInfo()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getSecondaryInfo() : "";
    }

    public String getTertiaryInfo()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getTertiaryInfo() : "";
    }

    public boolean isGivingInformation()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.isGivingInformation() : false;
    }

    public boolean wrenchCanSetFacing(EntityPlayer var1, int var2)
    {
        return !this.hasValidMetaTileEntity() ? false : this.mFacing != var2 && this.mMetaTileEntity.isFacingValid(var2);
    }

    public short getFacing()
    {
        return (short)this.mFacing;
    }

    public void setFacing(short var1)
    {
        if (this.hasValidMetaTileEntity() && this.mMetaTileEntity.isFacingValid(var1))
        {
            this.mFacing = var1;
        }

        this.mNeedsUpdate = true;
        this.onMachineBlockUpdate();
    }

    public boolean wrenchCanRemove(EntityPlayer var1)
    {
        return this.hasValidMetaTileEntity() && this.mMetaTileEntity.ownerWrench() ? this.playerOwnsThis(var1, true) : true;
    }

    public float getWrenchDropRate()
    {
        return this.hasValidMetaTileEntity() && this.mMetaTileEntity.isSimpleMachine() ? 1.0F : 0.8F;
    }

    public int getSizeInventory()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getInvSize() : 0;
    }

    public ItemStack getStackInSlot(int var1)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.mInventory[var1] : null;
    }

    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        this.mInventoryChanged = true;

        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.mInventory[var1] = var2;
        }
    }

    public String getInvName()
    {
        return mMetaTileList[this.mID] != null ? mMetaTileList[this.mID].mName : "";
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void openChest()
    {
        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.onOpenGUI();
        }
    }

    public void closeChest()
    {
        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.onCloseGUI();
        }
    }

    public int getStartInventorySide(ForgeDirection var1)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getInvSideIndex(var1, this.mFacing) : 0;
    }

    public int getSizeInventorySide(ForgeDirection var1)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getInvSideLength(var1, this.mFacing) : 0;
    }

    public boolean isAddedToEnergyNet()
    {
        return this.mIsAddedToEnet;
    }

    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.hasValidMetaTileEntity() && this.playerOwnsThis(var1, false) && this.mTickTimer > 40L && this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) < 64.0D && this.mMetaTileEntity.isAccessAllowed(var1);
    }

    public int getStored()
    {
        return this.hasValidMetaTileEntity() ? Math.min(this.mMetaTileEntity.getEnergyVar(), this.getCapacity()) : 0;
    }

    public void validate()
    {
        super.validate();
        this.mNeedsUpdate = true;
        this.mTickTimer = 0L;
    }

    public void invalidate()
    {
        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.onRemoval();
            this.mMetaTileEntity.inValidate();
            this.mMetaTileEntity.mBaseMetaTileEntity = null;
        }

        if (this.mIsAddedToEnet)
        {
            this.mIsAddedToEnet = !JAC_ModHandler.removeTileFromEnet(this);
        }

        super.invalidate();
    }

    public boolean acceptsEnergyFrom(TileEntity var1, Direction var2)
    {
        return !this.isInvalid() && !this.mReleaseEnergy ? (this.hasValidMetaTileEntity() ? this.mMetaTileEntity.isInputFacing((short)var2.toSideValue()) : false) : false;
    }

    public boolean emitsEnergyTo(TileEntity var1, Direction var2)
    {
        return !this.isInvalid() && !this.mReleaseEnergy ? (this.hasValidMetaTileEntity() ? this.mMetaTileEntity.isOutputFacing((short)var2.toSideValue()) : false) : this.mReleaseEnergy;
    }

    public ItemStack getStackInSlotOnClosing(int var1)
    {
        ItemStack var2 = this.getStackInSlot(var1);

        if (var2 != null)
        {
            this.setInventorySlotContents(var1, (ItemStack)null);
        }

        return var2;
    }

    public ItemStack decrStackSize(int var1, int var2)
    {
        this.mInventoryChanged = true;
        ItemStack var3 = this.getStackInSlot(var1);

        if (var3 != null)
        {
            if (var3.stackSize <= var2)
            {
                if (this.hasValidMetaTileEntity() && this.mMetaTileEntity.setStackToZeroInsteadOfNull(var1))
                {
                    var3.stackSize = 0;
                }
                else
                {
                    this.setInventorySlotContents(var1, (ItemStack)null);
                }
            }
            else
            {
                var3 = var3.splitStack(var2);

                if (var3.stackSize == 0 && this.hasValidMetaTileEntity() && !this.mMetaTileEntity.setStackToZeroInsteadOfNull(var1))
                {
                    this.setInventorySlotContents(var1, (ItemStack)null);
                }
            }
        }

        return var3;
    }

    public int getMaxEnergyOutput()
    {
        return this.mReleaseEnergy ? Integer.MAX_VALUE : (this.hasValidMetaTileEntity() ? this.mMetaTileEntity.maxEUOutput() : 0);
    }

    public int demandsEnergy()
    {
        return !this.mReleaseEnergy && this.hasValidMetaTileEntity() ? this.getCapacity() - this.mMetaTileEntity.getEnergyVar() : 0;
    }

    public int injectEnergy(Direction var1, int var2)
    {
        if (!this.hasValidMetaTileEntity())
        {
            return var2;
        }
        else if (var2 > this.mMetaTileEntity.maxEUInput())
        {
            this.doExplosion(var2);
            return 0;
        }
        else
        {
            this.setStoredEnergy(this.mMetaTileEntity.getEnergyVar() + var2);
            return 0;
        }
    }

    public int getCapacity()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.maxEUStore() : 0;
    }

    public int getOutput()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.maxEUOutput() : 0;
    }

    public void onMachineBlockUpdate()
    {
        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.onMachineBlockUpdate();
        }
    }

    public int fill(ForgeDirection var1, LiquidStack var2, boolean var3)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.fill(var2, var3) : 0;
    }

    public int fill(int var1, LiquidStack var2, boolean var3)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.fill(var2, var3) : 0;
    }

    public LiquidStack drain(ForgeDirection var1, int var2, boolean var3)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.drain(var2, var3) : null;
    }

    public LiquidStack drain(int var1, int var2, boolean var3)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.drain(var2, var3) : null;
    }

    public ILiquidTank[] getTanks(ForgeDirection var1)
    {
        return this.hasValidMetaTileEntity() && this.mMetaTileEntity.getCapacity() > 0 ? new ILiquidTank[] {this.mMetaTileEntity}: new ILiquidTank[0];
    }

    public ILiquidTank getTank(ForgeDirection var1, LiquidStack var2)
    {
        return this.hasValidMetaTileEntity() && this.mMetaTileEntity.getCapacity() > 0 && (var2 == null || var2 != null && var2.isLiquidEqual(this.mMetaTileEntity.getLiquid())) ? this.mMetaTileEntity : null;
    }

    public ItemStack getWrenchDrop(EntityPlayer var1)
    {
        return null;
    }

    public void setStored(int var1)
    {
        if (this.hasValidMetaTileEntity())
        {
            this.setStoredEnergy(var1);
        }
    }

    public boolean isTeleporterCompatible(Direction var1)
    {
        return false;
    }

    public int getMaxSafeInput()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.maxEUInput() : Integer.MAX_VALUE;
    }

    public int getMaxOutputPackets()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.maxEUPulses() : 0;
    }

    public boolean getRedstone()
    {
        return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
    }

    public boolean isActive()
    {
        return this.mActive;
    }

    public boolean isValidSlot(int var1)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.isValidSlot(var1) : false;
    }

    public boolean playerOwnsThis(EntityPlayer var1, boolean var2)
    {
        if (!this.hasValidMetaTileEntity())
        {
            return false;
        }
        else
        {
            if (var2 || this.mMetaTileEntity.ownerControl() || this.mOwnerName.equals(""))
            {
                if (this.mOwnerName.equals("") && !this.worldObj.isRemote)
                {
                    this.mOwnerName = var1.username;
                }
                else if (!var1.username.equals("Player") && !this.mOwnerName.equals("Player") && !this.mOwnerName.equals(var1.username))
                {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean privateAccess()
    {
        return !this.hasValidMetaTileEntity() ? false : this.mMetaTileEntity.ownerControl();
    }

    public boolean unbreakable()
    {
        return !this.hasValidMetaTileEntity() ? false : this.mMetaTileEntity.unbreakable();
    }

    public boolean setStoredEnergy(int var1)
    {
        if (!this.hasValidMetaTileEntity())
        {
            return false;
        }
        else
        {
            if (var1 < 0)
            {
                var1 = 0;
            }

            this.mMetaTileEntity.setEnergyVar(var1);
            return true;
        }
    }

    public boolean increaseStoredEnergy(int var1, boolean var2)
    {
        if (!this.hasValidMetaTileEntity())
        {
            return false;
        }
        else if (this.mMetaTileEntity.getEnergyVar() >= this.getCapacity() && !var2)
        {
            return false;
        }
        else
        {
            this.setStoredEnergy(this.mMetaTileEntity.getEnergyVar() + var1);
            return true;
        }
    }

    public boolean decreaseStoredEnergy(int var1, boolean var2)
    {
        if (!this.hasValidMetaTileEntity())
        {
            return false;
        }
        else if (this.mMetaTileEntity.getEnergyVar() - var1 < 0 && !var2)
        {
            return false;
        }
        else
        {
            this.mMetaTileEntity.setEnergyVar(this.mMetaTileEntity.getEnergyVar() - var1);

            if (this.mMetaTileEntity.getEnergyVar() < 0)
            {
                this.setStoredEnergy(0);
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    public void doEnergyExplosion()
    {
        if (this.getCapacity() > 0 && this.getStored() >= this.getCapacity() / 5)
        {
            this.doExplosion(this.getOutput() * (this.getStored() >= this.getCapacity() ? 4 : (this.getStored() >= this.getCapacity() / 2 ? 2 : 1)));
        }
    }

    public int getTier(int var1)
    {
        return var1 <= 32 ? 1 : (var1 <= 128 ? 2 : (var1 <= 512 ? 3 : (var1 <= 2048 ? 4 : (var1 <= 8192 ? 5 : 6))));
    }

    public int getTexture(int var1, int var2)
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getTextureIndex(var1, this.mFacing, this.mActive, this.mRedstone) : 0;
    }

    public boolean hasValidMetaTileEntity()
    {
        return this.mMetaTileEntity != null && this.mMetaTileEntity.mBaseMetaTileEntity == this;
    }

    public void doExplosion(int var1)
    {
        if (this.isAddedToEnergyNet() && mMachineWireFire)
        {
            try
            {
                this.mReleaseEnergy = true;
                JAC_ModHandler.removeTileFromEnet(this);
                JAC_ModHandler.addTileToEnet(this);
                EnergyNet var2 = EnergyNet.getForWorld(this.worldObj);

                if (var2 != null)
                {
                    var2.emitEnergyFrom(this, 32);
                    var2.emitEnergyFrom(this, 128);
                    var2.emitEnergyFrom(this, 512);
                    var2.emitEnergyFrom(this, 2048);
                    var2.emitEnergyFrom(this, 8192);
                }
            }
            catch (Exception var6)
            {
                ;
            }
        }

        this.mReleaseEnergy = false;

        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.onExplosion();
        }

        float var7 = var1 < 10 ? 1.0F : (var1 < 32 ? 2.0F : (var1 < 128 ? 3.0F : (var1 < 512 ? 4.0F : (var1 < 2048 ? 5.0F : (var1 < 4096 ? 6.0F : (var1 < 8192 ? 7.0F : 8.0F))))));
        int var3 = this.xCoord;
        int var4 = this.yCoord;
        int var5 = this.zCoord;
        this.worldObj.setBlock(var3, var4, var5, 0);
        this.worldObj.createExplosion((Entity)null, (double)var3 + 0.5D, (double)var4 + 0.5D, (double)var5 + 0.5D, var7, true);
    }

    public int addEnergy(int var1)
    {
        if (!this.hasValidMetaTileEntity())
        {
            return 0;
        }
        else
        {
            if (var1 > 0)
            {
                this.increaseStoredEnergy(var1, true);
            }
            else
            {
                this.decreaseStoredEnergy(-var1, true);
            }

            return this.mMetaTileEntity.getEnergyVar();
        }
    }

    public void onNetworkUpdate(String var1) {}

    public List getNetworkedFields()
    {
        ArrayList var1 = new ArrayList();
        var1.add("mOwnerName");
        return var1;
    }

    public void onRightclick(EntityPlayer var1)
    {
        this.mNeedsUpdate = true;
        this.mSendClientData = true;

        try
        {
            if (var1 != null && this.hasValidMetaTileEntity() && this.mID > 15)
            {
                this.mMetaTileEntity.onRightclick(var1);
            }
        }
        catch (Throwable var3)
        {
            System.err.println("Encountered Exception while rightclicking TileEntity, the Game should\'ve crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
            var3.printStackTrace();
        }
    }

    public void onLeftclick(EntityPlayer var1)
    {
        this.mNeedsUpdate = true;
        this.mSendClientData = true;

        try
        {
            if (var1 != null && this.hasValidMetaTileEntity() && this.mID > 15)
            {
                this.mMetaTileEntity.onLeftclick(var1);
            }
        }
        catch (Throwable var3)
        {
            System.err.println("Encountered Exception while leftclicking TileEntity, the Game should\'ve crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
            var3.printStackTrace();
        }
    }

    public boolean isQuantumChest()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.isQuantumChest() : false;
    }

    public ItemStack[] getStoredItemData()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getStoredItemData() : null;
    }

    public void setItemCount(int var1)
    {
        if (this.hasValidMetaTileEntity())
        {
            this.mMetaTileEntity.setItemCount(var1);
        }
    }

    public int getMaxItemCount()
    {
        return this.hasValidMetaTileEntity() ? this.mMetaTileEntity.getMaxItemCount() : 0;
    }
}

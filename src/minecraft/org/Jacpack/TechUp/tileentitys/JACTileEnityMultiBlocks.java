package org.Jacpack.TechUp.tileentitys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.machines.INetworkedObject;
import org.Jacpack.TechUp.util.misc.Reference;
import org.Jacpack.TechUp.util.network.PacketBuilder;
import org.Jacpack.TechUp.util.network.PacketTileEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class JACTileEnityMultiBlocks extends TileEntity implements INetworkedObject
{
    private String owner = Reference.CHANNEL_NAME;

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        PacketTileEntity var1 = new PacketTileEntity(this);
        return var1.getPacket();
    }

    public void writePacketData(DataOutputStream var1) throws IOException {}

    public void readPacketData(DataInputStream var1) throws IOException {}

    public void markBlockForUpdate()
    {
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public void sendUpdateToClient()
    {
        PacketBuilder.getInstance().sendTileEntityPacket(this);
    }

    public void onBlockPlacedBy(EntityLiving var1)
    {
        if (var1 instanceof EntityPlayer)
        {
            String var2 = ((EntityPlayer)var1).username;

            if (var2 != null)
            {
                this.owner = var2;
            }
        }
    }

    public boolean isRedstonePowered()
    {
        ForgeDirection[] var1 = ForgeDirection.VALID_DIRECTIONS;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            ForgeDirection var4 = var1[var3];

            if (this.isRedstonePowering(0, var4) || this.isRedstonePowering(-1, var4))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isRedstonePowering(int var1, ForgeDirection var2)
    {
        int var3 = JACTools.getBlockIdOnSide(this.worldObj, this.xCoord, this.yCoord + var1, this.zCoord, var2);

        if (var3 == Block.redstoneWire.blockID)
        {
            int var4 = JACTools.getBlockMetadataOnSide(this.worldObj, this.xCoord, this.yCoord + var1, this.zCoord, var2);

            if (var4 > 0)
            {
                return true;
            }
        }

        return false;
    }

    public Block getBlock()
    {
        if (this.blockType == null)
        {
            this.blockType = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
        }

        return this.blockType;
    }

    public final int getBlockId()
    {
        Block var1 = this.getBlock();
        return var1 == null ? 0 : var1.blockID;
    }

    public final int getDimension()
    {
        return this.worldObj == null ? 0 : this.worldObj.provider.dimensionId;
    }

    public final String getOwner()
    {
        return this.owner;
    }

    /**
     * Returns the name of the inventory.
     */
    public abstract String getInvName();

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq((double)this.xCoord, (double)this.yCoord, (double)this.zCoord) <= 64.0D;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setString("owner", this.owner);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.owner = var1.getString("owner");
    }

    public final int getX()
    {
        return this.xCoord;
    }

    public final int getY()
    {
        return this.yCoord;
    }

    public final int getZ()
    {
        return this.zCoord;
    }

    public final World getWorld()
    {
        return this.worldObj;
    }

    public abstract short getId();
}

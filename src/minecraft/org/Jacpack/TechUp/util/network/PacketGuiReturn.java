package org.Jacpack.TechUp.util.network;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PacketGuiReturn extends HarmonionPacket
{
    private IGuiReturnHandler obj;

    public PacketGuiReturn() {}

    public PacketGuiReturn(IGuiReturnHandler var1)
    {
        this.obj = var1;
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.obj.getWorld().provider.dimensionId);

        if (this.obj instanceof TileEntity)
        {
            TileEntity var2 = (TileEntity)this.obj;
            var1.writeBoolean(true);
            var1.writeInt(var2.xCoord);
            var1.writeInt(var2.yCoord);
            var1.writeInt(var2.zCoord);
        }
        else
        {
            if (!(this.obj instanceof Entity))
            {
                return;
            }

            Entity var3 = (Entity)this.obj;
            var1.writeBoolean(false);
            var1.writeInt(var3.entityId);
        }

        this.obj.writeGuiData(var1);
    }

    public void readData(DataInputStream var1) throws IOException
    {
        int var2 = var1.readInt();
        WorldServer var3 = DimensionManager.getWorld(var2);
        boolean var4 = var1.readBoolean();
        int var5;

        if (var4)
        {
            var5 = var1.readInt();
            int var6 = var1.readInt();
            int var7 = var1.readInt();
            TileEntity var8 = var3.getBlockTileEntity(var5, var6, var7);

            if (var8 instanceof IGuiReturnHandler)
            {
                ((IGuiReturnHandler)var8).readGuiData(var1);
            }
        }
        else
        {
            var5 = var1.readInt();
            Entity var9 = var3.getEntityByID(var5);

            if (var9 instanceof IGuiReturnHandler)
            {
                ((IGuiReturnHandler)var9).readGuiData(var1);
            }
        }
    }

    public void sendPacket()
    {
        PacketDispatcher.sendPacketToServer(this.getPacket());
    }

    public int getID()
    {
        return PacketType.GUI_RETURN.ordinal();
    }
}

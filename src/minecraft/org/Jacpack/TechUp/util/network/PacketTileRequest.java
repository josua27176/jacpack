package org.Jacpack.TechUp.util.network;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PacketTileRequest extends TechUpPacket
{
    private TileEntity tile;
    private Player player;

    public PacketTileRequest(Player var1)
    {
        this.player = var1;
    }

    public PacketTileRequest(TileEntity var1)
    {
        this.tile = var1;
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.tile.worldObj.provider.dimensionId);
        var1.writeInt(this.tile.xCoord);
        var1.writeInt(this.tile.yCoord);
        var1.writeInt(this.tile.zCoord);
    }

    public void readData(DataInputStream var1) throws IOException
    {
        WorldServer var2 = DimensionManager.getWorld(var1.readInt());

        if (var2 != null)
        {
            int var3 = var1.readInt();
            int var4 = var1.readInt();
            int var5 = var1.readInt();
            this.tile = var2.getBlockTileEntity(var3, var4, var5);

            if (this.tile != null && this.player != null)
            {
                Packet var6 = this.tile.getDescriptionPacket();
                PacketDispatcher.sendPacketToPlayer(var6, this.player);
            }
        }
    }

    public int getID()
    {
        return PacketType.TILE_REQUEST.ordinal();
    }
}

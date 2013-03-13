package org.Jacpack.TechUp.util.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.Jacpack.TechUp.api.machines.Game;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTileExtraData extends TechUpPacket
{
    private ITileExtraDataHandler tile;
    private ByteArrayOutputStream bytes;
    private DataOutputStream data;

    public PacketTileExtraData() {}

    public PacketTileExtraData(ITileExtraDataHandler var1)
    {
        this.tile = var1;
    }

    public DataOutputStream getDataStream()
    {
        if (this.data == null)
        {
            this.bytes = new ByteArrayOutputStream();
            this.data = new DataOutputStream(this.bytes);
        }

        return this.data;
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.tile.getX());
        var1.writeInt(this.tile.getY());
        var1.writeInt(this.tile.getZ());
        var1.write(this.bytes.toByteArray());
    }

    @SideOnly(Side.CLIENT)
    public void readData(DataInputStream var1) throws IOException
    {
        World var2 = Game.getWorld();
        int var3 = var1.readInt();
        int var4 = var1.readInt();
        int var5 = var1.readInt();
        TileEntity var6 = var2.getBlockTileEntity(var3, var4, var5);

        if (var6 instanceof ITileExtraDataHandler)
        {
            ((ITileExtraDataHandler)var6).onUpdatePacket(var1);
        }
    }

    public int getID()
    {
        return PacketType.TILE_EXTRA_DATA.ordinal();
    }
}

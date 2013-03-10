package org.Jacpack.TechUp.util.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.Jacpack.TechUp.api.old.Game;
import org.Jacpack.TechUp.tileentitys.JACTileEnityMultiBlocks;

import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTileEntity extends HarmonionPacket
{
    private JACTileEnityMultiBlocks tile;

    public PacketTileEntity() {}

    public PacketTileEntity(JACTileEnityMultiBlocks var1)
    {
        this.tile = var1;
    }

    public Packet getPacket()
    {
        Packet var1 = super.getPacket();
        var1.isChunkDataPacket = true;
        return var1;
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.tile.xCoord);
        var1.writeInt(this.tile.yCoord);
        var1.writeInt(this.tile.zCoord);
        var1.writeShort(this.tile.getId());
        this.tile.writePacketData(var1);
    }

    @SideOnly(Side.CLIENT)
    public void readData(DataInputStream var1) throws IOException
    {
        World var2 = Game.getWorld();

        if (var2 != null)
        {
            int var3 = var1.readInt();
            int var4 = var1.readInt();
            int var5 = var1.readInt();
            short var6 = var1.readShort();

            if (var6 >= 0 && var4 >= 0 && var2.blockExists(var3, var4, var5))
            {
                TileEntity var7 = var2.getBlockTileEntity(var3, var4, var5);

                if (var7 instanceof JACTileEnityMultiBlocks)
                {
                    this.tile = (JACTileEnityMultiBlocks)var7;

                    if (this.tile.getId() != var6)
                    {
                        this.tile = null;
                    }
                }
                else
                {
                    this.tile = null;
                }

                if (this.tile != null)
                {
                    this.tile.readPacketData(var1);
                }
            }
        }
    }

    public int getID()
    {
        return PacketType.TILE_ENTITY.ordinal();
    }
}

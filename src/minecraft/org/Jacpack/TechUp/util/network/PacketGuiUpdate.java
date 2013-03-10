package org.Jacpack.TechUp.util.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public class PacketGuiUpdate extends TechUpPacket
{
    private int windowId;
    private int dataId;
    private int value;

    public PacketGuiUpdate() {}

    public PacketGuiUpdate(int var1, int var2, int var3)
    {
        this.windowId = var1;
        this.dataId = var2;
        this.value = var3;
    }

    public void sendPacket(EntityPlayer var1)
    {
        PacketDispatcher.sendPacketToPlayer(this.getPacket(), (Player)var1);
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeByte(this.windowId);
        var1.writeByte(this.dataId);
        var1.writeInt(this.value);
    }

    public void readData(DataInputStream var1) throws IOException
    {
        this.windowId = var1.readByte();
        this.dataId = var1.readByte();
        this.value = var1.readInt();
        EntityClientPlayerMP var2 = FMLClientHandler.instance().getClient().thePlayer;

        if (var2.openContainer != null && var2.openContainer.windowId == this.windowId)
        {
            var2.openContainer.updateProgressBar(this.dataId, this.value);
        }
    }

    public int getID()
    {
        return PacketType.GUI_UPDATE.ordinal();
    }
}

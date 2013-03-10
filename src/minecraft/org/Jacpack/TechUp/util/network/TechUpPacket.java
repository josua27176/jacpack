package org.Jacpack.TechUp.util.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.Jacpack.TechUp.util.misc.Reference;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public abstract class TechUpPacket
{
    public static final String CHANNEL_NAME = Reference.CHANNEL_NAME;

    public Packet getPacket()
    {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DataOutputStream var2 = new DataOutputStream(var1);

        try
        {
            var2.writeByte(this.getID());
            this.writeData(var2);
        }
        catch (IOException var4)
        {
            var4.printStackTrace();
        }

        Packet250CustomPayload var3 = new Packet250CustomPayload();
        var3.channel = Reference.CHANNEL_NAME;
        var3.data = var1.toByteArray();
        var3.length = var3.data.length;
        return var3;
    }
    
    public enum PacketType
    {
        TILE_ENTITY,
        GUI_RETURN,
        TILE_EXTRA_DATA,
        TILE_REQUEST,
        GUI_UPDATE,
        EFFECT;
    }

    public abstract void writeData(DataOutputStream var1) throws IOException;

    public abstract void readData(DataInputStream var1) throws IOException;

    public abstract int getID();

    public String toString()
    {
        return this.getClass().getSimpleName();
    }
}

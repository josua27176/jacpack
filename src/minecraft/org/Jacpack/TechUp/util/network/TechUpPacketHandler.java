package org.Jacpack.TechUp.util.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.Jacpack.TechUp.api.old.Game;
import org.Jacpack.TechUp.util.network.TechUpPacket.PacketType;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class TechUpPacketHandler implements IPacketHandler
{
    private static PacketType[] packetTypes = PacketType.values();

    public void onPacketData(INetworkManager var1, Packet250CustomPayload var2, Player var3)
    {
        DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(var2.data));

        try
        {
            Object var5 = null;
            byte var6 = var4.readByte();

            if (var6 < 0)
            {
                return;
            }

            PacketType var7 = packetTypes[var6];

            switch ($SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[var7.ordinal()])
            {
                case 1:
                    var5 = new PacketTileEntity();
                    break;

                case 2:
                    var5 = new PacketGuiReturn();
                    break;

                case 3:
                    var5 = new PacketTileExtraData();
                    break;

                case 4:
                    var5 = new PacketTileRequest(var3);
                    break;

                case 5:
                    var5 = new PacketGuiUpdate();
                    break;

                case 6:

                case 7:
                case 8:
                case 9:
                case 10:

                default:
                    return;
            }

            ((TechUpPacket)var5).readData(var4);
        }
        catch (IOException var8)
        {
            Game.log(Level.SEVERE, "Exception in PacketHandler.onPacketData {0}", new Object[] {var8});
            var8.printStackTrace();
        }
    }
    
    static final int[] $SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType = new int[PacketType.values().length];

    static
    {
        try
        {
        	$SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[PacketType.TILE_ENTITY.ordinal()] = 1;
        }
        catch (NoSuchFieldError var10)
        {
            ;
        }

        try
        {
        	$SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[PacketType.GUI_RETURN.ordinal()] = 2;
        }
        catch (NoSuchFieldError var9)
        {
            ;
        }

        try
        {
        	$SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[PacketType.TILE_EXTRA_DATA.ordinal()] = 3;
        }
        catch (NoSuchFieldError var8)
        {
            ;
        }

        try
        {
        	$SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[PacketType.TILE_REQUEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError var7)
        {
            ;
        }

        try
        {
        	$SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[PacketType.GUI_UPDATE.ordinal()] = 5;
        }
        catch (NoSuchFieldError var6)
        {
            ;
        }

        try
        {
        	$SwitchMap$org$Jacpack$TechUp$util$network$TechUpPacket$PacketType[PacketType.EFFECT.ordinal()] = 6;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }
    }
}

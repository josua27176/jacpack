package org.Jacpack.TechUp.util.network;

import org.Jacpack.TechUp.util.network.HarmonionPacket.PacketType;

class TechUpPacketHandler$1
{
    static final int[] $SwitchMap$railcraft$common$util$network$PacketType = new int[PacketType.values().length];

    static
    {
        try
        {
            $SwitchMap$railcraft$common$util$network$PacketType[PacketType.TILE_ENTITY.ordinal()] = 1;
        }
        catch (NoSuchFieldError var10)
        {
            ;
        }

        try
        {
            $SwitchMap$railcraft$common$util$network$PacketType[PacketType.GUI_RETURN.ordinal()] = 2;
        }
        catch (NoSuchFieldError var9)
        {
            ;
        }

        try
        {
            $SwitchMap$railcraft$common$util$network$PacketType[PacketType.TILE_EXTRA_DATA.ordinal()] = 3;
        }
        catch (NoSuchFieldError var8)
        {
            ;
        }

        try
        {
            $SwitchMap$railcraft$common$util$network$PacketType[PacketType.TILE_REQUEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError var7)
        {
            ;
        }

        try
        {
            $SwitchMap$railcraft$common$util$network$PacketType[PacketType.GUI_UPDATE.ordinal()] = 5;
        }
        catch (NoSuchFieldError var6)
        {
            ;
        }

        try
        {
            $SwitchMap$railcraft$common$util$network$PacketType[PacketType.EFFECT.ordinal()] = 6;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }
    }
}

package org.Jacpack.TechUp.api;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;

public class JAC_ModHandler
{
    public static boolean addTileToEnet(TileEntity var0)
    {
        try
        {
            EnergyNet.getForWorld(var0.worldObj).addTileEntity(var0);
            return true;
        }
        catch (Exception var2)
        {
            return false;
        }
    }

    public static boolean removeTileFromEnet(TileEntity var0)
    {
        try
        {
            EnergyNet.getForWorld(var0.worldObj).removeTileEntity(var0);
            return true;
        }
        catch (Exception var2)
        {
            return false;
        }
    }

    public static int emitEnergyToEnet(int var0, TileEntity var1)
    {
        try
        {
            return EnergyNet.getForWorld(var1.worldObj).emitEnergyFrom((IEnergySource)var1, var0);
        }
        catch (Exception var3)
        {
            return var0;
        }
    }
}

package ic2.api.energy;

import java.lang.reflect.Method;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import ic2.api.energy.tile.IEnergySource;

public final class EnergyNet
{
    public static EnergyNet getForWorld(World world)
    {
        try
        {
            if (EnergyNet_getForWorld == null)
            {
                EnergyNet_getForWorld = Class.forName(getPackage() + ".core.EnergyNet").getMethod("getForWorld", World.class);
            }

            return new EnergyNet(EnergyNet_getForWorld.invoke(null, world));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private EnergyNet(Object energyNetInstance)
    {
        this.energyNetInstance = energyNetInstance;
    }

    @Deprecated
    public void addTileEntity(TileEntity addedTileEntity)
    {
        try
        {
            if (EnergyNet_addTileEntity == null)
            {
                EnergyNet_addTileEntity = Class.forName(getPackage() + ".core.EnergyNet").getMethod("addTileEntity", TileEntity.class);
            }

            EnergyNet_addTileEntity.invoke(energyNetInstance, addedTileEntity);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void removeTileEntity(TileEntity removedTileEntity)
    {
        try
        {
            if (EnergyNet_removeTileEntity == null)
            {
                EnergyNet_removeTileEntity = Class.forName(getPackage() + ".core.EnergyNet").getMethod("removeTileEntity", TileEntity.class);
            }

            EnergyNet_removeTileEntity.invoke(energyNetInstance, removedTileEntity);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public int emitEnergyFrom(IEnergySource energySource, int amount)
    {
        try
        {
            if (EnergyNet_emitEnergyFrom == null)
            {
                EnergyNet_emitEnergyFrom = Class.forName(getPackage() + ".core.EnergyNet").getMethod("emitEnergyFrom", IEnergySource.class, Integer.TYPE);
            }

            return ((Integer) EnergyNet_emitEnergyFrom.invoke(energyNetInstance, energySource, amount)).intValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public long getTotalEnergyConducted(TileEntity tileEntity)
    {
        try
        {
            if (EnergyNet_getTotalEnergyConducted == null)
            {
                EnergyNet_getTotalEnergyConducted = Class.forName(getPackage() + ".core.EnergyNet").getMethod("getTotalEnergyConducted", TileEntity.class);
            }

            return ((Long) EnergyNet_getTotalEnergyConducted.invoke(energyNetInstance, tileEntity)).longValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public long getTotalEnergyEmitted(TileEntity tileEntity)
    {
        try
        {
            if (EnergyNet_getTotalEnergyEmitted == null)
            {
                EnergyNet_getTotalEnergyEmitted = Class.forName(getPackage() + ".core.EnergyNet").getMethod("getTotalEnergyEmitted", TileEntity.class);
            }

            return ((Long) EnergyNet_getTotalEnergyEmitted.invoke(energyNetInstance, tileEntity)).longValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public long getTotalEnergySunken(TileEntity tileEntity)
    {
        try
        {
            if (EnergyNet_getTotalEnergySunken == null)
            {
                EnergyNet_getTotalEnergySunken = Class.forName(getPackage() + ".core.EnergyNet").getMethod("getTotalEnergySunken", TileEntity.class);
            }

            return ((Long) EnergyNet_getTotalEnergySunken.invoke(energyNetInstance, tileEntity)).longValue();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getPackage()
    {
        Package pkg = EnergyNet.class.getPackage();

        if (pkg != null)
        {
            String packageName = pkg.getName();
            return packageName.substring(0, packageName.length() - ".api.energy".length());
        }

        return "ic2";
    }

    Object energyNetInstance;

    private static Method EnergyNet_getForWorld;
    private static Method EnergyNet_addTileEntity;
    private static Method EnergyNet_removeTileEntity;
    private static Method EnergyNet_emitEnergyFrom;
    private static Method EnergyNet_getTotalEnergyConducted;
    private static Method EnergyNet_getTotalEnergyEmitted;
    private static Method EnergyNet_getTotalEnergySunken;
}

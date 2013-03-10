package ic2.api.network;

import java.lang.reflect.Method;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class NetworkHelper
{
    public static void updateTileEntityField(TileEntity te, String field)
    {
        try
        {
            if (NetworkManager_updateTileEntityField == null)
            {
                NetworkManager_updateTileEntityField = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("updateTileEntityField", TileEntity.class, String.class);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_updateTileEntityField.invoke(instance, te, field);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange)
    {
        try
        {
            if (NetworkManager_initiateTileEntityEvent == null)
            {
                NetworkManager_initiateTileEntityEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateTileEntityEvent", TileEntity.class, Integer.TYPE, Boolean.TYPE);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_initiateTileEntityEvent.invoke(instance, te, event, limitRange);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateItemEvent(EntityPlayer player, ItemStack itemStack, int event, boolean limitRange)
    {
        try
        {
            if (NetworkManager_initiateItemEvent == null)
            {
                NetworkManager_initiateItemEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateItemEvent", EntityPlayer.class, ItemStack.class, Integer.TYPE, Boolean.TYPE);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_initiateItemEvent.invoke(instance, player, itemStack, event, limitRange);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void announceBlockUpdate(World world, int x, int y, int z)
    {
        try
        {
            if (NetworkManager_announceBlockUpdate == null)
            {
                NetworkManager_announceBlockUpdate = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("announceBlockUpdate", World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_announceBlockUpdate.invoke(instance, world, x, y, z);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void requestInitialData(INetworkDataProvider dataProvider)
    {
        try
        {
            if (NetworkManager_requestInitialData == null)
            {
                NetworkManager_requestInitialData = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("requestInitialData", INetworkDataProvider.class);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_requestInitialData.invoke(instance, dataProvider);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateClientTileEntityEvent(TileEntity te, int event)
    {
        try
        {
            if (NetworkManager_initiateClientTileEntityEvent == null)
            {
                NetworkManager_initiateClientTileEntityEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateClientTileEntityEvent", TileEntity.class, Integer.TYPE);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_initiateClientTileEntityEvent.invoke(instance, te, event);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void initiateClientItemEvent(ItemStack itemStack, int event)
    {
        try
        {
            if (NetworkManager_initiateClientItemEvent == null)
            {
                NetworkManager_initiateClientItemEvent = Class.forName(getPackage() + ".core.network.NetworkManager").getMethod("initiateClientItemEvent", ItemStack.class, Integer.TYPE);
            }

            if (instance == null)
            {
                instance = getInstance();
            }

            NetworkManager_initiateClientItemEvent.invoke(instance, itemStack, event);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getPackage()
    {
        Package pkg = NetworkHelper.class.getPackage();

        if (pkg != null)
        {
            String packageName = pkg.getName();
            return packageName.substring(0, packageName.length() - ".api.network".length());
        }

        return "ic2";
    }

    private static Object getInstance()
    {
        try
        {
            return Class.forName(getPackage() + ".core.IC2").getDeclaredField("network").get(null);
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e);
        }
    }

    private static Object instance;
    private static Method NetworkManager_updateTileEntityField;
    private static Method NetworkManager_initiateTileEntityEvent;
    private static Method NetworkManager_initiateItemEvent;
    private static Method NetworkManager_announceBlockUpdate;
    private static Method NetworkManager_requestInitialData;
    private static Method NetworkManager_initiateClientTileEntityEvent;
    private static Method NetworkManager_initiateClientItemEvent;
}

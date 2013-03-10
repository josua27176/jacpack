package ic2.api;

import java.lang.reflect.Method;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public final class ElectricItem
{
    public static int charge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
    {
        try
        {
            if (ElectricItem_charge == null)
            {
                ElectricItem_charge = Class.forName(getPackage() + ".core.item.ElectricItem").getMethod("charge", ItemStack.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
            }

            return (Integer) ElectricItem_charge.invoke(null, itemStack, amount, tier, ignoreTransferLimit, simulate);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static int discharge(ItemStack itemStack, int amount, int tier, boolean ignoreTransferLimit, boolean simulate)
    {
        try
        {
            if (ElectricItem_discharge == null)
            {
                ElectricItem_discharge = Class.forName(getPackage() + ".core.item.ElectricItem").getMethod("discharge", ItemStack.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
            }

            return (Integer) ElectricItem_discharge.invoke(null, itemStack, amount, tier, ignoreTransferLimit, simulate);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean canUse(ItemStack itemStack, int amount)
    {
        try
        {
            if (ElectricItem_canUse == null)
            {
                ElectricItem_canUse = Class.forName(getPackage() + ".core.item.ElectricItem").getMethod("canUse", ItemStack.class, Integer.TYPE);
            }

            return (Boolean) ElectricItem_canUse.invoke(null, itemStack, amount);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean use(ItemStack itemStack, int amount, EntityPlayer player)
    {
        try
        {
            if (ElectricItem_use == null)
            {
                ElectricItem_use = Class.forName(getPackage() + ".core.item.ElectricItem").getMethod("use", ItemStack.class, Integer.TYPE, EntityPlayer.class);
            }

            return (Boolean) ElectricItem_use.invoke(null, itemStack, amount, player);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void chargeFromArmor(ItemStack itemStack, EntityPlayer player)
    {
        try
        {
            if (ElectricItem_chargeFromArmor == null)
            {
                ElectricItem_chargeFromArmor = Class.forName(getPackage() + ".core.item.ElectricItem").getMethod("chargeFromArmor", ItemStack.class, EntityPlayer.class);
            }

            ElectricItem_chargeFromArmor.invoke(null, itemStack, player);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static String getPackage()
    {
        Package pkg = ElectricItem.class.getPackage();

        if (pkg != null)
        {
            return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
        }
        else
        {
            return "ic2";
        }
    }

    private static Method ElectricItem_charge;
    private static Method ElectricItem_discharge;
    private static Method ElectricItem_canUse;
    private static Method ElectricItem_use;
    private static Method ElectricItem_chargeFromArmor;
}

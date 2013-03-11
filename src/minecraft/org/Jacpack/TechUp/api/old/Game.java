package org.Jacpack.TechUp.api.old;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.Jacpack.TechUp.TechUp;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class Game
{
    private static final Logger log = Logger.getLogger("Railcraft");

    public static boolean isHost(World var0)
    {
        return !var0.isRemote;
    }

    public static boolean isNotHost(World var0)
    {
        return var0.isRemote;
    }

    @SideOnly(Side.CLIENT)
    public static World getWorld()
    {
        Minecraft var0 = FMLClientHandler.instance().getClient();
        return var0 != null ? var0.theWorld : null;
    }

    public static boolean isObfuscated()
    {
        return !World.class.getSimpleName().equals("World");
    }

    public static void log(Level var0, String var1, Object ... var2)
    {
        String var3 = var1;

        for (int var4 = 0; var4 < var2.length; ++var4)
        {
            if (var2[var4] != null)
            {
                var3 = var3.replace("{" + var4 + "}", var2[var4].toString());
            }
        }

        log.log(var0, var3);
    }

    public static void logErrorAPI(String var0, Throwable var1)
    {
        StringBuilder var2 = new StringBuilder(var0);
        var2.append(" API error, please update your mods. Error: ").append(var1);
        StackTraceElement[] var3 = var1.getStackTrace();

        if (var3.length > 0)
        {
            var2.append(", ").append(var3[0]);
        }

        log.log(Level.SEVERE, var2.toString());
    }

    public static void logError(String var0, Throwable var1)
    {
        log.log(Level.SEVERE, var0, var1);
    }

    public static void logErrorFingerprint(String var0)
    {
        StringBuilder var1 = new StringBuilder(var0);
        var1.append(" failed validation, terminating. Please re-download ").append(var0).append(".");
        log.log(Level.SEVERE, var1.toString());
    }

    static
    {
        log.setParent(FMLCommonHandler.instance().getFMLLogger());
    }
}

package net.minecraft.server.dedicated;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class ConsoleLogManager
{
    /** Log manager of Minecraft. */
    public static Logger loggerLogManager = Logger.getLogger("Minecraft");

    /**
     * Initialises the console logger.
     */
    public static void init()
    {
        ConsoleLogFormatter var0 = new ConsoleLogFormatter();
        loggerLogManager.setParent(FMLLog.getLogger());

        try
        {
            FileHandler var2 = new FileHandler("server.log", true);
            var2.setFormatter(var0);
            loggerLogManager.addHandler(var2);
        }
        catch (Exception var3)
        {
            loggerLogManager.log(Level.WARNING, "Failed to log to server.log", var3);
        }
    }
}

package org.Jacpack.TechUp.api.machines;

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
    private static final Logger log = Logger.getLogger("TechUp");

    public static boolean isHost(World world)
    {
        return !world.isRemote;
    }

    public static boolean isNotHost(World world)
    {
        return world.isRemote;
    }

    @SideOnly(Side.CLIENT)
    public static World getWorld() {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc != null) {
          return mc.theWorld;
        }
        return null;
      }

    public static boolean isObfuscated()
    {
        return !World.class.getSimpleName().equals("World");
    }

    public static void log(Level level, String msg, Object[] params) {
        String m = msg;

        for (int i = 0; i < params.length; i++) {
          if (params[i] != null) {
            m = m.replace(new StringBuilder().append("{").append(i).append("}").toString(), params[i].toString());
          }
        }
        log.log(level, m);
      }

    public static void logDebug(Level level, String msg, Object[] params) {
        if (TechUp.getVersion().endsWith("0")) {
          return;
        }
        String m = msg;

        for (int i = 0; i < params.length; i++) {
          if (params[i] != null) {
            m = m.replace(new StringBuilder().append("{").append(i).append("}").toString(), params[i].toString());
          }
        }
        log.log(level, m);
      }

    public static void logErrorAPI(String mod, Throwable error) {
        StringBuilder msg = new StringBuilder(mod);
        msg.append(" API error, please update your mods. Error: ").append(error);
        StackTraceElement[] stackTrace = error.getStackTrace();
        if (stackTrace.length > 0) {
          msg.append(", ").append(stackTrace[0]);
        }
        log.log(Level.SEVERE, msg.toString());
    }

    public static void logError(String msg, Throwable error) {
        log.log(Level.SEVERE, msg, error);
      }

      public static void logErrorFingerprint(String mod) {
        StringBuilder msg = new StringBuilder(mod);
        msg.append(" failed validation, terminating. Please re-download ").append(mod).append(".");
        log.log(Level.SEVERE, msg.toString());
      }

      static
      {
        log.setParent(FMLCommonHandler.instance().getFMLLogger());
      }
}

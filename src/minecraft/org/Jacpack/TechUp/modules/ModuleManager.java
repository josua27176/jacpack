package org.Jacpack.TechUp.modules;

import java.io.File;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.Jacpack.TechUp.api.machines.Game;

import cpw.mods.fml.common.Loader;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public abstract class ModuleManager
{
    public static final String MODULE_CONFIG_FILE_NAME = "modules.cfg";
    public static final String CATEGORY_MODULES = "modules";
    private static Map modules = new EnumMap(Module.class);
    private static Set loadedModules = EnumSet.noneOf(Module.class);
    private static Set unloadedModules = EnumSet.noneOf(Module.class);

    public static void preInit()
    {
        registerModule(Module.TANKS, new ModuleCore());
        Locale var0 = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        Module[] var2 = Module.values();
        int var3 = var2.length;
        for (int var4 = 0; var4 < var3; ++var4)
        {
            Module var5 = var2[var4];

            if (var5 != Module.TANKS && !isEnabled(null, var5))
            {
                unloadedModules.add(var5);
                Game.log(Level.FINER, "Module disabled: {0}", new Object[] {var5});
            }
            else
            {
                JACModule var6 = (JACModule)modules.get(var5);

                if (var6.canModuleLoad())
                {
                    loadedModules.add(var5);
                }
                else
                {
                    unloadedModules.add(var5);
                    var6.printLoadError();
                }
            }
        }
        Locale.setDefault(var0);
        Iterator var7 = loadedModules.iterator();

        while (var7.hasNext())
        {
            Module var8 = (Module)var7.next();
            preInit(var8);
        }
    }

    public static void init()
    {
        Iterator var0 = loadedModules.iterator();
        Module var1;

        while (var0.hasNext())
        {
            var1 = (Module)var0.next();
            initFirst(var1);
        }

        var0 = loadedModules.iterator();

        while (var0.hasNext())
        {
            var1 = (Module)var0.next();
            initSecond(var1);
        }
    }

    public static void postInit()
    {
        Iterator var0 = loadedModules.iterator();
        Module var1;

        while (var0.hasNext())
        {
            var1 = (Module)var0.next();
            postInit(var1);
        }

        var0 = unloadedModules.iterator();

        while (var0.hasNext())
        {
            var1 = (Module)var0.next();
            postInitNotLoaded(var1);
        }
    }

    private static boolean isEnabled(Configuration var0, Module var1)
    {
        boolean var2 = true;
        Property var3 = var0.get("modules", var1.toString().toLowerCase().replace('_', '.'), var2);
        return Boolean.valueOf(var3.value).booleanValue();
    }

    public static boolean isModuleLoaded(Module var0)
    {
        return loadedModules.contains(var0);
    }

    private static void registerModule(Module var0, JACModule var1)
    {
        modules.put(var0, var1);
    }

    private static void preInit(Module var0)
    {
        JACModule var1 = (JACModule)modules.get(var0);

        if (var1 != null)
        {
            boolean var2 = false;

            try
            {
                var2 = var1.getClass().getMethod("preInit", new Class[0]).getDeclaringClass() != JACModule.class;
            }
            catch (Exception var4)
            {
                ;
            }

            if (var2)
            {
                Game.log(Level.FINER, "Pre-Init Start: {0}", new Object[] {var1});
                var1.preInit();
                Game.log(Level.FINER, "Pre-Init Complete: {0}", new Object[] {var1});
            }
        }
    }

    private static void initFirst(Module var0)
    {
        JACModule var1 = (JACModule)modules.get(var0);

        if (var1 != null)
        {
            boolean var2 = false;

            try
            {
                var2 = var1.getClass().getMethod("initFirst", new Class[0]).getDeclaringClass() != JACModule.class;
            }
            catch (Exception var4)
            {
                ;
            }

            if (var2)
            {
                Game.log(Level.FINER, "Init-First Start: {0}", new Object[] {var1});
                var1.initFirst();
                Game.log(Level.FINER, "Init-First Complete: {0}", new Object[] {var1});
            }
        }
    }

    private static void initSecond(Module var0)
    {
        JACModule var1 = (JACModule)modules.get(var0);

        if (var1 != null)
        {
            boolean var2 = false;

            try
            {
                var2 = var1.getClass().getMethod("initSecond", new Class[0]).getDeclaringClass() != JACModule.class;
            }
            catch (Exception var4)
            {
                ;
            }

            if (var2)
            {
                Game.log(Level.FINER, "Init-Second Start: {0}", new Object[] {var1});
                var1.initSecond();
                Game.log(Level.FINER, "Init-Second Complete: {0}", new Object[] {var1});
            }
        }
    }

    private static void postInit(Module var0)
    {
        JACModule var1 = (JACModule)modules.get(var0);

        if (var1 != null)
        {
            boolean var2 = false;

            try
            {
                var2 = var1.getClass().getMethod("postInit", new Class[0]).getDeclaringClass() != JACModule.class;
            }
            catch (Exception var4)
            {
                ;
            }

            if (var2)
            {
                Game.log(Level.FINER, "Post-Init Start: {0}", new Object[] {var1});
                var1.postInit();
                Game.log(Level.FINER, "Post-Init Complete: {0}", new Object[] {var1});
            }
        }
    }

    private static void postInitNotLoaded(Module var0)
    {
        JACModule var1 = (JACModule)modules.get(var0);

        if (var1 != null)
        {
            boolean var2 = false;

            try
            {
                var2 = var1.getClass().getMethod("postInitNotLoaded", new Class[0]).getDeclaringClass() != JACModule.class;
            }
            catch (Exception var4)
            {
                ;
            }

            if (var2)
            {
                Game.log(Level.FINER, "Disabled-Init Start: {0}", new Object[] {var1});
                var1.postInitNotLoaded();
                Game.log(Level.FINER, "Disabled-Init Complete: {0}", new Object[] {var1});
            }
        }
    }
    
    public enum Module
    {
        TANKS;
    }

}

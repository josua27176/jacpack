package org.Jacpack.TechUp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;

import org.Jacpack.TechUp.TechUp;
import org.Jacpack.TechUp.util.TagFile.TagFile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.StringTranslate;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Config
 * 
 * Loads in all specified localizations for the mod
 * 
 * @author Alexbegt,DJP
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Config {
	static boolean[] reservedIds = new boolean[32768];
	
    static File configDir = null;
    static File configFile = null;
    static TagFile config = null;
    static Properties JACTranslateTable = null;
    static boolean autoAssign = true;

    public static void loadConfig()
    {
        config = new TagFile();
        InputStream is = TechUp.class.getResourceAsStream("/org/Jacpack/Techup/client/config/default.cfg");
        config.readStream(is);

        if (configDir == null)
        {
            File d = Loader.instance().getConfigDir();
            d = new File(d, "/JacPack/");
            d.mkdir();
            configDir = d;
            configFile = new File(d, "JacPack.cfg");
        }
        if (configFile.exists())
        {
            config.readFile(configFile);
        }

        config.commentFile("JacPack Configuration");
        String key;
        Iterator iterator;

        for (iterator = config.query("blocks.%.%.id").iterator(); iterator.hasNext(); reservedIds[config.getInt(key)] = true)
        {
            key = (String)iterator.next();
        }

        for (iterator = config.query("items.%.%.id").iterator(); iterator.hasNext(); reservedIds[config.getInt(key) + 256] = true)
        {
            key = (String)iterator.next();
        }

        if (JACTranslateTable == null)
        {
            JACTranslateTable = new Properties();
        }

        try
        {
            JACTranslateTable.load(TechUp.class.getResourceAsStream("/org/Jacpack/Techup/client/config/JacPack.lang"));
            File trf = new File(configDir, "JacPack.lang");

            if (trf.exists())
            {
                FileInputStream ifs = new FileInputStream(trf);
                JACTranslateTable.load(ifs);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        iterator = JACTranslateTable.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry var6 = (Entry)iterator.next();
            LanguageRegistry.instance().addStringLocalization((String)var6.getKey(), (String)var6.getValue());
        }

        autoAssign = config.getInt("settings.core.autoAssign") > 0;
        config.addInt("settings.core.autoAssign", 0);
        config.commentTag("settings.core.autoAssign", "Automatically remap conflicting IDs.\nWARNING: May corrupt existing worlds");
    }

    public static void saveConfig()
    {
        config.saveFile(configFile);

        try
        {
            File trf = new File(configDir, "JacPack.lang");
            FileOutputStream os = new FileOutputStream(trf);
            JACTranslateTable.store(os, "JacPack Language File");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void addName(String tag, String name)
    {
        if (JACTranslateTable.get(tag) == null)
        {
            JACTranslateTable.put(tag, name);
            LanguageRegistry.instance().addStringLocalization(tag, name);
        }
    }

    public static void addName(Block bl, String name)
    {
        addName(bl.getBlockName() + ".name", name);
    }

    private static void die(String msg)
    {
        throw new RuntimeException("TechUp: " + msg);
    }

    public static int getItemID(String name)
    {
        int cid = config.getInt(name);

        if (Item.itemsList[256 + cid] == null)
        {
            return cid;
        }
        else if (!autoAssign)
        {
            die(String.format("ItemID %d exists, autoAssign is disabled.", new Object[] {Integer.valueOf(cid)}));
            return -1;
        }
        else
        {
            for (int i = 1024; i < 32000; i++)
            {
                if (!reservedIds[i] && Item.itemsList[i] == null)
                {
                    config.addInt(name, i - 256);
                    return i;
                }
            }

            die("Out of available ItemIDs, could not autoassign!");
            return -1;
        }
    }

    public static int getBlockID(String name)
    {
        int cid = config.getInt(name);

        if (Block.blocksList[cid] == null)
        {
            return cid;
        }
        else if (!autoAssign)
        {
            die(String.format("BlockID %d occupied by %s, autoAssign is disabled.", new Object[] {Integer.valueOf(cid), Block.blocksList[cid].getClass().getName()}));
            return -1;
        }
        else
        {
            for (int i = 255; i >= 20; i--)
            {
                if (!reservedIds[i] && Block.blocksList[i] == null)
                {
                    config.addInt(name, i);
                    return i;
                }
            }

            die("Out of available BlockIDs, could not autoassign!");
            return -1;
        }
    }

    public static int getInt(String name)
    {
        return config.getInt(name);
    }

    public static String getString(String name)
    {
        return config.getString(name);
    }
}
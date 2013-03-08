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
	
	static Properties hmcTranslateTable = null;
	private static Config instance;
	static File configDir = null;
    static File configFile = null;
    static boolean[] reservedIds = new boolean[32768];
    static TagFile config = null;
    static boolean autoAssign = true;

	/***
	 * Loads in all the localization files from the Localizations library class
	 */
	public static void loadLanguages() {
		config = new TagFile();
        InputStream var0 = TechUp.class.getResourceAsStream("/net/TechUp/client/lang/default.cfg");
        config.readStream(var0);
        File var1;
		
		if (configDir == null)
        {
            var1 = Loader.instance().getConfigDir();
            var1 = new File(var1, "/TechUp/");
            var1.mkdir();
            configDir = var1;
            configFile = new File(var1, "TechUp.cfg");
        }

        if (configFile.exists())
        {
            config.readFile(configFile);
        }

        config.commentFile("TechUp Configuration");
        String var2;
        Iterator var4;

        for (var4 = config.query("blocks.%.%.id").iterator(); var4.hasNext(); reservedIds[config.getInt(var2)] = true)
        {
            var2 = (String)var4.next();
        }

        for (var4 = config.query("items.%.%.id").iterator(); var4.hasNext(); reservedIds[config.getInt(var2) + 256] = true)
        {
            var2 = (String)var4.next();
        }
		
		if (hmcTranslateTable == null)
        {
            hmcTranslateTable = new Properties();
        }

        try
        {
            hmcTranslateTable.load(TechUp.class.getResourceAsStream("/net/TechUp/client/lang/TechUp.lang"));
            var1 = new File(configDir, "TechUp.lang");

            if (var1.exists())
            {
                FileInputStream var5 = new FileInputStream(var1);
                hmcTranslateTable.load(var5);
            }
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
        var4 = hmcTranslateTable.entrySet().iterator();

        while (var4.hasNext())
        {
            Entry var6 = (Entry)var4.next();
            LanguageRegistry.instance().addStringLocalization((String)var6.getKey(), (String)var6.getValue());
        }
	}
	
	public static Config getInstance()
    {
        if (instance == null)
        {
            instance = new Config();
        }

        return instance;
    }
	
	public static void addName(String var0, String var1)
    {
        if (hmcTranslateTable.get(var0) == null)
        {
            hmcTranslateTable.put(var0, var1);
            LanguageRegistry.instance().addStringLocalization(var0, var1);
        }
    }

    public static void addName(Block var0, String var1)
    {
        addName(var0.getBlockName() + ".name", var1);
    }

    private static void die(String var0)
    {
        throw new RuntimeException("TechUp: " + var0);
    }
    
    public static int getMaxTankSize()
    {
        return 9;//getInt("tweaks.blocks.PhazonTank.size");
    }

    public static boolean allowTankStacking()
    {
        return true;
    }

    public static int getItemID(String var0)
    {
        int var1 = config.getInt(var0);

        if (Item.itemsList[256 + var1] == null)
        {
            return var1;
        }
        else if (!autoAssign)
        {
            die(String.format("ItemID %d exists, autoAssign is disabled.", new Object[] {Integer.valueOf(var1)}));
            return -1;
        }
        else
        {
            for (int var2 = 1024; var2 < 32000; ++var2)
            {
                if (!reservedIds[var2] && Item.itemsList[var2] == null)
                {
                    config.addInt(var0, var2 - 256);
                    return var2;
                }
            }

            die("Out of available ItemIDs, could not autoassign!");
            return -1;
        }
    }

    public static int getBlockID(String var0)
    {
        int var1 = config.getInt(var0);

        if (Block.blocksList[var1] == null)
        {
            return var1;
        }
        else if (!autoAssign)
        {
            die(String.format("BlockID %d occupied by %s, autoAssign is disabled.", new Object[] {Integer.valueOf(var1), Block.blocksList[var1].getClass().getName()}));
            return -1;
        }
        else
        {
            for (int var2 = 255; var2 >= 20; --var2)
            {
                if (!reservedIds[var2] && Block.blocksList[var2] == null)
                {
                    config.addInt(var0, var2);
                    return var2;
                }
            }

            die("Out of available BlockIDs, could not autoassign!");
            return -1;
        }
    }

    public static int getInt(String var0)
    {
        return config.getInt(var0);
    }

    public static String getString(String var0)
    {
        return config.getString(var0);
    }
	
	public static String translate(String var0)
    {
        return getInstance().translate_do(var0);
    }

    private String translate_do(String var1)
    {
        String var2 = StringTranslate.getInstance().getCurrentLanguage();
        return var1;
    }
	
	public static void saveLanguages() {
		config.saveFile(configFile);
		
        try
        {
            File var0 = new File(configDir, "TechUp.lang");
            FileOutputStream var1 = new FileOutputStream(var0);
            hmcTranslateTable.store(var1, "TechUp Language File");
        }
        catch (IOException var2)
        {
            var2.printStackTrace();
        }
	}

}
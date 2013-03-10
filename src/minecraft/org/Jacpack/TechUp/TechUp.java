package org.Jacpack.TechUp;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

import org.Jacpack.TechUp.achievement.AchevementHandler;
import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.crafting.CraftingHandler;
import org.Jacpack.TechUp.gui.GuiHandler;
import org.Jacpack.TechUp.item.ModItems;
import org.Jacpack.TechUp.modules.ModuleManager;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;
import org.Jacpack.TechUp.tileentitys.TileEntityHandler;
import org.Jacpack.TechUp.util.Config;
import org.Jacpack.TechUp.util.misc.*;
import org.Jacpack.TechUp.util.network.TechUpPacketHandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION
)
@NetworkMod(
		channels={Reference.CHANNEL_NAME},
		clientSideRequired=true,
		serverSideRequired=false,
		packetHandler=TechUpPacketHandler.class
)
public class TechUp {
	
	@Instance(
			Reference.MOD_ID
	)
	public static TechUp instance;
	
	@SidedProxy(
			clientSide = Reference.CLIENT_PROXY_CLASS,
			serverSide = Reference.SERVER_PROXY_CLASS
	)
    public static CommonProxy proxy;
	
	public static String getVersion()
    {
        return Reference.VERSION;
    }
	
	public static TechUp getMod()
    {
        return instance;
    }
	
	public TechUp() {
		Config.StaffJACCapeList.add("alexbegt");
		Config.StaffJACCapeList.add("Zmaster587");
		Config.StaffJACCapeList.add("zmaster587");
		Config.StaffJACCapeList.add("xFinityPro");
		Config.StaffJACCapeList.add(Minecraft.getMinecraft().session.username);
		//Config.StaffJACCapeList.add("xFinityPro");
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
    	Config.loadConfig();
    	
    	ModuleManager.preInit();
    	
	}

	@Init
	public void load(FMLInitializationEvent event) {
		
		NetworkRegistry.instance().registerGuiHandler(TechUp.getMod(), new GuiHandler());
		
		GameHelper.init();
		
		proxy.registerRenderers();
		
		proxy.initClient();
		
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
		Config.saveConfig();
		
	}

}
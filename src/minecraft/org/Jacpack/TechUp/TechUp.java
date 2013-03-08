package org.Jacpack.TechUp;

import net.minecraft.block.Block;

import org.Jacpack.TechUp.util.Config;
import org.Jacpack.TechUp.util.misc.*;

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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
		modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION
)
@NetworkMod(
		clientSideRequired=true,
		serverSideRequired=false
)
public class TechUp {
	
	@Instance(Reference.MOD_ID)
	public static TechUp instance;
	
	@SidedProxy(
			clientSide = Reference.CLIENT_PROXY_CLASS,
			serverSide = Reference.SERVER_PROXY_CLASS
	)
    public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
    	Config.loadConfig();
    	
	}

	@Init
	public void load(FMLInitializationEvent event) {
		
		proxy.registerRenderers();
		
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
		Config.saveConfig();
		
	}

}
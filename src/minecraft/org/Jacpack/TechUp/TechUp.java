package org.Jacpack.TechUp;

import net.minecraft.block.Block;
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

@Mod(modid="TechUp", name="TechUp", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class TechUp {

	// The instance of your mod that Forge uses.
	@Instance("TechUp")
	public static TechUp instance;
	
	@SidedProxy(
			clientSide="tutorial.generic.client.ClientProxy",
			serverSide="tutorial.generic.CommonProxy"
	)
	public static CommonProxy proxy;
	
	public static Block ores;
	public static int oreBlockId;
	private static final String[] oreBlockNames = {
		"Copper","Tin"
	};

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
		//Load the config and stuff
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		oreBlockId = config.getBlock("oreBlocks", 500).getInt();
		
		config.save();
	}

	@Init
	public void load(FMLInitializationEvent event) {
		registerBlocks();
		registerRecipies();


		proxy.registerRenderers();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	public static void registerBlocks()
	{
		ores = new CondensedOreBlock(oreBlockId,0);
		MinecraftForge.setBlockHarvestLevel(ores, "pickaxe", 1);
		GameRegistry.registerBlock(ores, "oreBlocks");
		
		//Name all the different ores
		for(int i = 0; i < oreBlockNames.length; i++)
		{
			LanguageRegistry.addName(ores, oreBlockNames[i]);
			
			//Adds our ores to the forge dictionary
			OreDictionary.registerOre("ore" + oreBlockNames[i], new ItemStack(ores,1, i));
		}
	}
	
	public static void registerRecipies()
	{
	}

}
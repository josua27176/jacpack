package org.Jacpack.TechUp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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

@Mod(modid="TechUp", name="TechUp", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class TechUp {

        // The instance of your mod that Forge uses.
        @Instance("TechUp")
        public static TechUp instance;
       
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="tutorial.generic.client.ClientProxy", serverSide="tutorial.generic.CommonProxy")
        public static CommonProxy proxy;
       
        //Unfinished
        public final static Block TinOre = new Block(500, 1, Material.rock)
        	.setHardness(0.5F).setStepSound(Block.soundStoneFootstep)
        	.setBlockName("TinOre").setCreativeTab(CreativeTabs.tabBlock);
        
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @Init
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
        }
       
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
        }
}
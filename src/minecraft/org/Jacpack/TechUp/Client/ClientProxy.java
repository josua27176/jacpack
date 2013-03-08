package org.Jacpack.TechUp.client;

import net.minecraftforge.client.MinecraftForgeClient;

import org.Jacpack.TechUp.util.misc.CommonProxy;
import org.Jacpack.TechUp.util.misc.Reference;

public class ClientProxy extends CommonProxy {
       
        @Override
        public void registerRenderers() {
        	MinecraftForgeClient.preloadTexture(Reference.SPRITE_SHEET_LOCATION + Reference.BLOCK_SPRITE_SHEET);
            MinecraftForgeClient.preloadTexture(Reference.SPRITE_SHEET_LOCATION + Reference.ITEM_SPRITE_SHEET);
        }
       
}
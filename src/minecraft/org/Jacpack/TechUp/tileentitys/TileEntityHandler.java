package org.Jacpack.TechUp.tileentitys;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileBlastFurnace.class, "TileBlastFurnace");
	}
	
}

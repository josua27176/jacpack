package org.Jacpack.TechUp;

import net.minecraft.block.BlockOre;

public class CondensedOreBlock extends BlockOre {

	
	public CondensedOreBlock(int id, int texture) {
		super(id, texture);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		return 16 + meta;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCK_PNG;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
}

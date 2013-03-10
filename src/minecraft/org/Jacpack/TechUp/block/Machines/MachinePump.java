package org.Jacpack.TechUp.block.Machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.Jacpack.TechUp.block.AbstractMachineBlock;

public class MachinePump extends AbstractMachineBlock {

	public MachinePump(int id, Material material) {
		super(id, material);
	}

	@Override
	public boolean onBlockActivated(World world, int posX, int posY, int posZ,
			EntityPlayer player, int parX, float mouseX, float mouseY,
			float mouseZ) {
		
		return false;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		
		return 0;
	}
	
}

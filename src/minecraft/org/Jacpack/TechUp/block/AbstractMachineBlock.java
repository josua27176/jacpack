package org.Jacpack.TechUp.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class AbstractMachineBlock extends Block {

	public AbstractMachineBlock(int id, Material material) {
		super(id, material);
		this.setHardness(2.0F);
		this.setLightOpacity(1);
		this.setLightValue(0.0F);
		this.setResistance(0.5F);
		this.setTextureFile(""); //TODO: add texture file for machine blocks
		this.isBlockContainer = true;
	}

	public void onBlockPlacedBy(World world, int PosX, int PosY, int PosZ, EntityPlayer entity)
	{
		
		int direction = MathHelper.floor_double( (double)(entity.rotationYaw * 4.0F /360) + 0.5D) & 3;
		int metaBuffer = world.getBlockMetadata(PosX, PosY, PosZ);
		
		//Correct the direction so it stores the opposite of the direction the player is facing
		direction += 3;
		direction %= 4;
		
		int metaData = world.getBlockMetadata(PosX, PosY, PosZ) >> 2;
		
		world.setBlockAndMetadataWithUpdate(PosX, PosY, PosZ, this.blockID, direction | metaBuffer << 2, true);
	}
	
	
	public abstract boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int parX, float mouseX, float mouseY, float mouseZ );
	
	public abstract int getBlockTextureFromSideAndMetadata(int side, int meta);
}
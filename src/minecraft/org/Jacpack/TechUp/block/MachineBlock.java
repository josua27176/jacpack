package org.Jacpack.TechUp.block;

import org.Jacpack.TechUp.tileEntity.AbstactTileEntityMachine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineBlock extends Block {

	public MachineBlock(int id, Material material) {
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
	
	
	public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int parX, float mouseX, float mouseY, float mouseZ )
	{
		//Make sure to check only against the server world
		if(!world.isRemote)
		{
			AbstactTileEntityMachine tile = (AbstactTileEntityMachine) world.getBlockTileEntity(posX, posY, posZ);
			if(tile != null)
			{
				
			}
		}
		return true;
	}
}

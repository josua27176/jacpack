package org.Jacpack.TechUp.tileEntity.machine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;

import org.Jacpack.TechUp.tileEntity.AbstactTileEntityMachine;
import org.lwjgl.util.vector.Vector3f;

public class TilePump extends AbstactTileEntityMachine {

	private int liquidAmt;
	
	TilePump()
	{
		this.inv = new ItemStack[1];
	}
	
	public int getLiquidAmount() { return liquidAmt; }
	public void setLiquidAmount(int amt) { liquidAmt = amt; }
	
	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		Integer invSide;
		if((invSide = invMaps.get(side)) != null)
			return inv[invSide].getMaxStackSize();
		return 0;
	}

	@Override
	public String getInvName() {
		return "tile.tileentity.pump";
	}

	@Override
	public void activateMachine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivateMachine() {
		// TODO Auto-generated method stub
		
	}

	@Override	
	public void updateEntity() {
		if(!active)
			return;
		//TODO: make pump respect power
		
		
	}
	
	private BlockFluid findLiquidUnder()
	{
		BlockFluid fluid = null;
		int depth = this.yCoord;
		
		for(depth = this.yCoord; fluid == null && depth > 0; depth--)
		{
			int id = this.worldObj.getBlockId(this.xCoord, depth, this.zCoord);
			Material mat = this.worldObj.getBlockMaterial(this.xCoord, depth, this.zCoord);
			Block block = new Block(id, mat);
			if(block instanceof BlockFluid)
				fluid = (BlockFluid) block;
		}
		return fluid;
	}
	
	private boolean isLiquidConnected(BlockFluid block1, BlockFluid block2)
	{
		Vector3f posCheck = new Vector3f(block1., y, z)
		//Check north
		if()
	}
}

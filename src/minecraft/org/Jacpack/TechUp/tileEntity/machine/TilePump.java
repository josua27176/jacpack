package org.Jacpack.TechUp.tileEntity.machine;

import java.util.Vector;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.old.LiquidFilter;
import org.Jacpack.TechUp.tileEntity.AbstactTileEntityMachine;
import org.lwjgl.util.vector.Vector3f;

public class TilePump extends AbstactTileEntityMachine {

	private int liquidAmt;
	
	private final int Capacity = 10; //Internal storage is 10 buckets
	private final int liquidCheckDistance = 80; //Check in a radius of about 5 chunks
	private int[] currPos = new int[3];
	
	public TilePump()
	{
		this.inv = new ItemStack[2];
		liquidAmt = 0;
		currPos[0] = xCoord + liquidCheckDistance;
		currPos[1] = worldObj.getActualHeight();
		currPos[2] = zCoord + liquidCheckDistance;
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
		if(!active && !canSuck())
			return;
		//TODO: make pump respect power
		Vector<Integer> liquidUnder = findLiquidUnder();
		
		if(liquidUnder == null)
			return;
		
		Vector<Integer> pos2  = new Vector<Integer>(3);
		
		for(int i=0; i < 3; i++)
			pos2.add(i, currPos[i]);
		
		while(currPos[1] > 0)
		{
			if(LiquidFilter.liquidMaterials.isLiquid(worldObj.getBlockMaterial(currPos[0], currPos[1], currPos[2])) &&
					JACTools.isBlockConnectedByMat(worldObj, liquidUnder, pos2, LiquidFilter.liquidMaterials.getLiquidMaterials(), liquidCheckDistance))
			{
				worldObj.setBlock(currPos[0], currPos[1], currPos[2], 0);
				liquidAmt++;
				
				//Debug:
				System.out.println("Internal Amount: " + liquidAmt);
				
				break;
			}
			
			currPos[0]--;
			if(currPos[0] <= xCoord - liquidCheckDistance)
			{
				currPos[0] = xCoord + liquidCheckDistance;
				currPos[2]--;
			}
			if(currPos[2] <= zCoord - liquidCheckDistance)
			{
				currPos[2] = zCoord + liquidCheckDistance;
				currPos[1]--;
			}
			
		}
		consumePower();
	}
	
	private void consumePower()
	{
		return;
	}
	
	private boolean canSuck()
	{
		return liquidAmt < Capacity;
	}
	
	private Vector<Integer> findLiquidUnder()
	{
		int depth = this.yCoord;
		
		for(depth = this.yCoord; depth > 0; depth--)
		{
			Material mat = this.worldObj.getBlockMaterial(this.xCoord, depth, this.zCoord);
			if(LiquidFilter.liquidMaterials.isLiquid(mat))
			{
				Vector vec = new Vector<Integer>();
				vec.add(this.xCoord);
				vec.add(depth);
				vec.add(this.zCoord);
				return vec;
			}
		}
		return null;
	}

}

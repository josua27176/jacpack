package org.Jacpack.TechUp.tileEntity.machine;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidDictionary;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.old.LiquidFilter;
import org.Jacpack.TechUp.tileEntity.AbstactTileEntityMachine;
import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TilePump extends AbstactTileEntityMachine {

	private int liquidAmt = 0;

	private int heldId = -1;
	private final int Capacity = 1000; //Internal storage is 10 buckets
	private final int liquidCheckDistance = 7; //80; //Check in a radius of about 5 chunks

	Stack<ArrayList<Integer>> suckStack = new Stack<ArrayList<Integer>>();
	int[] targetBlock = new int[3];
	int[] liquidUnder = new int[3];
	int currDir = 1;

	public TilePump()
	{
		this.inv = new ItemStack[2];
		this.liquidAmt = 0;
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
		Stack<ArrayList<Integer>> liquidStack = new Stack<ArrayList<Integer>>();

		liquidUnder = findLiquidUnder();
		if (liquidUnder == null)
			return;

		int[] buffer = liquidUnder;
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.add(liquidUnder[0]); tmp.add(liquidUnder[1]); tmp.add(liquidUnder[2
		                                                                      ]);
		liquidStack.add(tmp);


		if(suckStack.isEmpty())
			buffer = getNextWater(buffer, liquidStack, 0);
		else
		{
			do
			{
				{
					buffer[0] = suckStack.peek().get(0);
					buffer[1] = suckStack.peek().get(1);
					buffer[2] = suckStack.pop().get(2);
				}
			}
			while(!LiquidFilter.liquidMaterials.isFlowing(worldObj.getBlockId(buffer[0], buffer[1], buffer[2]))  && 
					this.worldObj.getBlockId(buffer[0], buffer[1], buffer[2]) != this.heldId 
					&& this.heldId != -1 && !suckStack.isEmpty());
		}



		if( worldObj.getBlockId(buffer[0], buffer[1], buffer[2]) != this.heldId || (this.heldId == -1 && worldObj.getBlockId(buffer[0], buffer[1], buffer[2]) != 0))
		{
			if(!LiquidFilter.liquidMaterials.isFlowing(worldObj.getBlockId(buffer[0], buffer[1], buffer[2])))
			{
				liquidAmt++;
				this.heldId = this.worldObj.getBlockId(buffer[0], buffer[1], buffer[2]);
			}

			worldObj.setBlock(buffer[0], buffer[1], buffer[2], 0);
		}

	}

	private int[] getNextWater(int[] buffer, Stack<ArrayList<Integer>> liquidStack, int maxStack)
	{
		currDir = 1;
		for(int i = 1; ForgeDirection.getOrientation(i) != ForgeDirection.UNKNOWN; i++)
		{
			buffer[0] += ForgeDirection.getOrientation(i).offsetX;
			buffer[1] += ForgeDirection.getOrientation(i).offsetY;
			buffer[2] += ForgeDirection.getOrientation(i).offsetZ;

			ArrayList<Integer> tmp = new ArrayList<Integer>();
			tmp.add(buffer[0]);
			tmp.add(buffer[1]);
			tmp.add(buffer[2]);

			if(LiquidFilter.liquidMaterials.isLiquid(worldObj.getBlockId(buffer[0], buffer[1], buffer[2]))
					&& !liquidStack.contains(tmp)
					&& JACTools.getDistance(buffer[0], buffer[2], this.xCoord, this.zCoord) < liquidCheckDistance)
			{
				suckStack.add(tmp);
				liquidStack.push(tmp);
				return getNextWater(buffer, liquidStack, maxStack);
			}
			else if(liquidStack.size() > maxStack)
			{
				maxStack = liquidStack.size();
				targetBlock = buffer;
				buffer[0] = liquidStack.peek().get(0);
				buffer[1] = liquidStack.peek().get(1);
				buffer[2] = liquidStack.peek().get(2);
			}
			else
			{
				buffer[0] -= ForgeDirection.getOrientation(i).offsetX;
				buffer[1] -= ForgeDirection.getOrientation(i).offsetY;
				buffer[2] -= ForgeDirection.getOrientation(i).offsetZ;
			}

		}
		return targetBlock;
	}

	private void consumePower()
	{
		return;
	}

	private boolean canSuck()
	{
		return liquidAmt < Capacity;
	}

	private int[] findLiquidUnder()
	{
		int depth = this.yCoord;

		for(depth = this.yCoord; depth > 0; depth--)
		{
			int id = this.worldObj.getBlockId(this.xCoord, depth, this.zCoord);
			if(LiquidFilter.liquidMaterials.isLiquid(id))
			{

				int[] ret = {this.xCoord, depth, this.zCoord};
				return ret;
			}
		}
		return null;
	}

	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		NBTTagList stateList = new NBTTagList();
		NBTTagInt tag = new NBTTagInt("CurrentCapacity", liquidAmt);
		stateList.appendTag(tag);

		tagCompound.setTag("StateList", stateList);
	}

	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("StateList");
		liquidAmt = ((NBTTagInt)tagList.tagAt(0)).data;
	}

}

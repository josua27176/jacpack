package org.Jacpack.TechUp.tileEntity;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public abstract class AbstactTileEntityMachine extends TileEntity implements IInventory, ISidedInventory {

	protected ItemStack[] inv;
	
	
	protected HashMap<ForgeDirection,Integer> invMaps = new HashMap<ForgeDirection,Integer>(6);
	
	//Implementing IInventory
	@Override 
	public int getSizeInventory() {
		return inv.length;
	};
	
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		//If we try to retrieve a non existant slot let's return null rather than null pointer shall we?
		return slot >= inv.length ? null : inv[slot];
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inv[slot] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if(stack != null)
		{
			if(stack.stackSize <= amt)
				setInventorySlotContents(slot, null);
			else
			{
				stack = stack.splitStack(amt);
				if(stack.stackSize == 0)
					setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}
	
	
	//Return the same stack because we want to keep stuff in the inventory
	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return getStackInSlot(slot);
	}
	
	//Return 64
	@Override
	public int getInventoryStackLimit() { return 64; }
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
				player.getDistanceSq(xCoord + 0.5, yCoord, zCoord) < 64;
	}
	
	@Override
	public void openChest() {}
	
	@Override
	public void closeChest() {}
	
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		
		//Handle Inv data
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length)
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
		}
		
		tagList = tagCompound.getTagList("InvSides");	
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagInt tag = (NBTTagInt) tagList.tagAt(i);
			invMaps.put(ForgeDirection.getOrientation(i), tag.data);
		}
		
	}
	
	
	// Write Inventory and Side to NBT
	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		
		NBTTagList itemList = new NBTTagList("Inventory");
		
		for(int i = 0; i < inv.length; i++)
		{
			ItemStack stack = inv[i];
			
			if(stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		
		
		NBTTagList sideList = new NBTTagList();
		for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++)
		{
			NBTTagCompound tag = new NBTTagCompound();
			if(invMaps.get(ForgeDirection.getOrientation(i)) != null)
				tag.setInteger("Side",invMaps.get(ForgeDirection.getOrientation(i)));
			else
				tag.setInteger("Side",-1);
			itemList.appendTag(tag);
		}
		
		tagCompound.setTag("Inventory", itemList);
		tagCompound.setTag("InvSides", sideList);
	}
	
	//Implementing sided Inventory
	
	//Get the sides from the block's data
	@Override
	public int getStartInventorySide(ForgeDirection side)
	{
		return invMaps.get(side);
	}
	
	public abstract String getInvName();
	
	protected int powerLevel; //How much power the block contains
	protected float progress; //Progress of it's operation [0,1)
	protected boolean active; //If the machine is turned on
	protected ForgeDirection orientation; //Direction block is facing
	
	//Accessors
	protected int getPowerlevel() {return powerLevel;}
	protected float getProgress() {return progress;}
	protected boolean getActive() {return active;}
	
	//Mutators
	void setPowerlevel(int pow) { this.powerLevel = pow;}
	void setProgress(float prog) {this.progress = prog;}
	void setActive(boolean state) { this.active = state;}
	
	//Toggle machine on and off
	void toggleMachine()
	{
		if(active)
			activateMachine();
		else
			deactivateMachine();
		active = !active;
	}
	
	
	//Make sure player is within 7 blocks of the machine
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this &&
				player.getDistanceSq(this.xCoord, this.yCoord, this.zCoord) < 49;
	}
	
	public abstract void activateMachine();
	public abstract void deactivateMachine();
	public abstract void updateEntity();
	
	//Set the inventory side based on current config
	public void setInvSide(int inv,ForgeDirection sides)
	{
		invMaps.put(sides, inv);
	}
}
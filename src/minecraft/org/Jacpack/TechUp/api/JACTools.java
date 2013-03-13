package org.Jacpack.TechUp.api;

import java.util.ArrayList;
import java.util.Random;

import java.util.Iterator;

import org.Jacpack.TechUp.api.inventory.ISpecialInventory;
import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.api.machines.LiquidFilter;

import net.minecraft.nbt.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;

public class JACTools {
	
	private static Random rand = new Random();

    public static Random getRand()
    {
        return rand;
    }
    
    public static ItemStack depleteItem(ItemStack stack) {
        if (stack.stackSize == 1) {
          return stack.getItem().getContainerItemStack(stack);
        }
        stack.splitStack(1);
        return stack;
    }
    
    public static ItemStack moveItemStack(ItemStack stack, IInventory dest)
    {
      if (stack == null) {
        return null;
      }
      stack = stack.copy();
      if (dest == null) {
        return stack;
      }
      if ((dest instanceof ISpecialInventory)) {
        int used = ((ISpecialInventory)dest).addItem(stack, true, ForgeDirection.UNKNOWN);
        if (used >= stack.stackSize) {
          return null;
        }
        stack.stackSize -= used;
        return stack;
      }
      boolean movedItem;
      do
      {
        movedItem = false;

        for (int ii = 0; ii < dest.getSizeInventory(); ii++) {
          ItemStack destStack = dest.getStackInSlot(ii);
          if ((destStack != null) && (isItemEqual(destStack, stack))) {
            destStack = destStack.copy();
            int maxStack = Math.min(destStack.getMaxStackSize(), dest.getInventoryStackLimit());
            int room = maxStack - destStack.stackSize;
            if (room > 0) {
              int move = Math.min(room, stack.stackSize);
              destStack.stackSize += move;
              stack.stackSize -= move;
              dest.setInventorySlotContents(ii, destStack);
              if (stack.stackSize <= 0) {
                return null;
              }
              movedItem = true;
            }
          }
        }
        if (!movedItem)
          for (int ii = 0; ii < dest.getSizeInventory(); ii++) {
            ItemStack destStack = dest.getStackInSlot(ii);
            if (destStack == null) {
              if (stack.stackSize > dest.getInventoryStackLimit()) {
                dest.setInventorySlotContents(ii, stack.splitStack(dest.getInventoryStackLimit()));
              } else {
                dest.setInventorySlotContents(ii, stack);
                return null;
              }
              movedItem = true;
            }
          }
      }
      while (movedItem);
      return stack;
    }
	
	public static LiquidStack getLiquidInContainer(ItemStack stack) {
	    return LiquidContainerRegistry.getLiquidForFilledItem(stack);
	  }
	
	public static void writeInvToNBT(IInventory inv, String tag, NBTTagCompound data) {
		NBTTagList list = new NBTTagList();
	    for (byte slot = 0; slot < inv.getSizeInventory(); slot = (byte)(slot + 1)) {
	      ItemStack stack = inv.getStackInSlot(slot);
	      if (stack != null) {
	    	NBTTagCompound itemTag = new NBTTagCompound();
	        itemTag.setByte("Slot", slot);
	        writeItemToNBT(stack, itemTag);
	        list.appendTag(itemTag);
	      }
	    }
	    data.setTag(tag, list);
	  }

	public static void readInvFromNBT(IInventory inv, String tag, NBTTagCompound data) {
		NBTTagList list = data.getTagList(tag);
	    for (byte entry = 0; entry < list.tagCount(); entry = (byte)(entry + 1)) {
	      NBTTagCompound itemTag = (NBTTagCompound)list.tagAt(entry);
	      int slot = itemTag.getByte("Slot");
	      if ((slot >= 0) && (slot < inv.getInventoryStackLimit())) {
	    	ItemStack stack = readItemFromNBT(itemTag);
	        inv.setInventorySlotContents(slot, stack);
	      }
	    }
	  }

	public static void writeItemToNBT(ItemStack stack, NBTTagCompound data) {
	    if ((stack == null) || (stack.stackSize <= 0)) {
	      return;
	    }
	    if (stack.stackSize > 127) {
	      stack.stackSize = 127;
	    }
	    stack.writeToNBT(data);
	}

	public static ItemStack readItemFromNBT(NBTTagCompound data) {
	    int itemID = data.getShort("id");
	    int stackSize = data.getShort("stackSize");
	    int damage = data.getShort("Damage");

	    if (stackSize <= 0) {
	      stackSize = data.getByte("Count");
	    }

	    if ((itemID <= 0) || (stackSize <= 0)) {
	      return null;
	    }

	    ItemStack stack = new ItemStack(itemID, stackSize, damage);

	    if (data.hasKey("tag")) {
	      stack.stackTagCompound = data.getCompoundTag("tag");
	    }

	    return stack.getItem() != null ? stack : null;
	  }
	
	public static int getItemBurnTime(ItemStack stack)
	  {
	    try
	    {
	      if (stack == null) {
	        return 0;
	      }

	      int itemID = stack.getItem().itemID;

	      if (((stack.getItem() instanceof ItemBlock)) && (Block.blocksList[itemID] != null)) {
	    	Block block = Block.blocksList[itemID];

	        String name = block.getBlockName();
	        if ((name != null) && (name.contains("blockScaffold"))) {
	          return 0;
	        }
	      }

	      LiquidStack liquid = getLiquidInContainer(stack);
	      if (LiquidFilter.LAVA.isLiquidEqual(liquid)) {
	        return liquid.amount;
	      }

	      if ((itemID == Item.coal.itemID) && (stack.getItemDamage() == 0) && (!isSynthetic(stack))) {
	        return 3200;
	      }

	      if (itemID == Item.blazeRod.itemID) {
	        return 800;
	      }

	      String name = stack.getItem().getItemName();
	      if ((name != null) && (name.contains("itemScrap"))) {
	        return 0;
	      }

	      return TileEntityFurnace.getItemBurnTime(stack);
	    } catch (Exception ex) {
	      Game.logError("Error in Fuel Handler! Is some mod creating items that are not compliant with standards?", ex);
	    }
	    return 0;
	  }
	
	public static boolean isSynthetic(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
	    if ((nbt != null) && (nbt.hasKey("synthetic"))) return true;
	    return false;
	  }
	
	public static boolean isOreClass(ItemStack itemstack, String string) {
		
		ArrayList array = OreDictionary.getOres(string);
		Iterator iterator = array.iterator();
		ItemStack itemstack1;
		
		do {
			if(!iterator.hasNext())
			{
				return false;
			}
			
			itemstack1 = (ItemStack)iterator.next();
		}
		while (!isItemEqual(itemstack1, itemstack));
		
		return true;
	}
	
	public static String translate(String string)
    {
        return translate_do(string);
    }

    private static String translate_do(String string)
    {
        return string;
    }
    
    public static boolean isItemEqual(ItemStack a, ItemStack b)
    {
      if ((a == null) || (b == null)) {
        return false;
      }
      if (a.itemID != b.itemID) {
        return false;
      }
      if ((a.stackTagCompound != null) && (!a.stackTagCompound.equals(b.stackTagCompound))) {
        return false;
      }
      if (a.getHasSubtypes()) {
        if ((a.getItemDamage() == -1) || (b.getItemDamage() == -1)) {
          return true;
        }
        if (a.getItemDamage() != b.getItemDamage()) {
          return false;
        }
      }
      return true;
    }
    
	public static boolean blockExistsOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
        return world.blockExists(getXOnSide(x, side), getYOnSide(y, side), getZOnSide(z, side));
    }

    public static int getBlockMetadataOnSide(World world, int i, int j, int k, ForgeDirection side)
    {
        return world.getBlockMetadata(getXOnSide(i, side), getYOnSide(j, side), getZOnSide(k, side));
    }

    public static int getBlockIdOnSide(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
        return world.getBlockId(getXOnSide(x, side), getYOnSide(y, side), getZOnSide(z, side));
    }

    public static TileEntity getBlockTileEntityOnSide(World world, int i, int j, int k, ForgeDirection side)
    {
        return world.getBlockTileEntity(getXOnSide(i, side), getYOnSide(j, side), getZOnSide(k, side));
    }

    public static void notifyBlocksOfNeighborChangeOnSide(World world, int i, int j, int k, int blockID, ForgeDirection side)
    {
        world.notifyBlocksOfNeighborChange(getXOnSide(i, side), getYOnSide(j, side), getZOnSide(k, side), blockID);
    }
    
    public static ForgeDirection getSideClosestToPlayer(World world, int i, int j, int k, EntityLiving entityplayer)
    {
      if ((MathHelper.abs((float)entityplayer.posX - i) < 2.0F) && (MathHelper.abs((float)entityplayer.posZ - k) < 2.0F)) {
        double d = entityplayer.posX + 1.82D - entityplayer.yOffset;
        if (d - j > 2.0D) {
          return ForgeDirection.UP;
        }
        if (j - d > 0.0D) {
          return ForgeDirection.DOWN;
        }
      }
      int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
      switch (dir) {
      case 0:
        return ForgeDirection.NORTH;
      case 1:
        return ForgeDirection.EAST;
      case 2:
        return ForgeDirection.SOUTH;
      }
      return dir != 3 ? ForgeDirection.DOWN : ForgeDirection.WEST;
    }

    public static ForgeDirection getHorizontalSideClosestToPlayer(World world, int i, int j, int k, EntityLiving player)
    {
      ForgeDirection facing = ForgeDirection.NORTH;
      int dir = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
      switch (dir) {
      case 0:
        return ForgeDirection.NORTH;
      case 1:
        return ForgeDirection.EAST;
      case 2:
        return ForgeDirection.SOUTH;
      case 3:
        return ForgeDirection.WEST;
      }
      return ForgeDirection.NORTH;
    }

    public static ForgeDirection getOppositeSide(int side) {
        int s = side;
        s = s % 2 == 0 ? s + 1 : s - 1;
        return ForgeDirection.getOrientation(s);
      }

    public static int getYOnSide(int y, ForgeDirection side)
    {
        switch ($SwitchMap$net$minecraftforge$common$ForgeDirection[side.ordinal()]) {
        case 1:
            return y + 1;
          case 2:
            return y - 1;
          }
          return y;
    }

    public static int getXOnSide(int x, ForgeDirection side)
    {
        switch ($SwitchMap$net$minecraftforge$common$ForgeDirection[side.ordinal()]) {
        case 3:
            return x + 1;
          case 4:
            return x - 1;
          }
          return x;
    }

    public static int getZOnSide(int z, ForgeDirection side)
    {
        switch ($SwitchMap$net$minecraftforge$common$ForgeDirection[side.ordinal()]) {
        case 5:
            return z - 1;
          case 6:
            return z + 1;
          }
          return z;
        }
    
    public static void dropItem(ItemStack stack, World world, double x, double y, double z) {
        if ((stack == null) || (stack.stackSize < 1)) {
          return;
        }
        EntityItem entityItem = new EntityItem(world, x, y + 1.5D, z, stack);
        entityItem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityItem);
      }

    public static void dropInventory(IInventory inv, World world, int x, int y, int z) {
        if (Game.isNotHost(world)) return;
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
          ItemStack stack = inv.getStackInSlot(slot);
          if (stack != null) {
            float xOffset = getRand().nextFloat() * 0.8F + 0.1F;
            float yOffset = getRand().nextFloat() * 0.8F + 0.1F;
            float zOffset = getRand().nextFloat() * 0.8F + 0.1F;
            while (stack.stackSize > 0) {
              int numToDrop = getRand().nextInt(21) + 10;
              if (numToDrop > stack.stackSize) {
                numToDrop = stack.stackSize;
              }
              ItemStack newStack = stack.copy();
              newStack.stackSize = numToDrop;
              stack.stackSize -= numToDrop;
              EntityItem entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, newStack);
              float variance = 0.05F;
              entityItem.motionX = ((float)getRand().nextGaussian() * variance);
              entityItem.motionY = ((float)getRand().nextGaussian() * variance + 0.2F);
              entityItem.motionZ = ((float)getRand().nextGaussian() * variance);
              world.spawnEntityInWorld(entityItem);
            }
            inv.setInventorySlotContents(slot, null);
          }
        }
      }
    
	static final int[] $SwitchMap$net$minecraftforge$common$ForgeDirection = new int[ForgeDirection.values().length];

    static
    {
        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.UP.ordinal()] = 1;
        }
        catch (NoSuchFieldError var6)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.DOWN.ordinal()] = 2;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.EAST.ordinal()] = 3;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.WEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.NORTH.ordinal()] = 5;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            $SwitchMap$net$minecraftforge$common$ForgeDirection[ForgeDirection.SOUTH.ordinal()] = 6;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
    
}

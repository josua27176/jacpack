package org.Jacpack.TechUp.api;

import java.util.ArrayList;
import java.util.Random;

import java.util.Iterator;

import org.Jacpack.TechUp.api.inventory.ISpecialInventory;
import org.Jacpack.TechUp.api.old.Game;
import org.Jacpack.TechUp.api.old.LiquidFilter;

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
    
    public static ItemStack depleteItem(ItemStack var0)
    {
        if (var0.stackSize == 1)
        {
            return var0.getItem().getContainerItemStack(var0);
        }
        else
        {
            var0.splitStack(1);
            return var0;
        }
    }
    
    public static ItemStack moveItemStack(ItemStack var0, IInventory var1)
    {
        if (var0 == null)
        {
            return null;
        }
        else
        {
            var0 = var0.copy();

            if (var1 == null)
            {
                return var0;
            }
            else if (var1 instanceof ISpecialInventory)
            {
                int var8 = ((ISpecialInventory)var1).addItem(var0, true, ForgeDirection.UNKNOWN);

                if (var8 >= var0.stackSize)
                {
                    return null;
                }
                else
                {
                    var0.stackSize -= var8;
                    return var0;
                }
            }
            else
            {
                boolean var2;

                do
                {
                    var2 = false;
                    ItemStack var3;
                    int var4;

                    for (var4 = 0; var4 < var1.getSizeInventory(); ++var4)
                    {
                        var3 = var1.getStackInSlot(var4);

                        if (var3 != null && isItemEqual(var3, var0))
                        {
                            var3 = var3.copy();
                            int var5 = Math.min(var3.getMaxStackSize(), var1.getInventoryStackLimit());
                            int var6 = var5 - var3.stackSize;

                            if (var6 > 0)
                            {
                                int var7 = Math.min(var6, var0.stackSize);
                                var3.stackSize += var7;
                                var0.stackSize -= var7;
                                var1.setInventorySlotContents(var4, var3);

                                if (var0.stackSize <= 0)
                                {
                                    return null;
                                }

                                var2 = true;
                            }
                        }
                    }

                    if (!var2)
                    {
                        for (var4 = 0; var4 < var1.getSizeInventory(); ++var4)
                        {
                            var3 = var1.getStackInSlot(var4);

                            if (var3 == null)
                            {
                                if (var0.stackSize <= var1.getInventoryStackLimit())
                                {
                                    var1.setInventorySlotContents(var4, var0);
                                    return null;
                                }

                                var1.setInventorySlotContents(var4, var0.splitStack(var1.getInventoryStackLimit()));
                                var2 = true;
                            }
                        }
                    }
                }
                while (var2);

                return var0;
            }
        }
    }
	
	public static LiquidStack getLiquidInContainer(ItemStack var1)
    {
        return LiquidContainerRegistry.getLiquidForFilledItem(var1);
    }
	
	public static void writeInvToNBT(IInventory var0, String var1, NBTTagCompound var2)
    {
        NBTTagList var3 = new NBTTagList();

        for (byte var4 = 0; var4 < var0.getSizeInventory(); ++var4)
        {
            ItemStack var5 = var0.getStackInSlot(var4);

            if (var5 != null)
            {
                NBTTagCompound var6 = new NBTTagCompound();
                var6.setByte("Slot", var4);
                writeItemToNBT(var5, var6);
                var3.appendTag(var6);
            }
        }

        var2.setTag(var1, var3);
    }

    public static void readInvFromNBT(IInventory var0, String var1, NBTTagCompound var2)
    {
        NBTTagList var3 = var2.getTagList(var1);

        for (byte var4 = 0; var4 < var3.tagCount(); ++var4)
        {
            NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
            byte var6 = var5.getByte("Slot");

            if (var6 >= 0 && var6 < var0.getSizeInventory())
            {
                ItemStack var7 = readItemFromNBT(var5);
                var0.setInventorySlotContents(var6, var7);
            }
        }
    }

    public static void writeItemToNBT(ItemStack var0, NBTTagCompound var1)
    {
        if (var0 != null && var0.stackSize > 0)
        {
            if (var0.stackSize > 127)
            {
                var0.stackSize = 127;
            }

            var0.writeToNBT(var1);
        }
    }

    public static ItemStack readItemFromNBT(NBTTagCompound var0)
    {
        short var1 = var0.getShort("id");
        short var2 = var0.getShort("stackSize");
        short var3 = var0.getShort("Damage");

        if (var2 <= 0)
        {
            var2 = var0.getByte("Count");
        }

        if (var1 > 0 && var2 > 0)
        {
            ItemStack var4 = new ItemStack(var1, var2, var3);

            if (var0.hasKey("tag"))
            {
                var4.stackTagCompound = var0.getCompoundTag("tag");
            }

            return var4.getItem() != null ? var4 : null;
        }
        else
        {
            return null;
        }
    }
	
	public static int getItemBurnTime(ItemStack var0)
    {
        try
        {
            if (var0 == null)
            {
                return 0;
            }
            else
            {
                int var1 = var0.getItem().itemID;
                String var3;

                if (var0.getItem() instanceof ItemBlock && Block.blocksList[var1] != null)
                {
                    Block var2 = Block.blocksList[var1];
                    var3 = var2.getBlockName();

                    if (var3 != null && var3.contains("blockScaffold"))
                    {
                        return 0;
                    }
                }

                LiquidStack var5 = getLiquidInContainer(var0);

                if (LiquidFilter.LAVA.isLiquidEqual(var5))
                {
                    return var5.amount;
                }
                else if (var1 == Item.coal.itemID && var0.getItemDamage() == 0 && !isSynthetic(var0))
                {
                    return 3200;
                }
                else if (var1 == Item.blazeRod.itemID)
                {
                    return 800;
                }
                else
                {
                    var3 = var0.getItem().getItemName();
                    return var3 != null && var3.contains("itemScrap") ? 0 : TileEntityFurnace.getItemBurnTime(var0);
                }
            }
        }
        catch (Exception var4)
        {
            System.out.println("Error in Fuel Handler! Is some mod creating items that are not compliant with standards?");
            return 0;
        }
    }
	
	public static boolean isSynthetic(ItemStack var0)
    {
        NBTTagCompound var1 = var0.getTagCompound();
        return var1 != null && var1.hasKey("synthetic");
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
	
	public static String translate(String var0)
    {
        return translate_do(var0);
    }

    private static String translate_do(String var1)
    {
        return var1;
    }
	
	public static boolean isItemEqual(ItemStack var0, ItemStack var1)
    {
        if (var0 != null && var1 != null)
        {
            if (var0.itemID != var1.itemID)
            {
                return false;
            }
            else if (var0.stackTagCompound != null && !var0.stackTagCompound.equals(var1.stackTagCompound))
            {
                return false;
            }
            else
            {
                if (var0.getHasSubtypes())
                {
                    if (var0.getItemDamage() == -1 || var1.getItemDamage() == -1)
                    {
                        return true;
                    }
 
                    if (var0.getItemDamage() != var1.getItemDamage())
                    {
                        return false;
                    }
                }
 
                return true;
            }
        }
        else
        {
            return false;
        }
    }
	
	public static boolean blockExistsOnSide(World var0, int var1, int var2, int var3, ForgeDirection var4)
    {
        return var0.blockExists(getXOnSide(var1, var4), getYOnSide(var2, var4), getZOnSide(var3, var4));
    }

    public static int getBlockMetadataOnSide(World var0, int var1, int var2, int var3, ForgeDirection var4)
    {
        return var0.getBlockMetadata(getXOnSide(var1, var4), getYOnSide(var2, var4), getZOnSide(var3, var4));
    }

    public static int getBlockIdOnSide(IBlockAccess var0, int var1, int var2, int var3, ForgeDirection var4)
    {
        return var0.getBlockId(getXOnSide(var1, var4), getYOnSide(var2, var4), getZOnSide(var3, var4));
    }

    public static TileEntity getBlockTileEntityOnSide(World var0, int var1, int var2, int var3, ForgeDirection var4)
    {
        return var0.getBlockTileEntity(getXOnSide(var1, var4), getYOnSide(var2, var4), getZOnSide(var3, var4));
    }

    public static void notifyBlocksOfNeighborChangeOnSide(World var0, int var1, int var2, int var3, int var4, ForgeDirection var5)
    {
        var0.notifyBlocksOfNeighborChange(getXOnSide(var1, var5), getYOnSide(var2, var5), getZOnSide(var3, var5), var4);
    }
    
    public static ForgeDirection getSideClosestToPlayer(World var0, int var1, int var2, int var3, EntityLiving var4)
    {
        if (MathHelper.abs((float)var4.posX - (float)var1) < 2.0F && MathHelper.abs((float)var4.posZ - (float)var3) < 2.0F)
        {
            double var5 = var4.posY + 1.82D - (double)var4.yOffset;

            if (var5 - (double)var2 > 2.0D)
            {
                return ForgeDirection.UP;
            }

            if ((double)var2 - var5 > 0.0D)
            {
                return ForgeDirection.DOWN;
            }
        }

        int var7 = MathHelper.floor_double((double)(var4.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        switch (var7)
        {
            case 0:
                return ForgeDirection.NORTH;

            case 1:
                return ForgeDirection.EAST;

            case 2:
                return ForgeDirection.SOUTH;

            default:
                return var7 != 3 ? ForgeDirection.DOWN : ForgeDirection.WEST;
        }
    }

    public static ForgeDirection getHorizontalSideClosestToPlayer(World var0, int var1, int var2, int var3, EntityLiving var4)
    {
        ForgeDirection var5 = ForgeDirection.NORTH;
        int var6 = MathHelper.floor_double((double)(var4.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        switch (var6)
        {
            case 0:
                return ForgeDirection.NORTH;

            case 1:
                return ForgeDirection.EAST;

            case 2:
                return ForgeDirection.SOUTH;

            case 3:
                return ForgeDirection.WEST;

            default:
                return ForgeDirection.NORTH;
        }
    }

    public static ForgeDirection getOppositeSide(int var0)
    {
        int var1 = var0 % 2 == 0 ? var0 + 1 : var0 - 1;
        return ForgeDirection.getOrientation(var1);
    }

    public static int getYOnSide(int var0, ForgeDirection var1)
    {
        switch ($SwitchMap$net$minecraftforge$common$ForgeDirection[var1.ordinal()])
        {
            case 1:
                return var0 + 1;

            case 2:
                return var0 - 1;

            default:
                return var0;
        }
    }

    public static int getXOnSide(int var0, ForgeDirection var1)
    {
        switch ($SwitchMap$net$minecraftforge$common$ForgeDirection[var1.ordinal()])
        {
            case 3:
                return var0 + 1;

            case 4:
                return var0 - 1;

            default:
                return var0;
        }
    }

    public static int getZOnSide(int var0, ForgeDirection var1)
    {
        switch ($SwitchMap$net$minecraftforge$common$ForgeDirection[var1.ordinal()])
        {
            case 5:
                return var0 - 1;

            case 6:
                return var0 + 1;

            default:
                return var0;
        }
    }
    
    public static void dropItem(ItemStack var0, World var1, double var2, double var4, double var6)
    {
        if (var0 != null && var0.stackSize >= 1)
        {
            EntityItem var8 = new EntityItem(var1, var2, var4 + 1.5D, var6, var0);
            var8.delayBeforeCanPickup = 10;
            var1.spawnEntityInWorld(var8);
        }
    }

    public static void dropInventory(IInventory var0, World var1, int var2, int var3, int var4)
    {
        if (!Game.isNotHost(var1))
        {
            for (int var5 = 0; var5 < var0.getSizeInventory(); ++var5)
            {
                ItemStack var6 = var0.getStackInSlot(var5);

                if (var6 != null)
                {
                    float var7 = JACTools.getRand().nextFloat() * 0.8F + 0.1F;
                    float var8 = JACTools.getRand().nextFloat() * 0.8F + 0.1F;
                    float var9 = JACTools.getRand().nextFloat() * 0.8F + 0.1F;

                    while (var6.stackSize > 0)
                    {
                        int var10 = JACTools.getRand().nextInt(21) + 10;

                        if (var10 > var6.stackSize)
                        {
                            var10 = var6.stackSize;
                        }

                        ItemStack var11 = var6.copy();
                        var11.stackSize = var10;
                        var6.stackSize -= var10;
                        EntityItem var12 = new EntityItem(var1, (double)((float)var2 + var7), (double)((float)var3 + var8), (double)((float)var4 + var9), var11);
                        float var13 = 0.05F;
                        var12.motionX = (double)((float)JACTools.getRand().nextGaussian() * var13);
                        var12.motionY = (double)((float)JACTools.getRand().nextGaussian() * var13 + 0.2F);
                        var12.motionZ = (double)((float)JACTools.getRand().nextGaussian() * var13);
                        var1.spawnEntityInWorld(var12);
                    }

                    var0.setInventorySlotContents(var5, (ItemStack)null);
                }
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

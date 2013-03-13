package org.Jacpack.TechUp.tileentitys;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.inventory.ISpecialInventory;
import org.Jacpack.TechUp.api.inventory.InventoryCopy;
import org.Jacpack.TechUp.api.inventory.InventoryMapper;
import org.Jacpack.TechUp.api.machines.EnumMachineAlpha;
import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.api.machines.IEnumMachine;
import org.Jacpack.TechUp.api.machines.INeedsFuel;
import org.Jacpack.TechUp.block.ModBlocks;
import org.Jacpack.TechUp.crafting.IBlastFurnaceRecipe;
import org.Jacpack.TechUp.crafting.TechUpCraftingManager;
import org.Jacpack.TechUp.gui.EnumGui;
import org.Jacpack.TechUp.gui.GuiHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileBlastFurnace extends TileMultiBlockInventory implements ISidedInventory, ISpecialInventory, INeedsFuel
{
    private static final int FUEL_PER_TICK = 5;
    private static final int COOK_STEP_LENGTH = 8;
    private static final int SLOT_INPUT = 0;
    private static final int SLOT_FUEL = 1;
    private static final int SLOT_OUTPUT = 2;
    private static final List patterns = new ArrayList();
    public int burnTime = 0;
    public int currentItemBurnTime = 0;
    private int finishedAt;
    private final IInventory invFuel = new InventoryMapper(this, 1, 1);
    private final IInventory invInput = new InventoryMapper(this, 0, 1);
    private final IInventory invOutput = new InventoryMapper(this, 2, 1);
    protected int cookTime;
    protected boolean cooking;
    private boolean wasBurning;

    public TileBlastFurnace()
    {
        super("gui.blast.furnace.name", 3, patterns);
    }

    public IEnumMachine getMachineType()
    {
        return EnumMachineAlpha.BLAST_FURNACE;
    }

    public int getBlockTexture(int side)
    {
      if ((getPatternMarker() == 'W') && (isStructureValid())) {
        if (isBurning()) {
          return getMachineType().getTexture(7);
        }
        return getMachineType().getTexture(6);
      }
      return getMachineType().getTexture(0);
    }

    protected boolean isMapPositionValid(int i, int j, int k, char mapPos)
    {
        int id = this.worldObj.getBlockId(i, j, k);

        switch (mapPos)
        {
        	case 'O':
        		if (id != ModBlocks.getBlockMachineAlpha().blockID || this.worldObj.getBlockMetadata(i, j, k) != this.getBlockMetadata())
        		{
        			return true;
        		}
        		break;
            case 'B':
            case 'W':
                if (id == ModBlocks.getBlockMachineAlpha().blockID && this.worldObj.getBlockMetadata(i, j, k) == this.getBlockMetadata())
                {
                    return true;
                }

                break;
            case 'A':
                if (this.worldObj.isAirBlock(i, j, k) || this.worldObj.getBlockMaterial(i, j, k) == Material.lava)
                {
                    return true;
                }

                break;
        }

        return false;
    }

    protected void onMasterReset()
    {
        super.onMasterReset();
        this.cookTime = 0;
        this.burnTime = 0;
        this.currentItemBurnTime = 0;
    }
    
    public int getTotalCookTime()
    {
      ItemStack input = getStackInSlot(0);
      IBlastFurnaceRecipe recipe = TechUpCraftingManager.blastFurnace.getRecipe(input);
      if (recipe != null) {
        return recipe.getCookTime();
      }
      return 1;
    }

    public int getBurnProgressScaled(int i)
    {
    	if ((this.burnTime <= 0) || (this.currentItemBurnTime <= 0)) {
    	      return 0;
    	    }
    	    int scale = this.burnTime * i / this.currentItemBurnTime;
    	    scale = Math.min(scale, i);
    	    scale = Math.max(scale, 0);
    	    return scale;
    }

    private void setLavaIdle()
    {
        int xLava = this.xCoord + 1;
        int yLava = this.yCoord + 1;
        int zLava = this.zCoord + 1;

        if (this.worldObj.getBlockId(xLava, yLava, zLava) == 0)
        {
            this.worldObj.setBlockAndMetadata(xLava, yLava, zLava, Block.lavaStill.blockID, 7);
        }
    }

    private void setLavaBurn()
    {
        int xLava = this.xCoord + 1;
        int yLava = this.yCoord + 1;
        int zLava = this.zCoord + 1;

        if (this.worldObj.getBlockId(xLava, yLava, zLava) == 0)
        {
            this.worldObj.setBlockAndMetadata(xLava, yLava, zLava, Block.lavaMoving.blockID, 1);
        }

        yLava++;

        if (this.worldObj.getBlockId(xLava, yLava, zLava) == 0)
        {
            this.worldObj.setBlockAndMetadata(xLava, yLava, zLava, Block.lavaMoving.blockID, 1);
        }
    }

    private void destroyLava()
    {
        int xLava = this.xCoord + 1;
        int yLava = this.yCoord + 2;
        int zLava = this.zCoord + 1;

        if (this.worldObj.getBlockMaterial(xLava, yLava, zLava) == Material.lava)
        {
            this.worldObj.setBlock(xLava, yLava, zLava, 0);
        }

        yLava--;

        if (this.worldObj.getBlockMaterial(xLava, yLava, zLava) == Material.lava)
        {
            this.worldObj.setBlock(xLava, yLava, zLava, 0);
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        
        if ((getPatternMarker() == 'W') && 
      	      (this.update % 4 == 0))
      	      updateLighting();
        
        if ((Game.isHost(getWorld())) && 
        	      (isMaster())) {
        	      int prevBurnTime = this.burnTime;
        	      if ((this.update > this.finishedAt + 8 + 5) && 
        	        (this.cookTime <= 0)) {
        	        setCooking(false);
        	      }

        	      if (this.burnTime >= 5)
        	        this.burnTime -= 5;
        	      else {
        	        this.burnTime = 0;
        	      }

        	      if (isBurning())
        	        setLavaBurn();
        	      else {
        	        setLavaIdle();
        	      }

        	      ItemStack input = getStackInSlot(0);
        	      if ((input != null) && (input.stackSize > 0))
        	      {
        	    	ItemStack output = getStackInSlot(2);
        	        IBlastFurnaceRecipe recipe = TechUpCraftingManager.blastFurnace.getRecipe(input);

        	        if ((recipe != null) && (recipe.isRoomForOutput(output)))
        	        {
        	          if (this.burnTime <= 10) {
        	        	ItemStack fuel = getStackInSlot(1);
        	            if ((fuel != null) && (((fuel.getItem() == Item.coal) && (fuel.getItemDamage() == 1)))) {
        	              int itemBurnTime = JACTools.getItemBurnTime(fuel);
        	              if (itemBurnTime > 0) {
        	                this.currentItemBurnTime = (itemBurnTime + this.burnTime);
        	                this.burnTime = this.currentItemBurnTime;
        	                decrStackSize(1, 1);
        	              }
        	            }
        	          }

        	          if ((this.update % 8 == 0) && (isBurning())) {
        	            this.cookTime += 8;
        	            setCooking(true);

        	            if (this.cookTime >= recipe.getCookTime()) {
        	              this.cookTime = 0;
        	              this.finishedAt = this.update;
        	              if (output == null)
        	            	setInventorySlotContents(2, recipe.getOutput());
        	              else {
        	                output.stackSize += recipe.getOutputStackSize();
        	              }
        	              decrStackSize(0, 1);
        	            }
        	            sendUpdateToClient();
        	          }
        	        } else {
        	          this.cookTime = 0;
        	          setCooking(false);
        	        }
        	      } else {
        	        this.cookTime = 0;
        	        setCooking(false);
        	      }

        	      if (((prevBurnTime == 0 ? 1 : 0) ^ (this.burnTime == 0 ? 1 : 0)) != 0)
        	        sendUpdateToClient();
        	    }
    }

    public int getStartInventorySide(ForgeDirection side)
    {
      if (side == ForgeDirection.DOWN) {
        return 1;
      }
      if (side == ForgeDirection.UP) {
        return 0;
      }
      return 2;
    }

    public int getSizeInventorySide(ForgeDirection side)
    {
        return 1;
    }
    
    public boolean openGui(EntityPlayer player)
    {
      TileMultiBlock masterBlock = getMasterBlock();
      if (masterBlock != null) {
        GuiHandler.openGui(EnumGui.BLAST_FURNACE, player, this.worldObj, masterBlock.xCoord, masterBlock.yCoord, masterBlock.zCoord);
        return true;
      }
      return false;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound data)
    {
        super.writeToNBT(data);
        data.setInteger("burnTime", this.burnTime);
        data.setInteger("currentItemBurnTime", this.currentItemBurnTime);
        data.setInteger("cookTime", this.cookTime);
        data.setBoolean("cooking", this.cooking);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);
        this.burnTime = data.getInteger("burnTime");
        this.currentItemBurnTime = data.getInteger("currentItemBurnTime");
        this.cookTime = data.getInteger("cookTime");
        this.cooking = data.getBoolean("cooking");
    }

    public void writePacketData(DataOutputStream data) throws IOException
    {
        super.writePacketData(data);
        data.writeInt(this.burnTime);
        data.writeInt(this.currentItemBurnTime);
        data.writeInt(this.cookTime);
        data.writeBoolean(this.cooking);
    }

    public void readPacketData(DataInputStream data) throws IOException
    {
        super.readPacketData(data);
        this.burnTime = data.readInt();
        this.currentItemBurnTime = data.readInt();
        this.cookTime = data.readInt();
        this.cooking = data.readBoolean();
    }

    public boolean needsFuel()
    {
      ItemStack fuel = getStackInSlot(1);
      return (fuel == null) || (fuel.stackSize < 8);
    }
    
    public boolean isBurning()
    {
      TileBlastFurnace mBlock = (TileBlastFurnace)getMasterBlock();
      if (mBlock != null) {
        return mBlock.burnTime > 0;
      }
      return false;
    }
    
    public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from)
    {
      if (!isStructureValid()) {
        return 0;
      }
      if (stack == null) {
        return 0;
      }
      if (((stack.itemID == Item.coal.itemID) && (stack.getItemDamage() == 1)))
      {
    	IInventory inv = this.invFuel;
        if (!doAdd) {
          inv = new InventoryCopy(inv);
        }
        ItemStack remaining = JACTools.moveItemStack(stack, inv);
        if (remaining == null) {
          return stack.stackSize;
        }
        return stack.stackSize - remaining.stackSize;
      }

      if (TechUpCraftingManager.blastFurnace.getRecipe(stack) != null) {
    	IInventory inv = this.invInput;
        if (!doAdd) {
          inv = new InventoryCopy(inv);
        }
        ItemStack remaining = JACTools.moveItemStack(stack, inv);
        if (remaining == null) {
          return stack.stackSize;
        }
        return stack.stackSize - remaining.stackSize;
      }

      return 0;
    }
    
    public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount)
    {
      ItemStack output = this.getStackInSlot(2);

      ItemStack newStack = null;
      if (output != null) {
        newStack = output.copy();
        int stackSize = Math.min(output.stackSize, maxItemCount);
        newStack.stackSize = stackSize;

        if (doRemove) {
          decrStackSize(2, stackSize);
        }
      }

      if (newStack == null) {
        return new ItemStack[0];
      }

      return new ItemStack[] { newStack };
    }

    protected void updateLighting()
    {
        boolean b = this.isBurning();

        if (this.wasBurning != b)
        {
            this.wasBurning = b;
            this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
            this.markBlockForUpdate();
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(Random random)
    {
        this.updateLighting();

        if ((getPatternMarker() == 'W') && (isStructureValid()) && (random.nextInt(100) < 20) && (isBurning())) {
            float f = (float)this.xCoord + 0.5F;
            float f1 = (float)this.yCoord + 0.4375F + random.nextFloat() * 3.0F / 16.0F;
            float f2 = (float)this.zCoord + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;
            this.worldObj.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
        }
    }
    
    public int getCookTime() {
    	TileBlastFurnace masterOven = (TileBlastFurnace)getMasterBlock();
        if (masterOven != null) {
          return masterOven.cookTime;
        }
        return -1;
      }
    
    public boolean isCooking() {
    	TileBlastFurnace masterOven = (TileBlastFurnace)getMasterBlock();
        if (masterOven != null) {
          return masterOven.cooking;
        }
        return false;
      }

    public void setCooking(boolean c)
    {
        if (this.cooking != c)
        {
            this.cooking = c;
            this.sendUpdateToClient();
        }
    }

    public void setCookTime(int i)
    {
        this.cookTime = i;
    }

    public int getCookProgressScaled(int i) {
        if ((this.cookTime == 0) || (getTotalCookTime() == 0)) {
          return 0;
        }
        int scale = this.cookTime * i / getTotalCookTime();
        scale = Math.min(scale, i);
        scale = Math.max(scale, 0);
        return scale;
    }

    public int getLightValue()
    {
      if ((getPatternMarker() == 'W') && (isStructureValid()) && (isBurning())) {
        return 13;
      }
      return 0;
    }

    static
    {
    	char[][][] var0 = new char[][][] {{{'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'W', 'B', 'O'}, {'O', 'W', 'B', 'W', 'O'}, {'O', 'B', 'W', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}}};
        //char[][][] var0 = new char[][][] {{{'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'W', 'B', 'O'}, {'O', 'W', 'B', 'W', 'O'}, {'O', 'B', 'W', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'A', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'B', 'B', 'B', 'O'}, {'O', 'O', 'O', 'O', 'O'}}, {{'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}, {'O', 'O', 'O', 'O', 'O'}}};
        patterns.add(new MultiBlockPattern(var0));
    }
}

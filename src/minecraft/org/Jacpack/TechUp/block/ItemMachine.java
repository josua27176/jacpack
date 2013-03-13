package org.Jacpack.TechUp.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMachine extends ItemBlock
{
    public ItemMachine(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public String getTextureFile()
    {
        return "/railcraft/client/textures/machines.png";
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int damage)
    {
        return Block.blocksList[this.getBlockID()].getBlockTextureFromSideAndMetadata(2, damage);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int damage)
    {
        return damage;
    }
    public String getItemNameIS(ItemStack stack)
    {
      Block block = Block.blocksList[this.getBlockID()];
      if ((block instanceof BlockMachine)) {
        return ((BlockMachine)block).getMachineProxy().getTag(stack.getItemDamage());
      }
      return "";
    }
    
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
      if (!world.setBlockAndMetadataWithNotify(x, y, z, this.getBlockID(), metadata)) {
        return false;
      }

      if (world.getBlockId(x, y, z) == this.getBlockID()) {
        Block.blocksList[this.getBlockID()].onBlockPlacedBy(world, x, y, z, player);
        Block.blocksList[this.getBlockID()].onPostBlockPlaced(world, x, y, z, metadata);
        ((BlockMachine)Block.blocksList[this.getBlockID()]).initFromItem(world, x, y, z, stack);
      }

      return true;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean adv)
    {
    	Block block = Block.blocksList[this.getBlockID()];
        if ((block instanceof BlockMachine))
          ((BlockMachine)block).getMachineProxy().addItemInfo(stack, player, info, adv);
      }
}

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
        this.setItemName("rc.machine");
    }

    public String getTextureFile()
    {
        return "/railcraft/client/textures/machines.png";
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int var1)
    {
        return Block.blocksList[this.getBlockID()].getBlockTextureFromSideAndMetadata(2, var1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int var1)
    {
        return var1;
    }

    public String getItemNameIS(ItemStack var1)
    {
        Block var2 = Block.blocksList[this.getBlockID()];
        return var2 instanceof BlockMachine ? ((BlockMachine)var2).getMachineProxy().getTag(var1.getItemDamage()) : "";
    }

    public boolean placeBlockAt(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10, int var11)
    {
        if (!var3.setBlockAndMetadataWithNotify(var4, var5, var6, this.getBlockID(), var11))
        {
            return false;
        }
        else
        {
            if (var3.getBlockId(var4, var5, var6) == this.getBlockID())
            {
                Block.blocksList[this.getBlockID()].onBlockPlacedBy(var3, var4, var5, var6, var2);
                Block.blocksList[this.getBlockID()].onPostBlockPlaced(var3, var4, var5, var6, var11);
                ((BlockMachine)Block.blocksList[this.getBlockID()]).initFromItem(var3, var4, var5, var6, var1);
            }

            return true;
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4)
    {
        Block var5 = Block.blocksList[this.getBlockID()];

        if (var5 instanceof BlockMachine)
        {
            ((BlockMachine)var5).getMachineProxy().addItemInfo(var1, var2, var3, var4);
        }
    }
}

package org.Jacpack.TechUp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.Jacpack.TechUp.api.machines.IEnumMachine;
import org.Jacpack.TechUp.api.machines.IMachineProxy;
import org.Jacpack.TechUp.api.old.Game;
import org.Jacpack.TechUp.creativetabs.CreativeTabsHandler;
import org.Jacpack.TechUp.tileentitys.TileMachineBase;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockMachine extends BlockContainer
{
    private final IMachineProxy proxy;
    private final boolean opaque;
    private final int renderId;
    private final int[] metaOpacity;

    public BlockMachine(int var1, int var2, IMachineProxy var3, boolean var4, int[] var5)
    {
        super(var1, 45, Material.rock);
        this.setResistance(4.5F);
        this.setHardness(2.0F);
        this.setStepSound(soundStoneFootstep);
        this.setRequiresSelfNotify();
        this.setTickRandomly(true);
        this.proxy = var3;
        this.opaque = var4;
        this.renderId = var2;
        this.metaOpacity = var5;
        this.setCreativeTab(CreativeTabsHandler.tabTechUpB);
        opaqueCubeLookup[var1] = var4;
        lightOpacity[var1] = var4 ? 255 : 0;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return this.renderId;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the default ambient occlusion value based on block opacity
     */
    public float getAmbientOcclusionLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 1.0F;
    }

    public String getTextureFile()
    {
        return "/railcraft/client/textures/machines.png";
    }

    public IMachineProxy getMachineProxy()
    {
        return this.proxy;
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof TileMachineBase)
        {
            return ((TileMachineBase)var6).getBlockTexture(var5);
        }
        else
        {
            int var7 = var1.getBlockMetadata(var2, var3, var4);
            return this.getBlockTextureFromSideAndMetadata(var5, var7);
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        return this.proxy.getTexture(var2, var1);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return this.blockID;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5, int var6, float var7, float var8, float var9)
    {
        TileEntity var10 = var1.getBlockTileEntity(var2, var3, var4);
        return var10 instanceof TileMachineBase ? ((TileMachineBase)var10).blockActivated(var5, var6) : false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof TileMachineBase)
        {
            ((TileMachineBase)var6).randomDisplayTick(var5);
        }
    }

    public boolean isBlockNormalCube(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean isBlockSolidOnSide(World var1, int var2, int var3, int var4, ForgeDirection var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        return var6 instanceof TileMachineBase ? ((TileMachineBase)var6).isBlockSolidOnSide(var5) : true;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6) {}

    public boolean removeBlockByPlayer(World var1, EntityPlayer var2, int var3, int var4, int var5)
    {
        var2.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        var2.addExhaustion(0.025F);

        if (Game.isHost(var1) && !var2.capabilities.isCreativeMode)
        {
            this.dropBlockAsItem(var1, var3, var4, var5, 0, 0);
        }

        return var1.setBlockWithNotify(var3, var4, var5, 0);
    }

    public ArrayList getBlockDropped(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        TileEntity var7 = var1.getBlockTileEntity(var2, var3, var4);
        return var7 instanceof TileMachineBase ? ((TileMachineBase)var7).getBlockDropped(var6) : super.getBlockDropped(var1, var2, var3, var4, var5, var6);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int var1)
    {
        return var1;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return true;
    }

    /**
     * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
     * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
     * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean isProvidingWeakPower(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);
        return var6 instanceof TileMachineBase ? ((TileMachineBase)var6).isPoweringTo(var5) : false;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean isProvidingStrongPower(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.isProvidingWeakPower(var1, var2, var3, var4, var5);
    }

    public boolean canConnectRedstone(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof TileMachineBase)
        {
            ((TileMachineBase)var6).canConnectRedstone(var5);
        }

        return false;
    }

    public void initFromItem(World var1, int var2, int var3, int var4, ItemStack var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof TileMachineBase)
        {
            ((TileMachineBase)var6).initFromItem(var5);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof TileMachineBase)
        {
            ((TileMachineBase)var6).onBlockPlacedBy(var5);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var1.getBlockTileEntity(var2, var3, var4);

        if (var6 instanceof TileMachineBase)
        {
            ((TileMachineBase)var6).onNeighborBlockChange(var5);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        TileEntity var5 = var1.getBlockTileEntity(var2, var3, var4);

        if (var5 instanceof TileMachineBase)
        {
            ((TileMachineBase)var5).onBlockAdded();
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        TileEntity var7 = var1.getBlockTileEntity(var2, var3, var4);

        if (var7 instanceof TileMachineBase)
        {
            ((TileMachineBase)var7).onBlockRemoval();
        }

        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return this.proxy.getTileEntity(var2);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World var1)
    {
        return null;
    }

    public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
        TileEntity var5 = var1.getBlockTileEntity(var2, var3, var4);
        return var5 instanceof TileMachineBase ? ((TileMachineBase)var5).getLightValue() : 0;
    }

    public boolean hasTileEntity(int var1)
    {
        return true;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int var1, CreativeTabs var2, List var3)
    {
        Iterator var4 = this.proxy.getCreativeList().iterator();

        while (var4.hasNext())
        {
            IEnumMachine var5 = (IEnumMachine)var4.next();

            if (var5.isEnabled())
            {
                var3.add(var5.getItem());
            }
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public final boolean isOpaqueCube()
    {
        return this.opaque;
    }

    public int getLightOpacity(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        return this.metaOpacity[var5];
    }

    public float getExplosionResistance(Entity var1, World var2, int var3, int var4, int var5, double var6, double var8, double var10)
    {
        TileEntity var12 = var2.getBlockTileEntity(var3, var4, var5);
        return var12 instanceof TileMachineBase ? ((TileMachineBase)var12).getResistance(var1) * 3.0F / 5.0F : super.getExplosionResistance(var1, var2, var3, var4, var5, this.minX, this.minY, this.minZ);
    }

    public boolean canBeReplacedByLeaves(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    public boolean canCreatureSpawn(EnumCreatureType var1, World var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var2.getBlockTileEntity(var3, var4, var5);
        return var6 instanceof TileMachineBase ? ((TileMachineBase)var6).canCreatureSpawn(var1) : super.canCreatureSpawn(var1, var2, var3, var4, var5);
    }

    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    public float getBlockHardness(World var1, int var2, int var3, int var4)
    {
        TileEntity var5 = var1.getBlockTileEntity(var2, var3, var4);
        return var5 instanceof TileMachineBase ? ((TileMachineBase)var5).getHardness() : super.getBlockHardness(var1, var2, var3, var4);
    }
}

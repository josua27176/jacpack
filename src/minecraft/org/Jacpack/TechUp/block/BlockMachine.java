package org.Jacpack.TechUp.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.api.machines.IEnumMachine;
import org.Jacpack.TechUp.api.machines.IMachineProxy;
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

    public BlockMachine(int blockID, int renderId, IMachineProxy proxy, boolean opaque, int[] metaOpacity)
    {
        super(blockID, 45, Material.rock);
        this.setResistance(4.5F);
        this.setHardness(2.0F);
        this.setStepSound(soundStoneFootstep);
        this.setRequiresSelfNotify();
        this.setTickRandomly(true);
        this.proxy = proxy;
        this.opaque = opaque;
        this.renderId = renderId;
        this.metaOpacity = metaOpacity;
        this.setCreativeTab(CreativeTabsHandler.tabTechUpB);
        opaqueCubeLookup[blockID] = opaque;
        lightOpacity[blockID] = opaque ? 255 : 0;
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
    public float getAmbientOcclusionLightValue(IBlockAccess world, int x, int y, int z)
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
    public int getBlockTexture(IBlockAccess world, int i, int j, int k, int side)
    {
    	TileEntity tile = world.getBlockTileEntity(i, j, k);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).getBlockTexture(side);
      }
      int meta = world.getBlockMetadata(i, j, k);
      return this.getBlockTextureFromSideAndMetadata(side, meta);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int side, int meta)
    {
        return this.proxy.getTexture(side, meta);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int meta, Random random, int j)
    {
        return this.blockID;
    }
    
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int side, float u1, float u2, float u3)
    {
      TileEntity tile = world.getBlockTileEntity(i, j, k);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).blockActivated(player, side);
      }
      return false;
    }
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
      TileEntity tile = world.getBlockTileEntity(i, j, k);
      if ((tile instanceof TileMachineBase))
        ((TileMachineBase)tile).randomDisplayTick(random);
    }

    public boolean isBlockNormalCube(World world, int i, int j, int k)
    {
        return false;
    }
    
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
      TileEntity tile = world.getBlockTileEntity(x, y, z);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).isBlockSolidOnSide(side);
      }
      return true;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {
    }

    public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k)
    {
    	player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        player.addExhaustion(0.025F);

        if (Game.isHost(world) && !player.capabilities.isCreativeMode)
        {
            this.dropBlockAsItem(world, i, j, k, 0, 0);
        }

        return world.setBlockWithNotify(i, j, k, 0);
    }
    
    public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
      TileEntity tile = world.getBlockTileEntity(x, y, z);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).getBlockDropped(fortune);
      }
      return super.getBlockDropped(world, x, y, z, metadata, fortune);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta;
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
    public boolean isProvidingWeakPower(IBlockAccess world, int i, int j, int k, int side)
    {
      TileEntity tile = world.getBlockTileEntity(i, j, k);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).isPoweringTo(side);
      }
      return false;
    }

    /**
     * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
     * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
     */
    public boolean isProvidingStrongPower(IBlockAccess  world, int i, int j, int k, int side)
    {
        return this.isProvidingWeakPower(world, i, j, k, side);
    }

    public boolean canConnectRedstone(IBlockAccess world, int i, int j, int k, int dir)
    {
    	TileEntity tile = world.getBlockTileEntity(i, j, k);
        if ((tile instanceof TileMachineBase)) {
          ((TileMachineBase)tile).canConnectRedstone(dir);
        }
        return false;
    }
    
    public void initFromItem(World world, int i, int j, int k, ItemStack stack) {
        TileEntity tile = world.getBlockTileEntity(i, j, k);
        if ((tile instanceof TileMachineBase))
          ((TileMachineBase)tile).initFromItem(stack);
      }
    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
      TileEntity tile = world.getBlockTileEntity(i, j, k);
      if ((tile instanceof TileMachineBase))
        ((TileMachineBase)tile).onBlockPlacedBy(entityliving);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int i, int j, int k, int id)
    {
        TileEntity tile = world.getBlockTileEntity(i, j, k);
        if ((tile instanceof TileMachineBase))
          ((TileMachineBase)tile).onNeighborBlockChange(id);
      }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
        TileEntity tile = world.getBlockTileEntity(i, j, k);
        if ((tile instanceof TileMachineBase))
          ((TileMachineBase)tile).onBlockAdded();
      }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if ((tile instanceof TileMachineBase)) {
          ((TileMachineBase)tile).onBlockRemoval();
        }

        super.breakBlock(world, x, y, z, id, meta);
      }

    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return this.proxy.getTileEntity(metadata);
      }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return null;
    }
    
    public int getLightValue(World world, int i, int j, int k)
    {
      TileEntity tile = world.getBlockTileEntity(i, j, k);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).getLightValue();
      }
      return 0;
    }

    public boolean hasTileEntity(int metadata)
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

    public int getLightOpacity(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return this.metaOpacity[meta];
    }
    
    public float getExplosionResistance(Entity exploder, World world, int x, int y, int z, double srcX, double srcY, double srcZ)
    {
      TileEntity tile = world.getBlockTileEntity(x, y, z);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).getResistance(exploder) * 3.0F / 5.0F;
      }
      return super.getExplosionResistance(exploder, world, x, y, z, this.minX, this.minY, this.minZ);
    }

    public boolean canBeReplacedByLeaves(World world, int x, int y, int z)
    {
      return false;
    }

    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z)
    {
      TileEntity tile = world.getBlockTileEntity(x, y, z);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).canCreatureSpawn(type);
      }
      return super.canCreatureSpawn(type, world, x, y, z);
    }
    
    /**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    public float getBlockHardness(World world, int x, int y, int z)
    {
      TileEntity tile = world.getBlockTileEntity(x, y, z);
      if ((tile instanceof TileMachineBase)) {
        return ((TileMachineBase)tile).getHardness();
      }
      return super.getBlockHardness(world, x, y, z);
    }
}

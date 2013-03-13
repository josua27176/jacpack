package org.Jacpack.TechUp.tileentitys;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.api.inventory.SafeTimeTracker;
import org.Jacpack.TechUp.api.machines.Game;
import org.Jacpack.TechUp.util.network.PacketTileRequest;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public abstract class TileMultiBlock extends TileMachineBase
{
	private static final int UNKNOWN_STATE_RECHECK = 256;
	private static final int NETWORK_RECHECK = 64;
	private static final int NEIGHBOR_CHECK = 32;
	protected boolean isMaster;
	private byte patternX;
	private byte patternY;
	private byte patternZ;
	private boolean checkNeighbors = true;
	private boolean tested;
	private boolean requestPacket;
	protected int update = JACTools.getRand().nextInt();
	private SafeTimeTracker timeTracker = new SafeTimeTracker();
	private MultiBlockState state;
	private TileMultiBlock masterBlock;
	private MultiBlockPattern currentPattern;
	private final List patterns;
	private final TileMultiBlock[] neighbors = new TileMultiBlock[6];

    public TileMultiBlock(List patterns) {
        this.patterns = patterns;
        this.currentPattern = ((MultiBlockPattern)patterns.get(0));
        this.tested = (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER);
      }

    protected void onPatternLock(MultiBlockPattern var1) {}

    public final char getPatternMarker() {
        if ((this.currentPattern == null) || (!isStructureValid())) {
          return 'O';
        }
        return this.currentPattern.getPatternMarker(this.patternX, this.patternY, this.patternZ);
      }

    public final int getPatternPositionX()
    {
        return this.patternX;
    }

    public final int getPatternPositionY()
    {
        return this.patternY;
    }

    public final int getPatternPositionZ()
    {
        return this.patternZ;
    }

    private void setPatternPosition(byte var1, byte var2, byte var3)
    {
        this.patternX = var1;
        this.patternY = var2;
        this.patternZ = var3;
    }

    public final void setPattern(MultiBlockPattern var1)
    {
        this.currentPattern = var1;
        this.onPatternLock(var1);
    }

    public final MultiBlockPattern getPattern()
    {
        return this.currentPattern;
    }

    public final byte getPatternIndex()
    {
        return (byte)this.patterns.indexOf(this.currentPattern);
    }

    protected int getMaxRecursionDepth()
    {
        return 12;
    }

    public final boolean canUpdate()
    {
        return true;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        this.update += 1;
        if (Game.isHost(this.worldObj)) {
          if ((this.checkNeighbors) && (this.update % 32 == 0)) {
            grabNeighbors();
          }
          if ((!this.tested) && ((this.state != MultiBlockState.UNKNOWN) || (this.update % 256 == 0))) {
            testIfMasterBlock();
          }

        }
        else if ((this.requestPacket) && (this.timeTracker.markTimeIfDelay(this.worldObj, 64L))) {
          PacketTileRequest pkt = new PacketTileRequest(this);
          PacketDispatcher.sendPacketToServer(pkt.getPacket());
          this.requestPacket = false;
        }
      }
    
    private void testIfMasterBlock()
    {
      this.state = getMasterBlockState();
      this.tested = true;

      if (this.state == MultiBlockState.UNKNOWN) {
        this.tested = false;
      } else if (this.state == MultiBlockState.VALID) {
        this.isMaster = true;

        int xWidth = this.currentPattern.getPatternWidthX();
        int zWidth = this.currentPattern.getPatternWidthZ();
        int height = this.currentPattern.getPatternHeight();

        int xOffset = this.xCoord - this.currentPattern.getMasterOffsetX();
        int yOffset = this.yCoord - this.currentPattern.getMasterOffsetY();
        int zOffset = this.zCoord - this.currentPattern.getMasterOffsetZ();

        for (byte px = 0; px < xWidth; px = (byte)(px + 1)) {
          for (byte py = 0; py < height; py = (byte)(py + 1))
            for (byte pz = 0; pz < zWidth; pz = (byte)(pz + 1))
            {
              char marker = this.currentPattern.getPatternMarker(px, py, pz);
              if (!isMapPositionOtherBlock(marker))
              {
                int x = px + xOffset;
                int y = py + yOffset;
                int z = pz + zOffset;

                TileEntity tile = this.worldObj.getBlockTileEntity(x, y, z);
                if ((tile instanceof TileMultiBlock)) {
                  TileMultiBlock multiBlock = (TileMultiBlock)tile;
                  multiBlock.masterBlock = this;
                  multiBlock.tested = true;
                  multiBlock.checkNeighbors = true;
                  multiBlock.setPattern(this.currentPattern);
                  multiBlock.setPatternPosition(px, py, pz);
                  multiBlock.sendUpdateToClient();
                } else {
                  return;
                }
              }
            }
        }
      }
      else if (this.isMaster) {
        this.isMaster = false;
        onMasterReset();
        sendUpdateToClient();
      }
    }
    
    protected void onMasterReset()
    {
    }

    /**private void testIfMasterBlock()
    {
        this.state = this.getMasterBlockState();
        this.tested = true;

        if (this.state == MultiBlockState.UNKNOWN)
        {
            this.tested = false;
        }
        else if (this.state == MultiBlockState.VALID)
        {
            this.isMaster = true;
            int var1 = this.currentPattern.getPatternWidthX();
            int var2 = this.currentPattern.getPatternWidthZ();
            int var3 = this.currentPattern.getPatternHeight();
            int var4 = this.xCoord - this.currentPattern.getMasterOffsetX();
            int var5 = this.yCoord - this.currentPattern.getMasterOffsetY();
            int var6 = this.zCoord - this.currentPattern.getMasterOffsetZ();

            for (byte var7 = 0; var7 < var1; ++var7)
            {
                for (byte var8 = 0; var8 < var3; ++var8)
                {
                    for (byte var9 = 0; var9 < var2; ++var9)
                    {
                        char var10 = this.currentPattern.getPatternMarker(var7, var8, var9);

                        if (!this.isMapPositionOtherBlock(var10))
                        {
                            int var11 = var7 + var4;
                            int var12 = var8 + var5;
                            int var13 = var9 + var6;
                            TileEntity var14 = this.worldObj.getBlockTileEntity(var11, var12, var13);

                            if (!(var14 instanceof TileMultiBlock))
                            {
                                return;
                            }

                            TileMultiBlock var15 = (TileMultiBlock)var14;
                            var15.masterBlock = this;
                            var15.tested = true;
                            var15.checkNeighbors = true;
                            var15.setPattern(this.currentPattern);
                            var15.setPatternPosition(var7, var8, var9);
                            var15.sendUpdateToClient();
                        }
                    }
                }
            }
        }
        else if (this.isMaster)
        {
            this.isMaster = false;
            this.onMasterReset();
            this.sendUpdateToClient();
        }
    }

    protected void onMasterReset() {}*/

    protected boolean isMapPositionOtherBlock(char var1)
    {
        switch (var1)
        {
            case 'A':
            case 'O':
                return true;
        }
        return false;
    }

    protected boolean isMapPositionValid(int var1, int var2, int var3, char var4)
    {
        int var5 = this.worldObj.getBlockId(var1, var2, var3);

        switch (var4)
        {
            case 'A':
                if (!this.worldObj.isAirBlock(var1, var2, var3))
                {
                    return false;
                }

                break;

            case 'B':
            case 'W':
                if (var5 != this.getBlockId() || this.worldObj.getBlockMetadata(var1, var2, var3) != this.getBlockMetadata())
                {
                    return false;
                }

                break;

            case 'O':
                if (var5 == this.getBlockId() && this.worldObj.getBlockMetadata(var1, var2, var3) == this.getBlockMetadata())
                {
                    return false;
                }
        }

        return true;
    }
    
    private MultiBlockState getMasterBlockState() {
    	List<MultiBlockPattern> maps = this.patterns;
    	for (MultiBlockPattern map : maps) {
    		boolean valid = true;

    	    int xWidth = map.getPatternWidthX();
    	    int zWidth = map.getPatternWidthZ();
    	    int height = map.getPatternHeight();

    	    int xOffset = this.xCoord - map.getMasterOffsetX();
    	    int yOffset = this.yCoord - map.getMasterOffsetY();
    	    int zOffset = this.zCoord - map.getMasterOffsetZ();
    	    for (int x = 0; (x < xWidth) && (valid); x++) {
    	        for (int y = 0; (y < height) && (valid); y++) {
    	          for (int z = 0; z < zWidth; z++) {
    	            int xx = x + xOffset;
    	            int yy = y + yOffset;
    	            int zz = z + zOffset;
    	            if (!this.worldObj.blockExists(xx, yy, zz)) {
    	              return MultiBlockState.UNKNOWN;
    	            }
    	            if (!isMapPositionValid(xx, yy, zz, map.getPatternMarker(x, y, z))) {
    	              valid = false;
    	              break;
    	            }
    	          }
    	        }
    	      }
    	    if (valid) {
    	    	AxisAlignedBB entityCheckBounds = map.getEntityCheckBounds(this.xCoord, this.yCoord, this.zCoord);

    	        if ((entityCheckBounds != null) && (!this.worldObj.getEntitiesWithinAABB(Entity.class, entityCheckBounds).isEmpty())) {
    	          return MultiBlockState.INVALID;
    	        }
    	        this.currentPattern = map;
    	        return MultiBlockState.VALID;
    	      }
    	    }

    	    return MultiBlockState.INVALID;
    }

    /**private MultiBlockState getMasterBlockState()
    {
        Iterator var1 = this.patterns.iterator();
        MultiBlockPattern var2;
        boolean var3;

        do
        {
            if (!var1.hasNext())
            {
                return MultiBlockState.INVALID;
            }

            var2 = (MultiBlockPattern)var1.next();
            var3 = true;
            int xWidth = var2.getPatternWidthX();
            int zWidth = var2.getPatternWidthZ();
            int height = var2.getPatternHeight();
            int xOffset = this.xCoord - var2.getMasterOffsetX();
            int yOffset = this.yCoord - var2.getMasterOffsetY();
            int zOffset = this.zCoord - var2.getMasterOffsetZ();

            for (int x = 0; (x < xWidth) && (valid); x++) {
                for (int y = 0; (y < height) && (valid); y++) {
                  for (int z = 0; z < zWidth; z++) {
                            int xx = x + xOffset;
                            int yy = y + yOffset;
                            int zz = z + zOffset;

                            if (!this.worldObj.blockExists(var13, var14, var15))
                            {
                                return MultiBlockState.UNKNOWN;
                            }

                            if (!this.isMapPositionValid(var13, var14, var15, var2.getPatternMarker(var10, var11, var12)))
                            {
                            	var3 = false;
                                break;
                            }
                        }

                        ++var11;
                        break;
                    }
                }
            }
        }
        while (!var3);

        AxisAlignedBB var16 = var2.getEntityCheckBounds(this.xCoord, this.yCoord, this.zCoord);

        if (var16 != null && !this.worldObj.getEntitiesWithinAABB(Entity.class, var16).isEmpty())
        {
            return MultiBlockState.INVALID;
        }
        else
        {
            this.currentPattern = var2;
            return MultiBlockState.VALID;
        }
    }*

    private void grabNeighbors()
    {
        this.checkNeighbors = false;

        for (int var1 = 0; var1 < 6; ++var1)
        {
            this.neighbors[var1] = null;
            ForgeDirection var2 = ForgeDirection.getOrientation(var1);

            if (!JACTools.blockExistsOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, var2))
            {
                this.checkNeighbors = true;
            }
            else
            {
                TileEntity var3 = JACTools.getBlockTileEntityOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, var2);

                if (var3 != null && this.isStructureTile(var3))
                {
                    this.neighbors[var1] = (TileMultiBlock)var3;
                }
            }
        }
    }*/
    
    private void grabNeighbors() {
        this.checkNeighbors = false;
        for (int side = 0; side < 6; side++) {
          this.neighbors[side] = null;
          ForgeDirection dir = ForgeDirection.getOrientation(side);
          if (!JACTools.blockExistsOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir)) {
            this.checkNeighbors = true;
          }
          else {
        	  TileEntity tile = JACTools.getBlockTileEntityOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dir);
            if ((tile != null) && (isStructureTile(tile)))
              this.neighbors[side] = ((TileMultiBlock)tile);
          }
        }
      }

    public void onBlockAdded()
    {
        super.onBlockAdded();

        if (!Game.isNotHost(this.worldObj))
        {
            this.grabNeighbors();
            this.onBlockChange();
        }
    }

    public void onBlockRemoval()
    {
        super.onBlockRemoval();

        if (!Game.isNotHost(this.worldObj))
        {
            this.onBlockChange();
            this.isMaster = false;
        }
    }

    public void onChunkUnload()
    {
        super.onChunkUnload();

        if (!Game.isNotHost(this.worldObj))
        {
            this.tested = false;
            this.retestMasterBlock();
        }
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        if (this.worldObj == null || !Game.isNotHost(this.worldObj))
        {
            this.tested = false;
            this.retestMasterBlock();
            super.invalidate();
        }
    }

    /**private void onBlockChange()
    {
        for (int var1 = 0; var1 < 6; ++var1)
        {
            TileMultiBlock var2 = this.neighbors[var1];

            if (var2 != null)
            {
                var2.grabNeighbors();
                var2.onBlockChange(this.getMaxRecursionDepth());
            }
        }
    }

    private void onBlockChange(int var1)
    {
        --var1;

        if (var1 >= 0)
        {
            if (this.tested)
            {
                this.tested = false;

                for (int var2 = 0; var2 < 6; ++var2)
                {
                    TileMultiBlock var3 = this.neighbors[var2];

                    if (var3 != null && !var3.isInvalid())
                    {
                        TileMultiBlock var4 = var3.getMasterBlock();

                        if (var4 == null)
                        {
                            var3.onBlockChange(var1);
                        }
                        else
                        {
                            var4.grabNeighbors();
                            var4.onBlockChange(this.getMaxRecursionDepth());
                        }
                    }
                    else
                    {
                        this.neighbors[var2] = null;
                    }
                }
            }
        }
    }*/
    
    private void onBlockChange() {
        for (int side = 0; side < 6; side++) {
          TileMultiBlock tile = this.neighbors[side];
          if (tile != null) {
            tile.grabNeighbors();
            tile.onBlockChange(getMaxRecursionDepth());
          }
        }
      }

      private void onBlockChange(int depth) {
        depth--;
        if (depth < 0) {
          return;
        }
        if (this.tested) {
          this.tested = false;
          for (int side = 0; side < 6; side++) {
            TileMultiBlock tile = this.neighbors[side];
            if ((tile != null) && (!tile.isInvalid())) {
              TileMultiBlock mBlock = tile.getMasterBlock();
              if (mBlock == null) {
                tile.onBlockChange(depth);
              } else {
                mBlock.grabNeighbors();
                mBlock.onBlockChange(getMaxRecursionDepth());
              }
            } else {
              this.neighbors[side] = null;
            }
          }
        }
      }

    protected boolean isStructureTile(TileEntity var1)
    {
        return var1.getClass() == this.getClass();
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        super.onInventoryChanged();

        if (!this.isMaster)
        {
            TileMultiBlock var1 = this.getMasterBlock();

            if (var1 != null)
            {
                var1.onInventoryChanged();
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setBoolean("master", this.isMaster);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.isMaster = var1.getBoolean("master");
    }

    public void writePacketData(DataOutputStream var1) throws IOException
    {
        super.writePacketData(var1);
        boolean var2 = this.getMasterBlock() != null;
        var1.writeBoolean(var2);

        if (var2)
        {
            byte var3 = this.getPatternIndex();
            var1.writeByte(var3);
            var1.writeByte(this.patternX);
            var1.writeByte(this.patternY);
            var1.writeByte(this.patternZ);
        }
    }

    public void readPacketData(DataInputStream var1) throws IOException
    {
        super.readPacketData(var1);
        this.requestPacket = false;
        boolean var2 = false;
        boolean var3 = var1.readBoolean();

        if (var3)
        {
            byte var4 = var1.readByte();
            var4 = (byte)Math.max(var4, 0);
            var4 = (byte)Math.min(var4, this.patterns.size() - 1);
            MultiBlockPattern var5 = (MultiBlockPattern)this.patterns.get(var4);
            byte var6 = var1.readByte();
            byte var7 = var1.readByte();
            byte var8 = var1.readByte();

            if (this.patternX != var6 || this.patternY != var7 || this.patternZ != var8)
            {
                this.patternX = var6;
                this.patternY = var7;
                this.patternZ = var8;
                var2 = true;
            }

            this.isMaster = var6 == var5.getMasterOffsetX() && var7 == var5.getMasterOffsetY() && var8 == var5.getMasterOffsetZ();
            this.setPattern(var5);
            int var9 = var5.getMasterRelativeX(this.xCoord, var6);
            int var10 = var5.getMasterRelativeY(this.yCoord, var7);
            int var11 = var5.getMasterRelativeZ(this.zCoord, var8);
            TileEntity var12 = null;

            if (this.worldObj != null)
            {
                var12 = this.worldObj.getBlockTileEntity(var9, var10, var11);
            }

            if (var12 != null && this.masterBlock != var12 && this.isStructureTile(var12))
            {
                var2 = true;
                this.masterBlock = (TileMultiBlock)var12;
            }

            if (this.getMasterBlock() == null)
            {
                this.requestPacket = true;
            }
        }
        else if (this.masterBlock != null)
        {
            var2 = true;
            this.masterBlock = null;
            this.isMaster = false;
        }

        if (var2)
        {
            this.markBlockForUpdate();
        }
    }

    public final boolean isMaster()
    {
        return this.isMaster;
    }

    public final void setMaster(boolean var1)
    {
        this.isMaster = var1;
    }

    public final void retestMasterBlock()
    {
        if (this.masterBlock != null)
        {
            this.masterBlock.tested = false;
        }
    }

    public final boolean isStructureValid()
    {
      return (this.masterBlock != null) && (this.masterBlock.tested) && (this.masterBlock.isMaster) && (!this.masterBlock.isInvalid());
    }

    public final TileMultiBlock getMasterBlock() {
      if ((this.masterBlock != null) && (!isStructureValid())) {
        this.masterBlock = null;
        sendUpdateToClient();
      }
      return this.masterBlock;
    }

    public boolean canCreatureSpawn(EnumCreatureType var1)
    {
        return this.isStructureValid() && this.getPatternPositionY() < 2 ? false : super.canCreatureSpawn(var1);
    }
    
    enum MultiBlockState
    {
        VALID,
        INVALID,
        UNKNOWN;
    }
}

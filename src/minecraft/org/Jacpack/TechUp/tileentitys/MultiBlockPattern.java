package org.Jacpack.TechUp.tileentitys;

import net.minecraft.util.AxisAlignedBB;

public class MultiBlockPattern
{
    public final char[][][] pattern;
    private final int offsetX;
    private final int offsetY;
    private final int offsetZ;
    private final AxisAlignedBB entityCheckBounds;
    
    public MultiBlockPattern(char[][][] pattern)
    {
      this(pattern, 1, 1, 1);
    }

    public MultiBlockPattern(char[][][] pattern, int offsetX, int offsetY, int offsetZ) {
      this(pattern, offsetX, offsetY, offsetZ, null);
    }

    public MultiBlockPattern(char[][][] pattern, int offsetX, int offsetY, int offsetZ, AxisAlignedBB entityCheckBounds) {
      this.pattern = pattern;
      this.offsetX = offsetX;
      this.offsetY = offsetY;
      this.offsetZ = offsetZ;
      this.entityCheckBounds = entityCheckBounds;
    }

    public AxisAlignedBB getEntityCheckBounds(int masterX, int masterY, int masterZ) {
        if (this.entityCheckBounds == null) {
            return null;
          }
          return this.entityCheckBounds.copy().offset(masterX, masterY, masterZ);
        }

    public char getPatternMarkerChecked(int x, int y, int z) {
        if ((x < 0) || (y < 0) || (z < 0)) {
            return 'O';
          }
          if ((x >= getPatternWidthX()) || (y >= getPatternHeight()) || (z >= getPatternWidthZ())) {
            return 'O';
          }
          return getPatternMarker(x, y, z);
    }

    public char getPatternMarker(int x, int y, int z) {
        return this.pattern[y][x][z];
      }

      public int getMasterOffsetX() {
        return this.offsetX;
      }

      public int getMasterOffsetY() {
        return this.offsetY;
      }

      public int getMasterOffsetZ() {
        return this.offsetZ;
      }

      public int getPatternHeight() {
        return this.pattern.length;
      }

      public int getPatternWidthX() {
        return this.pattern[0].length;
      }

      public int getPatternWidthZ() {
        return this.pattern[0][0].length;
      }

      public int getMasterRelativeX(int posX, int patternX) {
        return this.offsetX - patternX + posX;
      }

      public int getMasterRelativeY(int posY, int patternY) {
        return this.offsetY - patternY + posY;
      }

      public int getMasterRelativeZ(int posZ, int patternZ) {
        return this.offsetZ - patternZ + posZ;
      }
}

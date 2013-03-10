package org.Jacpack.TechUp.client.render;

import net.minecraft.block.Block;

public class FakeBlockRenderInfo
{
    public Block template;
    public int[] texture;
    public float minX;
    public float minY;
    public float minZ;
    public float maxX;
    public float maxY;
    public float maxZ;
    public boolean renderTop;
    public boolean renderBottom;
    public boolean renderNorth;
    public boolean renderSouth;
    public boolean renderEast;
    public boolean renderWest;
    public float light;
    public int brightness;

    public FakeBlockRenderInfo()
    {
        this.template = Block.stone;
        this.texture = new int[] { -1};
        this.minX = 0.0F;
        this.minY = 0.0F;
        this.minZ = 0.0F;
        this.maxX = 1.0F;
        this.maxY = 1.0F;
        this.maxZ = 1.0F;
        this.renderTop = true;
        this.renderBottom = true;
        this.renderNorth = true;
        this.renderSouth = true;
        this.renderEast = true;
        this.renderWest = true;
        this.light = -1.0F;
        this.brightness = -1;
    }

    public FakeBlockRenderInfo(Block var1, int[] var2)
    {
        this.template = Block.stone;
        this.texture = new int[] { -1};
        this.minX = 0.0F;
        this.minY = 0.0F;
        this.minZ = 0.0F;
        this.maxX = 1.0F;
        this.maxY = 1.0F;
        this.maxZ = 1.0F;
        this.renderTop = true;
        this.renderBottom = true;
        this.renderNorth = true;
        this.renderSouth = true;
        this.renderEast = true;
        this.renderWest = true;
        this.light = -1.0F;
        this.brightness = -1;
        this.template = var1;
        this.texture = var2;
    }

    public FakeBlockRenderInfo(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        this.template = Block.stone;
        this.texture = new int[] { -1};
        this.minX = 0.0F;
        this.minY = 0.0F;
        this.minZ = 0.0F;
        this.maxX = 1.0F;
        this.maxY = 1.0F;
        this.maxZ = 1.0F;
        this.renderTop = true;
        this.renderBottom = true;
        this.renderNorth = true;
        this.renderSouth = true;
        this.renderEast = true;
        this.renderWest = true;
        this.light = -1.0F;
        this.brightness = -1;
        this.minX = var1;
        this.minY = var2;
        this.minZ = var3;
        this.maxX = var4;
        this.maxY = var5;
        this.maxZ = var6;
    }

    public void rotate()
    {
        float var1 = this.minX;
        this.minX = this.minZ;
        this.minZ = var1;
        var1 = this.maxX;
        this.maxX = this.maxZ;
        this.maxZ = var1;
    }

    public void reverseX()
    {
        float var1 = this.minX;
        this.minX = 1.0F - this.maxX;
        this.maxX = 1.0F - var1;
    }

    public void reverseZ()
    {
        float var1 = this.minZ;
        this.minZ = 1.0F - this.maxZ;
        this.maxZ = 1.0F - var1;
    }

    public int getBlockTextureFromSide(int var1)
    {
        if (this.texture[0] == -1)
        {
            return this.template.getBlockTextureFromSide(var1);
        }
        else
        {
            if (var1 >= this.texture.length)
            {
                var1 = 0;
            }

            return this.texture[var1];
        }
    }
}

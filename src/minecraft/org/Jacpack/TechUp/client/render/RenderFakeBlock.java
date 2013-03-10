package org.Jacpack.TechUp.client.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderFakeBlock
{
    public static void renderBlockForEntity(FakeBlockRenderInfo var0, IBlockAccess var1, int var2, int var3, int var4, boolean var5, boolean var6)
    {
        renderBlock(var0, var1, -0.5D, -0.5D, -0.5D, var2, var3, var4, var5, var6);
    }

    public static void renderBlock(FakeBlockRenderInfo var0, IBlockAccess var1, double var2, double var4, double var6, boolean var8, boolean var9)
    {
        renderBlock(var0, var1, var2, var4, var6, (int)var2, (int)var4, (int)var6, var8, var9);
    }

    public static void renderBlock(FakeBlockRenderInfo var0, IBlockAccess var1, double var2, double var4, double var6, int var8, int var9, int var10, boolean var11, boolean var12)
    {
        float var13 = 0.5F;
        float var14 = 1.0F;
        float var15 = 0.8F;
        float var16 = 0.6F;
        Tessellator var17 = Tessellator.instance;

        if (var1 == null)
        {
            var11 = false;
        }

        if (var12 && !var17.isDrawing)
        {
            var17.startDrawingQuads();
        }

        if (var0.brightness >= 0)
        {
            var17.setBrightness(var0.brightness);
        }

        float var18 = 0.0F;

        if (var11)
        {
            if (var0.light < 0.0F)
            {
                var18 = var0.template.getBlockBrightness(var1, var8, var9, var10);
                var18 += (1.0F - var18) * 0.4F;
            }
            else
            {
                var18 = var0.light;
            }

            boolean var19 = false;
            int var20;

            if (var0.brightness < 0)
            {
                var20 = var0.template.getMixedBrightnessForBlock(var1, var8, var9, var10);
            }
            else
            {
                var20 = var0.brightness;
            }

            var17.setBrightness(var20);
            var17.setColorOpaque_F(var13 * var18, var13 * var18, var13 * var18);
        }

        if (var0.renderBottom)
        {
            renderBottomFace(var0, var2, var4, var6, var0.getBlockTextureFromSide(0));
        }

        if (var11)
        {
            var17.setColorOpaque_F(var14 * var18, var14 * var18, var14 * var18);
        }

        if (var0.renderTop)
        {
            renderTopFace(var0, var2, var4, var6, var0.getBlockTextureFromSide(1));
        }

        if (var11)
        {
            var17.setColorOpaque_F(var15 * var18, var15 * var18, var15 * var18);
        }

        if (var0.renderWest)
        {
            renderEastFace(var0, var2, var4, var6, var0.getBlockTextureFromSide(2));
        }

        if (var11)
        {
            var17.setColorOpaque_F(var15 * var18, var15 * var18, var15 * var18);
        }

        if (var0.renderEast)
        {
            renderWestFace(var0, var2, var4, var6, var0.getBlockTextureFromSide(3));
        }

        if (var11)
        {
            var17.setColorOpaque_F(var16 * var18, var16 * var18, var16 * var18);
        }

        if (var0.renderNorth)
        {
            renderNorthFace(var0, var2, var4, var6, var0.getBlockTextureFromSide(4));
        }

        if (var11)
        {
            var17.setColorOpaque_F(var16 * var18, var16 * var18, var16 * var18);
        }

        if (var0.renderSouth)
        {
            renderSouthFace(var0, var2, var4, var6, var0.getBlockTextureFromSide(5));
        }

        if (var12 && var17.isDrawing)
        {
            var17.draw();
        }
    }

    public static void renderBottomFace(FakeBlockRenderInfo var0, double var1, double var3, double var5, int var7)
    {
        Tessellator var8 = Tessellator.instance;
        int var9 = (var7 & 15) << 4;
        int var10 = var7 & 240;
        double var11 = ((double)var9 + (double)var0.minX * 16.0D) / 256.0D;
        double var13 = ((double)var9 + (double)var0.maxX * 16.0D - 0.01D) / 256.0D;
        double var15 = ((double)var10 + (double)var0.minZ * 16.0D) / 256.0D;
        double var17 = ((double)var10 + (double)var0.maxZ * 16.0D - 0.01D) / 256.0D;

        if ((double)var0.minX < 0.0D || (double)var0.maxX > 1.0D)
        {
            var11 = (double)(((float)var9 + 0.0F) / 256.0F);
            var13 = (double)(((float)var9 + 15.99F) / 256.0F);
        }

        if ((double)var0.minZ < 0.0D || (double)var0.maxZ > 1.0D)
        {
            var15 = (double)(((float)var10 + 0.0F) / 256.0F);
            var17 = (double)(((float)var10 + 15.99F) / 256.0F);
        }

        double var19 = var1 + (double)var0.minX;
        double var21 = var1 + (double)var0.maxX;
        double var23 = var3 + (double)var0.minY;
        double var25 = var5 + (double)var0.minZ;
        double var27 = var5 + (double)var0.maxZ;
        var8.addVertexWithUV(var19, var23, var27, var11, var17);
        var8.addVertexWithUV(var19, var23, var25, var11, var15);
        var8.addVertexWithUV(var21, var23, var25, var13, var15);
        var8.addVertexWithUV(var21, var23, var27, var13, var17);
    }

    public static void renderTopFace(FakeBlockRenderInfo var0, double var1, double var3, double var5, int var7)
    {
        Tessellator var8 = Tessellator.instance;
        int var9 = (var7 & 15) << 4;
        int var10 = var7 & 240;
        double var11 = ((double)var9 + (double)var0.minX * 16.0D) / 256.0D;
        double var13 = ((double)var9 + (double)var0.maxX * 16.0D - 0.01D) / 256.0D;
        double var15 = ((double)var10 + (double)var0.minZ * 16.0D) / 256.0D;
        double var17 = ((double)var10 + (double)var0.maxZ * 16.0D - 0.01D) / 256.0D;

        if ((double)var0.minX < 0.0D || (double)var0.maxX > 1.0D)
        {
            var11 = (double)(((float)var9 + 0.0F) / 256.0F);
            var13 = (double)(((float)var9 + 15.99F) / 256.0F);
        }

        if ((double)var0.minZ < 0.0D || (double)var0.maxZ > 1.0D)
        {
            var15 = (double)(((float)var10 + 0.0F) / 256.0F);
            var17 = (double)(((float)var10 + 15.99F) / 256.0F);
        }

        double var19 = var1 + (double)var0.minX;
        double var21 = var1 + (double)var0.maxX;
        double var23 = var3 + (double)var0.maxY;
        double var25 = var5 + (double)var0.minZ;
        double var27 = var5 + (double)var0.maxZ;
        var8.addVertexWithUV(var21, var23, var27, var13, var17);
        var8.addVertexWithUV(var21, var23, var25, var13, var15);
        var8.addVertexWithUV(var19, var23, var25, var11, var15);
        var8.addVertexWithUV(var19, var23, var27, var11, var17);
    }

    public static void renderEastFace(FakeBlockRenderInfo var0, double var1, double var3, double var5, int var7)
    {
        Tessellator var8 = Tessellator.instance;
        int var9 = (var7 & 15) << 4;
        int var10 = var7 & 240;
        double var11 = ((double)var9 + (double)var0.minX * 16.0D) / 256.0D;
        double var13 = ((double)var9 + (double)var0.maxX * 16.0D - 0.01D) / 256.0D;
        double var15 = ((double)var10 + (double)var0.minY * 16.0D) / 256.0D;
        double var17 = ((double)var10 + (double)var0.maxY * 16.0D - 0.01D) / 256.0D;

        if ((double)var0.minX < 0.0D || (double)var0.maxX > 1.0D)
        {
            var11 = (double)(((float)var9 + 0.0F) / 256.0F);
            var13 = (double)(((float)var9 + 15.99F) / 256.0F);
        }

        if ((double)var0.minY < 0.0D || (double)var0.maxY > 1.0D)
        {
            var15 = (double)(((float)var10 + 0.0F) / 256.0F);
            var17 = (double)(((float)var10 + 15.99F) / 256.0F);
        }

        double var19 = var1 + (double)var0.minX;
        double var21 = var1 + (double)var0.maxX;
        double var23 = var3 + (double)var0.minY;
        double var25 = var3 + (double)var0.maxY;
        double var27 = var5 + (double)var0.minZ;
        var8.addVertexWithUV(var19, var25, var27, var13, var15);
        var8.addVertexWithUV(var21, var25, var27, var11, var15);
        var8.addVertexWithUV(var21, var23, var27, var11, var17);
        var8.addVertexWithUV(var19, var23, var27, var13, var17);
    }

    public static void renderWestFace(FakeBlockRenderInfo var0, double var1, double var3, double var5, int var7)
    {
        Tessellator var8 = Tessellator.instance;
        int var9 = (var7 & 15) << 4;
        int var10 = var7 & 240;
        double var11 = ((double)var9 + (double)var0.minX * 16.0D) / 256.0D;
        double var13 = ((double)var9 + (double)var0.maxX * 16.0D - 0.01D) / 256.0D;
        double var15 = ((double)var10 + (double)var0.minY * 16.0D) / 256.0D;
        double var17 = ((double)var10 + (double)var0.maxY * 16.0D - 0.01D) / 256.0D;

        if ((double)var0.minX < 0.0D || (double)var0.maxX > 1.0D)
        {
            var11 = (double)(((float)var9 + 0.0F) / 256.0F);
            var13 = (double)(((float)var9 + 15.99F) / 256.0F);
        }

        if ((double)var0.minY < 0.0D || (double)var0.maxY > 1.0D)
        {
            var15 = (double)(((float)var10 + 0.0F) / 256.0F);
            var17 = (double)(((float)var10 + 15.99F) / 256.0F);
        }

        double var19 = var1 + (double)var0.minX;
        double var21 = var1 + (double)var0.maxX;
        double var23 = var3 + (double)var0.minY;
        double var25 = var3 + (double)var0.maxY;
        double var27 = var5 + (double)var0.maxZ;
        var8.addVertexWithUV(var19, var25, var27, var11, var15);
        var8.addVertexWithUV(var19, var23, var27, var11, var17);
        var8.addVertexWithUV(var21, var23, var27, var13, var17);
        var8.addVertexWithUV(var21, var25, var27, var13, var15);
    }

    public static void renderNorthFace(FakeBlockRenderInfo var0, double var1, double var3, double var5, int var7)
    {
        Tessellator var8 = Tessellator.instance;
        int var9 = (var7 & 15) << 4;
        int var10 = var7 & 240;
        double var11 = ((double)var9 + (double)var0.minZ * 16.0D) / 256.0D;
        double var13 = ((double)var9 + (double)var0.maxZ * 16.0D - 0.01D) / 256.0D;
        double var15 = ((double)var10 + (double)var0.minY * 16.0D) / 256.0D;
        double var17 = ((double)var10 + (double)var0.maxY * 16.0D - 0.01D) / 256.0D;

        if ((double)var0.minZ < 0.0D || (double)var0.maxZ > 1.0D)
        {
            var11 = (double)(((float)var9 + 0.0F) / 256.0F);
            var13 = (double)(((float)var9 + 15.99F) / 256.0F);
        }

        if ((double)var0.minY < 0.0D || (double)var0.maxY > 1.0D)
        {
            var15 = (double)(((float)var10 + 0.0F) / 256.0F);
            var17 = (double)(((float)var10 + 15.99F) / 256.0F);
        }

        double var19 = var1 + (double)var0.minX;
        double var21 = var3 + (double)var0.minY;
        double var23 = var3 + (double)var0.maxY;
        double var25 = var5 + (double)var0.minZ;
        double var27 = var5 + (double)var0.maxZ;
        var8.addVertexWithUV(var19, var23, var27, var13, var15);
        var8.addVertexWithUV(var19, var23, var25, var11, var15);
        var8.addVertexWithUV(var19, var21, var25, var11, var17);
        var8.addVertexWithUV(var19, var21, var27, var13, var17);
    }

    public static void renderSouthFace(FakeBlockRenderInfo var0, double var1, double var3, double var5, int var7)
    {
        Tessellator var8 = Tessellator.instance;
        int var9 = (var7 & 15) << 4;
        int var10 = var7 & 240;
        double var11 = ((double)var9 + (double)var0.minZ * 16.0D) / 256.0D;
        double var13 = ((double)var9 + (double)var0.maxZ * 16.0D - 0.01D) / 256.0D;
        double var15 = ((double)var10 + (double)var0.minY * 16.0D) / 256.0D;
        double var17 = ((double)var10 + (double)var0.maxY * 16.0D - 0.01D) / 256.0D;

        if ((double)var0.minZ < 0.0D || (double)var0.maxZ > 1.0D)
        {
            var11 = (double)(((float)var9 + 0.0F) / 256.0F);
            var13 = (double)(((float)var9 + 15.99F) / 256.0F);
        }

        if ((double)var0.minY < 0.0D || (double)var0.maxY > 1.0D)
        {
            var15 = (double)(((float)var10 + 0.0F) / 256.0F);
            var17 = (double)(((float)var10 + 15.99F) / 256.0F);
        }

        double var19 = var1 + (double)var0.maxX;
        double var21 = var3 + (double)var0.minY;
        double var23 = var3 + (double)var0.maxY;
        double var25 = var5 + (double)var0.minZ;
        double var27 = var5 + (double)var0.maxZ;
        var8.addVertexWithUV(var19, var21, var27, var11, var17);
        var8.addVertexWithUV(var19, var21, var25, var13, var17);
        var8.addVertexWithUV(var19, var23, var25, var13, var15);
        var8.addVertexWithUV(var19, var23, var27, var11, var15);
    }
}
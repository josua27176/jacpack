package org.Jacpack.TechUp.client.gui;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.liquids.LiquidStack;

import org.Jacpack.TechUp.api.inventory.ItemStackMap;
import org.Jacpack.TechUp.client.render.FakeBlockRenderInfo;
import org.Jacpack.TechUp.client.render.RenderFakeBlock;
import org.lwjgl.opengl.GL11;

public class LiquidRenderer
{
    private static Map stage = new ItemStackMap();
    public static final int DISPLAY_STAGES = 100;
    private static final FakeBlockRenderInfo liquidBlock = new FakeBlockRenderInfo();

    public static int bindLiquidTexture(LiquidStack var0)
    {
        if (var0 != null && var0.amount > 0 && var0.itemID > 0)
        {
            String var1;
            int var2;

            if (var0.itemID < Block.blocksList.length && Block.blocksList[var0.itemID] != null)
            {
                var1 = Block.blocksList[var0.itemID].getTextureFile();
                var2 = Block.blocksList[var0.itemID].getBlockTextureFromSideAndMetadata(1, var0.itemMeta);
            }
            else
            {
                if (Item.itemsList[var0.itemID] == null)
                {
                    return -1;
                }

                var1 = Item.itemsList[var0.itemID].getTextureFile();
                var2 = Item.itemsList[var0.itemID].getIconFromDamage(var0.itemMeta);
            }

            ForgeHooksClient.bindTexture(var1, 0);
            return var2;
        }
        else
        {
            return -1;
        }
    }

    public static int[] getLiquidDisplayLists(LiquidStack var0)
    {
        if (var0 == null)
        {
            return null;
        }
        else
        {
            ItemStack var1 = var0.asItemStack();
            int[] var2 = (int[])stage.get(var1);

            if (var2 != null)
            {
                return var2;
            }
            else
            {
                var2 = new int[100];

                if (var0.itemID < Block.blocksList.length && Block.blocksList[var0.itemID] != null)
                {
                    liquidBlock.texture[0] = Block.blocksList[var0.itemID].getBlockTextureFromSideAndMetadata(1, var0.itemMeta);
                }
                else
                {
                    if (Item.itemsList[var0.itemID] == null)
                    {
                        return null;
                    }

                    liquidBlock.texture[0] = var1.getIconIndex();
                }

                stage.put(var1, var2);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                int var3 = var1.getItem().getColorFromItemStack(var1, 0);
                float var4 = (float)(var3 >> 16 & 255) / 255.0F;
                float var5 = (float)(var3 >> 8 & 255) / 255.0F;
                float var6 = (float)(var3 & 255) / 255.0F;
                GL11.glColor4f(var4, var5, var6, 1.0F);

                for (int var7 = 0; var7 < 100; ++var7)
                {
                    var2[var7] = GLAllocation.generateDisplayLists(1);
                    GL11.glNewList(var2[var7], GL11.GL_COMPILE);
                    liquidBlock.minX = 0.01F;
                    liquidBlock.minY = 0.0F;
                    liquidBlock.minZ = 0.01F;
                    liquidBlock.maxX = 0.99F;
                    liquidBlock.maxY = (float)var7 / 100.0F;
                    liquidBlock.maxZ = 0.99F;
                    RenderFakeBlock.renderBlockForEntity(liquidBlock, (IBlockAccess)null, 0, 0, 0, false, true);
                    GL11.glEndList();
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                return var2;
            }
        }
    }
}

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

    public static int bindLiquidTexture(LiquidStack liquid)
    {
        if (liquid != null && liquid.amount > 0 && liquid.itemID > 0)
        {
            String textureFile;
            int textureIndex;

            if (liquid.itemID < Block.blocksList.length && Block.blocksList[liquid.itemID] != null)
            {
            	textureFile = Block.blocksList[liquid.itemID].getTextureFile();
                textureIndex = Block.blocksList[liquid.itemID].getBlockTextureFromSideAndMetadata(1, liquid.itemMeta);
            }
            else
            {
                if (Item.itemsList[liquid.itemID] == null)
                {
                    return -1;
                }

                textureFile = Item.itemsList[liquid.itemID].getTextureFile();
                textureIndex = Item.itemsList[liquid.itemID].getIconFromDamage(liquid.itemMeta);
            }

            ForgeHooksClient.bindTexture(textureFile, 0);
            return textureIndex;
        }
        else
        {
            return -1;
        }
    }

    public static int[] getLiquidDisplayLists(LiquidStack liquid)
    {
        if (liquid == null)
        {
            return null;
        }
        else
        {
            ItemStack stack = liquid.asItemStack();
            int[] diplayLists = (int[])stage.get(stack);

            if (diplayLists != null)
            {
                return diplayLists;
            }
            else
            {
            	diplayLists = new int[100];

                if (liquid.itemID < Block.blocksList.length && Block.blocksList[liquid.itemID] != null)
                {
                    liquidBlock.texture[0] = Block.blocksList[liquid.itemID].getBlockTextureFromSideAndMetadata(1, liquid.itemMeta);
                }
                else
                {
                    if (Item.itemsList[liquid.itemID] == null)
                    {
                        return null;
                    }

                    liquidBlock.texture[0] = stack.getIconIndex();
                }

                stage.put(stack, diplayLists);
                
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                int color = stack.getItem().getColorFromItemStack(stack, 0);
                float c1 = (color >> 16 & 255) / 255.0F;
                float c2 = (color >> 8 & 255) / 255.0F;
                float c3 = (color & 255) / 255.0F;
                GL11.glColor4f(c1, c2, c3, 1.0F);

                for (int s = 0; s < 100; s++)
                {
                	diplayLists[s] = GLAllocation.generateDisplayLists(1);
                    GL11.glNewList(diplayLists[s], GL11.GL_COMPILE);
                    liquidBlock.minX = 0.01F;
                    liquidBlock.minY = 0.0F;
                    liquidBlock.minZ = 0.01F;
                    liquidBlock.maxX = 0.99F;
                    liquidBlock.maxY = (float)s / 100.0F;
                    liquidBlock.maxZ = 0.99F;
                    RenderFakeBlock.renderBlockForEntity(liquidBlock, (IBlockAccess)null, 0, 0, 0, false, true);
                    GL11.glEndList();
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                return diplayLists;
            }
        }
    }
}

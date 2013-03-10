package org.Jacpack.TechUp.client.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.liquids.LiquidStack;

import org.Jacpack.TechUp.gui.containers.JACContainer;
import org.Jacpack.TechUp.gui.indicator.Indicator;
import org.Jacpack.TechUp.gui.util.ToolTip;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainerJAC extends GuiContainer
{
    private final JACContainer container;
    public final String texturePath;

    public GuiContainerJAC(JACContainer var1, String var2)
    {
        super(var1);
        this.container = var1;
        this.texturePath = var2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        super.drawScreen(var1, var2, var3);
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        InventoryPlayer var6 = this.mc.thePlayer.inventory;

        if (var6.getItemStack() == null)
        {
            Iterator var7 = this.container.getGauges().iterator();
            List var9;

            var7 = this.container.getIndicators().iterator();

            while (var7.hasNext())
            {
                Indicator var10 = (Indicator)var7.next();

                if (this.isMouseOverIndicator(var10, var1, var2))
                {
                    var9 = var10.controller.getToolTip();

                    if (var9 != null)
                    {
                        this.drawToolTips(var9, var1, var2);
                    }

                    break;
                }
            }
        }

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        int var4 = this.mc.renderEngine.getTexture(this.texturePath);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        Iterator var7 = this.container.getGauges().iterator();

        var7 = this.container.getIndicators().iterator();

        while (var7.hasNext())
        {
            Indicator var9 = (Indicator)var7.next();
            this.drawIndicator(var9);
        }
    }

    private void drawButtons(int var1, int var2)
    {
        for (int var3 = 0; var3 < this.controlList.size(); ++var3)
        {
            GuiButton var4 = (GuiButton)this.controlList.get(var3);
            var4.drawButton(this.mc, var1, var2);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int var1, int var2, int var3)
    {
        super.mouseClicked(var1, var2, var3);

        if (var3 == 2)
        {
            Slot var4 = this.getSlotAtPosition(var1, var2);
            int var5 = this.guiLeft;
            int var6 = this.guiTop;
            boolean var7 = var1 < var5 || var2 < var6 || var1 >= var5 + this.xSize || var2 >= var6 + this.ySize;
            int var8 = -1;

            if (var4 != null)
            {
                var8 = var4.slotNumber;
            }

            if (var7)
            {
                var8 = -999;
            }

            if (var8 != -1)
            {
                boolean var9 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                this.handleMouseClick(var4, var8, var3, var9 ? 1 : 0);
            }
        }
    }

    private Slot getSlotAtPosition(int var1, int var2)
    {
        for (int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); ++var3)
        {
            Slot var4 = (Slot)this.inventorySlots.inventorySlots.get(var3);

            if (this.isMouseOverSlot(var4, var1, var2))
            {
                return var4;
            }
        }

        return null;
    }

    private boolean isMouseOverSlot(Slot var1, int var2, int var3)
    {
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        var2 -= var4;
        var3 -= var5;
        return var2 >= var1.xDisplayPosition - 1 && var2 < var1.xDisplayPosition + 16 + 1 && var3 >= var1.yDisplayPosition - 1 && var3 < var1.yDisplayPosition + 16 + 1;
    }

    private boolean isMouseOverIndicator(Indicator var1, int var2, int var3)
    {
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        var2 -= var4;
        var3 -= var5;
        return var2 >= var1.x - 1 && var2 < var1.x + var1.w + 1 && var3 >= var1.y - 1 && var3 < var1.y + var1.h + 1;
    }

    private void drawToolTips(List var1, int var2, int var3)
    {
        if (var1.size() > 0)
        {
            int var4 = this.guiLeft;
            int var5 = this.guiTop;
            int var6 = 0;
            Iterator var9 = var1.iterator();
            int var8;

            while (var9.hasNext())
            {
                ToolTip var10 = (ToolTip)var9.next();
                var8 = this.fontRenderer.getStringWidth(var10.text);

                if (var8 > var6)
                {
                    var6 = var8;
                }
            }

            int var7 = var2 - var4 + 12;
            var8 = var3 - var5 - 12;
            int var17 = 8;

            if (var1.size() > 1)
            {
                var17 += 2 + (var1.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int var18 = -267386864;
            this.drawGradientRect(var7 - 3, var8 - 4, var7 + var6 + 3, var8 - 3, var18, var18);
            this.drawGradientRect(var7 - 3, var8 + var17 + 3, var7 + var6 + 3, var8 + var17 + 4, var18, var18);
            this.drawGradientRect(var7 - 3, var8 - 3, var7 + var6 + 3, var8 + var17 + 3, var18, var18);
            this.drawGradientRect(var7 - 4, var8 - 3, var7 - 3, var8 + var17 + 3, var18, var18);
            this.drawGradientRect(var7 + var6 + 3, var8 - 3, var7 + var6 + 4, var8 + var17 + 3, var18, var18);
            int var11 = 1347420415;
            int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
            this.drawGradientRect(var7 - 3, var8 - 3 + 1, var7 - 3 + 1, var8 + var17 + 3 - 1, var11, var12);
            this.drawGradientRect(var7 + var6 + 2, var8 - 3 + 1, var7 + var6 + 3, var8 + var17 + 3 - 1, var11, var12);
            this.drawGradientRect(var7 - 3, var8 - 3, var7 + var6 + 3, var8 - 3 + 1, var11, var11);
            this.drawGradientRect(var7 - 3, var8 + var17 + 2, var7 + var6 + 3, var8 + var17 + 3, var12, var12);
            boolean var13 = true;

            for (Iterator var14 = var1.iterator(); var14.hasNext(); var8 += 10)
            {
                ToolTip var15 = (ToolTip)var14.next();
                String var16 = var15.text;

                if (var15.color == -1)
                {
                    var16 = "\u00a77" + var16;
                }
                else
                {
                    var16 = "\u00a7" + Integer.toHexString(var15.color) + var16;
                }

                this.fontRenderer.drawStringWithShadow(var16, var7, var8, -1);

                if (var13)
                {
                    var8 += 2;
                    var13 = false;
                }
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }
    }
    
    public void drawIndicator(Indicator var1)
    {
        int var2 = (this.width - this.xSize) / 2;
        int var3 = (this.height - this.ySize) / 2;
        int var4 = var1.controller.getScaledLevel(var1.h);
        this.drawTexturedModalRect(var2 + var1.x, var3 + var1.y + var1.h - var4, var1.u, var1.v + var1.h - var4, var1.w, var4);
    }
}

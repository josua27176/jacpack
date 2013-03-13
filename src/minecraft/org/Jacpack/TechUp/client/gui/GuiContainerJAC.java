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

    public GuiContainerJAC(JACContainer container, String texture)
    {
        super(container);
        this.container = container;
        this.texturePath = texture;
      }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float var3)
    {
        super.drawScreen(mouseX, mouseY, var3);
        int left = this.guiLeft;
        int top = this.guiTop;
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(left, top, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.disableStandardItemLighting();
        InventoryPlayer playerInv = this.mc.thePlayer.inventory;

        if (playerInv.getItemStack() == null)
        {
            Iterator Gauges = this.container.getGauges().iterator();
            List tips;

            Gauges = this.container.getIndicators().iterator();

            while (Gauges.hasNext())
            {
                Indicator indicator = (Indicator)Gauges.next();

                if (this.isMouseOverIndicator(indicator, mouseX, mouseY))
                {
                	tips = indicator.controller.getToolTip();

                    if (tips != null)
                    {
                        this.drawToolTips(tips, mouseX, mouseY);
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
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int texture = this.mc.renderEngine.getTexture(this.texturePath);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        Iterator Gauges = this.container.getGauges().iterator();

        Gauges = this.container.getIndicators().iterator();

        while (Gauges.hasNext())
        {
            Indicator indicator = (Indicator)Gauges.next();
            this.drawIndicator(indicator);
        }
    }

    private void drawButtons(int int1, int int2)
    {
        for (int int3 = 0; int3 < this.controlList.size(); int3++)
        {
            GuiButton button = (GuiButton)this.controlList.get(int3);
            button.drawButton(this.mc, int1, int2);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int mouseButton)
    {
        super.mouseClicked(i, j, mouseButton);

        if (mouseButton == 2)
        {
            Slot slot = this.getSlotAtPosition(i, j);
            int guiLeft = this.guiLeft;
            int guiTop = this.guiTop;
            boolean check = (i < guiLeft) || (j < guiTop) || (i >= guiLeft + this.xSize) || (j >= guiTop + this.ySize);
            int k = -1;

            if (slot != null)
            {
            	k = slot.slotNumber;
            }

            if (check)
            {
            	k = -999;
            }

            if (k != -1)
            {
                boolean check2 = k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                this.handleMouseClick(slot, k, mouseButton, check2 ? 1 : 0);
            }
        }
    }

    private Slot getSlotAtPosition(int i, int j)
    {
        for (int side = 0; side < this.inventorySlots.inventorySlots.size(); side++)
        {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(side);

            if (this.isMouseOverSlot(slot, i, j))
            {
                return slot;
            }
        }

        return null;
    }

    private boolean isMouseOverSlot(Slot par1Slot, int i, int j)
    {
        int k = this.guiLeft;
        int z = this.guiTop;
        i -= k;
        j -= z;
        return i >= par1Slot.xDisplayPosition - 1 && i < par1Slot.xDisplayPosition + 16 + 1 && j >= par1Slot.yDisplayPosition - 1 && j < par1Slot.yDisplayPosition + 16 + 1;
    }

    private boolean isMouseOverIndicator(Indicator gauge, int mouseX, int mouseY)
    {
        int left = this.guiLeft;
        int top = this.guiTop;
        mouseX -= left;
        mouseY -= top;
        return (mouseX >= gauge.x - 1) && (mouseX < gauge.x + gauge.w + 1) && (mouseY >= gauge.y - 1) && (mouseY < gauge.y + gauge.h + 1);
    }

    private void drawToolTips(List toolTips, int mouseX, int mouseY) {
        if (toolTips.size() > 0)
        {
            int left = this.guiLeft;
            int top  = this.guiTop;
            int lenght = 0;
            Iterator tip = toolTips.iterator();
            int y;

            while (tip.hasNext())
            {
                ToolTip var10 = (ToolTip)tip.next();
                y = this.fontRenderer.getStringWidth(var10.text);

                if (y > lenght)
                {
                	lenght = y;
                }
            }

            int x = mouseX - left + 12;
            y = mouseY - top - 12;
            int size = 8;

            if (toolTips.size() > 1)
            {
                size += 2 + (toolTips.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int random = -267386864;
            
            this.drawGradientRect(x - 3, y - 4, x + lenght + 3, y - 3, random, random);
            this.drawGradientRect(x - 3, y + size + 3, x + lenght + 3, y + size + 4, random, random);
            this.drawGradientRect(x - 3, y - 3, x + lenght + 3, y + size + 3, random, random);
            this.drawGradientRect(x - 4, y - 3, x - 3, y + size + 3, random, random);
            this.drawGradientRect(x + lenght + 3, y - 3, x + lenght + 4, y + size + 3, random, random);
            int random1 = 1347420415;
            int random2 = (random1 & 0xFEFEFE) >> 1 | random1 & 0xFF000000;
            this.drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + size + 3 - 1, random1, random2);
            this.drawGradientRect(x + lenght + 2, y - 3 + 1, x + lenght + 3, y + size + 3 - 1, random1, random2);
            this.drawGradientRect(x - 3, y - 3, x + lenght + 3, y - 3 + 1, random1, random1);
            this.drawGradientRect(x - 3, y + size + 2, x + lenght + 3, y + size + 3, random2, random2);
            
            boolean first = true;
            for (Iterator tip1 = toolTips.iterator(); tip1.hasNext(); y += 10)
            {
                ToolTip tip2 = (ToolTip)tip1.next();
                String string = tip2.text;

                if (tip2.color == -1)
                {
                	string = "\u00a77" + string;
                }
                else
                {
                	string = "\u00a7" + Integer.toHexString(tip2.color) + string;
                }

                this.fontRenderer.drawStringWithShadow(string, x, y, -1);

                if (first)
                {
                    y += 2;
                    first = false;
                }
            }

            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
        }
    }
    
    public void drawIndicator(Indicator indicator)
    {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        int scale = indicator.controller.getScaledLevel(indicator.h);
        this.drawTexturedModalRect(x + indicator.x, y + indicator.y + indicator.h - scale, indicator.u, indicator.v + indicator.h - scale, indicator.w, scale);
    }
}

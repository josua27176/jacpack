package org.Jacpack.TechUp.client.gui;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.gui.containers.ContainerBlastFurnace;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiBlastFurnace extends TileGui
{
    private TileBlastFurnace tile;

    public GuiBlastFurnace(InventoryPlayer var1, TileBlastFurnace var2)
    {
        super(var2, new ContainerBlastFurnace(var1, var2), "/gui/furnace.png");
        this.tile = var2;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int var1, int var2)
    {
        GuiTools.drawCenteredString(this.fontRenderer, "Blast Furnace", 6);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        super.drawGuiContainerBackgroundLayer(var1, var3, var3);
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        int var6;

        if (this.tile.isBurning())
        {
            var6 = this.tile.getBurnProgressScaled(12);
            this.drawTexturedModalRect(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 2);
        }

        var6 = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(var4 + 79, var5 + 34, 176, 14, var6 + 1, 16);
    }
}

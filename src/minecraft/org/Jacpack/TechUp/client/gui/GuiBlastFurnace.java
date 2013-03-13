package org.Jacpack.TechUp.client.gui;

import org.Jacpack.TechUp.api.JACTools;
import org.Jacpack.TechUp.gui.containers.ContainerBlastFurnace;
import org.Jacpack.TechUp.tileentitys.TileBlastFurnace;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiBlastFurnace extends TileGui
{
    private TileBlastFurnace tile;

    public GuiBlastFurnace(InventoryPlayer par1InventoryPlayer, TileBlastFurnace tile)
    {
        super(tile, new ContainerBlastFurnace(par1InventoryPlayer, tile), "/gui/furnace.png");
        this.tile = tile;
      }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        GuiTools.drawCenteredString(this.fontRenderer, "Blast Furnace", 6);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
      super.drawGuiContainerBackgroundLayer(par1, par3, par3);
      int x = (this.width - this.xSize) / 2;
      int y = (this.height - this.ySize) / 2;

      if (this.tile.isBurning()) {
        int scale = this.tile.getBurnProgressScaled(12);
        this.drawTexturedModalRect(x + 56, y + 36 + 12 - scale, 176, 12 - scale, 14, scale + 2);
      }

      int scale = this.tile.getCookProgressScaled(24);
      this.drawTexturedModalRect(x + 79, y + 34, 176, 14, scale + 1, 16);
    }
}

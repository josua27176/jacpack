package org.Jacpack.TechUp.client.gui;

import org.Jacpack.TechUp.gui.containers.JACContainer;
import org.Jacpack.TechUp.tileentitys.JACTileEnityMultiBlocks;

public abstract class TileGui extends GuiContainerJAC
{
    private final JACTileEnityMultiBlocks tile;

    public TileGui(JACTileEnityMultiBlocks tile, JACContainer container, String texture)
    {
        super(container, texture);
        this.tile = tile;
      }
}

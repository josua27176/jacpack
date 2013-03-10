package org.Jacpack.TechUp.client.gui;

import org.Jacpack.TechUp.gui.containers.JACContainer;
import org.Jacpack.TechUp.tileentitys.JACTileEnityMultiBlocks;

public abstract class TileGui extends GuiContainerJAC
{
    private final JACTileEnityMultiBlocks tile;

    public TileGui(JACTileEnityMultiBlocks var1, JACContainer var2, String var3)
    {
        super(var2, var3);
        this.tile = var1;
    }
}

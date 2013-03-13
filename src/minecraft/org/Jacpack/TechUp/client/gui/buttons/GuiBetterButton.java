package org.Jacpack.TechUp.client.gui.buttons;

import net.minecraft.client.gui.GuiButton;

public class GuiBetterButton extends GuiButton
{
    public GuiBetterButton(int id, int x, int y, String label)
    {
        this(id, x, y, 200, 20, label);
    }

    public GuiBetterButton(int id, int x, int y, int width, int height, String label)
    {
        super(id, x, y, width, height, label);
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }
}

package org.Jacpack.TechUp.client.gui.buttons;

import net.minecraft.client.gui.GuiButton;

public class GuiBetterButton extends GuiButton
{
    public GuiBetterButton(int var1, int var2, int var3, String var4)
    {
        this(var1, var2, var3, 200, 20, var4);
    }

    public GuiBetterButton(int var1, int var2, int var3, int var4, int var5, String var6)
    {
        super(var1, var2, var3, var4, var5, var6);
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

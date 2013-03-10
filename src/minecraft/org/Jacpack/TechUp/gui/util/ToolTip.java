package org.Jacpack.TechUp.gui.util;

public class ToolTip
{
    public String text;
    public final int color;

    public ToolTip(String var1, int var2)
    {
        this.text = var1;
        this.color = var2;
    }

    public ToolTip(String var1)
    {
        this(var1, -1);
    }

    public ToolTip()
    {
        this("", -1);
    }
}

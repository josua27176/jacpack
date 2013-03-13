package org.Jacpack.TechUp.gui.util;

public class ToolTip
{
    public String text;
    public final int color;

    public ToolTip(String text, int color)
    {
        this.text = text;
        this.color = color;
    }

    public ToolTip(String text)
    {
        this(text, -1);
    }

    public ToolTip()
    {
        this("", -1);
    }
}

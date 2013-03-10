package org.Jacpack.TechUp.gui.indicator;

public class Indicator
{
    public final IIndicatorController controller;
    public final int x;
    public final int y;
    public final int h;
    public final int w;
    public final int u;
    public final int v;

    public Indicator(IIndicatorController var1, int var2, int var3, int var4, int var5, int var6, int var7)
    {
        this.controller = var1;
        this.x = var2;
        this.y = var3;
        this.h = var5;
        this.w = var4;
        this.u = var6;
        this.v = var7;
    }
}

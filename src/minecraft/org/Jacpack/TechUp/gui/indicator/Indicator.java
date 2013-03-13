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

    public Indicator(IIndicatorController controller, int x, int y, int w, int h, int u, int v)
    {
    	this.controller = controller;
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.u = u;
        this.v = v;
      }
}

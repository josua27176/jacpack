package org.Jacpack.TechUp.client.gui;

import java.util.Iterator;
import java.util.List;

import org.Jacpack.TechUp.client.gui.buttons.GuiBetterButton;

import net.minecraft.client.gui.FontRenderer;

public class GuiTools
{
    public static void drawCenteredString(FontRenderer var0, String var1, int var2)
    {
        drawCenteredString(var0, var1, var2, 176);
    }

    public static void drawCenteredString(FontRenderer var0, String var1, int var2, int var3)
    {
        drawCenteredString(var0, var1, var2, var3, 4210752);
    }

    public static void drawCenteredString(FontRenderer var0, String var1, int var2, int var3, int var4)
    {
        int var5 = var0.getStringWidth(var1);
        int var6 = var3 / 2 - var5 / 2;
        var0.drawString(var1, var6, var2, 4210752);
    }

    public static void newButtonRowAuto(List var0, int var1, int var2, List var3)
    {
        int var4 = 0;
        GuiBetterButton var6;

        for (Iterator var5 = var3.iterator(); var5.hasNext(); var4 += var6.getWidth())
        {
            var6 = (GuiBetterButton)var5.next();
        }

        int var10 = var2 - var4;
        int var11 = var10 / (var3.size() + 1);
        int var7 = 0;
        Iterator var8 = var3.iterator();

        while (var8.hasNext())
        {
            GuiBetterButton var9 = (GuiBetterButton)var8.next();
            var7 += var11;
            var9.xPosition = var1 + var7;
            var7 += var9.getWidth();
            var0.add(var9);
        }
    }

    public static void newButtonRow(List var0, int var1, int var2, List var3)
    {
        int var4 = 0;
        Iterator var5 = var3.iterator();

        while (var5.hasNext())
        {
            GuiBetterButton var6 = (GuiBetterButton)var5.next();
            var6.xPosition = var1 + var4;
            var4 += var6.getWidth() + var2;
            var0.add(var6);
        }
    }
}

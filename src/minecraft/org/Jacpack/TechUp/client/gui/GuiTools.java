package org.Jacpack.TechUp.client.gui;

import java.util.Iterator;
import java.util.List;

import org.Jacpack.TechUp.client.gui.buttons.GuiBetterButton;

import net.minecraft.client.gui.FontRenderer;

public class GuiTools
{
    public static void drawCenteredString(FontRenderer fr, String s, int y)
    {
        drawCenteredString(fr, s, y, 176);
    }

    public static void drawCenteredString(FontRenderer fr, String s, int y, int guiWidth)
    {
        drawCenteredString(fr, s, y, guiWidth, 4210752);
    }

    public static void drawCenteredString(FontRenderer fr, String s, int y, int guiWidth, int color)
    {
        int sWidth = fr.getStringWidth(s);
        int sPos = guiWidth / 2 - sWidth / 2;
        fr.drawString(s, sPos, y, 4210752);
    }

    public static void newButtonRowAuto(List controlList, int xStart, int xSize, List buttons)
    {
        int buttonWidth = 0;
        GuiBetterButton b;

        for (Iterator b1 = buttons.iterator(); b1.hasNext(); buttonWidth += b.getWidth())
        {
            b = (GuiBetterButton)b1.next();
        }

        int remaining = xSize - buttonWidth;
        int spacing = remaining / (buttons.size() + 1);
        int pointer = 0;
        
        Iterator b2 = buttons.iterator();

        while (b2.hasNext())
        {
            GuiBetterButton b3 = (GuiBetterButton)b2.next();
            pointer += spacing;
            b3.xPosition = (xStart + pointer);
            pointer += b3.getWidth();
            controlList.add(b3);
        }
    }

    public static void newButtonRow(List controlList, int xStart, int spacing, List buttons)
    {
        int pointer = 0;
        Iterator b1 = buttons.iterator();

        while (b1.hasNext())
        {
            GuiBetterButton b = (GuiBetterButton)b1.next();
            b.xPosition = (xStart + pointer);
            pointer += b.getWidth() + spacing;
            controlList.add(b);
        }
    }
}

package org.Jacpack.TechUp.util.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.world.World;

public interface IGuiReturnHandler
{
    World getWorld();

    void writeGuiData(DataOutputStream var1) throws IOException;

    void readGuiData(DataInputStream var1) throws IOException;
}

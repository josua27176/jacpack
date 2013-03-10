package org.Jacpack.TechUp.util.network;

import java.io.DataInputStream;
import java.io.IOException;

public interface ITileExtraDataHandler
{
    void onUpdatePacket(DataInputStream var1) throws IOException;

    int getX();

    int getY();

    int getZ();
}

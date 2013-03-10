package org.Jacpack.TechUp.api.old;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.world.World;

public interface INetworkedObject
{
    World getWorld();

    void writePacketData(DataOutputStream var1) throws IOException;

    void readPacketData(DataInputStream var1) throws IOException;
}

package org.Jacpack.TechUp.api.machines;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.world.World;

public interface INetworkedObject
{
    World getWorld();

    void writePacketData(DataOutputStream paramDataOutputStream) throws IOException;

    void readPacketData(DataInputStream paramDataInputStream) throws IOException;
}

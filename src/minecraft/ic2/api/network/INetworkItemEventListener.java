package ic2.api.network;

import net.minecraft.entity.player.EntityPlayer;

public interface INetworkItemEventListener
{
    void onNetworkEvent(int metaData, EntityPlayer player, int event);
}

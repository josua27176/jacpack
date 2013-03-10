package net.minecraft.server.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ObjectArrays;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet51MapChunk;
import net.minecraft.network.packet.Packet52MultiBlockChange;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;

import net.minecraftforge.common.ForgeDummyContainer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkWatchEvent;

public class PlayerInstance
{
    public static int clumpingThreshold;

    public final List playersInChunk;

    /** note: this is final */
    private final ChunkCoordIntPair chunkLocation;
    private short[] locationOfBlockChange;
    private int numberOfTilesToUpdate;
    private int field_73260_f;

    final PlayerManager myManager;

    public PlayerInstance(PlayerManager par1PlayerManager, int par2, int par3)
    {
        this.myManager = par1PlayerManager;
        this.playersInChunk = new ArrayList();
        this.locationOfBlockChange = new short[64];
        this.numberOfTilesToUpdate = 0;
        this.chunkLocation = new ChunkCoordIntPair(par2, par3);
        par1PlayerManager.getWorldServer().theChunkProviderServer.loadChunk(par2, par3);
    }

    /**
     * called for all chunks within the visible radius of the player
     */
    public void addPlayerToChunkWatchingList(EntityPlayerMP par1EntityPlayerMP)
    {
        if (this.playersInChunk.contains(par1EntityPlayerMP))
        {
            throw new IllegalStateException("Failed to add player. " + par1EntityPlayerMP + " already is in chunk " + this.chunkLocation.chunkXPos + ", " + this.chunkLocation.chunkZPos);
        }
        else
        {
            this.playersInChunk.add(par1EntityPlayerMP);
            par1EntityPlayerMP.loadedChunks.add(this.chunkLocation);
        }
    }

    public void sendThisChunkToPlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        if (this.playersInChunk.contains(par1EntityPlayerMP))
        {
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet51MapChunk(PlayerManager.getWorldServer(this.myManager).getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), true, 0));
            this.playersInChunk.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.loadedChunks.remove(this.chunkLocation);

            MinecraftForge.EVENT_BUS.post(new ChunkWatchEvent.UnWatch(chunkLocation, par1EntityPlayerMP));

            if (this.playersInChunk.isEmpty())
            {
                long var2 = (long)this.chunkLocation.chunkXPos + 2147483647L | (long)this.chunkLocation.chunkZPos + 2147483647L << 32;
                PlayerManager.getChunkWatchers(this.myManager).remove(var2);

                if (this.numberOfTilesToUpdate > 0)
                {
                    PlayerManager.getChunkWatchersWithPlayers(this.myManager).remove(this);
                }

                this.myManager.getWorldServer().theChunkProviderServer.unloadChunksIfNotNearSpawn(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
            }
        }
    }

    public void flagChunkForUpdate(int par1, int par2, int par3)
    {
        if (this.numberOfTilesToUpdate == 0)
        {
            PlayerManager.getChunkWatchersWithPlayers(this.myManager).add(this);
        }

        this.field_73260_f |= 1 << (par2 >> 4);

        short var4 = (short)(par1 << 12 | par3 << 8 | par2);

        for (int var5 = 0; var5 < this.numberOfTilesToUpdate; ++var5)
        {
            if (this.locationOfBlockChange[var5] == var4)
            {
                return;
            }
        }

        if (this.numberOfTilesToUpdate == locationOfBlockChange.length)
        {
            this.locationOfBlockChange = Arrays.copyOf(this.locationOfBlockChange, locationOfBlockChange.length << 1);
        }
        this.locationOfBlockChange[this.numberOfTilesToUpdate++] = var4;
    }

    public void sendToAllPlayersWatchingChunk(Packet par1Packet)
    {
        for (int var2 = 0; var2 < this.playersInChunk.size(); ++var2)
        {
            EntityPlayerMP var3 = (EntityPlayerMP)this.playersInChunk.get(var2);

            if (!var3.loadedChunks.contains(this.chunkLocation))
            {
                var3.playerNetServerHandler.sendPacketToPlayer(par1Packet);
            }
        }
    }

    public void sendChunkUpdate()
    {
        if (this.numberOfTilesToUpdate != 0)
        {
            int var1;
            int var2;
            int var3;

            if (this.numberOfTilesToUpdate == 1)
            {
                var1 = this.chunkLocation.chunkXPos * 16 + (this.locationOfBlockChange[0] >> 12 & 15);
                var2 = this.locationOfBlockChange[0] & 255;
                var3 = this.chunkLocation.chunkZPos * 16 + (this.locationOfBlockChange[0] >> 8 & 15);
                this.sendToAllPlayersWatchingChunk(new Packet53BlockChange(var1, var2, var3, PlayerManager.getWorldServer(this.myManager)));

                if (PlayerManager.getWorldServer(this.myManager).blockHasTileEntity(var1, var2, var3))
                {
                    this.sendTileToAllPlayersWatchingChunk(PlayerManager.getWorldServer(this.myManager).getBlockTileEntity(var1, var2, var3));
                }
            }
            else
            {
                int var4;

                if (this.numberOfTilesToUpdate >= ForgeDummyContainer.clumpingThreshold)
                {
                    var1 = this.chunkLocation.chunkXPos * 16;
                    var2 = this.chunkLocation.chunkZPos * 16;
                    this.sendToAllPlayersWatchingChunk(new Packet51MapChunk(PlayerManager.getWorldServer(this.myManager).getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), false, this.field_73260_f));
                }
                else
                {
                    this.sendToAllPlayersWatchingChunk(new Packet52MultiBlockChange(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos, this.locationOfBlockChange, this.numberOfTilesToUpdate, PlayerManager.getWorldServer(this.myManager)));
                }

                for (var1 = 0; var1 < this.numberOfTilesToUpdate; ++var1)
                {
                    var2 = this.chunkLocation.chunkXPos * 16 + (this.locationOfBlockChange[var1] >> 12 & 15);
                    var3 = this.locationOfBlockChange[var1] & 255;
                    var4 = this.chunkLocation.chunkZPos * 16 + (this.locationOfBlockChange[var1] >> 8 & 15);

                    if (PlayerManager.getWorldServer(this.myManager).blockHasTileEntity(var2, var3, var4))
                    {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.getWorldServer(this.myManager).getBlockTileEntity(var2, var3, var4));
                    }
                }
            }

            this.numberOfTilesToUpdate = 0;
            this.field_73260_f = 0;
        }
    }

    private void sendTileToAllPlayersWatchingChunk(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet var2 = par1TileEntity.getDescriptionPacket();

            if (var2 != null)
            {
                this.sendToAllPlayersWatchingChunk(var2);
            }
        }
    }

    static ChunkCoordIntPair getChunkLocation(PlayerInstance par0PlayerInstance)
    {
        return par0PlayerInstance.chunkLocation;
    }

    static List getPlayersInChunk(PlayerInstance par0PlayerInstance)
    {
        return par0PlayerInstance.playersInChunk;
    }
}

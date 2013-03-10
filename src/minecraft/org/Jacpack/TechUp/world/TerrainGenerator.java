package org.Jacpack.TechUp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class TerrainGenerator implements IWorldGenerator
{
    Block ores;

    private final static int oreGenHeights[][] = {{40, 100, 3, 7, 0}, {40, 100, 3, 7, 0}, {40, 130, 3, 7, 25}, {5, 60, 3, 7, 0}, {1, 65, 3, 7, 25}, {1, 33, 3, 4, 5}, {1, 200, 8, 18, 13}};

    public TerrainGenerator(Block ore)
    {
        super();
        ores = ore;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
            IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.isSurfaceWorld())
            generateOverworld(random, chunkX, chunkZ, world,
                    chunkGenerator, chunkProvider);
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world,
            IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        for (int i = 0; i < oreGenHeights.length; i++)
        {
            for (int pass = 0; pass < oreGenHeights[i][4]; pass++)
            {
                int randX = random.nextInt(16) + chunkX;
                int randY = oreGenHeights[i][0] + random.nextInt(oreGenHeights[i][1] - oreGenHeights[i][0]);
                int randZ = random.nextInt(16) + chunkZ;
                int randClump = oreGenHeights[i][2] + random.nextInt(oreGenHeights[i][3] - oreGenHeights[i][2]);
                (new WorldGenMinable(ores.blockID, i, randClump)).generate(world, random, randX, randY, randZ);
            }
        }
    }
}

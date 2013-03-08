package org.Jacpack.TechUp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomOre extends WorldGenerator
{
    protected int minableBlockId;
    protected int minableBlockMeta;
    protected int numberOfBlocks;

    public WorldGenCustomOre(int id, int meta, int var3)
    {
        this.minableBlockId = id;
        this.minableBlockMeta = meta;
        this.numberOfBlocks = var3;
    }

    public void tryGenerateBlock(World world, Random random, int i, int j, int k)
    {
        if (world.getBlockId(i, j, k) == Block.stone.blockID)
        {
            world.setBlockAndMetadata(i, j, k, this.minableBlockId, this.minableBlockMeta);
        }
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        float f = random.nextFloat() * (float)Math.PI;
        double d = i + 8 + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double d1 = i + 8 - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
        double d2 = k + 8 + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double d3 = k + 8 - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
        double d4 = j + random.nextInt(3) + 2;
        double d5 = j + random.nextInt(3) + 2;

        for (int l = 0; l <= this.numberOfBlocks; l++)
        {
            double d6 = d + (d1 - d) * (double)l / (double)this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
            double d9 = random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (MathHelper.sin(l * 3.141593F / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper.sin(l * 3.141593F / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; k2++)
            {
            	double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);
                if (d12 * d12 < 1.0D)
                {
                	for (int l2 = j1; l2 <= i2; l2++)
                    {
                      double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);
                      if (d12 * d12 + d13 * d13 < 1.0D)
                      {
                        for (int i3 = k1; i3 <= j2; i3++)
                        {
                          double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);
                          if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                            tryGenerateBlock(world, random, k2, l2, i3);
                          }
                        }
                      }
                    }
                }
            }
        }

        return true;
    }
}

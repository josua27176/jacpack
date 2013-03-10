package ic2.api;

import java.util.HashMap;

import net.minecraft.world.biome.BiomeGenBase;

public class Crops
{
    private static final HashMap<BiomeGenBase, Integer> humidityBiomeBonus = new HashMap<BiomeGenBase, Integer>();
    private static final HashMap<BiomeGenBase, Integer> nutrientBiomeBonus = new HashMap<BiomeGenBase, Integer>();

    public static void addBiomeBonus(BiomeGenBase biome, int humidityBonus, int nutrientsBonus)
    {
        humidityBiomeBonus.put(biome, humidityBonus);
        nutrientBiomeBonus.put(biome, nutrientsBonus);
    }

    public static int getHumidityBiomeBonus(BiomeGenBase biome)
    {
        return humidityBiomeBonus.containsKey(biome) ? humidityBiomeBonus.get(biome) : 0;
    }

    public static int getNutrientBiomeBonus(BiomeGenBase biome)
    {
        return nutrientBiomeBonus.containsKey(biome) ? nutrientBiomeBonus.get(biome) : 0;
    }
}

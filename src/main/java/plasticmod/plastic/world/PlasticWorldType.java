package plasticmod.plastic.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class PlasticWorldType extends WorldType {

    public static WorldType PlasticWorldType = new PlasticWorldType("Plastic");

    private PlasticWorldType(String name) {
        super(name);
    }

    @Override
    public WorldChunkManager getChunkManager(World world) {
        return new PlasticWorldChunkManager(world);
    }

    @Override
    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new PlasticChunkProvider(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }
}
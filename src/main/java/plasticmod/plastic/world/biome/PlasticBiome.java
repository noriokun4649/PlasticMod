package plasticmod.plastic.world.biome;

import net.minecraft.world.biome.BiomeGenDesert;
import plasticmod.plastic.PlasticCore;

public class PlasticBiome extends BiomeGenDesert {
	public PlasticBiome(int p_i1986_1_) {
        super(p_i1986_1_);
        this.topBlock = PlasticCore.plasticblock;
        this.fillerBlock = PlasticCore.naphthaoil;
    }
}
package plasticmod.plastic.world.biome;
 
import net.minecraft.world.biome.BiomeGenDesert;
import plasticmod.plastic.PlasticCore;

public class PlasticBiome2 extends BiomeGenDesert {
    public PlasticBiome2(int p_i1986_1_) {
        super(p_i1986_1_);
        this.topBlock = PlasticCore.rareplasticblock;
        this.fillerBlock = PlasticCore.naphthaoil;
    }
}
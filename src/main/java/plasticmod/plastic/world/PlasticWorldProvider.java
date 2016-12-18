package plasticmod.plastic.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import plasticmod.plastic.config.PlasticConfig;

public class PlasticWorldProvider extends WorldProvider {

    public static PlasticWorldProvider Plastic;

	@Override
    public String getDimensionName() {
        return "Plastic";
    }

    // 独自のワールドタイプやワールドチャンクマネージャーを設定
    @Override
    protected void registerWorldChunkManager() {
        worldObj.getWorldInfo().setTerrainType(PlasticWorldType.PlasticWorldType);
        worldChunkMgr = new PlasticWorldChunkManager(worldObj);
        setDimension(PlasticConfig.dimensionID);
        hasNoSky = false;
    }

    // チャンク生成は独自のチャンクプロバイダが担当する
    @Override
    public IChunkProvider createChunkGenerator() {
        return new PlasticChunkProvider(worldObj, worldObj.getSeed(), true);
    }
}
package plasticmod.plastic.world;
 
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
 
// チャンクへのアクセスやチャンク生成を担当するクラス
//　ここではオーバーワールドのものを流用する
public class PlasticChunkProvider extends ChunkProviderGenerate implements IChunkProvider {
 
    public PlasticChunkProvider(World par1World, long par2, boolean par4) {
        super(par1World, par2, par4);
    }
 
}
package plasticmod.plastic.render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import plasticmod.plastic.model.ModelPlasticSkeleton;

/*
 * 今回は二本足のEntityを追加するので, RenderBipedを継承する.
 */
public class RenderPlasticSkeleton extends RenderBiped
{
	/*
	 * テクスチャへのResourceLocationを設定する.
	 */
	private static final ResourceLocation skeletonTextures = new ResourceLocation("plasticmod:textures/model/mob/plastic_skeleton.png");

	public RenderPlasticSkeleton()
	{
		/*
		 * スーパークラスのコンストラクタの引数は
		 * (このRenderと紐付けするModel, このRenderを使うEntityの足元の影の大きさ)
		 */
		super(new ModelPlasticSkeleton(), 0.5F);
	}

	/*
	 * このRenderが利用するテクスチャのResourceLocationを返す.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.skeletonTextures;
	}
}
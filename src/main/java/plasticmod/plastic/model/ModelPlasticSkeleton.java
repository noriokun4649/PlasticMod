package plasticmod.plastic.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
 
/*
 * 二本足のEntityを追加するので, ModelBipedを継承する.
 */
public class ModelPlasticSkeleton extends ModelBiped
{
	public ModelPlasticSkeleton()
	{
		this(0.0F);
	}

	public ModelPlasticSkeleton(float scaleFactor)
	{
		/*
		 * スーパークラスのコンストラクタの引数は
		 * (scale, yOffset, テクスチャのwidht, テクスチャのheight)
		 * scaleはModelのBox時代の大きさの倍率, 0.0Fが基準.
		 * yOffsetはModelBipedのBodyに作用する. 正値で下に, 負値で上にずれる.
		 * 64, 32は参照するテクスチャの解像度が64x32ということ.
		 */
		super(scaleFactor, 0.0F, 64, 32);

		/*
		 * 右腕のBoxを新しく追加する.
		 * ModelRendererの引数は(このクラス, 参照するテクスチャの参照開始地点x, y)
		 * つまりred_skeleton.pngの(xOffset=40, yOffset=16)から
		 */
		this.bipedRightArm = new ModelRenderer(this, 40, 16);
		this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, scaleFactor);	// (-1.0F, -2.0F, -1.0F)ベクトルを始点として, 下方向に大きさ(2, 12, 2)の直方体を追加する.
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);					// 回転の中心をセットする.

		this.bipedLeftArm = new ModelRenderer(this, 40, 16);					// 引数は右腕と同じ
		this.bipedLeftArm.mirror = true;										// 左側はmirrorをtrueにする.
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, scaleFactor);	// 引数は右腕と同じ.
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);					// xだけ反転

		/*
		 * 足も同様に
		 */
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, scaleFactor);
		this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);

		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, scaleFactor);
		this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
	}
}
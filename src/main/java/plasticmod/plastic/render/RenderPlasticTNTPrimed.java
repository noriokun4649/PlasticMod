package plasticmod.plastic.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.entity.EntityPlasticTNTPrimed;

@SideOnly(Side.CLIENT)
public class RenderPlasticTNTPrimed extends Render
{

    public RenderPlasticTNTPrimed()
    {
        blockRenderer = new RenderBlocks();
        shadowSize = 0.5F;
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation("plasticmod:textures/atlas/blocks.png");

    public void func_153_a(EntityPlasticTNTPrimed entitytntprimed, double d, double d1, double d2, 
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        if(((float)entitytntprimed.fuse - f1) + 1.0F < 10F)
        {
            float f2 = 1.0F - (((float)entitytntprimed.fuse - f1) + 1.0F) / 10F;
            if(f2 < 0.0F)
            {
                f2 = 0.0F;
            }
            if(f2 > 1.0F)
            {
                f2 = 1.0F;
            }
            f2 *= f2;
            f2 *= f2;
            float f4 = 1.0F + f2 * 0.3F;
            GL11.glScalef(f4, f4, f4);
        }
        float f3 = (1.0F - (((float)entitytntprimed.fuse - f1) + 1.0F) / 100F) * 0.8F;
        this.bindEntityTexture(entitytntprimed);
        blockRenderer.renderBlockAsItem(PlasticCore.PlasticTNT, 0, entitytntprimed.getBrightness(f1));
        if((entitytntprimed.fuse / 5) % 2 == 0)
        {
            GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
            GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f3);
            blockRenderer.renderBlockAsItem(PlasticCore.PlasticTNT, 0, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042 /*GL_BLEND*/);
            GL11.glEnable(2896 /*GL_LIGHTING*/);
            GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
        }
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        func_153_a((EntityPlasticTNTPrimed)entity, d, d1, d2, f, f1);
    }

    private RenderBlocks blockRenderer;

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return TEXTURE;
	}
}


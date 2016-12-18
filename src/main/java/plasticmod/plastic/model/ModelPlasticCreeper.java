package plasticmod.plastic.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlasticCreeper extends ModelBase {
	
	public static int model = 1;
	  //fields
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer tuno1;
    ModelRenderer tuno2;
    ModelRenderer hana;
    ModelRenderer kuti1;
    ModelRenderer kuti2;
    ModelRenderer eye;
    ModelRenderer eye1;

    public ModelPlasticCreeper()
    {
        this(0.0F);
    }
  public ModelPlasticCreeper(float p_i1147_1_)
  {
    textureWidth = 64;
    textureHeight = 64;
    /*
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -8F, -4F, 12, 39, 12);
        head.setRotationPoint(-2F, -25F, -2F);
        head.setTextureSize(64, 64);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 24);
        body.addBox(-3F, 0F, -2F, 6, 12, 4);
        body.setRotationPoint(0F, 6F, 0F);
        body.setTextureSize(64, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 20, 24);
        leg3.addBox(-2F, 0F, -2F, 4, 6, 4);
        leg3.setRotationPoint(-2F, 18F, -4F);
        leg3.setTextureSize(64, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 20, 24);
        leg4.addBox(-2F, 0F, -2F, 4, 6, 4);
        leg4.setRotationPoint(2F, 18F, -4F);
        leg4.setTextureSize(64, 64);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        leg1 = new ModelRenderer(this, 20, 24);
        leg1.addBox(0F, 0F, -2F, 4, 6, 4);
        leg1.setRotationPoint(-4F, 18F, 4F);
        leg1.setTextureSize(64, 64);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 20, 24);
        leg2.addBox(-2F, 0F, -2F, 4, 6, 4);
        leg2.setRotationPoint(2F, 18F, 4F);
        leg2.setTextureSize(64, 64);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        tuno1 = new ModelRenderer(this, 0, 0);
        tuno1.addBox(0F, 0F, 0F, 2, 6, 2);
        tuno1.setRotationPoint(3F, -38F, -1F);
        tuno1.setTextureSize(64, 64);
        tuno1.mirror = true;
        setRotation(tuno1, 0F, 0F, 0.2268928F);
        tuno2 = new ModelRenderer(this, 0, 0);
        tuno2.addBox(0F, 0F, 0F, 2, 6, 2);
        tuno2.setRotationPoint(-3F, -38F, 1F);
        tuno2.setTextureSize(64, 64);
        tuno2.mirror = true;
        setRotation(tuno2, 0F, 3.141593F, -0.2268928F);
        hana = new ModelRenderer(this, 48, 5);
        hana.addBox(0F, 0F, 0F, 2, 3, 2);
        hana.setRotationPoint(-1F, 0F, -8F);
        hana.setTextureSize(64, 64);
        hana.mirror = true;
        setRotation(hana, 0F, 0F, 0F);
        kuti1 = new ModelRenderer(this, 48, 0);
        kuti1.addBox(0F, 0F, 0F, 2, 4, 1);
        kuti1.setRotationPoint(-3F, 1F, -7F);
        kuti1.setTextureSize(64, 64);
        kuti1.mirror = true;
        setRotation(kuti1, 0F, 0F, 0F);
        kuti2 = new ModelRenderer(this, 48, 18);
        kuti2.addBox(0F, 0F, 0F, 2, 4, 1);
        kuti2.setRotationPoint(1F, 1F, -7F);
        kuti2.setTextureSize(64, 64);
        kuti2.mirror = true;
        setRotation(kuti2, 0F, 0F, 0F);
        eye = new ModelRenderer(this, 48, 10);
        eye.addBox(0F, 0F, 0F, 3, 3, 1);
        eye.setRotationPoint(-5F, -4F, -7F);
        eye.setTextureSize(64, 64);
        eye.mirror = true;
        setRotation(eye, 0F, 0F, 0F);
        eye1 = new ModelRenderer(this, 48, 14);
        eye1.addBox(0F, 0F, 0F, 3, 3, 1);
        eye1.setRotationPoint(2F, -4F, -7F);
        eye1.setTextureSize(64, 64);
        eye1.mirror = true;
        setRotation(eye1, 0F, 0F, 0F);
        */
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -8F, -4F, 12, 12, 12,p_i1147_1_);
        head.setRotationPoint(-2F, 2F, -2F);
        head.setTextureSize(64, 64);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 24);
        body.addBox(-3F, 0F, -2F, 6, 12, 4);
        body.setRotationPoint(0F, 6F, 0F);
        body.setTextureSize(64, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 20, 24);
        leg3.addBox(-2F, 0F, -2F, 4, 6, 4,p_i1147_1_);
        leg3.setRotationPoint(-2F, 18F, -4F);
        leg3.setTextureSize(64, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 20, 24);
        leg4.addBox(-2F, 0F, -2F, 4, 6, 4,p_i1147_1_);
        leg4.setRotationPoint(2F, 18F, -4F);
        leg4.setTextureSize(64, 64);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        leg1 = new ModelRenderer(this, 20, 24);
        leg1.addBox(0F, 0F, -2F, 4, 6, 4,p_i1147_1_);
        leg1.setRotationPoint(-4F, 18F, 4F);
        leg1.setTextureSize(64, 64);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 20, 24);
        leg2.addBox(-2F, 0F, -2F, 4, 6, 4,p_i1147_1_);
        leg2.setRotationPoint(2F, 18F, 4F);
        leg2.setTextureSize(64, 64);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        tuno1 = new ModelRenderer(this, 0, 0);
        tuno1.addBox(0F, 0F, 0F, 2, 6, 2,p_i1147_1_);
        tuno1.setRotationPoint(3F, -11F, -1F);
        tuno1.setTextureSize(64, 64);
        tuno1.mirror = true;
        setRotation(tuno1, 0F, 0F, 0.2268928F);
        tuno2 = new ModelRenderer(this, 0, 0);
        tuno2.addBox(0F, 0F, 0F, 2, 6, 2,p_i1147_1_);
        tuno2.setRotationPoint(-3F, -11F, 1F);
        tuno2.setTextureSize(64, 64);
        tuno2.mirror = true;
        setRotation(tuno2, 0F, 3.141593F, -0.2268928F);
        hana = new ModelRenderer(this, 48, 5);
        hana.addBox(0F, 0F, 0F, 2, 3, 2);
        hana.setRotationPoint(-1F, 0F, -8F);
        hana.setTextureSize(64, 64);
        hana.mirror = true;
        setRotation(hana, 0F, 0F, 0F);
        kuti1 = new ModelRenderer(this, 48, 0);
        kuti1.addBox(0F, 0F, 0F, 2, 4, 1);
        kuti1.setRotationPoint(-3F, 1F, -7F);
        kuti1.setTextureSize(64, 64);
        kuti1.mirror = true;
        setRotation(kuti1, 0F, 0F, 0F);
        kuti2 = new ModelRenderer(this, 48, 18);
        kuti2.addBox(0F, 0F, 0F, 2, 4, 1);
        kuti2.setRotationPoint(1F, 1F, -7F);
        kuti2.setTextureSize(64, 64);
        kuti2.mirror = false;
        setRotation(kuti2, 0F, 0F, 0F);
        eye = new ModelRenderer(this, 48, 10);
        eye.addBox(0F, 0F, 0F, 3, 3, 1);
        eye.setRotationPoint(-5F, -4F, -7F);
        eye.setTextureSize(64, 64);
        eye.mirror = true;
        setRotation(eye, 0F, 0F, 0F);
        eye1 = new ModelRenderer(this, 48, 14);
        eye1.addBox(0F, 0F, 0F, 3, 3, 1);
        eye1.setRotationPoint(2F, -4F, -7F);
        eye1.setTextureSize(64, 64);
        eye1.mirror = true;
        setRotation(eye1, 0F, 0F, 0F);
  }
  

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5 )
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    head.render(f5);
    body.render(f5);
    leg3.render(f5);
    leg4.render(f5);
    leg1.render(f5);
    leg2.render(f5);
    tuno1.render(f5);
    tuno2.render(f5);
    hana.render(f5);
    kuti1.render(f5);
    kuti2.render(f5);
    eye.render(f5);
    eye1.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5,Entity f6)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, f6);
  }
}

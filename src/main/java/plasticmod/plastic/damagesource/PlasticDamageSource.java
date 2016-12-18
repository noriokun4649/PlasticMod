package plasticmod.plastic.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import plasticmod.plastic.entity.EntityPlasticBullet;

public class PlasticDamageSource extends DamageSource
{
	public PlasticDamageSource(String par1Str)
	{
		super(par1Str);
	}

	public static DamageSource causeBulletDamage(EntityPlasticBullet par0EntityBullet, Entity par1Entity)
	{
        	return (new EntityDamageSourceIndirect("bullet", par0EntityBullet, par1Entity)).setProjectile();
	}
}
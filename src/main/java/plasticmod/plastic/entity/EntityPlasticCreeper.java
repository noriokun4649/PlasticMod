package plasticmod.plastic.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.config.PlasticConfig;

public class EntityPlasticCreeper extends EntityCreeper implements IProjectile
{
	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	private int lastActiveTime;
	/** The amount of time since the creeper was close enough to the player to ignite */
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private float timeWolfIsShaking;
	/** Explosion radius for this creeper. */
	private int explosionRadius = 6;
	private static final String __OBFID = "CL_00001684";

	public EntityPlasticCreeper(World p_i1733_1_)
	{
		super(p_i1733_1_);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAICreeperSwell(this));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
		this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAITempt(this, 1.25D,Items.gunpowder, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled()
	{
		return true;
	}

	/**
	 * The number of iterations PathFinder.getSafePoint will execute before giving up.
	 */
	public int getMaxSafePointTries()
	{
		return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	protected void fall(float p_70069_1_)
	{
		super.fall(p_70069_1_);
		this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + p_70069_1_ * 1.5F);

		if (this.timeSinceIgnited > this.fuseTime - 5)
		{
			this.timeSinceIgnited = this.fuseTime - 5;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);

		if (this.dataWatcher.getWatchableObjectByte(17) == 1)
		{
			p_70014_1_.setBoolean("powered", true);
		}
		p_70014_1_.setShort("Fuse", (short)this.fuseTime);
		p_70014_1_.setByte("ExplosionRadius", (byte)this.explosionRadius);
		p_70014_1_.setBoolean("ignited", this.func_146078_ca());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte)(p_70037_1_.getBoolean("powered") ? 1 : 0)));

		if (p_70037_1_.hasKey("Fuse", 99))
		{
			this.fuseTime = p_70037_1_.getShort("Fuse");
		}

		if (p_70037_1_.hasKey("ExplosionRadius", 99))
		{
			this.explosionRadius = p_70037_1_.getByte("ExplosionRadius");
		}

		if (p_70037_1_.getBoolean("ignited"))
		{
			this.func_146079_cb();
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if (this.isEntityAlive())
		{
			this.lastActiveTime = this.timeSinceIgnited;

			if (this.func_146078_ca())
			{
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();

			if (i > 0 && this.timeSinceIgnited == 0)
			{
				this.playSound("creeper.primed", 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;

			if (this.timeSinceIgnited < 0)
			{
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime)
			{
				this.timeSinceIgnited = this.fuseTime;
				this.func_146077_cc();
			}
		}

		super.onUpdate();
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.creeper.say";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.creeper.death";
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	public void onDeath(DamageSource p_70645_1_)
	{
		super.onDeath(p_70645_1_);

		if (p_70645_1_.getEntity() instanceof EntityCreeper)
		{
			int i = Item.getIdFromItem(Items.record_13);
			int j = Item.getIdFromItem(Items.record_wait);
			int k = i + this.rand.nextInt(j - i + 1);
			this.dropItem(Item.getItemById(k), 1);
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return true;
	}

	/**
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	public boolean getPowered()
	{
		return this.dataWatcher.getWatchableObjectByte(17) == 1;
	}

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float p_70831_1_)
	{
		return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (float)(this.fuseTime - 2);
	}

	protected Item getDropItem()
	{
		return PlasticCore.plasticpowder;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState()
	{
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int p_70829_1_)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)p_70829_1_));
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	public void onStruckByLightning(EntityLightningBolt p_70077_1_)
	{
		super.onStruckByLightning(p_70077_1_);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	@Override
	protected boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();

		if (itemstack != null &&( itemstack.getItem() == Items.flint_and_steel  || itemstack.getItem() == PlasticCore.flintAndPlasticSteel || itemstack.getItem() == PlasticCore.flintAndRarePlasticSteel) )
		{
			PlasticCore.logger("火打ち石認識");
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			p_70085_1_.swingItem();

			if (!this.worldObj.isRemote)
			{
				this.func_146079_cb();
				itemstack.damageItem(1, p_70085_1_);
				return true;
			}
		}else if (itemstack != null &&( itemstack.getItem() == Items.gunpowder) ){
			PlasticCore.logger("火薬認識");
			p_70085_1_.inventory.consumeInventoryItem(Items.gunpowder);
			this.explosionRadius = explosionRadius + 6;

			float f = (float)this.boundingBox.minY;
			int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float)Math.PI) * 7.0F);

			for (int j = 0; j < i; ++j)
			{
				float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
				float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
				this.worldObj.spawnParticle("splash", this.posX + (double)f1, (double)(f + 0.8F), this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ);
			}

		}else if (itemstack != null &&( itemstack.getItem() == Items.milk_bucket) ){
			PlasticCore.logger("牛乳認識");
			p_70085_1_.inventory.consumeInventoryItem(Items.milk_bucket);
			p_70085_1_.inventory.addItemStackToInventory(new ItemStack(Items.bucket));
			this.explosionRadius =  6;
		}
		PlasticCore.logger("クリーパーの状態: "+ this.getCreeperState());
		PlasticCore.logger("最後のアクティブな時間: "+lastActiveTime);
		PlasticCore.logger("爆発時間: "+fuseTime);
		PlasticCore.logger("点火時間: "+timeSinceIgnited);
		PlasticCore.logger("爆発範囲: "+explosionRadius);
		return super.interact(p_70085_1_);
	}

	private void func_146077_cc()
	{
		if (!this.worldObj.isRemote)
		{
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

			if (this.getPowered())
			{
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), flag);
			}
			else
			{
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag);
			}

			this.setDead();
		}
	}

	public boolean func_146078_ca()
	{
		return this.dataWatcher.getWatchableObjectByte(18) != 0;
	}

	public void func_146079_cb()
	{
		this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
	}

	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_,
			float p_70186_8_) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
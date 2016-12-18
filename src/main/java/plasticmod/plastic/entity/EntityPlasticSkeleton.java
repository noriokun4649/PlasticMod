package plasticmod.plastic.entity;

import java.util.Calendar;

import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import plasticmod.plastic.PlasticCore;

public class EntityPlasticSkeleton extends EntityMob implements IProjectile, IThrowableEntity, IRangedAttackMob
{
	protected int canBePickedUp;

    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	public EntityPlasticSkeleton(World world)
	{
		super(world);

		/*
		 * タスクにAIを追加する.
		 * 引数は(優先度, AIクラスのインスタンス)
		 */
        this.tasks.addTask(0, new EntityAISwimming(this));
		/*
		 * 近接攻撃を行うAIを追加する.
		 * EntityAIAttackOnCollideの引数のうち, 末尾2つは(攻撃距離, ずっと追い続けるかどうか)
		 */

		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, true));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));

		/*
		 * うろうろ移動するAIの追加
		 */
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));

		/*
		 * 見回すAIの追加
		 */
		this.tasks.addTask(4, new EntityAILookIdle(this));

		/*
		 * ターゲットタスクにAIを追加する.
		 */

		/*
		 * 攻撃されたら反撃するAIを追加する.
		 * EntityAIHurtByTargetの第二引数は反撃時に周りのMobに助けを求めるかどうか
		 */
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));

		/*
		 * 周囲に特定のEntityがいる場合, ターゲッティングするAI.
		 * EntityAINearestAttackableTargetの最後の引数は視界で遮るかどうか.
		 * trueだとブロックに囲われた(ガラスブロックでも)村人はターゲットにならない.
		 * falseだとどんなブロックに囲まれていようとターゲットにする(ゾンビと同じ).
		 */
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, true));
	}

	/*
	 * AIを使うかどうか.
	 * 今回は使うのでtrueを返している.
	 */
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	/*
	 * このEntityに性質を付与する.
	 * 今回は移動速度を変更.
	 */
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(12);
	}

	/*
	 * このMobが生きてるときの音のファイルパスを返す.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}

	/*
	 * このMobがダメージを受けたときの音のファイルパスを返す.
	 */         
	@Override
	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}

	/*
	 * このMobが倒されたときの音のファイルパスを返す.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}

	/*
	 * このMobが動いているときの音のファイルパスを返す.
	 * 引数のblockはMobの下にあるBlock.
	 */
	@Override
	protected void func_145780_a(int x, int y, int z, Block block)
	{
		this.playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	/*
	 * Mobの属性を返す.
	 * 今回はUNDEADにしているので, エンチャントのSmiteが有効.
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

    /**
     * Set this skeleton's type.
     */
    public void setSkeletonType(int p_82201_1_)
    {
        this.dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
        this.isImmuneToFire = p_82201_1_ == 1;

        if (p_82201_1_ == 2)
        {
            this.setSize(0.72F, 2.34F);
        }
        else
        {
            this.setSize(0.6F, 1.8F);
        }
    }
	/*
	 * このMobの通常ドロップのアイテムを返すメソッド.
	 */
	@Override
	protected Item getDropItem()
	{
		return PlasticCore.rareplasticstick;
	}
    /**
     * Makes entity wear random armor based on difficulty
     */
    protected void addRandomArmor()
    {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(PlasticCore.rareplasticbow));
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);

        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
        {
            this.tasks.addTask(4, this.aiAttackOnCollide);
            //this.setSkeletonType(2);
            this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
        }
        else
        {
            this.tasks.addTask(4, this.aiArrowAttack);
            this.addRandomArmor();
            this.enchantEquipment();
        }

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));

        if (this.getEquipmentInSlot(4) == null)
        {
            Calendar calendar = this.worldObj.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }

        return p_110161_1_;
    }
    /**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        ItemStack itemstack = this.getHeldItem();

        if (itemstack != null && itemstack.getItem() == PlasticCore.rareplasticbow)
        {
            this.tasks.addTask(4, this.aiArrowAttack);
        }
        else
        {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }

	@Override
	public Entity getThrower() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setThrower(Entity entity) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_,
			double p_70186_5_, float p_70186_7_, float p_70186_8_) {
		// TODO 自動生成されたメソッド・スタブ

	}


	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
		// TODO 自動生成されたメソッド・スタブ

	}


}


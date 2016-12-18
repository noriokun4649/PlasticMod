package plasticmod.plastic.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.entity.EntityPlasticBullet;

public class RarePlasticGun extends Item
{
	private int maxdamage;
	private float range;
	private double attack;
	private int wait_attack;
	private int wait_reload;
	private String gunsound;
	private Boolean knockback;
	private Boolean fire;

	//装填数，射程距離(弓の最大1.0F)，攻撃力補正，攻撃後のWAIT，リロード後のWAIT，効果音，ノックバックの有無，炎上効果の有無
	public RarePlasticGun(int par1, float par2, double par3, int par4, int par5, String par6, Boolean par7, Boolean par8)
	{
		this.maxdamage = par1;
		this.range = par2;
		this.attack = par3;
		this.wait_attack = par4;
		this.wait_reload = par5;
		this.gunsound = par6;
		this.knockback = par7;
		this.fire = par8;

		this.maxStackSize = 1;
		this.setMaxDamage(this.maxdamage);
	}

	//右クリック
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		//耐久値が0（ダメージ値max）なら弾の有無判定へ
		if (par1ItemStack.getItemDamage() == this.maxdamage)
		{
			//弾があれば消費して耐久値回復
			if (par3EntityPlayer.inventory.hasItem(PlasticCore.rareplasticBullet))
			{
				par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
				par2World.playSoundAtEntity(par3EntityPlayer, "plasticmod:gun_reload", 10.0F, 1.0F); //装填音
				par3EntityPlayer.inventory.consumeInventoryItem(PlasticCore.rareplasticBullet); //弾消費
				par1ItemStack.damageItem(this.maxdamage * -1, par3EntityPlayer); //耐久値回復
				par3EntityPlayer.attackTime = this.wait_reload;

			}
		}else{
			if (par3EntityPlayer.attackTime == 0) //連射防止
			{
				EntityPlasticBullet entitybullet = new EntityPlasticBullet(par2World, par3EntityPlayer, this.range * 2.0F);

				//クリティカル
				int ran = (int)(Math.random() * 10);
				if (ran < 2)
				{
					entitybullet.setIsCritical(true);
				}

				//攻撃力
				entitybullet.setDamage(entitybullet.getDamage() + this.attack);

				//ノックバック
				if (this.knockback == true)
				{
					entitybullet.setKnockbackStrength(1);
				}

				//炎上
				if (this.fire == true)
				{
					entitybullet.setFire(100);
				}

				par1ItemStack.damageItem(1, par3EntityPlayer); //耐久値消費
				par2World.playSoundAtEntity(par3EntityPlayer, "plasticmod:gun1", 0.7F, 1.0F); //発射音

				if (!par2World.isRemote)
				{
					par2World.spawnEntityInWorld(entitybullet); //エンティティ呼びだし
				}

				par3EntityPlayer.attackTime = this.wait_attack;
			}
		}

		return par1ItemStack;
	}

	//右クリック時の動作
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		if (par1ItemStack.getItemDamage() == this.maxdamage)
		{
			return EnumAction.block;

		}else{
			return EnumAction.bow;

		}
	}

	//エンチャント不可
	public boolean isItemTool(ItemStack par1ItemStack)
	{
		return false;
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	public boolean isFull3D()
	{
		return true;
	}
}
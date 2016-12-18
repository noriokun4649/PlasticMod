package plasticmod.plastic.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class PlasticArmor extends ItemArmor
{
	private String armorTexturePath = "plasticmod:textures/model/armor/";
	//private String iconTexturePath = SaganyanCore.modid + ":/";

	public PlasticArmor(ArmorMaterial armorMaterial,int par3,int par4,String type)
	{
		super(armorMaterial,par3,par4);

		this.setMaxStackSize(1);
		this.setTextureSaga(type,par4);
	}

	private void setTextureSaga(String type,int armorPart)
	{
		switch(armorPart)
		{
		case 0:
			this.armorTexturePath += type + "_layer_1.png";
			//this.iconTexturePath += type + "_item_helmet";
			break;

		case 1:
			this.armorTexturePath += type + "_layer_1.png";
			//this.iconTexturePath += type + "_item_chestplate";
			break;

		case 2:
			this.armorTexturePath += type + "_layer_2.png";
			//this.iconTexturePath += type + "_item_leggings";
			break;

		case 3:
			this.armorTexturePath += type + "_layer_1.png";
			//this.iconTexturePath += type + "_item_boots";
			break;
		}
	}

/**
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(this.iconTexturePath);
	}
*/
	public String getArmorTexture(ItemStack itemstack,Entity entity,int slot,String type)
	{
		return this.armorTexturePath;
	}
}

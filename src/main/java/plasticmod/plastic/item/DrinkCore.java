package plasticmod.plastic.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class DrinkCore extends ItemFood
{
    public DrinkCore(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        --p_77654_1_.stackSize;
        p_77654_3_.getFoodStats().func_151686_a(this, p_77654_1_);
        p_77654_2_.playSoundAtEntity(p_77654_3_, "random.burp", 0.5F, p_77654_2_.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(p_77654_1_, p_77654_2_, p_77654_3_);
        return p_77654_1_.stackSize <= 0 ? new ItemStack(PlasticCore.pet) : p_77654_1_;
        //リターンでペットボトルを返す
    }
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
    	//飲み物
        return EnumAction.drink;
    }

}
package plasticmod.plastic.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class PlasticFluidBucket extends Item
{
	
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_7_ == 0)
        {
            --p_77648_5_;
        }

        if (p_77648_7_ == 1)
        {
            ++p_77648_5_;
        }

        if (p_77648_7_ == 2)
        {
            --p_77648_6_;
        }

        if (p_77648_7_ == 3)
        {
            ++p_77648_6_;
        }

        if (p_77648_7_ == 4)
        {
            --p_77648_4_;
        }

        if (p_77648_7_ == 5)
        {
            ++p_77648_4_;
        }

        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
        {
            return false;
        }
        else
        {
		        	//液体プラスチック設置
		        	p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, PlasticCore.PlasticFluidBlock);
					if (!(p_77648_2_.capabilities.isCreativeMode)){
		        		//液体プラスチック入りバケツを削除
						p_77648_2_.inventory.consumeInventoryItem(this);
						//バケツを返却
						p_77648_2_.inventory.addItemStackToInventory(new ItemStack (Items.bucket));
					}
			return true;
        }
    }

}

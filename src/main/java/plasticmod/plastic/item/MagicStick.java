package plasticmod.plastic.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import plasticmod.plastic.config.PlasticConfig;
import plasticmod.plastic.world.PlasticTeleporter;

public class MagicStick extends Item
{
    private static final String __OBFID = "CL_00000035";

    public MagicStick()
    {
    }
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
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
			p_77648_3_.playSoundEffect((double)p_77648_4_ + 0.5D, (double)p_77648_5_ + 0.5D, (double)p_77648_6_ + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
			//ディメンション転送
			PlasticTeleporter.transferEntityToWorld(p_77648_2_, p_77648_3_.provider.dimensionId == PlasticConfig.dimensionID ? 0 : PlasticConfig.dimensionID);
			//ダメージ１
			p_77648_1_.damageItem(1, p_77648_2_);
			return true;
        }
    }
    @Override
	@SideOnly(Side.CLIENT)
    //アイテムに情報を追加
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List list, boolean advanced) {
    	list.add(ChatFormatting.RED+(StatCollector.translateToLocal("title.magicstick.name")));
    	}
}
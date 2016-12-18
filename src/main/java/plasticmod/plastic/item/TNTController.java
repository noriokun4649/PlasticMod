package plasticmod.plastic.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.block.PlasticWirelessTNT;

public class TNTController extends Item
{
	public static int fast =1;
	private static final String __OBFID = "CL_00000035";

	public TNTController()
	{
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (player.isSneaking())
		{
			player.addChatMessage(new ChatComponentTranslation("[INFO]チャンネル切り替え"));
		} else {
			int playernow =player.getEntityId();
			if (!((PlasticCore.tntx == 0 )|| (PlasticCore.tnty == 0) || (PlasticCore.tntz == 0 ))){
				if(PlasticWirelessTNT.player == playernow ){
					//初期化
					if (!(Minecraft.getMinecraft().theWorld.getBlock(PlasticCore.tntx, PlasticCore.tnty, PlasticCore.tntz ) == PlasticCore.PlasticWirelessTNT)){
						player.addChatMessage(new ChatComponentTranslation("[ERROR]選択されている座標上にTNTを見つけられませんでした。"));
						player.addChatMessage(new ChatComponentTranslation("[ERROR]もう一度TNTを選択し直してください。"));
					}else {
						player.addChatMessage(new ChatComponentTranslation("[WARNING]発破します。"));
						//エアーブロックにチェンジ
						world.setBlockToAir(PlasticCore.tntx, PlasticCore.tnty, PlasticCore.tntz );
						//ドカーン
						PlasticWirelessTNT.func_150114_a(world, PlasticCore.tntx, PlasticCore.tnty, PlasticCore.tntz , 1,  (EntityLivingBase)null);
						//コントローラーにダメージ
						itemStack.damageItem(1, player);
					}
				}else {
					player.addChatMessage(new ChatComponentTranslation("[ERROR]TNTを選択したプレイヤーとあなたが一致しませんでした。"));
				}
			}else{
				player.addChatMessage(new ChatComponentTranslation("[ERROR]対象のTNTが選択されていません。"));
			}
		}
		return itemStack;
	}



	@Override
	@SideOnly(Side.CLIENT)
	//アイテムに情報を追加
	public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List list, boolean advanced) {
		list.add(ChatFormatting.RED+(StatCollector.translateToLocal("title.tntcontroller.name")));
	}
}
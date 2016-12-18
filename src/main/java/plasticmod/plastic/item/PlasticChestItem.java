package plasticmod.plastic.item;
 
 
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
 
public class PlasticChestItem extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon sidedIcon;
	private IIcon topIcon;
	
	public PlasticChestItem()
    {
        super();
        //スタックサイズは1コ
        this.setMaxStackSize(1);
    }
    
   // private static final Item textures = new Item.getItemFromBlock(Plasticcore.plasticblockblueCarpet);
 
    /*
        Itemが右クリックされた時のメソッド
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        /*
            GUIを開く。インスタンス, GUIのID, World, X, Y, Z
         */
        player.openGui(PlasticCore.INSTANCE, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return itemStack;
	}
}

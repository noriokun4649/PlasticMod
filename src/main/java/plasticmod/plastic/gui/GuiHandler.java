package plasticmod.plastic.gui;


import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class GuiHandler implements IGuiHandler {
   /*サーバー側の処理*/
   @Override
   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
       if (ID == PlasticCore.ChestBlockGUI_ID) {
           return new PlasticContainer(z, y, z);
       }
       if(ID == PlasticCore.ChestItemGUI_ID)
           return new PlasticContainerInventoryItem(player.inventory);
       return null;
   }

   /*クライアント側の処理*/
   @Override
   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
       if (ID == PlasticCore.ChestBlockGUI_ID) {
           return new PlasticGuiContainer(x, y, z);
       }
       if(ID == PlasticCore.ChestItemGUI_ID)
           return new PlasticGuiInventoryItem(player.inventory);
       return null;
   }
}
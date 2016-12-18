package plasticmod.plastic.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;

public class PlasticContainer extends Container {
	private InventoryBasic inventory;
   //座標でGUIを開くか判定するためのもの。
   int xCoord, yCoord, zCorrd;
   public PlasticContainer(int x, int y, int z) {
       this.xCoord = x;
       this.yCoord = y;
       this.zCorrd = z;
   }

   @Override
   public boolean canInteractWith(EntityPlayer player) {
       //もし、ブロックとの位置関係でGUI制御したいなら、こちらを使う
//       return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCorrd + 0.5D) <= 64D;
       return true;
   }

}
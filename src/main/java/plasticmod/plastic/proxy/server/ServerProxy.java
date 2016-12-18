package plasticmod.plastic.proxy.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import plasticmod.plastic.proxy.CommonProxy;

public class ServerProxy implements CommonProxy{
   @Override
   public World getClientWorld()
   {
      return null;
   }
   @Override
   public int getNewRenderType(){
      return -1;
   }
   
   @Override
   public void registerRenderes(){
      
   }
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	@Override
	public void message(EntityPlayer player){
	}
   
}

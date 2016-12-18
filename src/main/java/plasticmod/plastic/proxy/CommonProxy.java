package plasticmod.plastic.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface CommonProxy {
   public World getClientWorld();
   
   public int getNewRenderType();
   
   public void registerRenderes();
   
   public void init();

   public void message(EntityPlayer player);
}

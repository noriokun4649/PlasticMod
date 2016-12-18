package plasticmod.plastic.proxy.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import plasticmod.plastic.config.PlasticConfig;
import plasticmod.plastic.entity.EntityPlasticArrow;
import plasticmod.plastic.entity.EntityPlasticBullet;
import plasticmod.plastic.entity.EntityPlasticCreeper;
import plasticmod.plastic.entity.EntityPlasticSkeleton;
import plasticmod.plastic.entity.EntityPlasticTNTPrimed;
import plasticmod.plastic.entity.EntityRarePlasticArrow;
import plasticmod.plastic.proxy.CommonProxy;
import plasticmod.plastic.render.RenderPlasticArrow;
import plasticmod.plastic.render.RenderPlasticBullet;
import plasticmod.plastic.render.RenderPlasticCreeper;
import plasticmod.plastic.render.RenderPlasticLamp;
import plasticmod.plastic.render.RenderPlasticSkeleton;
import plasticmod.plastic.render.RenderRarePlasticArrow;
import plasticmod.plastic.render.RenderPlasticTNTPrimed;

public class ClientProxy implements CommonProxy{

   @Override
   public World getClientWorld(){
      return FMLClientHandler.instance().getClient().theWorld;
   }

   @Override
   public int getNewRenderType(){
      return RenderingRegistry.getNextAvailableRenderId();
      
   }
   @Override
   public void message(EntityPlayer player){
	   
   	player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("title.magicstick.name")));
   }
   
   @Override
   public void registerRenderes(){
	   RenderingRegistry.registerBlockHandler(new RenderPlasticLamp());
	   RenderingRegistry.registerEntityRenderingHandler(EntityPlasticTNTPrimed.class, new RenderPlasticTNTPrimed());
	   RenderingRegistry.registerEntityRenderingHandler(EntityPlasticBullet.class, new RenderPlasticBullet());
	   RenderingRegistry.registerEntityRenderingHandler(EntityPlasticArrow.class, new RenderPlasticArrow());
	   RenderingRegistry.registerEntityRenderingHandler(EntityRarePlasticArrow.class, new RenderRarePlasticArrow());
	   RenderingRegistry.registerEntityRenderingHandler(EntityPlasticSkeleton.class, new RenderPlasticSkeleton());
	   RenderingRegistry.registerEntityRenderingHandler(EntityPlasticCreeper.class, new RenderPlasticCreeper()); 
   }

   public void init() {
		//村人のテクスチャ
		//村人登録
		VillagerRegistry.instance().registerVillagerSkin(PlasticConfig.PlasticVillagerID,new ResourceLocation("plasticmod:textures/entity/villager/plastic.png"));
   }
}
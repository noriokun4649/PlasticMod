package plasticmod.plastic.drop;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import plasticmod.plastic.PlasticCore;
public class MonsterDrop {

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if(event.entityLiving.worldObj.isRemote) {
			return;
		}
		if(event.entityLiving instanceof EntityZombie) {
			if(event.entityLiving.worldObj.rand.nextInt(10) == 0) {
				event.entityLiving.entityDropItem(new ItemStack (PlasticCore.tomatospecies,3), 10F);
				event.entityLiving.entityDropItem(new ItemStack (PlasticCore.strawberryspecies,3), 10F);
			}
		}
	}
}
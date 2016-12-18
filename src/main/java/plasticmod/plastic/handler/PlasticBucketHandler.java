package plasticmod.plastic.handler;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
public class PlasticBucketHandler {

    public static PlasticBucketHandler INSTANCE = new PlasticBucketHandler();
    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {

            ItemStack result = fillCustomBucket(event.world, event.target);

            if (result == null)
                    return;

            event.result = result;
            event.setResult(Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
    	//BlockIDを取得
            Block blockID = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
            //BlockIDから登録した液体バケツを取得
            Item bucket = buckets.get(blockID);
            if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
                    world.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.air);
                    return new ItemStack(bucket);//液体バケツを返却
            } else
                    return null;
    }
}

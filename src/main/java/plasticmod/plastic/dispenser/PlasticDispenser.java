package plasticmod.plastic.dispenser;

import cpw.mods.fml.common.Mod.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.entity.EntityPlasticArrow;
import plasticmod.plastic.entity.EntityPlasticCreeper;
import plasticmod.plastic.entity.EntityPlasticSkeleton;
import plasticmod.plastic.entity.EntityPlasticTNTPrimed;
import plasticmod.plastic.entity.EntityRarePlasticArrow;

public class PlasticDispenser {
	@EventHandler
	public static void init(){
		//矢のディスペンサー対応
		BlockDispenser.dispenseBehaviorRegistry.putObject(PlasticCore.plasticarrow, new BehaviorProjectileDispense()
		{
			private static final String __OBFID = "CL_00001398";
			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
			{
				EntityPlasticArrow plasticarrowantity = new EntityPlasticArrow(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
				plasticarrowantity.canBePickedUp = 1;
			    return plasticarrowantity;
			}
		});
		//矢のディスペンサー対応
		BlockDispenser.dispenseBehaviorRegistry.putObject(PlasticCore.rareplasticarrow, new BehaviorProjectileDispense()
		{
			private static final String __OBFID = "CL_00001398";
			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
			{
				EntityRarePlasticArrow rareplasticarrowantity = new EntityRarePlasticArrow(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
				rareplasticarrowantity.canBePickedUp = 1;
				return rareplasticarrowantity;
		    }
		});
		//プラスチックのスポーンエッグ、ディスペンサー対応
		BlockDispenser.dispenseBehaviorRegistry.putObject(PlasticCore.itemSpawnEgg,new  IBehaviorDispenseItem(){
		       @Override
		       public ItemStack dispense(IBlockSource var1, ItemStack var2){
		              World world = var1.getWorld();//World
		              /*Item item = var2.getItem();*/ //Item
		              IPosition iposition = BlockDispenser.func_149939_a(var1);//IPosition
		              double x = iposition.getX();//
		              double y = iposition.getY();//ディスペンサー射出口前の座標を取得
		              double z = iposition.getZ();//
		              int damage = var2.getItemDamage();
		              if (damage == 0 ){
						EntityPlasticSkeleton plasticskeleton = new EntityPlasticSkeleton(world);
						plasticskeleton.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
						world.spawnEntityInWorld(plasticskeleton);
		              }else if (damage == 1){
		  				EntityPlasticCreeper plasticcreeper = new EntityPlasticCreeper(world);
						plasticcreeper.setLocationAndAngles(x,y,z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
						world.spawnEntityInWorld(plasticcreeper);
		              }
		              --var2.stackSize;//アイテムをひとつ減らす
		              return var2;
		       }
		});
		//溶けたプラスチックのディスペンサー対応
		BlockDispenser.dispenseBehaviorRegistry.putObject(PlasticCore.PlasticFluid_Bucket,new  IBehaviorDispenseItem(){
		       @Override
		       public ItemStack dispense(IBlockSource var1, ItemStack var2){
		    	   		EnumFacing enumfacing = BlockDispenser.func_149937_b(var1.getBlockMetadata());
						World world = var1.getWorld();//World
						int x = var1.getXInt() + enumfacing.getFrontOffsetX();
						int y = var1.getYInt() + enumfacing.getFrontOffsetY();
						int z = var1.getZInt() + enumfacing.getFrontOffsetZ();
		              world.setBlock( (int)x, (int)y, (int)z,PlasticCore.PlasticFluidBlock);
		              var2.func_150996_a(Items.bucket);
		              var2.stackSize = 1;
		              return var2;
		       }
		});
		//TNTのディスペンサー対応
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(PlasticCore.PlasticTNT), new BehaviorDefaultDispenseItem()
        {
            private static final String __OBFID = "CL_00001403";
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
            {
                EnumFacing enumfacing = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                World world = p_82487_1_.getWorld();
                int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
                int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
                int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();
                EntityPlasticTNTPrimed entitytntprimed = new EntityPlasticTNTPrimed(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), (EntityLivingBase)null);
                world.spawnEntityInWorld(entitytntprimed);
                --p_82487_2_.stackSize;
                return p_82487_2_;
            }
        });
        //火打石のディスペンサー対応
        BehaviorDefaultDispenseItem steel = new BehaviorDefaultDispenseItem()
        {
            private boolean field_150839_b = true;
            private static final String __OBFID = "CL_00001401";
            /**
             * Dispense the specified stack, play the dispense sound and spawn particles.
             */
            protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
            {
                EnumFacing enumfacing = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                World world = p_82487_1_.getWorld();
                int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
                int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
                int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();

                if (world.isAirBlock(i, j, k))
                {
                    world.setBlock(i, j, k, Blocks.fire);

                    if (p_82487_2_.attemptDamageItem(1, world.rand))
                    {
                        p_82487_2_.stackSize = 0;
                    }
                }
                else if (world.getBlock(i, j, k) == Blocks.tnt)
                {
                    Blocks.tnt.onBlockDestroyedByPlayer(world, i, j, k, 1);
                    world.setBlockToAir(i, j, k);
                }
                else
                {
                    this.field_150839_b = false;
                }

                return p_82487_2_;
            }
            /**
             * Play the dispense sound from the specified block.
             */
            protected void playDispenseSound(IBlockSource p_82485_1_)
            {
                if (this.field_150839_b)
                {
                    p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
                }
                else
                {
                    p_82485_1_.getWorld().playAuxSFX(1001, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
                }
            }
        };
        BlockDispenser.dispenseBehaviorRegistry.putObject(PlasticCore.flintAndPlasticSteel,steel );
        BlockDispenser.dispenseBehaviorRegistry.putObject(PlasticCore.flintAndRarePlasticSteel,steel );
        //空バケツ時で前に解けたプラスチックがあった場合の処理
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem()
        {
            private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001400";
            public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
            {
                EnumFacing enumfacing = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                World world = p_82487_1_.getWorld();
                int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
                int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
                int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();
                Material material = world.getBlock(i, j, k).getMaterial();
                Block getblock = world.getBlock(i, j, k);
                int l = world.getBlockMetadata(i, j, k);
                Item item = null;

                if (Material.water.equals(material) && l == 0)
                {
                	//追加部分ここから
                	if (!(getblock == PlasticCore.PlasticFluidBlock))
                	{
                    item = Items.water_bucket;
                	}else if(getblock == PlasticCore.PlasticFluidBlock){
                		item = PlasticCore.PlasticFluid_Bucket;
                	}
                	//ここまで
                }
                else
                {
                    if (!Material.lava.equals(material) || l != 0)
                    {
                        return super.dispenseStack(p_82487_1_, p_82487_2_);
                    }

                    item = Items.lava_bucket;
                }

                world.setBlockToAir(i, j, k);

                if (--p_82487_2_.stackSize == 0)
                {
                    p_82487_2_.func_150996_a(item);
                    p_82487_2_.stackSize = 1;
                }
                else if (((TileEntityDispenser)p_82487_1_.getBlockTileEntity()).func_146019_a(new ItemStack(item)) < 0)
                {
                    this.field_150840_b.dispense(p_82487_1_, new ItemStack(item));
                }

                return p_82487_2_;
            }
        });
	}
}

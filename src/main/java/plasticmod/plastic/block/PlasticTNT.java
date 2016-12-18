package plasticmod.plastic.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.entity.EntityPlasticArrow;
import plasticmod.plastic.entity.EntityPlasticBullet;
import plasticmod.plastic.entity.EntityPlasticTNTPrimed;
import plasticmod.plastic.entity.EntityRarePlasticArrow;

public class PlasticTNT extends Block
{

    @SideOnly(Side.CLIENT)
    private IIcon field_150116_a;
    @SideOnly(Side.CLIENT)
    private IIcon field_150115_b;
    public PlasticTNT()
    {
        super(Material.tnt);
        setBlockName("plasticTNT");
        setHardness(1.0F);
        setCreativeTab(PlasticCore.plasticTab);
    }
    //テクスチャを返す処理。面によって違うのを返す。
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 0 ? this.field_150115_b : (p_149691_1_ == 1 ? this.field_150116_a : this.blockIcon);
    }

    //ブロックを設置した時の処理。レッドストーン入力があると、即"シュー"
    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);

        if (p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_))
        {
            //プレイヤーが壊した時の処理にEntityのスポーンの処理をまとめている模様。
            this.onBlockDestroyedByPlayer(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, 1);
            p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
        }
    }
    //隣のブロックに変化があった時呼ばれる。レッドストーン入力の検知。
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))
        {
            this.onBlockDestroyedByPlayer(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
        
        
        
    }


    //破壊時のドロップ数
    public int quantityDropped(Random p_149745_1_)
    {
        return 1;
    }
    //爆発で壊れたときは・・・
    public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
    {
        if (!p_149723_1_.isRemote)
        {
        	EntityPlasticTNTPrimed entitytntprimed = new EntityPlasticTNTPrimed(p_149723_1_, (double)((float)p_149723_2_ + 0.5F), (double)((float)p_149723_3_ + 0.5F), (double)((float)p_149723_4_ + 0.5F), p_149723_5_.getExplosivePlacedBy());
            //爆発までの時間を短くクライアントとサーバーでずれると困るのでランダムにはしない。
        	entitytntprimed.fuse = p_149723_1_.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            p_149723_1_.spawnEntityInWorld(entitytntprimed);
        }
    }

    //プレイヤーに壊された!!
    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
    {
        this.func_150114_a(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_, (EntityLivingBase)null);
    }

    public void func_150114_a(World p_150114_1_, int p_150114_2_, int p_150114_3_, int p_150114_4_, int p_150114_5_, EntityLivingBase p_150114_6_)
    {
        if (!p_150114_1_.isRemote)
        {
            if ((p_150114_5_ & 1) == 1) //Metadataが1だと実行。実際、Metadataは1にならないはずだが、爆発させたい破壊時に1になる。
            {
            	EntityPlasticTNTPrimed entitytntprimed = new EntityPlasticTNTPrimed(p_150114_1_, (double)((float)p_150114_2_ + 0.5F), (double)((float)p_150114_3_ + 0.5F), (double)((float)p_150114_4_ + 0.5F), p_150114_6_);
                p_150114_1_.spawnEntityInWorld(entitytntprimed);
                p_150114_1_.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
            }
        }
    }


    //右クリックされた時
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        //クリックしたプレイヤーが火打石を持っていると
        if ( (p_149727_5_.getCurrentEquippedItem() != null && p_149727_5_.getCurrentEquippedItem().getItem() == Items.flint_and_steel) || 
        	 (p_149727_5_.getCurrentEquippedItem() != null && p_149727_5_.getCurrentEquippedItem().getItem() == PlasticCore.flintAndPlasticSteel) || 
        	 (p_149727_5_.getCurrentEquippedItem() != null && p_149727_5_.getCurrentEquippedItem().getItem() == PlasticCore.flintAndRarePlasticSteel) )
        {
        	//バーン
            this.func_150114_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, 1, p_149727_5_);
            //エアーブロックにチェンジ
            p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
            //火打ち石にダメージ
            p_149727_5_.getCurrentEquippedItem().damageItem(1, p_149727_5_);
            return true;
        }
        else
        {
        	//標準の処理をする
            return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
        }
	}
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
    {
        if (p_149670_5_ instanceof EntityArrow && !p_149670_1_.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow)p_149670_5_;

            if (entityarrow.isBurning())
            {
                this.func_150114_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, 1, entityarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityarrow.shootingEntity : null);
                p_149670_1_.setBlockToAir(p_149670_2_, p_149670_3_, p_149670_4_);
            }
        }
        if (p_149670_5_ instanceof EntityRarePlasticArrow && !p_149670_1_.isRemote)
        {
        	EntityRarePlasticArrow entityrareplasticarrow = (EntityRarePlasticArrow)p_149670_5_;

            if (entityrareplasticarrow.isBurning())
            {
                this.func_150114_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, 1, entityrareplasticarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityrareplasticarrow.shootingEntity : null);
                p_149670_1_.setBlockToAir(p_149670_2_, p_149670_3_, p_149670_4_);
            }
        }
        if (p_149670_5_ instanceof EntityPlasticArrow && !p_149670_1_.isRemote)
        {
        	EntityPlasticArrow entityplasticarrow = (EntityPlasticArrow)p_149670_5_;

            if (entityplasticarrow.isBurning())
            {
                this.func_150114_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, 1, entityplasticarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityplasticarrow.shootingEntity : null);
                p_149670_1_.setBlockToAir(p_149670_2_, p_149670_3_, p_149670_4_);
            }
        }
        if (p_149670_5_ instanceof EntityPlasticBullet && !p_149670_1_.isRemote)
        {
        	EntityPlasticBullet entityplasticarrow = (EntityPlasticBullet)p_149670_5_;
                this.func_150114_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, 1, entityplasticarrow.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)entityplasticarrow.shootingEntity : null);
                p_149670_1_.setBlockToAir(p_149670_2_, p_149670_3_, p_149670_4_);
            
        }
    }
    
    //このブロックが爆発からドロップできるかどうかを返します。

    public boolean canDropFromExplosion(Explosion p_149659_1_)
    {
        return false;
    }

    protected ItemStack createStackedBlock(int par1)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon("plasticmod:plastictnt_side");
        this.field_150116_a = p_149651_1_.registerIcon("plasticmod:plastictnt_top");
        this.field_150115_b = p_149651_1_.registerIcon("plasticmod:plastictnt_bottom");
    }

}

package plasticmod.plastic.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class NaphthaBlock extends Block
{
    public NaphthaBlock() {
        super(Material.rock);
        setCreativeTab(PlasticCore.plasticTab);//クリエイティブタブの選択
        setBlockName("naphthablock");//システム名の設定
        setBlockTextureName("plasticmod:naphthablock");//テクスチャ指定
        setHardness(1.5F);//硬さ
        setResistance(3.0F);//爆破耐性
        setStepSound(soundTypeStone);//ブロックの上を歩いた時の音
        setLightOpacity(10);//ブロックの透過係数。デフォルト０（不透過）
        /*setLightLevel(1.0F);*//*明るさ 1.0F = 15*/
    }
    public boolean isBlockBurning(World world, int x, int y, int z){
	return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ){
        //ブロックを右クリックした際の動作
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player){
        //ブロックを左クリックした際の動作
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock){
        //周囲のブロックが更新された際の動作
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random){
        //ドロップするアイテムを返す
        return quantityDroppedWithBonus(fortune, random);
    }

    @Override
    public int quantityDropped(Random random){
        //ドロップさせる量を返す
        return 1;
    }
    /* @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.TopIcon = par1IconRegister.registerIcon("zombiemod:plasticblock");
        this.SideIcon = par1IconRegister.registerIcon("zombiemod:plasticblock-side");
    }
 
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
         if(par1 == 0 || par1 == 1)
         {
                  return TopIcon;
         }
         if(par1 == 2 || par1 == 3 || par1 == 4 || par1 == 5 || par1 == 6)
         {
                  return SideIcon;
         }
         else
         {
                 return null;
         }
    }*/
}

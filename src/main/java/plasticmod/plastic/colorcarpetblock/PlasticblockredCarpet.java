package plasticmod.plastic.colorcarpetblock;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.config.PlasticConfig;

public class PlasticblockredCarpet extends Block
{

    public PlasticblockredCarpet() {
	super(Material.grass);
		setCreativeTab(PlasticCore.plasticTabBlock2);/*クリエイティブタブの選択*/
		//setBlockName("plasticBlock");/*システム名の設定*/
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
		setBlockTextureName("plasticmod:plasticblock");/*ブロックのテクスチャの指定(複数指定の場合は消してください。)*/
		/*以下のものは消しても結構です*/
		setHardness(0F);/*硬さ*/
		setResistance(1.0F);/*爆破耐性*/
		setStepSound(Block.soundTypeStone);/*ブロックの上を歩いた時の音*/
		/*setBlockUnbreakable();*//*ブロックを破壊不可に設定*/
		/*setTickRandomly(true);*//*ブロックのtick処理をランダムに。デフォルトfalse*/
		/*disableStats();*//*ブロックの統計情報を保存しない*/
		setLightOpacity(PlasticConfig.plasticcarpetlight);/*ブロックの透過係数。デフォルト０（不透過）*/
		//setLightLevel(1.0F);//*明るさ 1.0F = 15*/
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
    public boolean isOpaqueCube()
    {
       return false;
    }
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock){
        //周囲のブロックが更新された際の動作
    	if (world.getBlock(x, y-1, z) == Blocks.air)
    	{
    		world.setBlockToAir(x, y, z);
    		this.dropBlockAsItem(world,x,y,z, world.getBlockMetadata(x, y, z), 0);
    	}
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
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta)
	{
		return 0xff0000;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return 0xff0000;
	}

}
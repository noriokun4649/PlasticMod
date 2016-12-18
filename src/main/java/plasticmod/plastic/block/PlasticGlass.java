package plasticmod.plastic.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class PlasticGlass extends Block
{

    public PlasticGlass() {
	super(Material.glass);
		setCreativeTab(PlasticCore.plasticTab);
		setBlockName("PlasticGlass");
		setBlockTextureName("minecraft:glass");
		setHardness(0.5F);
		setResistance(10000000.0F);
		setStepSound(Block.soundTypeGlass);
		setLightOpacity(0);
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
    
}
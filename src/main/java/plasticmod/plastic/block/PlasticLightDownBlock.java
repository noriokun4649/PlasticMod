package plasticmod.plastic.block;
	 
	import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;
	 
	public class PlasticLightDownBlock extends Block
	{
	 
	    public PlasticLightDownBlock() {
			super(Material.glass);
			setCreativeTab(PlasticCore.plasticTab);
			setBlockName("plasticlight");
			setBlockTextureName("plasticmod:plasticlight");
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			setHardness(1.5F);
			setResistance(1.5F);
			setStepSound(Block.soundTypeStone);
			setLightOpacity(10);
			setLightLevel(1.0F);
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

	}
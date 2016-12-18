package plasticmod.plastic.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class PlasticWall extends BlockWall
{
	public static Block BlockType;

    public PlasticWall(Block p_i45435_1_)
    {
        super(p_i45435_1_);
        this.BlockType = p_i45435_1_;
		this.setCreativeTab(PlasticCore.plasticTab);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
    @Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return PlasticCore.plasticblock.getBlockTextureFromSide(p_149691_1_);
	}
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ){
        //ブロックを右クリックした際の動作]
        return false;
    }
	/**
	 * Return whether an adjacent block can connect to a wall.
	 */
    @Override
	public boolean canConnectWallTo(IBlockAccess p_150091_1_, int p_150091_2_, int p_150091_3_, int p_150091_4_)
	{
		Block block = p_150091_1_.getBlock(p_150091_2_, p_150091_3_, p_150091_4_);
		return block != this && block != Blocks.fence_gate  && block != PlasticCore.RarePlasticWall && block != Blocks.wall_sign && block != PlasticCore.PlasticFenceGate  && block != PlasticCore.RarePlasticFenceGate ? (block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false) : true;
	}
}
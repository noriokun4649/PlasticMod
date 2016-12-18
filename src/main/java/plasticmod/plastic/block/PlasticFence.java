package plasticmod.plastic.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import plasticmod.plastic.PlasticCore;

public class PlasticFence extends BlockFence
{
	private final String field_149827_a;
	private static final String __OBFID = "CL_00000242";

	public PlasticFence(String p_i45406_1_, Material p_i45406_2_)
	{

		super(p_i45406_1_, p_i45406_2_);
		this.field_149827_a = p_i45406_1_;
		this.setCreativeTab(PlasticCore.plasticTab);
	}
	/**
	 * Returns true if the specified block can be connected by a fence
	 */
	public boolean canConnectFenceTo(IBlockAccess p_149826_1_, int p_149826_2_, int p_149826_3_, int p_149826_4_)
	{
		Block block = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
		return block != this &&block != Blocks.fence_gate &&block != PlasticCore.RarePlasticFence &&block != PlasticCore.PlasticFence  &&block != PlasticCore.PlasticFence   &&block != PlasticCore.PlasticFenceGate &&block != PlasticCore.RarePlasticFenceGate  &&block != Blocks.fence  &&block != Blocks.nether_brick_fence  ? (block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false) : true;
	}
	public static boolean func_149825_a(Block p_149825_0_)
	{
		return p_149825_0_ == Blocks.fence || p_149825_0_ == Blocks.nether_brick_fence || p_149825_0_ == PlasticCore.PlasticFence || p_149825_0_ == PlasticCore.RarePlasticFence  ;
	}
}
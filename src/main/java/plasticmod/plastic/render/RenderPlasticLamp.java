package plasticmod.plastic.render;
 
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import plasticmod.plastic.PlasticCore;
 
public class RenderPlasticLamp implements ISimpleBlockRenderingHandler
{
 
   @Override
   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
   {
      
   }
 
   @Override
   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{

	           renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.log));
	           renderer.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 0.125D, 0.75D);
	           renderer.renderStandardBlock(block, x, y, z);
	           renderer.renderAllFaces = false;
	           
	           renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.glass));
	           renderer.setRenderBounds(0.3125D, 0.125D, 0.3125D, 0.6875D, 0.8125D, 0.6875D);
	           renderer.renderStandardBlock(block, x, y, z);
	           renderer.renderAllFaces = false;
	           
	           renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.log));
	           renderer.setRenderBounds(0.25D, 0.8125D, 0.25D, 0.75D, 0.9375D, 0.75D);
	           renderer.renderStandardBlock(block, x, y, z);
	           renderer.renderAllFaces = false;
	           
	           renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.log));
	           renderer.setRenderBounds(0.4375D, 0.9375D, 0.4375D, 0.5625D, 1.0D, 0.5625D);
	           renderer.renderStandardBlock(block, x, y, z);
	           renderer.renderAllFaces = false;
	           
	           renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.quartz_block));
	           renderer.setRenderBounds(0.4375D, 0.125D, 0.4375D, 0.5625D, 0.5D, 0.5625D);
	           renderer.renderStandardBlock(block, x, y, z);
	           renderer.renderAllFaces = false;
			
			
					// 追加部分。テクスチャを変更したら、必ず元に戻す。
					renderer.clearOverrideBlockTexture();
					block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					renderer.setRenderBoundsFromBlock(block);
			return true;
		}
		return false;
     }
   @Override
   public boolean shouldRender3DInInventory(int modelId) 
   {
      return false;
   }
 
   @Override
   public int getRenderId()
   {
      return PlasticCore.PlasticRenderID;
   }
 
}
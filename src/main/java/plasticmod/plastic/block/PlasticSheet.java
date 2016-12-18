package plasticmod.plastic.block;

	import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

	public class PlasticSheet extends Block
	{

	    public PlasticSheet() {
			super(Material.glass);
			setCreativeTab(PlasticCore.plasticTab);
			setBlockName("plasticsheet");
			setBlockTextureName("plasticmod:plasticblock");
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
			setHardness(15.5F);
			setResistance(15.5F);
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
	    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
	      int side, float posX, float posY, float posZ){
	      
	      ItemStack itemstack = player.inventory.getCurrentItem();
	      Item holdItem = itemstack == null ? null : itemstack.getItem();
	      //三項演算子 [trueかfalse] ? [trueのときの値] : [falseのときの値] もついでに覚えときましょｗ（難しかったらいいですｗ）
	      
	      if(holdItem != null && holdItem == PlasticCore.plasticshears){
				if(checkBlock(world, x, y, z, PlasticCore.PlasticSheet)){ // PlasticSheetかどうか調べる！
	    	  BreakDrop(itemstack,player,world,x,y,z,PlasticCore.PlasticSheet);
				}
	        // 簡単なアルゴリズムではないので、補足は書きますが、ちょっとむずかしいと思います。
	    	
	    		// import java.util.ArrayList; をファイルの先頭に記述してね
	    		final ArrayList<int[]> pointList = new ArrayList<int[]>(); //動的配列を作成
	    		
	    		//final修飾子は再代入不可能！
	    		//簡単に言うと、
	    		//pointList = new ArrayList<int[]>();
	    		//って書くとエラーになる！
	    		//別になくてもいいけど、間違わないように！
	    	
	    		try{
	    			pointList.add(new int[]{x, y, z});
	    			// 座標は、Point2Dを使ったり、自分で作ってもいいんだけど、
	    			// わかりやすいように配列を使うね。
	    	
	    			while(pointList.size() > 0){
	    				final int[] checking = pointList.get(0); // 先頭のデータ（配列）を代入！
	    				pointList.remove(0); // 変数に代入したので、リストからは消しちゃって大丈夫！
	    				
	    				final int
	    				cx = checking[0],
	    				cy = checking[1],
	    				cz = checking[2]; //わかりやすいように変数に代入！（わかりやすいし、直接参照するより速かったりするハズ）
	    	
	    				final int[][] checkfors = new int[][]{ // 隣接する座標を配列にする！
	    						new int[]{cx + 1, cy, cz},
	    						new int[]{cx, cy, cz + 1},
	    						new int[]{cx - 1, cy, cz},
	    						new int[]{cx, cy, cz - 1}, // 最後の要素の,(コンマ)は消さなくていい
	    				};
	    	
	    				for(final int[] checkpoint : checkfors){ // 拡張for文ですべてのデータにアクセス！
	    					final int
	    					nx = checkpoint[0],
	    					ny = checkpoint[1],
	    					nz = checkpoint[2]; // 代入！
	    					
	    					if(checkBlock(world, nx, ny, nz, PlasticCore.PlasticSheet)){ // PlasticSheetかどうか調べる！
	    						BreakDrop(itemstack, player, world, nx, ny, nz, PlasticCore.PlasticSheet); // まず壊す！！
	    						pointList.add(checkpoint); // 次に調べる項目として登録！！！
	    					}
	    				}
	    				if (itemstack.getItemDamage() > 0)
	    				{
	    					break;
	    				}
	    				
	    				/* // もしアイテムが壊れたときに処理を中止するなら、
	    				if(アイテムが壊れているかどうかの条件式){
	    				  break;
	    				}
	    				//とかけばいいです。こういう時にbreak;を使うのが一般的ですよ！
	    				//条件式は多分 itemstack.getDamage() > 0 とかだと思います（わからない）
	    				*/
	    				
	    			}
	    		}catch(ArrayIndexOutOfBoundsException ex){//起こりえないけど、存在しない添字でアクセスすると例外が飛ぶので一応
	    			ex.printStackTrace();
	    		}
	    	// boolean を return する処理
	      }
  		if (holdItem != null && holdItem == Item.getItemFromBlock(PlasticCore.PlasticSheet)){
			return true;
		}
		return false;
	      
	    }

	    /**
	     * 
	     * 指定したワールドの(x,y,z)にあるブロックがblockなのか調べる。
	     * 
	     */
	    private boolean checkBlock(World world, int x, int y, int z, Block block){
	      return world.getBlock(x, y, z) == block;
	    }

	    /**
	     * 
	     * 持ってるアイテムをゴリッとして、
	     * 指定したブロックを破壊する。
	     * 
	     * ただしこれだと、もってるアイテムが壊れたあとも壊し続けてしまうので、
	     * 壊れたらやめる、というプロセスを追加したほうがいいかも。
	     * 
	     */
	    private void BreakDrop(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, Block block){
	      itemstack.damageItem(1, player);
	      world.setBlockToAir(x, y, z);
	      this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
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
package plasticmod.plastic.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import plasticmod.plastic.PlasticCore;
import plasticmod.plastic.config.PlasticConfig;
import plasticmod.plastic.world.PlasticTeleporter;

public class PlasticPortalBlock extends Block {
	
    public static int func_149999_b(int p_149999_0_)
    {
        return p_149999_0_ & 3;
    }

	ForgeDirection[] dirs = {ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.EAST,ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH};

	public PlasticPortalBlock( Material par2Material) {
		super(par2Material);
		this.setTickRandomly(true);
		this.setLightLevel(1.0F);
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		int n = 0;
		for(ForgeDirection dir : dirs) {
			Block block = par1World.getBlock(par2 + dir.offsetX, par3 + dir.offsetY, par4 + dir.offsetZ);
			if(block == PlasticCore.plasticblock || block == this) {
				n++;
			}
		}
		if(n < 4) {
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}
@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBox(0,0,0,0,0,0);
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, Block par5) {
		int n = 0;
		for(ForgeDirection dir : dirs) {
			Block block = par1World.getBlock(par2 + dir.offsetX, par3 + dir.offsetY, par4 + dir.offsetZ);
			if(block == PlasticCore.plasticblock || block == this) {
				n++;
			}
		}
		if(n < 4) {
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		PlasticTeleporter.transferEntityToWorld(par5Entity, par1World.provider.dimensionId == PlasticConfig.dimensionID ? 0 : PlasticConfig.dimensionID);
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}


	@Override
	public void randomDisplayTick(World par1World, int par2, int par3,int par4, Random par5Random) {
		if (PlasticConfig.gateeffect == true){

		if (par5Random.nextInt(100) == 0) {
			par1World.playSound(par2 + 0.5D, par3 + 0.5D,
					par4 + 0.5D, "portal.portal", 0.5F,
					par5Random.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int l = 0; l < 4; ++l) {
			double d0 = (par2 + par5Random.nextFloat());
			double d1 = (par3 + par5Random.nextFloat());
			double d2 = (par4 + par5Random.nextFloat());
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			int i1 = par5Random.nextInt(2) * 2 - 1;
			d3 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			d4 = (par5Random.nextFloat() - 0.5D) * 0.5D;
			d5 = (par5Random.nextFloat() - 0.5D) * 0.5D;

			if (par1World.getBlock(par2 - 1, par3, par4) != this
					&& par1World.getBlock(par2 + 1, par3, par4) != this) {
				d0 = par2 + 0.5D + 0.25D * i1;
				d3 = (par5Random.nextFloat() * 2.0F * i1);
			} else {
				d2 = par4 + 0.5D + 0.25D * i1;
				d5 = (par5Random.nextFloat() * 2.0F * i1);
			}

			par1World.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
		}
		}
	}
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        int l = func_149999_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));

        if (l == 0)
        {
            if (p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_) != this && p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_) != this)
            {
                l = 2;
            }
            else
            {
                l = 1;
            }

            if (p_149719_1_ instanceof World && !((World)p_149719_1_).isRemote)
            {
                ((World)p_149719_1_).setBlockMetadataWithNotify(p_149719_2_, p_149719_3_, p_149719_4_, l, 2);
            }
        }

        float f = 0.125F;
        float f1 = 0.125F;

        if (l == 1)
        {
            f = 0.5F;
        }

        if (l == 2)
        {
            f1 = 0.5F;
        }

        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
    }
	@Override
	public Item getItem(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("plasticmod:plasticportal");
	}

}

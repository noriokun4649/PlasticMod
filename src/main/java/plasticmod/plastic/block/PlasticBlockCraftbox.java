package plasticmod.plastic.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import plasticmod.plastic.PlasticCore;

public class PlasticBlockCraftbox extends Block
{

	@SideOnly(Side.CLIENT)
	private IIcon sidedIcon;

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.openGui(PlasticCore.INSTANCE, PlasticCore.ChestBlockGUI_ID, world, x, y, z);
        return true;
    }

	public PlasticBlockCraftbox(Material material)
	{
		super(material);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		this.sidedIcon = iconRegister.registerIcon("crafting_table_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == ForgeDirection.UP.ordinal() || side == ForgeDirection.DOWN.ordinal()) {
			return this.blockIcon;
		}
		return this.sidedIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta)
	{
		return 0x00ff00;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z)
	{
		return 0x00ff00;
	}

}
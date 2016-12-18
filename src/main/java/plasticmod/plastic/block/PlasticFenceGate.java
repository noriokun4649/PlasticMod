package plasticmod.plastic.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.IIcon;
import plasticmod.plastic.PlasticCore;

public class PlasticFenceGate extends BlockFenceGate
{
    private static final String __OBFID = "CL_00000243";
    public PlasticFenceGate()
    {
        super();
        this.setCreativeTab(PlasticCore.plasticTab);
    }
	/**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return PlasticCore.plasticblock.getBlockTextureFromSide(p_149691_1_);
    }
}
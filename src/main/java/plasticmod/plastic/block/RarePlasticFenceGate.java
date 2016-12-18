package plasticmod.plastic.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import plasticmod.plastic.PlasticCore;

public class RarePlasticFenceGate extends BlockFenceGate
{
    private static final String __OBFID = "CL_00000243";
    public RarePlasticFenceGate()
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
        return PlasticCore.rareplasticblock.getBlockTextureFromSide(p_149691_1_);
    }
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        return false;
    }
}
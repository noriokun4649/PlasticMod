package plasticmod.plastic.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import plasticmod.plastic.PlasticCore;

public class PlasticStairs extends BlockStairs
{
    private static final int[][] field_150150_a = new int[][] {{2, 6}, {3, 7}, {2, 3}, {6, 7}, {0, 4}, {1, 5}, {0, 1}, {4, 5}};
    private final Block field_150149_b;
    private final int field_150151_M;
    private boolean field_150152_N;
    private int field_150153_O;
    private static final String __OBFID = "CL_00000314";

    public PlasticStairs(Block p_i45428_1_, int p_i45428_2_)
    {
        super(p_i45428_1_, p_i45428_2_);
        this.field_150149_b = p_i45428_1_;
        this.field_150151_M = p_i45428_2_;
        this.setStepSound(p_i45428_1_.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(PlasticCore.plasticTab);
    }

}
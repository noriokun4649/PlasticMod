package plasticmod.plastic.gui;
 
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
 
public class PlasticGuiInventoryItem extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
 
    public PlasticGuiInventoryItem(InventoryPlayer inventoryPlayer)
    {
        super(new PlasticContainerInventoryItem(inventoryPlayer));
        this.ySize = 222;
        this.xSize = 222;
        
    }
 
    /*
        ChestとかInventoryとか文字を描画する
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int p_146979_2_)
    {
        //描画する文字, X, Y, 色
        this.fontRendererObj.drawString(StatCollector.translateToLocal("title.plasticchestname.name"), 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("title.Inventory.name"), 8, this.ySize - 96 + 2, 4210752);
    }
 
    /*
        背景の描画
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
package plasticmod.plastic.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
 
public class PlasticGuiContainer extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("plasticmod", "textures/gui/gui2.png");
    public PlasticGuiContainer(int x, int y, int z) {
        super(new PlasticContainer(x, y, z));
        }

    /*GUIの文字等の描画処理*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
        //描画する文字, X, Y, 色
        this.fontRendererObj.drawString(StatCollector.translateToLocal("title.plasticchest.name"), 8, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("title.Inventory.name"), 8, this.ySize - 96 + 2, 4210752);
    }
 
    /*GUIの背景の描画処理*/
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ) {
        this.mc.renderEngine.bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
        
    }
 
    /*GUIが開いている時にゲームの処理を止めるかどうか。*/
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    
}
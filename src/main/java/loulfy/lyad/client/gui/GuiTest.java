package loulfy.lyad.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GuiTest extends GuiScreen
{
    @Override
    public void initGui()
    {
        //this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "This is button a"));
        //this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 4, "This is button b"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        Fluid fluid = FluidRegistry.getFluid("lava");
        TextureAtlasSprite fluidTexture = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        assert fluidTexture != null;
        drawTexturedModalRect(this.width/2 - 50, this.height/2 - 50, fluidTexture, 100, 100);

        this.drawString(this.mc.fontRenderer, "Ma Super GUI", this.width/2 - 30, this.height/2 - 2, 0xFFFFFF);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}

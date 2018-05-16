package loulfy.lyad.client.proxy;

import loulfy.lyad.common.init.Blocks;
import loulfy.lyad.common.proxy.CommonProxy;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerBlockRenders()
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Blocks.HeaterWall), 0, new ModelResourceLocation("lyad:heater_wall", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Blocks.HeaterPort), 0, new ModelResourceLocation("lyad:heater_port", "inventory"));
    }
}

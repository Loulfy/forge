package loulfy.lyad.common.proxy;

import loulfy.lyad.GuiProxy;
import loulfy.lyad.LyadMod;
import loulfy.lyad.common.init.Blocks;
import loulfy.lyad.common.tile.HeaterTileEntity;
import loulfy.lyad.common.tile.NetworkNode;
import loulfy.lyad.common.tile.NodeTileEntity;
import loulfy.lyad.common.tile.RSHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void initialize()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(LyadMod.instance, new GuiProxy());

        GameRegistry.registerTileEntity(HeaterTileEntity.class, "Heater");
        GameRegistry.registerTileEntity(NodeTileEntity.class, "Node");
        RSHelper.API.getNetworkNodeRegistry().add("test", (tag, world, pos) -> {
            NetworkNode node = new NetworkNode(world, pos);
            return node;
        });
    }

    public void registerBlockRenders() {}
}

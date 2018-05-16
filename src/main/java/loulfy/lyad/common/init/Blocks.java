package loulfy.lyad.common.init;

import loulfy.lyad.common.block.NodeBlock;
import loulfy.lyad.common.multiblock.HeaterBlockBase;
import loulfy.lyad.common.multiblock.HeaterBlockPort;
import loulfy.lyad.common.multiblock.HeaterBlockType;
import loulfy.lyad.common.multiblock.HeaterBlockWall;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder("lyad")
public final class Blocks
{
    public static final HeaterBlockBase HeaterWall = new HeaterBlockWall();
    public static final HeaterBlockBase HeaterPort = new HeaterBlockPort(HeaterBlockType.Liquid);
    public static final NodeBlock Node = new NodeBlock();

    public static void registerBlocks(IForgeRegistry<Block> registry)
    {
        registry.register(init(HeaterWall, "heater_wall"));
        registry.register(init(HeaterPort, "heater_port"));
        registry.register(init(Node, "node"));

    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {
        registry.register(Items.init(new ItemBlock(HeaterWall), "heater_wall"));
        registry.register(Items.init(new ItemBlock(HeaterPort), "heater_port"));
        registry.register(Items.init(new ItemBlock(Node), "node"));
    }

    public static Block init(Block block, String name)
    {
        return block.setUnlocalizedName(name).setRegistryName("lyad:" + name);
    }
}

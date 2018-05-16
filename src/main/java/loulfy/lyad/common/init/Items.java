package loulfy.lyad.common.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder("lyad")
public final class Items
{
    public static void registerItems(IForgeRegistry<Item> registry)
    {

    }

    public static Item init(Item item, String name)
    {
        return item.setUnlocalizedName(name).setRegistryName("lyad:" + name);
    }
}

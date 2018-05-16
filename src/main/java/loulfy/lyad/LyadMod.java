package loulfy.lyad;

import loulfy.lyad.common.init.Blocks;
import loulfy.lyad.common.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = LyadMod.MODID, version = LyadMod.VERSION, dependencies = "required-after:zerocore;")
@Mod.EventBusSubscriber
public class LyadMod
{
    public static final String MODID = "lyad";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static LyadMod instance;

    @SidedProxy(serverSide = "loulfy.lyad.common.proxy.CommonProxy", clientSide = "loulfy.lyad.client.proxy.ClientProxy")
    private static CommonProxy proxy;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.initialize();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        Blocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        Blocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        proxy.registerBlockRenders();
    }

    public static final CreativeTabs TAB = new CreativeTabs("lyad")
    {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.CARROT);
        }
    };
}

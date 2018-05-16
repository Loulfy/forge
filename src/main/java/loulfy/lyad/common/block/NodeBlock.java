package loulfy.lyad.common.block;

import com.raoulvdberge.refinedstorage.api.network.node.INetworkNode;
import com.raoulvdberge.refinedstorage.api.network.node.INetworkNodeManager;
import loulfy.lyad.LyadMod;
import loulfy.lyad.common.tile.NetworkNode;
import loulfy.lyad.common.tile.NodeTileEntity;
import loulfy.lyad.common.tile.RSHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class NodeBlock extends BasicBlock
{
    public NodeBlock()
    {
        super(Material.IRON);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new NodeTileEntity();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        super.breakBlock(world, pos, state);

        INetworkNodeManager manager = RSHelper.API.getNetworkNodeManager(world);

        INetworkNode node = manager.getNode(pos);

        manager.removeNode(pos);
        manager.markForSaving();

        if(node.getNetwork() != null)
        {
            node.getNetwork().getNodeGraph().rebuild();
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        if(!world.isRemote)
        {
            RSHelper.API.discoverNode(world, pos);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z)
    {
        if(world.isRemote) player.openGui(LyadMod.instance, 0, world, (int)x, (int)y, (int)z);
        //if(world.isRemote) return false;

        //ItemStack stack = player.getHeldItem(hand);

        //NodeTileEntity tile = (NodeTileEntity) world.getTileEntity(pos);
        //NetworkNode node =  tile.getNode();
        //node.add(stack);
        //player.sendMessage(new TextComponentString("ADDED!"));
        //tile.slot();
        return true;
    }


}

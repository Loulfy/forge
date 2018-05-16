package loulfy.lyad.common.tile;

import com.raoulvdberge.refinedstorage.api.network.node.INetworkNodeManager;
import com.raoulvdberge.refinedstorage.api.network.node.INetworkNodeProxy;
import loulfy.lyad.common.capabilities.Capabilities;
import mekanism.api.Coord4D;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NodeTileEntity extends TileEntity implements INetworkNodeProxy<NetworkNode>
{
    @CapabilityInject(INetworkNodeProxy.class)
    private static final Capability<INetworkNodeProxy> NETWORK_NODE_PROXY_CAPABILITY = null;

    private NetworkNode clientSideNode;

    @Nonnull
    @Override
    public NetworkNode getNode()
    {
        if(world.isRemote)
        {
            if(clientSideNode == null)
            {
                clientSideNode = new NetworkNode(world, pos);
            }

            return clientSideNode;
        }

        INetworkNodeManager manager = RSHelper.API.getNetworkNodeManager(world);

        NetworkNode node = (NetworkNode) manager.getNode(pos);

        if(node == null || !node.getId().equals("test"))
        {
            manager.setNode(pos, node = new NetworkNode(world, pos));
            manager.markForSaving();
        }

        return node;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing side)
    {
        if(capability == NETWORK_NODE_PROXY_CAPABILITY)
        {
            return true;
        }

        return super.hasCapability(capability, side);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing side)
    {
        if(capability == NETWORK_NODE_PROXY_CAPABILITY)
        {
            return NETWORK_NODE_PROXY_CAPABILITY.cast(this);
        }

        return super.getCapability(capability, side);
    }

    public void slot()
    {   if(world.isRemote) return;
        for(EnumFacing side : EnumFacing.values())
        {
            TileEntity adj = Coord4D.get(this).offset(side).getTileEntity(world);
            for(EnumFacing s : EnumFacing.values())
            {
                if(Capabilities.hasCapability(adj, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, s))
                {
                    IItemHandler items =  Capabilities.getCapability(adj, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, s);
                    System.out.println("SLOT "+s.getName()+" : "+items.getSlots());
                }
            }
        }
    }
}

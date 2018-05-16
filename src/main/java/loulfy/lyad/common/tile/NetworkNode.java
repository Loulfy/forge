package loulfy.lyad.common.tile;

import com.raoulvdberge.refinedstorage.api.autocrafting.ICraftingPatternProvider;
import mekanism.api.infuse.InfuseObject;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfuseType;

import com.raoulvdberge.refinedstorage.api.autocrafting.task.ICraftingStep;
import com.raoulvdberge.refinedstorage.api.autocrafting.ICraftingPattern;
import com.raoulvdberge.refinedstorage.api.autocrafting.ICraftingPatternContainer;
import com.raoulvdberge.refinedstorage.api.network.INetwork;
import com.raoulvdberge.refinedstorage.api.network.node.INetworkNode;
import com.raoulvdberge.refinedstorage.api.util.IStackList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class NetworkNode implements INetworkNode, ICraftingPatternContainer, ICraftingStep
{
    @Nullable
    private INetwork network;
    private World world;
    private BlockPos pos;

    List<ICraftingPattern> actualPatterns = new ArrayList<>();
    public InfuseType infused;

    private ItemStackHandler items = new ItemStackHandler();

    public NetworkNode(World world, BlockPos pos)
    {
        this.world = world;
        this.pos = pos;

        InfuseObject infused =  InfuseRegistry.getObject(new ItemStack(Items.REDSTONE));
    }

    @Override
    public int getEnergyUsage()
    {
        return 10;
    }

    @Nonnull
    @Override
    public ItemStack getItemStack()
    {
        IBlockState state = world.getBlockState(pos);
        return new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, state.getBlock().getMetaFromState(state));
    }

    @Override
    public void onConnected(INetwork network)
    {
        this.network = network;
    }

    @Override
    public void onDisconnected(INetwork network)
    {
        this.network = null;
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    @Nullable
    @Override
    public INetwork getNetwork()
    {
        return network;
    }

    @Override
    public void update()
    {

    }

    @Override
    public NBTTagCompound write(NBTTagCompound tag)
    {
        return tag;
    }

    @Override
    public BlockPos getPos()
    {
        return pos;
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public void markDirty()
    {
        if(!world.isRemote)
        {
            RSHelper.API.getNetworkNodeManager(world).markForSaving();
        }
    }

    @Override
    public String getId()
    {
        return "test";
    }

    @Override
    public boolean equals(Object o)
    {
        return RSHelper.API.isNetworkNodeEqual(this, o);
    }

    @Override
    public int hashCode()
    {
        return RSHelper.API.getNetworkNodeHashCode(this);
    }

    @Override
    public int getSpeedUpdateCount() {
        return 0;
    }

    @Override
    public IItemHandler getFacingInventory() {
        return null;
    }

    @Override
    public TileEntity getFacingTile() {
        return null;
    }

    @Override
    public List<ICraftingPattern> getPatterns()
    {
        return actualPatterns;
    }

    public void add(ItemStack stack)
    {
        ICraftingPattern pattern = ((ICraftingPatternProvider) stack.getItem()).create(world, stack, this);
        actualPatterns.clear();
        actualPatterns.add(pattern);
        if(getNetwork() != null)
            getNetwork().getCraftingManager().rebuild();
    }

    @Override
    public BlockPos getPosition() {
        return pos;
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    @Override
    public void setBlocked(boolean blocked) {

    }

    // ===========================================

    @Override
    public ICraftingPattern getPattern() {
        return null;
    }

    @Override
    public List<ItemStack> getInputs() {
        return null;
    }

    @Override
    public List<ICraftingStep> getPreliminarySteps() {
        return null;
    }

    @Override
    public boolean canStartProcessing(IStackList<ItemStack> items, IStackList<FluidStack> fluids) {
        return false;
    }

    @Override
    public boolean canStartProcessing() {
        return false;
    }

    @Override
    public void setStartedProcessing() {

    }

    @Override
    public boolean hasStartedProcessing() {
        return false;
    }

    @Override
    public void execute(Deque<ItemStack> toInsertItems, Deque<FluidStack> toInsertFluids) {

    }

    @Override
    public boolean hasReceivedOutputs() {
        return false;
    }

    @Override
    public boolean hasReceivedOutput(ItemStack stack) {
        return false;
    }

    @Override
    public int getReceivedOutput(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean onReceiveOutput(ItemStack stack) {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        return null;
    }

    // ----------------------------------
}

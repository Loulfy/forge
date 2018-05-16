package loulfy.lyad.common.multiblock;

import it.zerono.mods.zerocore.api.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.api.multiblock.MultiblockControllerBase;
import it.zerono.mods.zerocore.api.multiblock.rectangular.RectangularMultiblockControllerBase;
import it.zerono.mods.zerocore.api.multiblock.validation.IMultiblockValidator;
import it.zerono.mods.zerocore.lib.block.ModTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class HeaterController extends RectangularMultiblockControllerBase
{
    private boolean active;

    public HeaterController(World world)
    {
        super(world);
        this.active = false;
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart iMultiblockPart, NBTTagCompound nbtTagCompound)
    {

    }

    @Override
    protected void onBlockAdded(IMultiblockPart iMultiblockPart)
    {

    }

    @Override
    protected void onBlockRemoved(IMultiblockPart iMultiblockPart)
    {

    }

    @Override
    protected void onMachineAssembled()
    {
        if(this.WORLD.isRemote) this.markMultiblockForRenderUpdate();
    }

    @Override
    protected void onMachineRestored()
    {
        if(this.WORLD.isRemote) this.markMultiblockForRenderUpdate();
    }

    @Override
    protected void onMachinePaused()
    {
        if(this.WORLD.isRemote) this.markMultiblockForRenderUpdate();
    }

    @Override
    protected void onMachineDisassembled()
    {
        if(this.WORLD.isRemote) this.markMultiblockForRenderUpdate();
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine()
    {
        return 27;
    }

    @Override
    protected int getMaximumXSize()
    {
        return 3;
    }

    @Override
    protected int getMaximumZSize()
    {
        return 3;
    }

    @Override
    protected int getMaximumYSize()
    {
        return 3;
    }

    @Override
    protected void onAssimilate(MultiblockControllerBase multiblockControllerBase)
    {

    }

    @Override
    protected void onAssimilated(MultiblockControllerBase multiblockControllerBase)
    {

    }

    @Override
    protected boolean updateServer()
    {
        return false;
    }

    @Override
    protected void updateClient()
    {

    }

    @Override
    protected boolean isBlockGoodForFrame(World world, int i, int i1, int i2, IMultiblockValidator iMultiblockValidator)
    {
        return false;
    }

    @Override
    protected boolean isBlockGoodForTop(World world, int i, int i1, int i2, IMultiblockValidator iMultiblockValidator)
    {
        return false;
    }

    @Override
    protected boolean isBlockGoodForBottom(World world, int i, int i1, int i2, IMultiblockValidator iMultiblockValidator)
    {
        return false;
    }

    @Override
    protected boolean isBlockGoodForSides(World world, int i, int i1, int i2, IMultiblockValidator iMultiblockValidator)
    {
        return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(World world, int i, int i1, int i2, IMultiblockValidator iMultiblockValidator)
    {
        return false;
    }

    @Override
    protected void syncDataFrom(NBTTagCompound data, ModTileEntity.SyncReason syncReason)
    {
        if(data.hasKey("isActive")) this.setActive(data.getBoolean("isActive"));
    }

    @Override
    protected void syncDataTo(NBTTagCompound data, ModTileEntity.SyncReason syncReason)
    {
        data.setBoolean("isActive", this.isActive());
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {

        if(this.active == active) return;

        // the state was changed, set it
        this.active = active;

        if(WORLD.isRemote)
        {
            // on the client, request a render update
            this.markMultiblockForRenderUpdate();
        }
        else
        {
            // on the server side, request an update to be sent to the client and mark the save delegate as dirty
            this.markReferenceCoordForUpdate();
            this.markReferenceCoordDirty();
        }
    }

    public void toggle()
    {
        this.setActive(!this.active);
    }
}

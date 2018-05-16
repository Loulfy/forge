package loulfy.lyad.common.tile;

import it.zerono.mods.zerocore.api.multiblock.MultiblockControllerBase;
import it.zerono.mods.zerocore.api.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import it.zerono.mods.zerocore.api.multiblock.validation.IMultiblockValidator;
import loulfy.lyad.common.multiblock.HeaterController;

public class HeaterTileEntity extends RectangularMultiblockTileEntityBase
{
    @Override
    public boolean isGoodForFrame(IMultiblockValidator iMultiblockValidator)
    {
        return true;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator iMultiblockValidator)
    {
        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator iMultiblockValidator)
    {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator iMultiblockValidator)
    {
        return true;
    }

    @Override
    public boolean isGoodForInterior(IMultiblockValidator iMultiblockValidator)
    {
        return true;
    }

    @Override
    public void onMachineActivated()
    {

    }

    @Override
    public void onMachineDeactivated()
    {

    }

    @Override
    public MultiblockControllerBase createNewMultiblock()
    {
        return new HeaterController(this.world);
    }

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType()
    {
        return HeaterController.class;
    }
}

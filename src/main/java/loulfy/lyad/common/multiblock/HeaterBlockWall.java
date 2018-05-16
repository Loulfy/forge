package loulfy.lyad.common.multiblock;

import com.google.common.collect.Lists;
import it.zerono.mods.zerocore.lib.BlockFacings;
import it.zerono.mods.zerocore.api.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.PropertyBlockFacings;
import loulfy.lyad.common.tile.HeaterTileEntity;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;

public class HeaterBlockWall extends HeaterBlockBase
{
    private final static PropertyEnum FACES;

    static
    {

        ArrayList<PropertyBlockFacings> values = Lists.newArrayList();

        values.addAll(PropertyBlockFacings.ALL_AND_NONE);
        values.addAll(PropertyBlockFacings.FACES);
        values.addAll(PropertyBlockFacings.ANGLES);
        values.addAll(PropertyBlockFacings.CORNERS);
        values.add(PropertyBlockFacings.Opposite_EW);

        FACES = PropertyEnum.create("faces", PropertyBlockFacings.class, values);
    }

    public HeaterBlockWall()
    {
        super(HeaterBlockType.Wall);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACES, PropertyBlockFacings.All));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACES);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        IMultiblockPart part = this.getMultiblockPartAt(world, pos);

        if(part instanceof HeaterTileEntity)
        {

            HeaterTileEntity wallTile = (HeaterTileEntity)part;
            boolean assembled = wallTile.isConnected() && wallTile.getMultiblockController().isAssembled();
            BlockFacings facings = assembled ? wallTile.getOutwardsDir() : BlockFacings.ALL;

            state = state.withProperty(FACES, facings.toProperty());

            // active icon hack

            if(wallTile.getPartPosition().isFace())
            {

                HeaterController controller = (HeaterController)wallTile.getMultiblockController();
                boolean active = controller.isAssembled() && controller.isActive();

                if(active) state = state.withProperty(FACES, PropertyBlockFacings.Opposite_EW);
            }
        }

        return state;
    }
}

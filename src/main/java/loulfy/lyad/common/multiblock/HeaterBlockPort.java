package loulfy.lyad.common.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.properties.PropertyDirection;
import loulfy.lyad.common.tile.HeaterTileEntity;
import it.zerono.mods.zerocore.api.multiblock.IMultiblockPart;

public class HeaterBlockPort extends HeaterBlockBase
{
    public static final PropertyBool ASSEMBLED = PropertyBool.create("assembled");
    public static final PropertyDirection HFACING = PropertyDirection.create("hfacing", EnumFacing.Plane.HORIZONTAL);

    public HeaterBlockPort(HeaterBlockType type)
    {
        super(type);
        if(type == HeaterBlockType.Wall) throw new IllegalArgumentException("Invalid port type");
        this.setDefaultState(this.blockState.getBaseState().withProperty(HFACING, EnumFacing.NORTH).withProperty(ASSEMBLED, false));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(HFACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HFACING, ASSEMBLED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if(EnumFacing.Axis.Y == enumfacing.getAxis()) enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(HFACING, enumfacing);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position)
    {
        IMultiblockPart part = this.getMultiblockPartAt(world, position);

        if(part instanceof HeaterTileEntity)
        {

            HeaterTileEntity wallTile = (HeaterTileEntity) part;
            boolean assembled = wallTile.isConnected() && wallTile.getMultiblockController().isAssembled();

            state = state.withProperty(ASSEMBLED, assembled);

            if(assembled)
            {

                switch(wallTile.getPartPosition())
                {
                    case NorthFace:
                        state = state.withProperty(HFACING, EnumFacing.NORTH);
                        break;

                    case SouthFace:
                        state = state.withProperty(HFACING, EnumFacing.SOUTH);
                        break;

                    case WestFace:
                        state = state.withProperty(HFACING, EnumFacing.WEST);
                        break;

                    case EastFace:
                        state = state.withProperty(HFACING, EnumFacing.EAST);
                        break;
                }
            }
        }
        return state;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        facing = (null != placer) ? placer.getHorizontalFacing().getOpposite() : EnumFacing.NORTH;
        return this.getDefaultState().withProperty(HFACING, facing);
    }

    @Override
    public void onBlockAdded(World world, BlockPos position, IBlockState state)
    {
        EnumFacing newFacing = this.suggestDefaultFacing(world, position, state.getValue(HFACING));
        world.setBlockState(position, state.withProperty(HFACING, newFacing), 2);
    }

    private EnumFacing suggestDefaultFacing(World world, BlockPos position, EnumFacing currentFacing)
    {

        EnumFacing oppositeFacing = currentFacing.getOpposite();
        IBlockState facingBlockState = world.getBlockState(position.offset(currentFacing));
        IBlockState oppositeBlockState = world.getBlockState(position.offset(oppositeFacing));
        return facingBlockState.isFullBlock() && !oppositeBlockState.isFullBlock() ? oppositeFacing : currentFacing;
    }
}

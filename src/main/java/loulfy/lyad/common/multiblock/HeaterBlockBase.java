package loulfy.lyad.common.multiblock;

import loulfy.lyad.common.block.BasicBlock;
import loulfy.lyad.common.tile.HeaterTileEntity;
import it.zerono.mods.zerocore.api.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.api.multiblock.MultiblockControllerBase;
import it.zerono.mods.zerocore.api.multiblock.validation.ValidationError;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class HeaterBlockBase extends BasicBlock
{
    private HeaterBlockType type;

    public HeaterBlockBase(HeaterBlockType type)
    {
        super(Material.IRON);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);

        this.type = type;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        switch(type)
        {
            default:
                return new HeaterTileEntity();
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote || (hand != EnumHand.OFF_HAND)) return false;

        HeaterController controller = getHeaterController(world, pos);

        if(controller != null)
        {

            if(player.isSneaking())
            {
                // toggle machine status
                controller.toggle();
                player.sendMessage(new TextComponentString("done!"));
                return true;
            }
            else
            {
                ValidationError status = controller.getLastError();

                if(status != null)
                {
                    player.sendMessage(status.getChatMessage());
                    return true;
                }
            }
        }

        return false;
    }

    protected IMultiblockPart getMultiblockPartAt(IBlockAccess world, BlockPos position)
    {
        TileEntity te = world.getTileEntity(position);
        return te instanceof IMultiblockPart ? (IMultiblockPart) te : null;
    }

    protected HeaterController getHeaterController(IBlockAccess world, BlockPos position)
    {
        IMultiblockPart part = this.getMultiblockPartAt(world, position);
        if(part != null)
        {
            MultiblockControllerBase controller = part.getMultiblockController();
            return controller instanceof HeaterController ? (HeaterController) controller : null;
        }
        return null;
    }
}

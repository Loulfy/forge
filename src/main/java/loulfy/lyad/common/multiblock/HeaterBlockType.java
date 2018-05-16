package loulfy.lyad.common.multiblock;

import net.minecraft.util.IStringSerializable;

public enum HeaterBlockType implements IStringSerializable
{
    Wall,
    Liquid,
    Thermo;

    @Override
    public String getName()
    {
        return this.toString();
    }
}

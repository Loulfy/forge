package loulfy.lyad.common.block;

import loulfy.lyad.LyadMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block
{
    public BasicBlock(Material materiel)
    {
        super(materiel);
        this.setCreativeTab(LyadMod.TAB);
    }
}

package net.minecraft.block;

public class BlockYellowFlower extends BlockFlower
{
    /**
     * Get the Category of this flower (Yellow/Red)
     */
    public BlockFlower.EnumFlowerColor getBlockType()
    {
        return BlockFlower.EnumFlowerColor.YELLOW;
    }
}

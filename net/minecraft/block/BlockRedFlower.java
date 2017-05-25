package net.minecraft.block;

public class BlockRedFlower extends BlockFlower
{
    /**
     * Get the Category of this flower (Yellow/Red)
     */
    public BlockFlower.EnumFlowerColor getBlockType()
    {
        return BlockFlower.EnumFlowerColor.RED;
    }
}

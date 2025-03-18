package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CheesusVoxels {

    public static VoxelShape s_cheese_0 () {
        return Block.box(2, 0, 2, 14, 4, 14);
    }
    public static VoxelShape s_cheese_1 () {
        return Shapes.join(Block.box(2, 0, 2, 8, 4, 14), Block.box(8, 0, 8, 14, 4, 14), BooleanOp.OR);
    }
    public static VoxelShape s_cheese_2 () {
        return Block.box(2, 0, 2, 8, 4, 14);
    }
    public static VoxelShape s_cheese_3 () {
        return Block.box(2, 0, 8, 8, 4, 14);
    }

    public static VoxelShape cheese_0 () {
        return Block.box(2, 0, 2, 14, 6, 14);
    }
    public static VoxelShape cheese_1 () {
        return Shapes.join(Block.box(2, 0, 2, 8, 6, 14), Block.box(8, 0, 8, 14, 6, 14), BooleanOp.OR);
    }
    public static VoxelShape cheese_2 () {
        return Block.box(2, 0, 2, 8, 6, 14);
    }
    public static VoxelShape cheese_3 () {
        return Block.box(2, 0, 8, 8, 6, 14);
    }

    public static final VoxelShape[] NORMAL_SHAPE_BY_BITE = new VoxelShape[]{cheese_0(), cheese_1(), cheese_2(), cheese_3()};
    public static final VoxelShape[] SMALL_SHAPE_BY_BITE = new VoxelShape[]{s_cheese_0(), s_cheese_1(), s_cheese_2(), s_cheese_3()};

    public static VoxelShape CheeseStrainer() {
        return Stream.of(
                Block.box(13, 2, 1, 15, 16, 15),
                Block.box(1, 0, 1, 15, 2, 15),
                Block.box(3, 2, 1, 13, 16, 3),
                Block.box(3, 2, 13, 13, 16, 15),
                Block.box(1, 2, 1, 3, 16, 15)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

}

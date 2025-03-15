package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class CheesusVoxels {

    public static VoxelShape cheese_0 () {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape,
                Shapes.box(0.125, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape cheese_1 () {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape,
                Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.5, 0.5, 0.375, 0.875),
                BooleanOp.OR);

        return shape;
    }
    public static VoxelShape cheese_2 () {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape,
                Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.875), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape cheese_3 () {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.125, 0.875, 0.375, 0.5),
                BooleanOp.OR);

        return shape;
    }

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

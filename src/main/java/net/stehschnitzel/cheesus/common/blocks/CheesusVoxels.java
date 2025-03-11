package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape,
                Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375),
                BooleanOp.OR);
        shape = Shapes.join(shape,
                Shapes.box(0.0625, 0.0625, 0.0625, 0.9375, 0.5, 0.125),
                BooleanOp.OR);
        shape = Shapes.join(shape,
                Shapes.box(0.0625, 0.0625, 0.875, 0.9375, 0.5, 0.9375),
                BooleanOp.OR);
        shape = Shapes.join(shape,
                Shapes.box(0.875, 0.0625, 0.125, 0.9375, 0.5, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape,
                Shapes.box(0.0625, 0.0625, 0.125, 0.125, 0.5, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape,
                Shapes.box(0.125, 0.0625, 0.125, 0.875, 0.46875, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape,
                Shapes.box(0.125, 0.0625, 0.125, 0.875, 0.4375, 0.875),
                BooleanOp.OR);

        return shape;
    }

}

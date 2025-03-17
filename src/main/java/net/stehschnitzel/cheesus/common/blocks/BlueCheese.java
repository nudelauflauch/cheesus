package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlueCheese extends BasicCheese{
    public BlueCheese(Properties pProperties) {
        super(pProperties);
    }

    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{Block.box(2, 0, 2, 14, 3, 14),
            Shapes.join(Block.box(2, 0, 8, 8, 3, 14), Block.box(8, 0, 2, 14, 3, 14), BooleanOp.OR),
            Block.box(8, 0, 2, 14, 3, 14),
            Block.box(8, 0, 2, 14, 3, 8)
    };

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_BITE[pState.getValue(BITES)];
    }
}

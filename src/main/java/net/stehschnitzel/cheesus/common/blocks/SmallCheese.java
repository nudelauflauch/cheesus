package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallCheese extends EatableCheese {

    public SmallCheese(Properties pProperties) {
        super(pProperties);
    }

    public SmallCheese(Properties pProperties, MobEffect effect) {
        super(pProperties, effect);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return CheesusVoxels.SMALL_SHAPE_BY_BITE[pState.getValue(BITES)];
    }
}

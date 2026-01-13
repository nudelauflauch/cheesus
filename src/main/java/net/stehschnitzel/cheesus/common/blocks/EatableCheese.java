package net.stehschnitzel.cheesus.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

//this is the basic cheese class that all the cheese use
public class EatableCheese extends Block {

	public static final IntegerProperty BITES = IntegerProperty.create("bites",
			0, 3);
	public static final int MAX_BITES = 4;
	private final MobEffect effect;

	public EatableCheese(Properties pProperties) {
		super(pProperties);
		this.effect = null;
	}

	public EatableCheese(Properties pProperties, MobEffect effect) {
		super(pProperties);
		this.effect = effect;
	}

    public static final DispenseItemBehavior DISPENSE_CHEESE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

        protected ItemStack execute(BlockSource source, ItemStack stack) {
            ServerLevel level = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
            if (!(level.getBlockState(blockpos).getBlock() == Blocks.AIR &&
                    stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof EatableCheese)) {
                return defaultDispenseItemBehavior.dispense(source, stack);
            }

            level.setBlockAndUpdate(blockpos, blockItem.getBlock().defaultBlockState());
            return ItemStack.EMPTY;
        }
    };

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel,
			BlockPos pPos, CollisionContext pContext) {
		return CheesusVoxels.NORMAL_SHAPE_BY_BITE[pState.getValue(BITES)];
	}

	@Override
	public InteractionResult use(BlockState state, Level pLevel, BlockPos pos,
		Player player, InteractionHand handIn, BlockHitResult hit) {
		if (player.canEat(player.getFoodData().needsFood())) {
			player.getFoodData().eat(2, 3);

			if (state.getValue(BITES) == MAX_BITES - 1) {
				pLevel.removeBlock(pos, false);
			} else {
				pLevel.setBlockAndUpdate(pos, state.setValue(BITES, state.getValue(BITES) + 1));
			}

			if (this.effect != null) {
				player.addEffect(new MobEffectInstance(this.effect, 200, 0));
			}

			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		}
		return InteractionResult.FAIL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(BITES, 0);
	}

	@Override
	protected void createBlockStateDefinition(
			StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BITES);
	}
}
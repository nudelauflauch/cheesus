package net.stehschnitzel.cheesus.common.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.PushReaction;
import net.stehschnitzel.cheesus.common.blocks.entities.CheeseStrainerBlockEntity;
import net.stehschnitzel.cheesus.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.stehschnitzel.cheesus.init.BlockInit;
import org.jetbrains.annotations.Nullable;

public class CheeseStrainer extends BaseEntityBlock {

	public static final IntegerProperty LEVEL = IntegerProperty.create("level",
			0, 11);

	public static final DispenseItemBehavior DISPENSE_CHEESE_STRAINER_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

		protected ItemStack execute(BlockSource source, ItemStack stack) {
			if (stack.getItem() != BlockInit.CHEESE_STRAINER.get().asItem()) return defaultDispenseItemBehavior.dispense(source, stack);

			ServerLevel level = source.level();
			BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

			level.setBlockAndUpdate(blockpos, BlockInit.CHEESE_STRAINER.get().defaultBlockState());
			return ItemStack.EMPTY;
		}
	};

	public static final DispenseItemBehavior DISPENSE_INTO_CHEESE_STRAINER_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

		protected ItemStack execute(BlockSource source, ItemStack stack) {
            ServerLevel level = source.level();
            BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

			if (level.getBlockState(blockpos).is(BlockInit.CHEESE_STRAINER.get())) {
                BlockState state = level.getBlockState(blockpos);

				if (stack.getItem() == Items.WATER_BUCKET && state.getValue(LEVEL) == 0) {
					source.level().setBlockAndUpdate(blockpos, state.setValue(LEVEL, 7));

					return new ItemStack(Items.BUCKET);
				} else if (stack.getItem() == Items.MILK_BUCKET && state.getValue(LEVEL) < 3) {
					source.level().setBlockAndUpdate(blockpos, state.setValue(LEVEL, state.getValue(LEVEL) + 1));

					return new ItemStack(Items.BUCKET);
				} else if (stack.getItem() == BlockInit.CHEESE.get().asItem() && state.getValue(LEVEL) == 0) {
					source.level().setBlockAndUpdate(blockpos, state.setValue(LEVEL, 5));

					return ItemStack.EMPTY;
				}
			}
			return defaultDispenseItemBehavior.dispense(source,stack);
		}
	};

	public CheeseStrainer(Properties pProperties) {
		super(pProperties);
	}

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return switch (state.getValue(LEVEL)) {
            case 1, 5 -> 1;
            case 2, 3 -> state.getValue(LEVEL);
            case 4, 6 -> 4;
            case 7, 8, 9, 10, 11 -> 15 - (state.getValue(LEVEL) - 7) * 3;
            default -> 0;
        };
    }

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel,
			BlockPos pPos, CollisionContext pContext) {
		return CheesusVoxels.CheeseStrainer();
	}

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state,
                                              Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		int milkLevel = level.getBlockState(pos).getValue(LEVEL);
		Item item = stack.getItem();

        //add milk to the strainer from milk buckets, golden milk buckets and milk cup
        //has to be #contains milk otherwise it doesnt work when the mods arent loaded
		if (milkLevel < 3 && stack.getComponents().keySet().contains("milk")) {
            if (stack.getComponents().keySet().contains("golden_milk_bucket")) {
//                int milk_level = stack.getOrCreateTag().getInt("FluidLevel");
//                for (int i = milk_level; i > -1; i--) {
//                    milkLevel++;
//                    milk_level--;
//                    if (milkLevel > 2) break;
//                }
//                if (!player.isCreative()) {
//                    if (milk_level <= -1) {
//                        stack.shrink(1);
//                        addItemOrDrop(CCItems.GOLDEN_BUCKET.get(), player);
//                    } else {
//                        stack.getOrCreateTag().putInt("FluidLevel", milk_level);
//                    }
//                }
//
//            } else if (stack.getDescriptionId().contains("milk_cup")) {
//                if (!player.isCreative()) {
//                    player.getMainHandItem().shrink(1);
//                    addItemOrDrop(MDItems.COPPER_CUP, player);
//                }
//                milkLevel++;
//            } else if (item.equals(Items.MILK_BUCKET)) {
//                if (!player.isCreative()) {
//                    player.getMainHandItem().shrink(1);
//                    addItemOrDrop(Items.BUCKET, player);
//                }
//                milkLevel++;
            }
            level.setBlockAndUpdate(pos, state.setValue(LEVEL, milkLevel));
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.VILLAGER_WORK_LEATHERWORKER, SoundSource.BLOCKS, 1F, 1.0F, false);
            return ItemInteractionResult.CONSUME;

            //get milk out of the strainer again
        } else if (0 < milkLevel && milkLevel <= 3 && stack.is(Items.BUCKET)) {
            stack.shrink(1);
            addItemOrDrop(Items.MILK_BUCKET, player);
            level.setBlockAndUpdate(pos, state.setValue(LEVEL, level.getBlockState(pos).getValue(LEVEL)-1));

            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.8F, 1.0F, false);

        } else if (milkLevel == 0 && item == BlockInit.CHEESE.get().asItem()) {
			level.setBlockAndUpdate(pos, state.setValue(LEVEL, 5));
			if (!player.isCreative()) {
				stack.shrink(1);
			}
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.CORAL_BLOCK_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F, false);

            return ItemInteractionResult.CONSUME;

		} else if ((milkLevel == 0 || milkLevel >= 7) && item == Items.WATER_BUCKET) {
            if (!player.isCreative()) {
                stack.shrink(1);
                addItemOrDrop(Items.BUCKET, player);
            }
            level.setBlockAndUpdate(pos, state.setValue(LEVEL, 7));

            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.VILLAGER_WORK_LEATHERWORKER, SoundSource.BLOCKS, 1F, 1.0F, false);
            return ItemInteractionResult.CONSUME;

		} else if (milkLevel == 4) {
			addItemOrDrop(BlockInit.CHEESE.get(), player);
			level.setBlockAndUpdate(pos, state.setValue(LEVEL, 0));
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.3F, 1.0F, false);

            return ItemInteractionResult.CONSUME;
		} else if (milkLevel == 6) {
			addItemOrDrop(BlockInit.GREY_CHEESE.get(), player);
			level.setBlockAndUpdate(pos, state.setValue(LEVEL, 0));
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.3F, 1.0F, false);

			return ItemInteractionResult.CONSUME;
		}

		return ItemInteractionResult.sidedSuccess(!level.isClientSide());
	}

	private void addItemOrDrop(ItemLike item, Player player) {
		if (!player.addItem(new ItemStack(item))) {
			player.drop(new ItemStack(item), false);
		}
	}

	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        //for water dripping down on the sides
        if (pState.getValue(LEVEL) >= 7) {
            double d0 = (double) pPos.getX() + 0.1D;
            double d1 = (double) pPos.getY() + 0.9D - (pState.getValue(LEVEL) - 7) * 0.2;
            double d2 = (double) pPos.getZ() + 0.1D;

            double r0 = 0.8D;
            double r1 = 0.1;
            double r2 = 0.8D;

            //south
            pLevel.addParticle(ParticleTypes.FALLING_DRIPSTONE_WATER,
                    pPos.getX(), d1 + pRandom.nextDouble() * r1, d2 + pRandom.nextDouble() * r2,
                    0.0D, 2.0D, 0.0D);
            //west
            pLevel.addParticle(ParticleTypes.FALLING_DRIPSTONE_WATER,
                    d0 + pRandom.nextDouble() * r0, d1 + pRandom.nextDouble() * r1, pPos.getZ(),
                    0.0D, 2.0D, 0.0D);
            //north
            pLevel.addParticle(ParticleTypes.FALLING_DRIPSTONE_WATER,
                    pPos.getX() + 1, d1 + pRandom.nextDouble() * r1, d2 + pRandom.nextDouble() * r2,
                    0.0D, 2.0D, 0.0D);
            //east
            pLevel.addParticle(ParticleTypes.FALLING_DRIPSTONE_WATER,
                    d0 + pRandom.nextDouble() * r0, d1 + pRandom.nextDouble() * r1, pPos.getZ() + 1,
                    0.0D, 2.0D, 0.0D);

        }

        //for when it is making a new cheese
        if (isRandomlyTicking(pState)) {
            double d0 = (double) pPos.getX() + 0.5D;
            double d1 = (double) pPos.getY() + 0.9D;
            double d2 = (double) pPos.getZ() + 0.5D;

            double r0 = pRandom.nextDouble() * 0.6 - 0.3D;
            double r1 = pRandom.nextDouble() * 0.1;
            double r2 = pRandom.nextDouble() * 0.6 - 0.3D;
            pLevel.addParticle(ParticleTypes.MYCELIUM, d0 + r0, d1 + r1, d2 + r2,
                    0.0D, 2.0D, 0.0D);

		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(LEVEL) == 3 || pState.getValue(LEVEL) == 5;
	}

	@Override
	public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
		if (pState.getValue(LEVEL) == 3) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 4));
		} else if (pState.getValue(LEVEL) == 5) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 6));
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		return defaultBlockState().setValue(LEVEL, 0);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
		pBuilder.add(LEVEL);
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		if(pLevel.isClientSide() && pState.getValue(LEVEL) < 7) {
			return null;
		}

		return createTickerHelper(pBlockEntityType, BlockEntityInit.CHEESE_STRAINER.get(),
				(level, blockPos, blockState, growthChamberBlockEntity) -> growthChamberBlockEntity.tick(level, blockPos, blockState));
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return new CheeseStrainerBlockEntity(pPos, pState);
	}
}

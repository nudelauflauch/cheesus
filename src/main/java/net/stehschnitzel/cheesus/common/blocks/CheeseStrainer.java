package net.stehschnitzel.cheesus.common.blocks;

import com.sammy.minersdelight.setup.MDItems;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.Arrays;

public class CheeseStrainer extends BaseEntityBlock {

	public static final IntegerProperty LEVEL = IntegerProperty.create("level",
			0, 11);

	public static final DispenseItemBehavior DISPENSE_CHEESE_STRAINER_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

		protected ItemStack execute(BlockSource source, ItemStack stack) {
			if (stack.getItem() != BlockInit.CHEESE_STRAINER.get().asItem()) return defaultDispenseItemBehavior.dispense(source, stack);

			ServerLevel level = source.getLevel();
			BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

			level.setBlockAndUpdate(blockpos, BlockInit.CHEESE_STRAINER.get().defaultBlockState());
			return ItemStack.EMPTY;
		}
	};

	public static final DispenseItemBehavior DISPENSE_INTO_CHEESE_STRAINER_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

		protected ItemStack execute(BlockSource source, ItemStack stack) {
            ServerLevel level = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

			if (level.getBlockState(blockpos).is(BlockInit.CHEESE_STRAINER.get())) {
                BlockState state = level.getBlockState(blockpos);

				if (stack.getItem() == Items.WATER_BUCKET && state.getValue(LEVEL) == 0) {
					source.getLevel().setBlockAndUpdate(blockpos, state.setValue(LEVEL, 7));

					return new ItemStack(Items.BUCKET);
				} else if (stack.getItem() == Items.MILK_BUCKET && state.getValue(LEVEL) < 3) {
					source.getLevel().setBlockAndUpdate(blockpos, state.setValue(LEVEL, state.getValue(LEVEL) + 1));

					return new ItemStack(Items.BUCKET);
				} else if (stack.getItem() == BlockInit.CHEESE.get().asItem() && state.getValue(LEVEL) == 0) {
					source.getLevel().setBlockAndUpdate(blockpos, state.setValue(LEVEL, 5));

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
	public boolean hasAnalogOutputSignal(BlockState pState) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return switch (pState.getValue(LEVEL)) {
            case 1, 5 -> 1;
            case 2, 3 -> pState.getValue(LEVEL);
            case 4, 6 -> 4;
            case 7, 8, 9, 10, 11 -> 15 - (pState.getValue(LEVEL) - 7) * 3;
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
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
			Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		int level = pLevel.getBlockState(pPos).getValue(LEVEL);
        ItemStack stack = pPlayer.getItemInHand(pHand);
		Item item = stack.getItem();

        //add milk to the strainer from milk buckets, golden milk buckets and milk cup
        //has to be #contains milk otherwise it doesnt work when the mods arent loaded
		if (level < 3 && stack.getDescriptionId().contains("milk")) {
            if (stack.getDescriptionId().contains("golden_milk_bucket")) {
                int milk_level = stack.getOrCreateTag().getInt("FluidLevel");
                for (int i = milk_level; i > -1; i--) {
                    level++;
                    milk_level--;
                    if (level > 2) break;
                }
                if (!pPlayer.isCreative()) {
                    if (milk_level <= -1) {
                        stack.shrink(1);
                        addItemOrDrop(CCItems.GOLDEN_BUCKET.get(), pPlayer);
                    } else {
                        stack.getOrCreateTag().putInt("FluidLevel", milk_level);
                    }
                }

            } else if (stack.getDescriptionId().contains("milk_cup")) {
                if (!pPlayer.isCreative()) {
                    pPlayer.getMainHandItem().shrink(1);
                    addItemOrDrop(MDItems.COPPER_CUP, pPlayer);
                }
                level++;
            } else if (item.equals(Items.MILK_BUCKET)) {
                if (!pPlayer.isCreative()) {
                    pPlayer.getMainHandItem().shrink(1);
                    addItemOrDrop(Items.BUCKET, pPlayer);
                }
                level++;
            }
            pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, level));
            pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.VILLAGER_WORK_LEATHERWORKER, SoundSource.BLOCKS, 1F, 1.0F, false);
            return InteractionResult.sidedSuccess(pLevel.isClientSide());

            //get milk out of the strainer again
        } else if (0 < level && level <= 3 && stack.is(Items.BUCKET)) {
            stack.shrink(1);
            addItemOrDrop(Items.MILK_BUCKET, pPlayer);
            pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, pLevel.getBlockState(pPos).getValue(LEVEL)-1));

            pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.8F, 1.0F, false);

        } else if (level == 0 && item == BlockInit.CHEESE.get().asItem()) {
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 5));
			if (!pPlayer.isCreative()) {
				pPlayer.getItemInHand(pHand).shrink(1);
			}
            pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.CORAL_BLOCK_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F, false);

			return InteractionResult.sidedSuccess(pLevel.isClientSide());

		} else if ((level == 0 || level >= 7) && item == Items.WATER_BUCKET) {
            if (!pPlayer.isCreative()) {
                stack.shrink(1);
                addItemOrDrop(Items.BUCKET, pPlayer);
            }
            pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 7));

            pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.VILLAGER_WORK_LEATHERWORKER, SoundSource.BLOCKS, 1F, 1.0F, false);
			return InteractionResult.sidedSuccess(pLevel.isClientSide());

		} else if (level == 4) {
			addItemOrDrop(BlockInit.CHEESE.get(), pPlayer);
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 0));
            pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.3F, 1.0F, false);

			return InteractionResult.sidedSuccess(pLevel.isClientSide());
		} else if (level == 6) {
			addItemOrDrop(BlockInit.GREY_CHEESE.get(), pPlayer);
			pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, 0));
            pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.3F, 1.0F, false);

			return InteractionResult.sidedSuccess(pLevel.isClientSide());
		}

		return InteractionResult.FAIL;
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

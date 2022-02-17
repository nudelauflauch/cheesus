package at.akunatur.cheesus.common.blocks;

import java.util.Random;
import java.util.stream.Stream;

import at.akunatur.cheesus.common.blocks.entity.CheeseStrainerBlockEntity;
import at.akunatur.cheesus.core.init.BlockEntityInit;
import at.akunatur.cheesus.core.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CheeseStrainer extends HorizontalDirectionalBlock implements EntityBlock {

	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 8);
	private static final VoxelShape SHAPE = Stream
			.of(Block.box(13, 0, 13, 15, 2, 15), Block.box(2, 1, 2, 14, 4, 14), Block.box(1, 0, 13, 3, 2, 15),
					Block.box(1, 0, 1, 3, 2, 3), Block.box(13, 0, 1, 15, 2, 3), Block.box(1, 3, 1, 15, 10, 2),
					Block.box(1, 3, 14, 15, 10, 15), Block.box(14, 3, 2, 15, 10, 14), Block.box(1, 3, 2, 2, 10, 14))
			.reduce((v1, v2) -> {
				return Shapes.join(v1, v2, BooleanOp.OR);
			}).get();

	public CheeseStrainer(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_,
			CollisionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public static void setLevel(Level level, BlockPos pos, BlockState state, int i) {
		level.setBlockAndUpdate(pos, state.setValue(LEVEL, Integer.valueOf(Mth.clamp(i, 0, 10))));
	}

	private void setIteminHand(InteractionHand handIn, Player player, Item item) {
		if (player.getItemInHand(handIn).isEmpty()) {
			player.setItemInHand(handIn, new ItemStack(item));
		} else if (!player.getInventory().add(new ItemStack(item))) {
			player.drop(new ItemStack(item), false);
		}
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {
		ItemStack itemstack = pPlayer.getItemInHand(pHand);
		Item item = itemstack.getItem();
		int level = pState.getValue(LEVEL);
		if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof final CheeseStrainerBlockEntity cheese) {
			if (!pPlayer.isCrouching()) {
				if (level == 0 && item == Items.MILK_BUCKET) {
					pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BUCKET_EMPTY,
							SoundSource.BLOCKS, 1.0f, 1.0f, false);
					if (!pPlayer.getAbilities().instabuild) {
						itemstack.shrink(1);
						setIteminHand(pHand, pPlayer, Items.BUCKET);
					}
					pLevel.setBlockAndUpdate(pPos, pState.setValue(LEVEL, Integer.valueOf(Mth.clamp(1, 0, 3))));
				} else if (level == 1) {
					if (item == Items.ORANGE_DYE || item == Items.STONE || item == Items.DIORITE
							|| item == Items.ANDESITE || item == Items.GRANITE || item == Items.COBBLESTONE
							|| item == Items.DEEPSLATE || item == Items.TUFF || item == Items.FERN
							|| item == Items.GRASS || item == Items.BROWN_MUSHROOM || item == Items.RED_MUSHROOM
							|| item == Items.GRAY_DYE) {
						if (cheese.setItem(itemstack)) {
							if (!pPlayer.getAbilities().instabuild) {
								itemstack.shrink(1);
							}
							setLevel(pLevel, pPos, pState, 2);
						}
					}
				}
				if (level < 8 && level > 1) {
					pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BEEHIVE_SHEAR,
							SoundSource.BLOCKS, 1.0f, 1.0f, false);
					if (level == 3) {
						popResource(pLevel, pPos, new ItemStack(ItemInit.CHEDDAR.get(), 1));
						setLevel(pLevel, pPos, pState, 0);
					} else if (level == 4) {
						popResource(pLevel, pPos, new ItemStack(ItemInit.ALPINE_CHEESE.get(), 1));
						setLevel(pLevel, pPos, pState, 0);
					} else if (level == 5) {
						popResource(pLevel, pPos, new ItemStack(ItemInit.HERB_CHEESE.get(), 1));
						setLevel(pLevel, pPos, pState, 0);
					} else if (level == 6) {
						popResource(pLevel, pPos, new ItemStack(ItemInit.CAMAEMBERT.get(), 1));
						setLevel(pLevel, pPos, pState, 0);
					} else if (level == 7) {
						popResource(pLevel, pPos, new ItemStack(ItemInit.DIABOLICAL_CHEESE.get(), 1));
						setLevel(pLevel, pPos, pState, 0);
					} else if (level == 8) {
						popResource(pLevel, pPos, new ItemStack(ItemInit.GRAUKAS.get(), 1));
						setLevel(pLevel, pPos, pState, 0);
					}
				}
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
			BlockEntityType<T> pBlockEntityType) {
		return pLevel.isClientSide ? null
				: ($0, $1, $2, blockEntity) -> ((CheeseStrainerBlockEntity) blockEntity).tick();
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
		return BlockEntityInit.CHEESE_STRAINER.get().create(pPos, pState);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LEVEL, FACING);
	}

	@Override
	public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
		if (pState.getValue(LEVEL) == 2) {
			pLevel.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pPos.getX() + 0.5F, pPos.getY() + 0.8F,
					pPos.getZ() + 0.5F, this.RANDOM.nextDouble(-0.01, 0.01), this.RANDOM.nextDouble(0.03),
					this.RANDOM.nextDouble(-0.01, 0.01));
		}
	}

}

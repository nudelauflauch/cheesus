package at.akunatur.cheesus.common.blocks;

import java.util.stream.Stream;

import at.akunatur.cheesus.common.te.CheeseStrainerBlockEntity;
import at.akunatur.cheesus.core.init.BlockEntityTypesInit;
import at.akunatur.cheesus.core.init.ItemInit;
import net.minecraft.core.BlockPos;
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

public class CheeseStrainer extends Block implements EntityBlock {

	public CheeseStrainerBlockEntity blockentity;
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 7);
	private static final VoxelShape SHAPE = Stream
			.of(Block.box(13, 0, 13, 15, 2, 15), Block.box(2, 1, 2, 14, 4, 14), Block.box(1, 0, 13, 3, 2, 15),
					Block.box(1, 0, 1, 3, 2, 3), Block.box(13, 0, 1, 15, 2, 3), Block.box(1, 3, 1, 15, 10, 2),
					Block.box(1, 3, 14, 15, 10, 15), Block.box(14, 3, 2, 15, 10, 14), Block.box(1, 3, 2, 2, 10, 14))
			.reduce((v1, v2) -> {
				return Shapes.join(v1, v2, BooleanOp.OR);
			}).get();

	public CheeseStrainer(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_,
			CollisionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		blockentity = new CheeseStrainerBlockEntity(context.getClickedPos(), this.defaultBlockState());
		return super.getStateForPlacement(context);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	public static void setLevel(Level level, BlockPos pos, BlockState state, int i) {
		level.setBlockAndUpdate(pos, state.setValue(LEVEL, Integer.valueOf(Mth.clamp(i, 0, 10))));

	}

	private void setIteminHand(ItemStack itemstack, InteractionHand handIn, Player player, Item item) {
		if (itemstack.isEmpty()) {
			player.setItemInHand(handIn, new ItemStack(item));
		} else if (!player.getInventory().add(new ItemStack(item))) {
			player.drop(new ItemStack(item), false);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		blockentity = (CheeseStrainerBlockEntity) level.getBlockEntity(pos);
		int i = state.getValue(LEVEL);
		ItemStack itemstack = player.getItemInHand(handIn);
		Item item = itemstack.getItem();
		
		if (item == Items.MILK_BUCKET && i == 0) {
			level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f,
					1.0f, false);
			level.setBlockAndUpdate(pos, state.setValue(LEVEL, Integer.valueOf(Mth.clamp(1, 0, 3))));
			blockentity.setItem(item);
			itemstack.shrink(1);
			setIteminHand(itemstack, handIn, player, Items.MILK_BUCKET);
			return InteractionResult.CONSUME;

		} else if (i == 1 && blockentity.getItem() == Items.MILK_BUCKET) {
			if (item == Items.ORANGE_DYE || item == Items.STONE || item == Items.DIORITE || item == Items.ANDESITE
					|| item == Items.GRANITE || item == Items.COBBLESTONE || item == Items.FERN || item == Items.GRASS
					|| item == Items.BROWN_MUSHROOM || item == Items.RED_MUSHROOM || item == Items.GRAY_DYE) {
				if (blockentity.getItem() != Items.AIR) {
					level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GENERIC_SPLASH,
							SoundSource.BLOCKS, 1.0f, 1.0f, false);
					blockentity.setItem(item);
					itemstack.shrink(1);
				}
				return InteractionResult.CONSUME;
			}
		}

		if (i < 8 && i > 1) {
			level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS,
					1.0f, 1.0f, false);
			if (i == 2) {
				popResource(level, pos, new ItemStack(ItemInit.CHEDDAR.get(), 1));
				setLevel(level, pos, state, 0);
			} else if (i == 3) {
				popResource(level, pos, new ItemStack(ItemInit.ALPINE_CHEESE.get(), 1));
				setLevel(level, pos, state, 0);
			} else if (i == 4) {
				popResource(level, pos, new ItemStack(ItemInit.HERB_CHEESE.get(), 1));
				setLevel(level, pos, state, 0);
			} else if (i == 5) {
				popResource(level, pos, new ItemStack(ItemInit.CAMAEMBERT.get(), 1));
				setLevel(level, pos, state, 0);
			} else if (i == 6) {
				popResource(level, pos, new ItemStack(ItemInit.DIABOLICAL_CHEESE.get(), 1));
				setLevel(level, pos, state, 0);
			} else if (i == 7) {
				popResource(level, pos, new ItemStack(ItemInit.GRAUKAS.get(), 1));
				setLevel(level, pos, state, 0);
			}

			return InteractionResult.CONSUME;
		} else {
			return InteractionResult.FAIL;

		}
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return type == BlockEntityTypesInit.CHEESE_STRAINER_TILE_ENTITY.get() ? CheeseStrainerBlockEntity::tick : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new CheeseStrainerBlockEntity(p_153215_, p_153216_);
	}

}

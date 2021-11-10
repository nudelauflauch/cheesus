package at.akunatur.cheesus.common.blocks;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import at.akunatur.cheesus.common.te.CheeseStrainerTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CheeseStrainer extends Block implements EntityBlock {

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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LEVEL);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		CheeseStrainerTileEntity tile = newBlockEntity(pos, state);
		int i = state.getValue(LEVEL);
		ItemStack itemstack = player.getItemInHand(handIn);
		Item item = itemstack.getItem();
		System.out.println(tile.timer);
		System.out.println("bevore" + tile.getItemStack());
		if (itemstack.isEmpty()) {
			return InteractionResult.FAIL;
		} else {
			if (tile.getItemStack() == null) {
				tile.setItem(i, itemstack);
				itemstack.shrink(1);
				System.out.println("after" + tile.getItemStack());
				return InteractionResult.CONSUME;
			} else {
				return InteractionResult.FAIL;
			}
		}
	}

	@Nullable
	@Override
	public CheeseStrainerTileEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CheeseStrainerTileEntity(pos, state);
	}

}

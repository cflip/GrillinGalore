package net.cflip.grillingalore.block;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class OnionsBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
	};

	public OnionsBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return ModItems.ONION;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[state.get(AGE)];
	}
}

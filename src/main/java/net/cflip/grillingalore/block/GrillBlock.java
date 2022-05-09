package net.cflip.grillingalore.block;

import net.cflip.grillingalore.block.entity.GrillBlockEntity;
import net.cflip.grillingalore.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrillBlock extends AbstractGrillBlock {
	public GrillBlock(Settings settings) {
		super(settings);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return GrillBlock.checkType(type, ModBlockEntities.GRILL, GrillBlockEntity::tick);
	}

	@Override
	protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof GrillBlockEntity)
			player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GrillBlockEntity(pos, state);
	}
}

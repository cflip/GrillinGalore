package net.cflip.grillingalore.block;

import net.cflip.grillingalore.registry.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PeppersBlock extends CropBlock {
	private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 10.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 10.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0),
			Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0)
	};

	public PeppersBlock(FabricBlockSettings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		boolean isAtMaxAge = state.get(AGE) == MAX_AGE;
		if (!isAtMaxAge && player.getStackInHand(hand).isOf(Items.BONE_MEAL))
			return ActionResult.PASS;

		if (isAtMaxAge) {
			int dropCount = 1 + world.random.nextInt(2);
			dropStack(world, pos, new ItemStack(ModItems.PEPPER, dropCount));
			world.playSound(player, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.f, 0.8f + world.random.nextFloat() * 0.4f);
			world.setBlockState(pos, state.with(AGE, 4), Block.NOTIFY_LISTENERS);
			return ActionResult.success(world.isClient);
		}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return ModItems.PEPPER;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[state.get(AGE)];
	}
}

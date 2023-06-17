package net.cflip.grillingalore.block;

import net.cflip.grillingalore.block.entity.AbstractGrillBlockEntity;
import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModCriteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("deprecated")
public abstract class AbstractGrillBlock extends BlockWithEntity {
	public static final BooleanProperty LIT = Properties.LIT;

	public AbstractGrillBlock(Settings settings) {
		super(settings);
		setDefaultState(stateManager.getDefaultState().with(LIT, false));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (state.get(LIT) && !entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.damage(world.getDamageSources().hotFloor(), 1.0f);
		}
		super.onSteppedOn(world, pos, state, entity);
	}

	protected abstract void openScreen(World world, BlockPos pos, PlayerEntity player);

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.isOf(newState.getBlock()))
			return;

		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof AbstractGrillBlockEntity) {
			if (!world.isClient)
				ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
			world.updateComparators(pos, this);
		}

		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player instanceof ServerPlayerEntity)
			ModCriteria.USE_GRILL.trigger((ServerPlayerEntity) player, (ServerWorld) world, pos);

		if (!world.isClient) {
			if (hit.getSide() == Direction.UP && player.getStackInHand(hand).isOf(ModBlocks.RAW_RIBS.asItem()))
				return ActionResult.PASS;

			openScreen(world, pos, player);
			return ActionResult.CONSUME;
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(LIT))
			return;

		if (world.getBlockState(pos.up()).isOf(ModBlocks.RAW_RIBS)) {
			world.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + random.nextFloat(), pos.getY() + 1.1f, pos.getZ() + random.nextFloat(), 0f, 0f, 0f);
			world.addParticle(ParticleTypes.LAVA, pos.getX() + random.nextFloat(), pos.getY() + 1.1f, pos.getZ() + random.nextFloat(), 0f, 0f, 0f);
		}

		world.addParticle(ParticleTypes.SMOKE, pos.getX() + random.nextFloat(), pos.getY() + 1.1f, pos.getZ() + random.nextFloat(), 0f, 0f, 0f);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

}

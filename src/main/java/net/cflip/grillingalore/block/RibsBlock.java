package net.cflip.grillingalore.block;

import net.cflip.grillingalore.registry.ModCriteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.List;

@SuppressWarnings("deprecated")
public class RibsBlock extends Block {
	public static final int MAX_BITES = 7;
	public static final IntProperty BITES = IntProperty.of("bites", 0, MAX_BITES);
	private static final VoxelShape[] BITES_TO_SHAPE = {
			Block.createCuboidShape(4, 0, 0, 12, 4, 16),
			Block.createCuboidShape(4, 0, 2, 12, 4, 16),
			Block.createCuboidShape(4, 0, 4, 12, 4, 16),
			Block.createCuboidShape(4, 0, 6, 12, 4, 16),
			Block.createCuboidShape(4, 0, 8, 12, 4, 16),
			Block.createCuboidShape(4, 0, 10, 12, 4, 16),
			Block.createCuboidShape(4, 0, 12, 12, 4, 16),
			Block.createCuboidShape(4, 0, 14, 12, 4, 16)
	};

	private final FoodComponent foodComponent;

	public RibsBlock(Settings settings, FoodComponent foodComponent) {
		super(settings);
		this.foodComponent = foodComponent;
		setDefaultState(getStateManager().getDefaultState().with(BITES, 0));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return BITES_TO_SHAPE[state.get(BITES)];
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!player.canConsume(false))
			return ActionResult.PASS;

		player.getHungerManager().add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
		foodComponent.getStatusEffects().forEach(statusEffectAndChance -> {
			if (world.isClient || statusEffectAndChance.getFirst() == null || !(world.random.nextFloat() < statusEffectAndChance.getSecond()))
				return;
			player.addStatusEffect(new StatusEffectInstance(statusEffectAndChance.getFirst()));
		});

		if (player instanceof ServerPlayerEntity)
			ModCriteria.CONSUME_RIB_BLOCK.trigger((ServerPlayerEntity) player, (ServerWorld) world, pos);

		if (world.getRandom().nextFloat() < 0.05f)
			world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.BONE)));

		int i = state.get(BITES);
		world.emitGameEvent(player, GameEvent.EAT, pos);
		if (i < MAX_BITES) {
			world.setBlockState(pos, state.with(BITES, i + 1));
		} else {
			world.removeBlock(pos, false);
			world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).getMaterial().isSolid();
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(BITES);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient() && !player.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
			ItemStack itemStack = new ItemStack(this);
			NbtCompound nbt = new NbtCompound();
			nbt.putInt(BITES.getName(), state.get(BITES));
			itemStack.setSubNbt(BlockItem.BLOCK_STATE_TAG_KEY, nbt);
			ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
			itemEntity.setToDefaultPickupDelay();
			world.spawnEntity(itemEntity);
		}
		super.onBreak(world, pos, state, player);
	}

	@Override
	public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext options) {
		super.appendTooltip(stack, world, tooltip, options);
		NbtCompound nbt = stack.getSubNbt(BlockItem.BLOCK_STATE_TAG_KEY);
		if (nbt != null && nbt.contains(BITES.getName(), NbtElement.INT_TYPE)) {
			int bites = nbt.getInt(BITES.getName());
			int bitesLeft = MAX_BITES - bites + 1;
			if (bites > 0)
				tooltip.add(Text.translatable("tooltip.ribs.bites_left", bitesLeft).formatted(Formatting.GRAY));
		}
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}
}

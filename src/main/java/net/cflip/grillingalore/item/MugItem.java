package net.cflip.grillingalore.item;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class MugItem extends Item {
	public MugItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);

		if (hitResult.getType() == HitResult.Type.MISS)
			return TypedActionResult.pass(itemStack);

		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = hitResult.getBlockPos();

			if (!world.canPlayerModifyAt(user, blockPos))
				return TypedActionResult.pass(itemStack);

			if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
				world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
				return TypedActionResult.success(ItemUsage.exchangeStack(itemStack, user, new ItemStack(ModItems.WATER_MUG)));
			}
		}
		return TypedActionResult.pass(itemStack);
	}
}

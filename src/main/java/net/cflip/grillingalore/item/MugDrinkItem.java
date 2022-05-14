package net.cflip.grillingalore.item;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class MugDrinkItem extends Item {
	private static final int MAX_USE_TIME = 32;
	private final List<StatusEffectInstance> statusEffects;

	public MugDrinkItem(Item.Settings settings) {
		this(settings, new ArrayList<>());
	}

	public MugDrinkItem(Item.Settings settings, List<StatusEffectInstance> statusEffects) {
		super(settings);
		this.statusEffects = statusEffects;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity instanceof ServerPlayerEntity)
			Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);

		if (!world.isClient) {
			for (StatusEffectInstance statusEffectInstance : statusEffects) {
				if (statusEffectInstance.getEffectType().isInstant()) {
					statusEffectInstance.getEffectType().applyInstantEffect(playerEntity, playerEntity, user, statusEffectInstance.getAmplifier(), 1.0);
					continue;
				}
				user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
			}
		}

		if (playerEntity != null) {
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
			if (!playerEntity.getAbilities().creativeMode) {
				stack.decrement(1);
			}
		}

		if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
			if (stack.isEmpty()) {
				return new ItemStack(ModItems.GLASS_MUG);
			}
			if (playerEntity != null) {
				playerEntity.getInventory().insertStack(new ItemStack(ModItems.GLASS_MUG));
			}
		}

		world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
		return stack;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return MAX_USE_TIME;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		statusEffects.forEach(statusEffectInstance -> {
			TranslatableText text = new TranslatableText(statusEffectInstance.getTranslationKey());

			if (statusEffectInstance.getAmplifier() > 0)
				text = new TranslatableText("potion.withAmplifier", text, new TranslatableText("potion.potency." + statusEffectInstance.getAmplifier()));

			if (statusEffectInstance.getDuration() > 20)
				text = new TranslatableText("potion.withDuration", text, StatusEffectUtil.durationToString(statusEffectInstance, 1.f));

			text.formatted(statusEffectInstance.getEffectType().getCategory().getFormatting());
			tooltip.add(text);
		});
	}
}

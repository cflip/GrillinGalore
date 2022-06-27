package net.cflip.grillingalore.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		user.emitGameEvent(GameEvent.DRINK);
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
		PotionUtil.buildTooltip(stack, tooltip, 1.0F);
/*
		super.appendTooltip(stack, world, tooltip, context);
 		ArrayList<Pair<EntityAttribute, EntityAttributeModifier>> modifiers = Lists.newArrayList();

		statusEffects.forEach(statusEffectInstance -> {
			MutableText text = Text.translatable(statusEffectInstance.getTranslationKey());
			Map<EntityAttribute, EntityAttributeModifier> effectModifiers = statusEffectInstance.getEffectType().getAttributeModifiers();

			if (!effectModifiers.isEmpty()) {
				for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : effectModifiers.entrySet()) {
					EntityAttributeModifier entityAttributeModifier = entry.getValue();
					EntityAttributeModifier entityAttributeModifier2 = new EntityAttributeModifier(entityAttributeModifier.getName(), statusEffectInstance.getEffectType().adjustModifierAmount(statusEffectInstance.getAmplifier(), entityAttributeModifier), entityAttributeModifier.getOperation());
					modifiers.add(new Pair<>(entry.getKey(), entityAttributeModifier2));
				}
			}

			if (statusEffectInstance.getAmplifier() > 0)
				text = Text.translatable("potion.withAmplifier", text, Text.translatable("potion.potency." + statusEffectInstance.getAmplifier()));

			if (statusEffectInstance.getDuration() > 20)
				text = Text.translatable("potion.withDuration", text, StatusEffectUtil.durationToString(statusEffectInstance, 1.f));

			text.formatted(statusEffectInstance.getEffectType().getCategory().getFormatting());
			tooltip.add(text);
		});

		if (!modifiers.isEmpty()) {
			tooltip.add(ScreenTexts.EMPTY);
			tooltip.add(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
			for (Pair<EntityAttribute, EntityAttributeModifier> attributeAndModifier : modifiers) {
				EntityAttributeModifier attributeModifier = attributeAndModifier.getSecond();
				double value = attributeModifier.getValue();
				boolean isMultiplier = attributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_BASE || attributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_TOTAL;
				double multiplier = isMultiplier ? value * 100.0 : value;
				if (value < 0.0)
					multiplier *= -1.0;

				tooltip.add(new TranslatableText("attribute.modifier.plus." + attributeModifier.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(multiplier), new TranslatableText(attributeAndModifier.getFirst().getTranslationKey())).formatted(Formatting.BLUE));
			}
		}
*/
	}
}

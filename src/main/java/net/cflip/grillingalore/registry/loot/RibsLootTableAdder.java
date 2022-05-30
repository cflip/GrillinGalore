package net.cflip.grillingalore.registry.loot;

import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;

public class RibsLootTableAdder extends LootTableAdder {
	public RibsLootTableAdder() {
		addToLootPool(EntityType.COW.getLootTableId(), 0.06f);
		addToLootPool(EntityType.MOOSHROOM.getLootTableId(), 0.06f);
		addToLootPool(EntityType.PIG.getLootTableId(), 0.04f);
		addToLootPool(EntityType.HOGLIN.getLootTableId(), 0.18f);
	}

	@Override
	public void addToLootTable(FabricLootSupplierBuilder table, float dropChance) {
		FabricLootPoolBuilder rawRibsLootPool = FabricLootPoolBuilder.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.withCondition(RandomChanceLootCondition.builder(dropChance).build())
				.withCondition(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().flags(new EntityFlagsPredicate.Builder().onFire(false).build()).build()).build())
				.with(ItemEntry.builder(ModBlocks.RAW_RIBS));

		FabricLootPoolBuilder cookedRibsLootPool = FabricLootPoolBuilder.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.withCondition(RandomChanceLootCondition.builder(dropChance).build())
				.withCondition(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().flags(new EntityFlagsPredicate.Builder().onFire(true).build()).build()).build())
				.with(ItemEntry.builder(ModBlocks.RIBS));

		table.pool(rawRibsLootPool);
		table.pool(cookedRibsLootPool);
	}
}

package net.cflip.grillingalore.registry.loot;

import net.cflip.grillingalore.registry.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
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
	public void addToLootTable(LootTable.Builder table, float dropChance) {
		LootPool.Builder rawRibsLootPool = LootPool.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(RandomChanceLootCondition.builder(dropChance).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().flags(new EntityFlagsPredicate.Builder().onFire(false).build()).build()).build())
				.with(ItemEntry.builder(ModBlocks.RAW_RIBS));

		LootPool.Builder cookedRibsLootPool = LootPool.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(RandomChanceLootCondition.builder(dropChance).build())
				.conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, new EntityPredicate.Builder().flags(new EntityFlagsPredicate.Builder().onFire(true).build()).build()).build())
				.with(ItemEntry.builder(ModBlocks.RIBS));

		table.pool(rawRibsLootPool);
		table.pool(cookedRibsLootPool);
	}
}

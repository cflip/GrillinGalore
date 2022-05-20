package net.cflip.grillingalore.registry;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModLootTables {
	private static final List<Pair<Identifier, Float>> lootTablesForRibDrops = new ArrayList<>();
	private static final List<Identifier> lootTablesForOnionDrops = new ArrayList<>();
	private static final List<Identifier> lootTablesForSarsaparillaDrops = new ArrayList<>();

	public static void addLootTables() {
		lootTablesForRibDrops.add(Pair.of(EntityType.COW.getLootTableId(), 0.06f));
		lootTablesForRibDrops.add(Pair.of(EntityType.MOOSHROOM.getLootTableId(), 0.06f));
		lootTablesForRibDrops.add(Pair.of(EntityType.PIG.getLootTableId(), 0.04f));
		lootTablesForRibDrops.add(Pair.of(EntityType.HOGLIN.getLootTableId(), 0.18f));

		lootTablesForOnionDrops.add(Blocks.GRASS.getLootTableId());
		lootTablesForOnionDrops.add(Blocks.TALL_GRASS.getLootTableId());
		lootTablesForOnionDrops.add(Blocks.FERN.getLootTableId());
		lootTablesForOnionDrops.add(Blocks.LARGE_FERN.getLootTableId());

		lootTablesForSarsaparillaDrops.add(Blocks.FERN.getLootTableId());
		lootTablesForSarsaparillaDrops.add(Blocks.LARGE_FERN.getLootTableId());

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
			lootTablesForRibDrops.forEach(lootTableAndChance -> {
				if (lootTableAndChance.getFirst().equals(id))
					addRibsToLootPool(table, lootTableAndChance.getSecond());
			});
			lootTablesForOnionDrops.forEach(lootTableAndChance -> {
				if (lootTableAndChance.equals(id))
					addOnionToLootPool(table);
			});
			lootTablesForSarsaparillaDrops.forEach(lootTableAndChance -> {
				if (lootTableAndChance.equals(id))
					addSarsaparillaToLootPool(table);
			});
		});
	}

	private static void addRibsToLootPool(FabricLootSupplierBuilder table, float dropChance) {
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

	private static void addOnionToLootPool(FabricLootSupplierBuilder table) {
		table.pool(FabricLootPoolBuilder.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.withCondition(RandomChanceLootCondition.builder(0.08f).build())
				.with(ItemEntry.builder(ModItems.ONION)));
	}

	private static void addSarsaparillaToLootPool(FabricLootSupplierBuilder table) {
		table.pool(FabricLootPoolBuilder.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.withCondition(RandomChanceLootCondition.builder(0.15f).build())
				.with(ItemEntry.builder(ModItems.SARSAPARILLA)));
	}
}

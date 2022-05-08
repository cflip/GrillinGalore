package net.cflip.grillingalore.registry;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class RibsLootTables {
	private static final List<Pair<Identifier, Float>> lootTables = new ArrayList<>();

	public static void addRibsToLootTables() {
		lootTables.add(Pair.of(EntityType.COW.getLootTableId(), 0.08f));
		lootTables.add(Pair.of(EntityType.MOOSHROOM.getLootTableId(), 0.08f));
		lootTables.add(Pair.of(EntityType.PIG.getLootTableId(), 0.04f));
		lootTables.add(Pair.of(EntityType.HOGLIN.getLootTableId(), 0.12f));

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) ->
				lootTables.forEach(lootTableAndChance -> {
					if (lootTableAndChance.getFirst().equals(id))
						table.pool(createLootPool(lootTableAndChance.getSecond()));
				}));
	}

	private static FabricLootPoolBuilder createLootPool(float dropChance) {
		return FabricLootPoolBuilder.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.withCondition(RandomChanceLootCondition.builder(dropChance).build())
				.with(ItemEntry.builder(ModBlocks.RAW_RIBS));
	}
}

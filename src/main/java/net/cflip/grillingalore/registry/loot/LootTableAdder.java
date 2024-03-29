package net.cflip.grillingalore.registry.loot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class LootTableAdder {
	private final List<Pair<Identifier, Float>> lootTables = new ArrayList<>();
	private final Item defaultItem;

	public LootTableAdder(Item defaultItem) {
		this.defaultItem = defaultItem;
	}

	public LootTableAdder() {
		this(Items.AIR);
	}

	protected void addToLootPool(Identifier id, float dropChance) {
		lootTables.add(new Pair<>(id, dropChance));
	}

	public void tryAdd(Identifier id, LootTable.Builder table) {
		lootTables.forEach(lootTableAndChance -> {
			if (lootTableAndChance.getFirst().equals(id))
				addToLootTable(table, lootTableAndChance.getSecond());
		});
	}

	public void addToLootTable(LootTable.Builder table, float dropChance) {
		table.pool(LootPool.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.conditionally(RandomChanceLootCondition.builder(dropChance).build())
				.with(ItemEntry.builder(defaultItem)));
	}
}

package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.registry.loot.LootTableAdder;
import net.cflip.grillingalore.registry.loot.OnionLootTableAdder;
import net.cflip.grillingalore.registry.loot.PepperLootTableAdder;
import net.cflip.grillingalore.registry.loot.RibsLootTableAdder;
import net.cflip.grillingalore.registry.loot.SarsaparillaLootTableAdder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

import java.util.ArrayList;
import java.util.List;

public class ModLootTables {
	private static final List<LootTableAdder> lootTableAdders = new ArrayList<>();

	public static void addLootTables() {
		lootTableAdders.add(new OnionLootTableAdder());
		lootTableAdders.add(new PepperLootTableAdder());
		lootTableAdders.add(new RibsLootTableAdder());
		lootTableAdders.add(new SarsaparillaLootTableAdder());

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, table, setter) -> {
			lootTableAdders.forEach(lootTableAdder -> lootTableAdder.tryAdd(id, table));
		});
	}
}

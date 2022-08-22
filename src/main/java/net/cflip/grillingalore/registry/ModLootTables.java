package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.registry.loot.LootTableAdder;
import net.cflip.grillingalore.registry.loot.OnionLootTableAdder;
import net.cflip.grillingalore.registry.loot.PepperLootTableAdder;
import net.cflip.grillingalore.registry.loot.RibsLootTableAdder;
import net.cflip.grillingalore.registry.loot.SarsaparillaLootTableAdder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.ComposterBlock;

import java.util.ArrayList;
import java.util.List;

public class ModLootTables {
	private static final List<LootTableAdder> lootTableAdders = new ArrayList<>();

	public static void addLootTables() {
		lootTableAdders.add(new OnionLootTableAdder());
		lootTableAdders.add(new PepperLootTableAdder());
		lootTableAdders.add(new RibsLootTableAdder());
		lootTableAdders.add(new SarsaparillaLootTableAdder());

		// Let's just throw in items to be composted here too
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.PEPPER_SEEDS, 0.3f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.ONION_RINGS, 0.5f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.SARSAPARILLA, 0.5f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.ONION, 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.RED_PEPPER, 0.65f);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.GREEN_PEPPER, 0.65f);

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, table, setter) -> {
			lootTableAdders.forEach(lootTableAdder -> lootTableAdder.tryAdd(id, table));
		});
	}
}

package net.cflip.grillingalore.registry.loot;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.loot.LootTables;

public class PepperLootTableAdder extends LootTableAdder {
	public PepperLootTableAdder() {
		super(ModItems.PEPPER_SEEDS);
		addToLootPool(LootTables.SIMPLE_DUNGEON_CHEST, 1);
	}
}

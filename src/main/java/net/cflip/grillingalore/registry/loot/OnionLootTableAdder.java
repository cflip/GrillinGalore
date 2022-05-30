package net.cflip.grillingalore.registry.loot;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.block.Blocks;

public class OnionLootTableAdder extends LootTableAdder {
	private static final float DROP_CHANCE = 0.08f;

	public OnionLootTableAdder() {
		super(ModItems.ONION);
		addToLootPool(Blocks.GRASS.getLootTableId(), DROP_CHANCE);
		addToLootPool(Blocks.TALL_GRASS.getLootTableId(), DROP_CHANCE);
		addToLootPool(Blocks.FERN.getLootTableId(), DROP_CHANCE);
		addToLootPool(Blocks.LARGE_FERN.getLootTableId(), DROP_CHANCE);
	}
}

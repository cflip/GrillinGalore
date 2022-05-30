package net.cflip.grillingalore.registry.loot;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.block.Blocks;

public class SarsaparillaLootTableAdder extends LootTableAdder {
	private static final float DROP_CHANCE = 0.15f;

	public SarsaparillaLootTableAdder() {
		super(ModItems.SARSAPARILLA);
		addToLootPool(Blocks.FERN.getLootTableId(), DROP_CHANCE);
		addToLootPool(Blocks.LARGE_FERN.getLootTableId(), DROP_CHANCE);
	}
}

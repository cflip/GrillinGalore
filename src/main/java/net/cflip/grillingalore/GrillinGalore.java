package net.cflip.grillingalore;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModItems;
import net.cflip.grillingalore.registry.RibsLootTables;
import net.fabricmc.api.ModInitializer;

public class GrillinGalore implements ModInitializer {
	public static final String MODID = "grillingalore";

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
		ModBlockEntities.register();
		RibsLootTables.addRibsToLootTables();
	}
}

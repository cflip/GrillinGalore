package net.cflip.grillingalore;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModItems;
import net.cflip.grillingalore.registry.ModLootTables;
import net.cflip.grillingalore.registry.ModRecipes;
import net.cflip.grillingalore.registry.ModScreens;
import net.fabricmc.api.ModInitializer;

public class GrillinGalore implements ModInitializer {
	public static final String MODID = "grillingalore";

	@Override
	public void onInitialize() {
		ModItems.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModRecipes.register();
		ModScreens.register();
		ModLootTables.addLootTables();
	}
}

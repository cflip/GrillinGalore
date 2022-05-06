package net.cflip.grillingalore;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.registry.ModBlocks;
import net.fabricmc.api.ModInitializer;

public class GrillinGalore implements ModInitializer {
	public static final String MODID = "grillingalore";

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModBlockEntities.register();
	}
}

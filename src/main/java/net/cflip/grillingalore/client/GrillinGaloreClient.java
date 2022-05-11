package net.cflip.grillingalore.client;

import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class GrillinGaloreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModScreens.registerClientScreens();
		ModBlocks.registerTranslucency();
	}
}

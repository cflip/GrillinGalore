package net.cflip.grillingalore.client;

import net.cflip.grillingalore.client.gui.CharcoalGrillScreen;
import net.cflip.grillingalore.client.gui.GrillScreen;
import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class GrillinGaloreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ModScreens.GRILL, GrillScreen::new);
		ScreenRegistry.register(ModScreens.CHARCOAL_GRILL, CharcoalGrillScreen::new);
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHARCOAL_GRILL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAW_RIBS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RIBS, RenderLayer.getCutout());
	}
}

package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.client.gui.CharcoalGrillScreen;
import net.cflip.grillingalore.client.gui.GrillScreen;
import net.cflip.grillingalore.screen.CharcoalGrillScreenHandler;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
	public static ScreenHandlerType<GrillScreenHandler> GRILL;
	public static ScreenHandlerType<CharcoalGrillScreenHandler> CHARCOAL_GRILL;

	public static void register() {
		GRILL = ScreenHandlerRegistry.registerSimple(new Identifier(GrillinGalore.MODID, "grill"), GrillScreenHandler::new);
		CHARCOAL_GRILL = ScreenHandlerRegistry.registerSimple(new Identifier(GrillinGalore.MODID, "charcoal_grill"), CharcoalGrillScreenHandler::new);
	}

	public static void registerClientScreens() {
		ScreenRegistry.register(ModScreens.GRILL, GrillScreen::new);
		ScreenRegistry.register(ModScreens.CHARCOAL_GRILL, CharcoalGrillScreen::new);
	}
}

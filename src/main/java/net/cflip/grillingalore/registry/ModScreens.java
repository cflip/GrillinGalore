package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.CharcoalGrillScreenHandler;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
	public static final ScreenHandlerType<GrillScreenHandler> GRILL = ScreenHandlerRegistry.registerSimple(new Identifier(GrillinGalore.MODID, "grill"), GrillScreenHandler::new);
	public static final ScreenHandlerType<CharcoalGrillScreenHandler> CHARCOAL_GRILL = ScreenHandlerRegistry.registerSimple(new Identifier(GrillinGalore.MODID, "charcoal_grill"), CharcoalGrillScreenHandler::new);
}

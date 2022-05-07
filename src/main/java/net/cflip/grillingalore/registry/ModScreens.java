package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
	public static final ScreenHandlerType<GrillScreenHandler> GRILL = ScreenHandlerRegistry.registerSimple(new Identifier(GrillinGalore.MODID, "grill"), GrillScreenHandler::new);
}

package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.BarbecueScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
	public static final ScreenHandlerType<BarbecueScreenHandler> BARBECUE = ScreenHandlerRegistry.registerSimple(new Identifier(GrillinGalore.MODID, "barbecue"), BarbecueScreenHandler::new);
}

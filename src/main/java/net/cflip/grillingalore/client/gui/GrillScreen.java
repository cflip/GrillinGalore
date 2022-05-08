package net.cflip.grillingalore.client.gui;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GrillScreen extends AbstractGrillScreen<GrillScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(GrillinGalore.MODID, "textures/gui/container/grill.png");

	public GrillScreen(GrillScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
	}
}

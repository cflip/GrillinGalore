package net.cflip.grillingalore.client.gui;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.CharcoalGrillScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CharcoalGrillScreen extends AbstractGrillScreen<CharcoalGrillScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(GrillinGalore.MODID, "textures/gui/container/charcoal_grill.png");

	public CharcoalGrillScreen(CharcoalGrillScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
	}
}

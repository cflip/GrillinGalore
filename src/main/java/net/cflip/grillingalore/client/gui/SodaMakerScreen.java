package net.cflip.grillingalore.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.SodaMakerScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SodaMakerScreen extends HandledScreen<SodaMakerScreenHandler> {
	private final Identifier TEXTURE = new Identifier(GrillinGalore.MODID, "textures/gui/container/soda_maker.png");

	public SodaMakerScreen(SodaMakerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = (width - backgroundWidth) / 2;
		int j = (height - backgroundHeight) / 2;
		drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight);

		if (handler.getBrewProgress() > 0) {
			int progress = (int) (54.f * handler.getBrewProgress());
			drawTexture(matrices, i + 150, j + 16 + (54 - progress), 176, 54 - progress, 4, progress);
		}
	}
}

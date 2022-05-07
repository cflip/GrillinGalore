package net.cflip.grillingalore.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GrillScreen extends HandledScreen<GrillScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(GrillinGalore.MODID, "textures/gui/container/grill.png");

	public GrillScreen(GrillScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	private void drawSlotProgress(MatrixStack matrices, Slot slot, int index) {
		int progress = handler.getCookProgress(index);
		if (progress == 0)
			return;

		RenderSystem.disableDepthTest();
		RenderSystem.colorMask(true, true, true, false);
		fillGradient(matrices, x + slot.x, y + slot.y + (16 - progress), x + slot.x + 16, y + slot.y + 16, 0x80ffffff, 0x80ffffff, getZOffset());
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.enableDepthTest();
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = (width - backgroundWidth) / 2;
		int j = (height - backgroundHeight) / 2;
		drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight);
		for (int s = 0; s < 8; s++) {
			drawSlotProgress(matrices, handler.getSlot(s), s);
		}
		int fuelProgress = handler.getFuelProgress();
		drawTexture(matrices, i + 14, j + 45 + 13 - fuelProgress, 176, 13 - fuelProgress, 14, fuelProgress);
	}
}

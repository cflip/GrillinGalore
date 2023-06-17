package net.cflip.grillingalore.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cflip.grillingalore.screen.AbstractGrillScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class AbstractGrillScreen<T extends AbstractGrillScreenHandler> extends HandledScreen<T> {
	private final Identifier backgroundTexture;

	public AbstractGrillScreen(T handler, PlayerInventory inventory, Text title, Identifier backgroundTexture) {
		super(handler, inventory, title);
		this.backgroundTexture = backgroundTexture;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		renderBackground(drawContext);
		super.render(drawContext, mouseX, mouseY, delta);
		drawMouseoverTooltip(drawContext, mouseX, mouseY);
	}

	private void drawSlotProgress(DrawContext drawContext, Slot slot, int index) {
		int progress = handler.getCookProgress(index);
		if (progress == 0)
			return;

		RenderSystem.disableDepthTest();
		RenderSystem.colorMask(true, true, true, false);
		drawContext.fillGradient(x + slot.x, y + slot.y + (16 - progress), x + slot.x + 16, y + slot.y + 16, 0x80ffffff, 0x80ffffff);
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.enableDepthTest();
	}

	@Override
	protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
		int i = (width - backgroundWidth) / 2;
		int j = (height - backgroundHeight) / 2;
		drawContext.drawTexture(backgroundTexture, i, j, 0, 0, backgroundWidth, backgroundHeight);
		for (int s = 0; s < handler.getNumberOfGrillSlots(); s++) {
			drawSlotProgress(drawContext, handler.getSlot(s), s);
		}
		int fuelProgress = handler.getFuelProgress();
		drawContext.drawTexture(backgroundTexture, i + 14, j + 45 + 13 - fuelProgress, 176, 13 - fuelProgress, 14, fuelProgress);
	}
}

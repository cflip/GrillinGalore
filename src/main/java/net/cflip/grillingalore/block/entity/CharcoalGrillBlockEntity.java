package net.cflip.grillingalore.block.entity;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.screen.CharcoalGrillScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class CharcoalGrillBlockEntity extends AbstractGrillBlockEntity {
	public CharcoalGrillBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CHARCOAL_GRILL, pos, state, 4);
	}

	@Override
	protected Text getContainerName() {
		return Text.translatable("container.charcoal_grill");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new CharcoalGrillScreenHandler(syncId, playerInventory, this, propertyDelegate);
	}
}

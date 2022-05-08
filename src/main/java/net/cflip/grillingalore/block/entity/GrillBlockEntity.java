package net.cflip.grillingalore.block.entity;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class GrillBlockEntity extends AbstractGrillBlockEntity {
	public GrillBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.GRILL, pos, state, 8);
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.grill");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new GrillScreenHandler(syncId, playerInventory, this, propertyDelegate);
	}
}

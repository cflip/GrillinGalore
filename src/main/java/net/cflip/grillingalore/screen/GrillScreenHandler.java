package net.cflip.grillingalore.screen;

import net.cflip.grillingalore.registry.ModScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PropertyDelegate;

public class GrillScreenHandler extends AbstractGrillScreenHandler {
	public GrillScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ModScreens.GRILL, 8, syncId, playerInventory);
	}

	public GrillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModScreens.GRILL, 8, syncId, playerInventory, inventory, propertyDelegate);
	}
}

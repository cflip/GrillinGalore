package net.cflip.grillingalore.screen;

import net.cflip.grillingalore.registry.ModScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PropertyDelegate;

public class CharcoalGrillScreenHandler extends AbstractGrillScreenHandler {
	public CharcoalGrillScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ModScreens.CHARCOAL_GRILL, 8, syncId, playerInventory);
	}

	public CharcoalGrillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModScreens.CHARCOAL_GRILL, 8, syncId, playerInventory, inventory, propertyDelegate);
	}
}

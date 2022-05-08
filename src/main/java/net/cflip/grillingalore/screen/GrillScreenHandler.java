package net.cflip.grillingalore.screen;

import net.cflip.grillingalore.registry.ModScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class GrillScreenHandler extends AbstractGrillScreenHandler {
	public GrillScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ModScreens.GRILL, 8, syncId, playerInventory);
	}

	public GrillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModScreens.GRILL, 8, syncId, playerInventory, inventory, propertyDelegate);
	}

	@Override
	protected void addSlots(Inventory inventory) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				addSlot(new GrillSlot(inventory, j + i * 4, 53 + j * 18, 26 + i * 18));
			}
		}
		addSlot(new Slot(inventory, 8, 14, 26));
	}
}

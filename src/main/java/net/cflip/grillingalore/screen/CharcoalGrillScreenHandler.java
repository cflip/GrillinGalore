package net.cflip.grillingalore.screen;

import net.cflip.grillingalore.registry.ModScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class CharcoalGrillScreenHandler extends AbstractGrillScreenHandler {
	public CharcoalGrillScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ModScreens.CHARCOAL_GRILL, 4, syncId, playerInventory);
	}

	public CharcoalGrillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModScreens.CHARCOAL_GRILL, 4, syncId, playerInventory, inventory, propertyDelegate);
	}

	@Override
	protected void addSlots(Inventory inventory) {
		addSlot(new GrillSlot(inventory, 0, 71, 26));
		addSlot(new GrillSlot(inventory, 1, 89, 26));
		addSlot(new GrillSlot(inventory, 2, 71, 44));
		addSlot(new GrillSlot(inventory, 3, 89, 44));
		addSlot(new Slot(inventory, 4, 14, 26));
	}
}

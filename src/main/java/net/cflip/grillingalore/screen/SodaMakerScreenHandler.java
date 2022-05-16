package net.cflip.grillingalore.screen;

import net.cflip.grillingalore.registry.ModScreens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SodaMakerScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;

	public SodaMakerScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(7), new ArrayPropertyDelegate(1));
	}

	public SodaMakerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModScreens.SODA_MAKER, syncId);
		this.inventory = inventory;
		this.propertyDelegate = propertyDelegate;

		addSlot(new Slot(inventory, 0, 16, 28));
		addSlot(new Slot(inventory, 1, 62, 17));
		addSlot(new Slot(inventory, 2, 80, 17));
		addSlot(new Slot(inventory, 3, 98, 17));
		addSlot(new Slot(inventory, 4, 50, 49));
		addSlot(new Slot(inventory, 5, 80, 49));
		addSlot(new Slot(inventory, 6, 110, 49));
		addProperties(propertyDelegate);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack resultStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			resultStack = originalStack.copy();
			if (index >= 0 && index <= 7) {
				if (!insertItem(originalStack, 7, 43, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickTransfer(originalStack, resultStack);
			} else {
				if (!insertItem(originalStack, 1, 7, false)) {
					return ItemStack.EMPTY;
				}
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}

			if (originalStack.getCount() == resultStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, originalStack);
		}
		return resultStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return inventory.canPlayerUse(player);
	}

	public float getBrewProgress() {
		int i = propertyDelegate.get(0);
		if (i == 0)
			return 0;
		return (400 - i) / 400.f;
	}
}

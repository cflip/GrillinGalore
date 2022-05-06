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

public class BarbecueScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;

	public BarbecueScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(8), new ArrayPropertyDelegate(8));
	}

	public BarbecueScreenHandler(int syncId, PlayerInventory playerInventory, Inventory barbecueInventory, PropertyDelegate propertyDelegate) {
		super(ModScreens.BARBECUE, syncId);
		int i, j;
		inventory = barbecueInventory;
		this.propertyDelegate = propertyDelegate;
		addProperties(propertyDelegate);
		barbecueInventory.onOpen(playerInventory.player);

		// Barbecue inventory slots
		for (i = 0; i < 2; i++) {
			for (j = 0; j < 4; j++) {
				addSlot(new Slot(barbecueInventory, j + i * 4, 53 + j * 18, 26 + i * 18));
			}
		}

		// Player inventory slots
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	public int getCookProgress(int index) {
		return propertyDelegate.get(index);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (index < inventory.size()) {
				if (!insertItem(originalStack, inventory.size(), slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!insertItem(originalStack, 0, inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return inventory.canPlayerUse(player);
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		inventory.onClose(player);
	}
}

package net.cflip.grillingalore.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public abstract class AbstractGrillScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;
	protected final int numberOfGrillSlots;

	private static class GrillSlot extends Slot {
		public GrillSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public int getMaxItemCount() {
			return 1;
		}
	}

	public AbstractGrillScreenHandler(ScreenHandlerType<?> handlerType, int numberOfGrillSlots, int syncId, PlayerInventory playerInventory) {
		this(handlerType, numberOfGrillSlots, syncId, playerInventory, new SimpleInventory(numberOfGrillSlots + 1), new ArrayPropertyDelegate(numberOfGrillSlots + 1));
	}

	public AbstractGrillScreenHandler(ScreenHandlerType<?> handlerType, int numberOfGrillSlots, int syncId, PlayerInventory playerInventory, Inventory grillInventory, PropertyDelegate propertyDelegate) {
		super(handlerType, syncId);
		int i, j;
		inventory = grillInventory;
		this.propertyDelegate = propertyDelegate;
		this.numberOfGrillSlots = numberOfGrillSlots;
		addProperties(propertyDelegate);
		grillInventory.onOpen(playerInventory.player);

		// Grill inventory slots
		for (i = 0; i < 2; i++) {
			for (j = 0; j < 4; j++) {
				addSlot(new GrillSlot(grillInventory, j + i * 4, 53 + j * 18, 26 + i * 18));
			}
		}
		addSlot(new Slot(grillInventory, 8, 14, 26));

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

	public int getFuelProgress() {
		return propertyDelegate.get(8);
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
			} else {
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

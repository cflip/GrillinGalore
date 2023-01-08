package net.cflip.grillingalore.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
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

	protected static class GrillSlot extends Slot {
		public GrillSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public int getMaxItemCount() {
			return 1;
		}
	}

	protected static class GrillFuelSlot extends Slot {
		public GrillFuelSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean canInsert(ItemStack stack) {
			return AbstractFurnaceBlockEntity.canUseAsFuel(stack);
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

		addSlots(grillInventory);

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

	protected abstract void addSlots(Inventory inventory);

	public int getNumberOfGrillSlots() {
		return numberOfGrillSlots;
	}

	public int getCookProgress(int index) {
		return propertyDelegate.get(index);
	}

	public int getFuelProgress() {
		return propertyDelegate.get(numberOfGrillSlots);
	}

	@Override
	protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
		// Since the implementation of this function in ScreenHandler doesn't check if the stack
		// exceeds the maximum stack limit of the slot it is being transferred into, we need to
		// override it in order to add this missing instruction and prevent the user from being
		// able to shift-click more than one item into any of the grill slots.

		ItemStack itemStack;
		Slot slot;
		boolean bl = false;
		int i = startIndex;
		if (fromLast) {
			i = endIndex - 1;
		}
		if (stack.isStackable()) {
			while (!stack.isEmpty() && (fromLast ? i >= startIndex : i < endIndex)) {
				slot = this.slots.get(i);
				itemStack = slot.getStack();
				if (!itemStack.isEmpty() && stack.getCount() < slot.getMaxItemCount(stack) && ItemStack.canCombine(stack, itemStack)) {
					int j = itemStack.getCount() + stack.getCount();
					if (j <= stack.getMaxCount()) {
						stack.setCount(0);
						itemStack.setCount(j);
						slot.markDirty();
						bl = true;
					} else if (itemStack.getCount() < stack.getMaxCount()) {
						stack.decrement(stack.getMaxCount() - itemStack.getCount());
						itemStack.setCount(stack.getMaxCount());
						slot.markDirty();
						bl = true;
					}
				}
				if (fromLast) {
					--i;
					continue;
				}
				++i;
			}
		}
		if (!stack.isEmpty()) {
			i = fromLast ? endIndex - 1 : startIndex;
			while (fromLast ? i >= startIndex : i < endIndex) {
				slot = this.slots.get(i);
				itemStack = slot.getStack();
				if (itemStack.isEmpty() && slot.canInsert(stack)) {
					if (stack.getCount() > slot.getMaxItemCount()) {
						slot.setStack(stack.split(slot.getMaxItemCount()));
					} else {
						slot.setStack(stack.split(stack.getCount()));
					}
					slot.markDirty();
					bl = true;
					break;
				}
				if (fromLast) {
					--i;
					continue;
				}
				++i;
			}
		}
		return bl;
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (index < inventory.size()) {
				// Transfer from the grill into the inventory
				if (!insertItem(originalStack, inventory.size(), slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!slots.get(numberOfGrillSlots).hasStack() && slots.get(numberOfGrillSlots).canInsert(originalStack)) {
				// Transfer from the inventory into the fuel slot
				if (!insertItem(originalStack, numberOfGrillSlots, numberOfGrillSlots + 1, false)) {
					return ItemStack.EMPTY;
				}
			} else {
				// Transfer from the inventory to a grill slot
				if (!insertItem(originalStack, 0, numberOfGrillSlots, false)) {
					return ItemStack.EMPTY;
				}
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

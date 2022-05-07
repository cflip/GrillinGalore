package net.cflip.grillingalore.block.entity;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.screen.GrillScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class GrillBlockEntity extends LockableContainerBlockEntity {
	private static final int INVENTORY_SIZE = 8;
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
	private final int[] cookingTimes = new int[INVENTORY_SIZE];
	private final int[] totalCookingTimes = new int[INVENTORY_SIZE];

	private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			if (inventory.get(index).isEmpty() || totalCookingTimes[index] <= 0)
				return 0;
			return (int) (((float) cookingTimes[index] / (float) totalCookingTimes[index]) * 16.f);
		}

		@Override
		public void set(int index, int value) {
			cookingTimes[index] = value;
		}

		@Override
		public int size() {
			return INVENTORY_SIZE;
		}
	};

	public GrillBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.GRILL, pos, state);
	}

	public static Optional<SmokingRecipe> getRecipeFor(World world, ItemStack item) {
		return world.getRecipeManager().getFirstMatch(RecipeType.SMOKING, new SimpleInventory(item), world);
	}

	public static void tick(World world, BlockPos pos, BlockState state, GrillBlockEntity grill) {
		boolean shouldMarkDirty = false;
		for (int i = 0; i < grill.inventory.size(); i++) {
			ItemStack itemStack = grill.inventory.get(i);
			if (itemStack.isEmpty() || getRecipeFor(world, itemStack).isEmpty())
				continue;

			shouldMarkDirty = true;
			grill.cookingTimes[i]++;
			if (grill.cookingTimes[i] < grill.totalCookingTimes[i])
				continue;

			SimpleInventory tempInventory = new SimpleInventory(itemStack);
			ItemStack cookedItem = getRecipeFor(world, itemStack).map(recipe -> recipe.craft(tempInventory)).orElse(itemStack);
			grill.inventory.set(i, cookedItem);
			grill.totalCookingTimes[i] = -1;
		}

		if (shouldMarkDirty)
			grill.markDirty();
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.grill");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new GrillScreenHandler(syncId, playerInventory, this, propertyDelegate);
	}

	@Override
	public ItemStack getStack(int slot) {
		return inventory.get(slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		if (stack.getCount() > getMaxCountPerStack())
			stack.setCount(getMaxCountPerStack());

		Optional<SmokingRecipe> optionalRecipe = world.getRecipeManager().getFirstMatch(RecipeType.SMOKING, new SimpleInventory(stack), world);
		optionalRecipe.ifPresent(recipe -> {
			cookingTimes[slot] = 0;
			totalCookingTimes[slot] = recipe.getCookTime();
			markDirty();
		});
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return Inventories.splitStack(inventory, slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public int size() {
		return inventory.size();
	}

	@Override
	public int getMaxCountPerStack() {
		return 1;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : inventory) {
			if (itemStack.isEmpty()) continue;
			return false;
		}
		return true;
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (world.getBlockEntity(pos) != this)
			return false;
		return player.squaredDistanceTo((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5) <= 64.0;
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		int[] tempArray;
		Inventories.readNbt(nbt, inventory);
		if (nbt.contains("CookingTimes", NbtElement.INT_ARRAY_TYPE)) {
			tempArray = nbt.getIntArray("CookingTimes");
			System.arraycopy(tempArray, 0, cookingTimes, 0, Math.min(totalCookingTimes.length, tempArray.length));
		}
		if (nbt.contains("TotalCookingTimes", NbtElement.INT_ARRAY_TYPE)) {
			tempArray = nbt.getIntArray("TotalCookingTimes");
			System.arraycopy(tempArray, 0, totalCookingTimes, 0, Math.min(totalCookingTimes.length, tempArray.length));
		}
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, inventory);
		nbt.putIntArray("CookingTimes", cookingTimes);
		nbt.putIntArray("TotalCookingTimes", totalCookingTimes);
	}
}

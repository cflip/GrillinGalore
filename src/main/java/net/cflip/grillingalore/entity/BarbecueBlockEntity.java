package net.cflip.grillingalore.entity;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.screen.BarbecueScreenHandler;
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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class BarbecueBlockEntity extends LockableContainerBlockEntity {
	private static final int INVENTORY_SIZE = 8;
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
	private final int[] cookingTimes = new int[INVENTORY_SIZE];
	private final int[] totalCookingTimes = new int[INVENTORY_SIZE];

	public BarbecueBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BARBECUE, pos, state);
	}

	public static Optional<SmokingRecipe> getRecipeFor(World world, ItemStack item) {
		return world.getRecipeManager().getFirstMatch(RecipeType.SMOKING, new SimpleInventory(item), world);
	}

	public static void tick(World world, BlockPos pos, BlockState state, BarbecueBlockEntity barbecue) {
		boolean shouldMarkDirty = false;
		for (int i = 0; i < barbecue.inventory.size(); i++) {
			ItemStack itemStack = barbecue.inventory.get(i);
			if (itemStack.isEmpty() || getRecipeFor(world, itemStack).isEmpty())
				continue;

			shouldMarkDirty = true;
			barbecue.cookingTimes[i]++;
			if (barbecue.cookingTimes[i] < barbecue.totalCookingTimes[i])
				continue;

			SimpleInventory tempInventory = new SimpleInventory(itemStack);
			ItemStack cookedItem = getRecipeFor(world, itemStack).map(recipe -> recipe.craft(tempInventory)).orElse(itemStack);
			barbecue.inventory.set(i, cookedItem);
		}

		if (shouldMarkDirty)
			barbecue.markDirty();
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.barbecue");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new BarbecueScreenHandler(syncId, playerInventory, this);
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

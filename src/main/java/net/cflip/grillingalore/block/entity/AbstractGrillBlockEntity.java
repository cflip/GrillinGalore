package net.cflip.grillingalore.block.entity;

import net.cflip.grillingalore.block.AbstractGrillBlock;
import net.cflip.grillingalore.block.RibsBlock;
import net.cflip.grillingalore.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Optional;

public abstract class AbstractGrillBlockEntity extends LockableContainerBlockEntity {
	private static final int RIBS_COOKING_TIME = 200;

	protected final int numberOfGrillSlots;
	protected final DefaultedList<ItemStack> inventory;
	protected final Map<Item, Integer> fuelTimeMap;

	protected final int[] cookingTimes;
	protected final int[] totalCookingTimes;
	protected int remainingFuel;
	protected int maxRemainingFuel;

	protected int ribsCookProgress;

	protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			if (index < numberOfGrillSlots) {
				if (inventory.get(index).isEmpty() || totalCookingTimes[index] <= 0)
					return 0;
				return (int) (((float) cookingTimes[index] / (float) totalCookingTimes[index]) * 16.f);
			}
			int totalFuel = maxRemainingFuel;
			if (totalFuel == 0)
				totalFuel = 200;
			return remainingFuel * 13 / totalFuel;
		}

		@Override
		public void set(int index, int value) {
			cookingTimes[index] = value;
		}

		@Override
		public int size() {
			return numberOfGrillSlots + 1;
		}
	};

	public AbstractGrillBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state, int numberOfGrillSlots) {
		super(entityType, pos, state);
		this.numberOfGrillSlots = numberOfGrillSlots;
		inventory = DefaultedList.ofSize(numberOfGrillSlots + 1, ItemStack.EMPTY);
		fuelTimeMap = AbstractFurnaceBlockEntity.createFuelTimeMap();

		cookingTimes = new int[numberOfGrillSlots];
		totalCookingTimes = new int[numberOfGrillSlots];
		remainingFuel = 0;
		maxRemainingFuel = 500;
	}

	private boolean isBurning() {
		return remainingFuel > 0;
	}

	protected abstract Text getContainerName();

	public static Optional<SmokingRecipe> getRecipeFor(World world, ItemStack item) {
		return world.getRecipeManager().getFirstMatch(RecipeType.SMOKING, new SimpleInventory(item), world);
	}

	public static void tick(World world, BlockPos pos, BlockState state, AbstractGrillBlockEntity grill) {
		boolean oldCookingState = grill.isBurning();
		boolean didCook = false;

		if (grill.remainingFuel <= 0 && !grill.inventory.get(grill.numberOfGrillSlots).isEmpty()) {
			ItemStack fuelStack = grill.inventory.get(grill.numberOfGrillSlots);
			int fuelForItem = grill.fuelTimeMap.getOrDefault(fuelStack.getItem(), 0);
			if (fuelForItem > 0) {
				fuelStack.decrement(1);
				grill.remainingFuel = grill.maxRemainingFuel = fuelForItem;
			}
		}

		for (int i = 0; i < grill.numberOfGrillSlots; i++) {
			if (grill.isBurning()) {
				ItemStack itemStack = grill.inventory.get(i);
				if (itemStack.isEmpty() || getRecipeFor(world, itemStack).isEmpty())
					continue;

				didCook = true;
				grill.remainingFuel--;
				grill.cookingTimes[i]++;
				if (grill.cookingTimes[i] < grill.totalCookingTimes[i])
					continue;

				SimpleInventory tempInventory = new SimpleInventory(itemStack);
				ItemStack cookedItem = getRecipeFor(world, itemStack).map(recipe -> recipe.craft(tempInventory)).orElse(itemStack);
				grill.inventory.set(i, cookedItem);
				grill.totalCookingTimes[i] = -1;
			} else {
				grill.cookingTimes[i] = MathHelper.clamp(grill.cookingTimes[i] - 2, 0, grill.totalCookingTimes[i]);
			}
		}

		if (grill.remainingFuel > 0 && world.getBlockState(pos.up()).isOf(ModBlocks.RAW_RIBS)) {
			didCook = true;
			grill.remainingFuel--;
			grill.ribsCookProgress++;
			if (grill.ribsCookProgress >= RIBS_COOKING_TIME) {
				BlockState oldRibsState = world.getBlockState(pos.up());
				BlockState cookedRibsState = ModBlocks.RIBS.getDefaultState().with(RibsBlock.BITES, oldRibsState.get(RibsBlock.BITES));
				world.setBlockState(pos.up(), cookedRibsState);
				grill.ribsCookProgress = 0;
			}
		} else {
			grill.ribsCookProgress = 0;
		}

		if (!didCook && grill.isBurning())
			grill.remainingFuel--;

		if (oldCookingState != grill.isBurning()) {
			didCook = true;
			world.setBlockState(pos, state.with(AbstractGrillBlock.LIT, grill.isBurning()));
		}

		if (didCook)
			AbstractGrillBlockEntity.markDirty(world, pos, state);
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

		Optional<SmokingRecipe> optionalRecipe = getRecipeFor(world, stack);
		if (slot != numberOfGrillSlots && optionalRecipe.isPresent()) {
			SmokingRecipe recipe = optionalRecipe.get();
			cookingTimes[slot] = 0;
			totalCookingTimes[slot] = recipe.getCookTime();
			markDirty();
		}
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
		remainingFuel = nbt.getShort("RemainingFuel");
		maxRemainingFuel = nbt.getShort("MaxRemainingFuel");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, inventory);
		nbt.putIntArray("CookingTimes", cookingTimes);
		nbt.putIntArray("TotalCookingTimes", totalCookingTimes);
		nbt.putShort("RemainingFuel", (short)remainingFuel);
		nbt.putShort("MaxRemainingFuel", (short)maxRemainingFuel);
	}
}

package net.cflip.grillingalore.block.entity;

import net.cflip.grillingalore.recipe.SodaRecipe;
import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.registry.ModItems;
import net.cflip.grillingalore.screen.SodaMakerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class SodaMakerBlockEntity extends LockableContainerBlockEntity {
	private static final int MAX_BREW_TIME = 400;

	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7, ItemStack.EMPTY);
	private int remainingBrewTime;

	private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			return remainingBrewTime;
		}

		@Override
		public void set(int index, int value) {
			remainingBrewTime = value;
		}

		@Override
		public int size() {
			return 1;
		}
	};

	public SodaMakerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.SODA_MAKER, pos, state);
	}

	public static void tick(World world, BlockPos blockPos, BlockState blockState, SodaMakerBlockEntity sodaMaker) {
		if (sodaMaker.remainingBrewTime > 0) {
			sodaMaker.remainingBrewTime--;
			if (sodaMaker.remainingBrewTime == 0)
				sodaMaker.craft();
		}
	}

	private void craft() {
		world.getRecipeManager().getFirstMatch(SodaRecipe.TYPE, this, world).ifPresent(recipe -> {
			for (int i = 1; i < 4; i++) {
				ItemStack stack = getStack(i);
				Item ingredientItem = stack.getItem();
				getStack(i).decrement(1);
				if (stack.isEmpty()) {
					Item remainder = ingredientItem.getRecipeRemainder();
					inventory.set(i, remainder == null ? ItemStack.EMPTY : new ItemStack(remainder));
				}
			}
			for (int i = 4; i < 7; i++) {
				if (getStack(i).isOf(recipe.getContainer()))
					setStack(i, recipe.craft(this));
			}
			markDirty();
		});
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.soda_maker");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new SodaMakerScreenHandler(syncId, playerInventory, this, propertyDelegate);
	}

	public boolean hasContainerInSlots(Item container) {
		boolean result = false;
		if (getStack(4).isOf(container)) result = true;
		if (getStack(5).isOf(container)) result = true;
		if (getStack(6).isOf(container)) result = true;
		return result;
	}

	@Override
	public int size() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		if (slot >= 0 && slot < this.inventory.size()) {
			return this.inventory.get(slot);
		}
		return ItemStack.EMPTY;
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
	public void setStack(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		if (stack.getCount() > getMaxCountPerStack())
			stack.setCount(getMaxCountPerStack());

		remainingBrewTime = 0;
		Optional<SodaRecipe> optionalRecipe = world.getRecipeManager().getFirstMatch(SodaRecipe.TYPE, this, world);
		optionalRecipe.ifPresent(recipe -> {
			remainingBrewTime = MAX_BREW_TIME;
			markDirty();
		});
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (world.getBlockEntity(pos) != this)
			return false;
		return !(player.squaredDistanceTo((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5) > 64.0);
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, inventory);
		remainingBrewTime = nbt.getShort("RemainingBrewTime");
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, inventory);
		nbt.putShort("RemainingBrewTime", (short) remainingBrewTime);
	}
}

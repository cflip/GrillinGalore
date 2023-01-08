package net.cflip.grillingalore.recipe;

import net.cflip.grillingalore.block.RibsBlock;
import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RibCraftingRecipe extends SpecialCraftingRecipe {
	public static final String NAME = "crafting_special_ribs";

	public RibCraftingRecipe(Identifier id, CraftingRecipeCategory category) {
		super(id, category);
	}
	public static final RecipeSerializer<RibCraftingRecipe> SERIALIZER = new SpecialRecipeSerializer<>(RibCraftingRecipe::new);

	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		ItemStack ribsStack = ItemStack.EMPTY;
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isEmpty())
				continue;

			if (stack.isOf(ModBlocks.RAW_RIBS.asItem()) || stack.isOf(ModBlocks.RIBS.asItem())) {
				if (ribsStack.isEmpty()) {
					ribsStack = stack;
				} else {
					return false;
				}
			}
		}

		return !ribsStack.isEmpty();
	}

	@Override
	public ItemStack craft(CraftingInventory inventory) {
		ItemStack ribsStack = ItemStack.EMPTY;
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isEmpty())
				continue;

			if (stack.isOf(ModBlocks.RAW_RIBS.asItem()) || stack.isOf(ModBlocks.RIBS.asItem())) {
				ribsStack = stack;
				break;
			}
		}

		int bitesLeft = RibsBlock.MAX_BITES + 1;
		NbtCompound nbt = ribsStack.getSubNbt(BlockItem.BLOCK_STATE_TAG_KEY);
		if (nbt != null && nbt.contains("bites", NbtElement.INT_TYPE)) {
			int bites = nbt.getInt("bites");
			bitesLeft = RibsBlock.MAX_BITES - bites + 1;
		}

		if (ribsStack.isEmpty())
			return ItemStack.EMPTY;

		if (ribsStack.isOf(ModBlocks.RAW_RIBS.asItem()))
			return new ItemStack(ModItems.RAW_RIB, bitesLeft);
		else
			return new ItemStack(ModItems.RIB, bitesLeft);
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
}

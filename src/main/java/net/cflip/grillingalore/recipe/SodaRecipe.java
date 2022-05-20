package net.cflip.grillingalore.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.cflip.grillingalore.block.entity.SodaMakerBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SodaRecipe implements Recipe<SodaMakerBlockEntity> {
	public static final String NAME = "soda";
	public static final RecipeSerializer<SodaRecipe> SERIALIZER = new Serializer();
	public static final RecipeType<SodaRecipe> TYPE = new Type();

	private final Identifier id;
	private final DefaultedList<Ingredient> input;
	private final Item container;
	private final ItemStack output;

	public SodaRecipe(Identifier id, DefaultedList<Ingredient> input, Item container, ItemStack output) {
		this.id = id;
		this.input = input;
		this.container = container;
		this.output = output;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return input;
	}

	public Item getContainer() {
		return container;
	}

	@Override
	public ItemStack getOutput() {
		return output;
	}

	@Override
	public boolean matches(SodaMakerBlockEntity inventory, World world) {
		if (!inventory.hasContainerInSlots(container))
			return false;

		RecipeMatcher recipeMatcher = new RecipeMatcher();
		int numberOfItemsInInventory = 0;
		for (int i = 0; i < 2; i++) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isEmpty())
				continue;
			numberOfItemsInInventory++;
			recipeMatcher.addInput(stack, 1);
		}
		return input.size() == numberOfItemsInInventory && recipeMatcher.match(this, null);
	}

	@Override
	public ItemStack craft(SodaMakerBlockEntity inventory) {
		return output.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return TYPE;
	}

	public static class Serializer implements RecipeSerializer<SodaRecipe> {
		private static DefaultedList<Ingredient> getIngredients(JsonArray jsonArray) {
			DefaultedList<Ingredient> result = DefaultedList.of();
			for (int i = 0; i < jsonArray.size(); i++) {
				Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i));
				if (ingredient.isEmpty())
					continue;
				result.add(ingredient);
			}
			return result;
		}

		@Override
		public SodaRecipe read(Identifier id, JsonObject json) {
			DefaultedList<Ingredient> ingredients = getIngredients(JsonHelper.getArray(json, "ingredients"));

			if (ingredients.isEmpty())
				throw new JsonParseException("No ingredients for soda recipe");

			if (ingredients.size() > 2)
				throw new JsonParseException("Soda recipe cannot have more than 2 ingredients");

			Item containerItem = ShapedRecipe.getItem(JsonHelper.getObject(json, "container"));
			ItemStack resultStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
			return new SodaRecipe(id, ingredients, containerItem, resultStack);
		}

		@Override
		public SodaRecipe read(Identifier id, PacketByteBuf buf) {
			int ingredientCount = buf.readVarInt();
			DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(ingredientCount, Ingredient.EMPTY);

			for (int i = 0; i < ingredients.size(); i++)
				ingredients.set(i, Ingredient.fromPacket(buf));

			Item containerItem = Item.byRawId(buf.readVarInt());
			ItemStack resultStack = buf.readItemStack();
			return new SodaRecipe(id, ingredients, containerItem, resultStack);
		}

		@Override
		public void write(PacketByteBuf buf, SodaRecipe recipe) {
			buf.writeVarInt(recipe.input.size());
			for (Ingredient ingredient : recipe.input)
				ingredient.write(buf);
			buf.writeVarInt(Item.getRawId(recipe.container));
			buf.writeItemStack(recipe.output);
		}
	}

	public static class Type implements RecipeType<SodaRecipe> {

	}
}

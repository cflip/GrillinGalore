package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.recipe.RibCraftingRecipe;
import net.cflip.grillingalore.recipe.SodaRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
	public static void register() {
		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(GrillinGalore.MODID, RibCraftingRecipe.NAME), RibCraftingRecipe.SERIALIZER);
		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(GrillinGalore.MODID, SodaRecipe.NAME), SodaRecipe.SERIALIZER);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(GrillinGalore.MODID, SodaRecipe.NAME), SodaRecipe.TYPE);
	}
}

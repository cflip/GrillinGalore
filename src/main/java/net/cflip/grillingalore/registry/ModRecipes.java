package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.recipe.RibCraftingRecipe;
import net.cflip.grillingalore.recipe.SodaRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
	public static void register() {
		Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(GrillinGalore.MODID, RibCraftingRecipe.NAME), RibCraftingRecipe.SERIALIZER);
		Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(GrillinGalore.MODID, SodaRecipe.NAME), SodaRecipe.SERIALIZER);
		Registry.register(Registries.RECIPE_TYPE, new Identifier(GrillinGalore.MODID, SodaRecipe.NAME), SodaRecipe.TYPE);
	}
}

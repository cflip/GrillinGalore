package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final Item ONION = new AliasedBlockItem(ModBlocks.ONIONS, new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).build()));
	public static final Item ONION_RINGS = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).snack().build()));
	public static final Item GRILLED_ONION_RINGS = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).snack().build()));
	public static final Item RIB_SANDWICH = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(12).saturationModifier(0.8f).build()));

	public static void register() {
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "onion"), ONION);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "onion_rings"), ONION_RINGS);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "grilled_onion_rings"), GRILLED_ONION_RINGS);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "rib_sandwich"), RIB_SANDWICH);
	}
}

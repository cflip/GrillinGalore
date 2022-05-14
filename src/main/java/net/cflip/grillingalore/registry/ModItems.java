package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.item.MugDrinkItem;
import net.cflip.grillingalore.item.MugItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ModItems {
	public static final FoodComponent RAW_RIB_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(0.05f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3f).meat().build();
	public static final FoodComponent RIB_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).build();

	public static final Item ONION = new AliasedBlockItem(ModBlocks.ONIONS, new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).build()));
	public static final Item ONION_RINGS = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).snack().build()));
	public static final Item GRILLED_ONION_RINGS = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).snack().build()));
	public static final Item PEPPER = new AliasedBlockItem(ModBlocks.PEPPERS, new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).build()));
	public static final Item RAW_RIB = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(RAW_RIB_FOOD));
	public static final Item RIB = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(RIB_FOOD));
	public static final Item RIB_SANDWICH = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(new FoodComponent.Builder().hunger(12).saturationModifier(0.8f).build()));
	public static final Item GLASS_MUG = new MugItem(new FabricItemSettings().group(ItemGroup.FOOD));
	public static final Item WATER_MUG = new MugDrinkItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1));
	public static final Item ROOT_BEER = new MugDrinkItem(new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1), List.of(new StatusEffectInstance(StatusEffects.SPEED, 600, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0)));

	public static void register() {
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "onion"), ONION);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "onion_rings"), ONION_RINGS);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "grilled_onion_rings"), GRILLED_ONION_RINGS);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "pepper"), PEPPER);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "raw_rib"), RAW_RIB);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "rib"), RIB);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "rib_sandwich"), RIB_SANDWICH);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "glass_mug"), GLASS_MUG);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "water_mug"), WATER_MUG);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "root_beer"), ROOT_BEER);
	}
}

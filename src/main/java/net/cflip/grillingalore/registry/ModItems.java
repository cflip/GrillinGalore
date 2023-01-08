package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.item.MugDrinkItem;
import net.cflip.grillingalore.item.MugItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.List;

public class ModItems {
	public static final FoodComponent RAW_RIB_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(0.05f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3f).meat().build();
	public static final FoodComponent RIB_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).build();
	public static final FoodComponent PEPPER_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).build();

	public static final Item ONION = new AliasedBlockItem(ModBlocks.ONIONS, new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).build()));
	public static final Item ONION_RINGS = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).snack().build()));
	public static final Item GRILLED_ONION_RINGS = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).snack().build()));
	public static final Item PEPPER_SEEDS = new AliasedBlockItem(ModBlocks.PEPPERS, new FabricItemSettings());
	public static final Item RED_PEPPER = new Item(new FabricItemSettings().food(PEPPER_FOOD));
	public static final Item GREEN_PEPPER = new Item(new FabricItemSettings().food(PEPPER_FOOD));
	public static final Item SARSAPARILLA = new Item(new FabricItemSettings());
	public static final Item RAW_RIB = new Item(new FabricItemSettings().food(RAW_RIB_FOOD));
	public static final Item RIB = new Item(new FabricItemSettings().food(RIB_FOOD));
	public static final Item RIB_SANDWICH = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(12).saturationModifier(0.8f).build()));
	public static final Item GLASS_MUG = new MugItem(new FabricItemSettings());
	public static final Item WATER_MUG = new MugDrinkItem(new FabricItemSettings().maxCount(1));
	public static final Item ROOT_BEER = new MugDrinkItem(new FabricItemSettings().maxCount(1), List.of(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0)));
	public static final Item CREAM_SODA = new MugDrinkItem(new FabricItemSettings().maxCount(1), List.of(new StatusEffectInstance(StatusEffects.SPEED, 800, 0)));

	public static void register() {
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "onion"), ONION);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "onion_rings"), ONION_RINGS);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "grilled_onion_rings"), GRILLED_ONION_RINGS);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "pepper_seeds"), PEPPER_SEEDS);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "red_pepper"), RED_PEPPER);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "green_pepper"), GREEN_PEPPER);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "sarsaparilla"), SARSAPARILLA);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "raw_rib"), RAW_RIB);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "rib"), RIB);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "rib_sandwich"), RIB_SANDWICH);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "glass_mug"), GLASS_MUG);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "water_mug"), WATER_MUG);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "root_beer"), ROOT_BEER);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, "cream_soda"), CREAM_SODA);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ONION));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(ONION_RINGS));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(GRILLED_ONION_RINGS));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(PEPPER_SEEDS));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(RED_PEPPER));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(GREEN_PEPPER));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(SARSAPARILLA));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(RAW_RIB));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(RIB));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(RIB_SANDWICH));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(GLASS_MUG));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(WATER_MUG));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(ROOT_BEER));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(CREAM_SODA));

	}
}

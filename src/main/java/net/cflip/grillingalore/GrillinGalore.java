package net.cflip.grillingalore;

import net.cflip.grillingalore.registry.ModBlockEntities;
import net.cflip.grillingalore.registry.ModBlocks;
import net.cflip.grillingalore.registry.ModCriteria;
import net.cflip.grillingalore.registry.ModItems;
import net.cflip.grillingalore.registry.ModLootTables;
import net.cflip.grillingalore.registry.ModRecipes;
import net.cflip.grillingalore.registry.ModScreens;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class GrillinGalore implements ModInitializer {
	public static final String MODID = "grillingalore";

	@Override
	public void onInitialize() {
		ModItems.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModRecipes.register();
		ModScreens.register();
		ModLootTables.addLootTables();
		ModCriteria.register();
	}
	ItemGroup GRILLIN_GROUP = FabricItemGroup.builder(new Identifier(MODID, "items"))
			.icon(() -> new ItemStack(ModBlocks.CHARCOAL_GRILL))
			.entries((enabledFeatures, entries, operatorEnabled) -> {
				entries.add(ModItems.RAW_RIB);
				entries.add(ModBlocks.RAW_RIBS);
				entries.add(ModItems.RIB);
				entries.add(ModBlocks.RIBS);
				entries.add(ModBlocks.GRILL);
				entries.add(ModBlocks.CHARCOAL_GRILL);
				entries.add(ModItems.GLASS_MUG);
				entries.add(ModItems.WATER_MUG);
				entries.add(ModItems.CREAM_SODA);
				entries.add(ModItems.ROOT_BEER);
				entries.add(ModItems.SARSAPARILLA);
				entries.add(ModItems.GREEN_PEPPER);
				entries.add(ModItems.PEPPER_SEEDS);
				entries.add(ModItems.RIB_SANDWICH);
				entries.add(ModItems.GRILLED_ONION_RINGS);
				entries.add(ModItems.ONION_RINGS);
				entries.add(ModBlocks.SODA_MAKER);
			})
			.build();


}

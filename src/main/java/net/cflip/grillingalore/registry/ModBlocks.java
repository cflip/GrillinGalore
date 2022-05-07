package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.block.GrillBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
	public static final Block GRILL = new GrillBlock(FabricBlockSettings.of(Material.METAL));

	public static void register() {
		Registry.register(Registry.BLOCK, new Identifier(GrillinGalore.MODID, "grill"), GRILL);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "grill"), new BlockItem(GRILL, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
	}
}

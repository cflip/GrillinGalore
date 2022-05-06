package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.block.BarbecueBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
	public static final Block BARBECUE = new BarbecueBlock(FabricBlockSettings.of(Material.METAL));

	public static void register() {
		Registry.register(Registry.BLOCK, new Identifier(GrillinGalore.MODID, "barbecue"), BARBECUE);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, "barbecue"), new BlockItem(BARBECUE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
	}
}

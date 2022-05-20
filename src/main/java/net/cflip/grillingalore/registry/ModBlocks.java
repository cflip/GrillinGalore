package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.block.CharcoalGrillBlock;
import net.cflip.grillingalore.block.GrillBlock;
import net.cflip.grillingalore.block.OnionsBlock;
import net.cflip.grillingalore.block.PeppersBlock;
import net.cflip.grillingalore.block.RibsBlock;
import net.cflip.grillingalore.block.SodaMakerBlock;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
	public static final Block GRILL = new GrillBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f, 12.0f).requiresTool());
	public static final Block CHARCOAL_GRILL = new CharcoalGrillBlock(FabricBlockSettings.of(Material.METAL).strength(1.0f, 6.0f));
	public static final Block SODA_MAKER = new SodaMakerBlock(FabricBlockSettings.of(Material.AMETHYST));
	public static final Block RAW_RIBS = new RibsBlock(FabricBlockSettings.of(Material.CAKE).strength(0.6f).sounds(BlockSoundGroup.SLIME), ModItems.RAW_RIB_FOOD);
	public static final Block RIBS = new RibsBlock(FabricBlockSettings.of(Material.CAKE).strength(0.6f).sounds(BlockSoundGroup.SLIME), ModItems.RIB_FOOD);
	public static final Block ONIONS = new OnionsBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
	public static final Block PEPPERS = new PeppersBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

	public static void register() {
		registerBlockWithItem(GRILL, "grill", new FabricItemSettings().group(ItemGroup.DECORATIONS));
		registerBlockWithItem(CHARCOAL_GRILL, "charcoal_grill", new FabricItemSettings().group(ItemGroup.DECORATIONS));
		registerBlockWithItem(SODA_MAKER, "soda_maker", new FabricItemSettings().group(ItemGroup.DECORATIONS));
		registerBlockWithItem(RAW_RIBS, "raw_ribs", new FabricItemSettings().group(ItemGroup.FOOD));
		registerBlockWithItem(RIBS, "ribs", new FabricItemSettings().group(ItemGroup.FOOD));
		Registry.register(Registry.BLOCK, new Identifier(GrillinGalore.MODID, "onions"), ONIONS);
		Registry.register(Registry.BLOCK, new Identifier(GrillinGalore.MODID, "peppers"), PEPPERS);
	}

	public static void registerTranslucency() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHARCOAL_GRILL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SODA_MAKER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAW_RIBS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RIBS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ONIONS, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEPPERS, RenderLayer.getCutout());
	}

	private static void registerBlockWithItem(Block block, String identifier, FabricItemSettings itemSettings) {
		Registry.register(Registry.BLOCK, new Identifier(GrillinGalore.MODID, identifier), block);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, identifier), new BlockItem(block, itemSettings));
	}
}

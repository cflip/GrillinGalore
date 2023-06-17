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
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
	public static final Block GRILL = new GrillBlock(FabricBlockSettings.create().mapColor(MapColor.DEEPSLATE_GRAY).instrument(Instrument.BASEDRUM).requiresTool().strength(4.0f, 12.0f));
	public static final Block CHARCOAL_GRILL = new CharcoalGrillBlock(FabricBlockSettings.create().mapColor(MapColor.DEEPSLATE_GRAY).instrument(Instrument.BASEDRUM).strength(1.0f, 6.0f).nonOpaque());
	public static final Block SODA_MAKER = new SodaMakerBlock(FabricBlockSettings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(0.5f).nonOpaque());
	public static final Block RAW_RIBS = new RibsBlock(FabricBlockSettings.create().strength(0.6f).sounds(BlockSoundGroup.SLIME), ModItems.RAW_RIB_FOOD);
	public static final Block RIBS = new RibsBlock(FabricBlockSettings.create().strength(0.6f).sounds(BlockSoundGroup.SLIME), ModItems.RIB_FOOD);
	public static final Block ONIONS = new OnionsBlock(FabricBlockSettings.copy(Blocks.WHEAT));
	public static final Block PEPPERS = new PeppersBlock(FabricBlockSettings.copy(Blocks.WHEAT));

	public static void register() {
		registerBlockWithItem(GRILL, "grill", new FabricItemSettings());
		registerBlockWithItem(CHARCOAL_GRILL, "charcoal_grill", new FabricItemSettings());
		registerBlockWithItem(SODA_MAKER, "soda_maker", new FabricItemSettings());
		registerBlockWithItem(RAW_RIBS, "raw_ribs", new FabricItemSettings());
		registerBlockWithItem(RIBS, "ribs", new FabricItemSettings());
		Registry.register(Registries.BLOCK, new Identifier(GrillinGalore.MODID, "onions"), ONIONS);
		Registry.register(Registries.BLOCK, new Identifier(GrillinGalore.MODID, "peppers"), PEPPERS);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(GRILL));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(CHARCOAL_GRILL));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(SODA_MAKER));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(RAW_RIBS));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(RIBS));
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
		Registry.register(Registries.BLOCK, new Identifier(GrillinGalore.MODID, identifier), block);
		Registry.register(Registries.ITEM, new Identifier(GrillinGalore.MODID, identifier), new BlockItem(block, itemSettings));
	}
}

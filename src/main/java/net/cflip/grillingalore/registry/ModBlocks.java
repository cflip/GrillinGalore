package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.block.CharcoalGrillBlock;
import net.cflip.grillingalore.block.GrillBlock;
import net.cflip.grillingalore.block.RibsBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
	public static final Block GRILL = new GrillBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f, 12.0f).requiresTool());
	public static final Block CHARCOAL_GRILL = new CharcoalGrillBlock(FabricBlockSettings.of(Material.METAL).strength(1.0f, 6.0f));
	public static final Block RAW_RIBS = new RibsBlock(FabricBlockSettings.of(Material.CAKE).strength(0.6f).sounds(BlockSoundGroup.SLIME), new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3f).meat().build());
	public static final Block RIBS = new RibsBlock(FabricBlockSettings.of(Material.CAKE).strength(0.6f).sounds(BlockSoundGroup.SLIME), new FoodComponent.Builder().hunger(4).saturationModifier(0.8f).build());

	public static void register() {
		registerBlockWithItem(GRILL, "grill", new FabricItemSettings().group(ItemGroup.DECORATIONS));
		registerBlockWithItem(CHARCOAL_GRILL, "charcoal_grill", new FabricItemSettings().group(ItemGroup.DECORATIONS));
		registerBlockWithItem(RAW_RIBS, "raw_ribs", new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1));
		registerBlockWithItem(RIBS, "ribs", new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1));
	}

	private static void registerBlockWithItem(Block block, String identifier, FabricItemSettings itemSettings)  {
		Registry.register(Registry.BLOCK, new Identifier(GrillinGalore.MODID, identifier), block);
		Registry.register(Registry.ITEM, new Identifier(GrillinGalore.MODID, identifier), new BlockItem(block, itemSettings));
	}
}

package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.block.entity.CharcoalGrillBlockEntity;
import net.cflip.grillingalore.block.entity.GrillBlockEntity;
import net.cflip.grillingalore.block.entity.SodaMakerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
	public static BlockEntityType<GrillBlockEntity> GRILL;
	public static BlockEntityType<CharcoalGrillBlockEntity> CHARCOAL_GRILL;
	public static BlockEntityType<SodaMakerBlockEntity> SODA_MAKER;

	public static void register() {
		GRILL = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(GrillinGalore.MODID, "grill"), FabricBlockEntityTypeBuilder.create(GrillBlockEntity::new, ModBlocks.GRILL).build(null));
		CHARCOAL_GRILL = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(GrillinGalore.MODID, "charcoal_grill"), FabricBlockEntityTypeBuilder.create(CharcoalGrillBlockEntity::new, ModBlocks.CHARCOAL_GRILL).build(null));
		SODA_MAKER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(GrillinGalore.MODID, "soda_maker"), FabricBlockEntityTypeBuilder.create(SodaMakerBlockEntity::new, ModBlocks.SODA_MAKER).build());
	}
}

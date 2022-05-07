package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.block.entity.GrillBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
	public static BlockEntityType<GrillBlockEntity> GRILL;

	public static void register() {
		GRILL = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(GrillinGalore.MODID, "grill"), FabricBlockEntityTypeBuilder.create(GrillBlockEntity::new, ModBlocks.GRILL).build(null));
	}
}

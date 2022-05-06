package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.GrillinGalore;
import net.cflip.grillingalore.entity.BarbecueBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
	public static BlockEntityType<BarbecueBlockEntity> BARBECUE;

	public static void register() {
		BARBECUE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(GrillinGalore.MODID, "barbecue_entity"), FabricBlockEntityTypeBuilder.create(BarbecueBlockEntity::new, ModBlocks.BARBECUE).build(null));
	}
}

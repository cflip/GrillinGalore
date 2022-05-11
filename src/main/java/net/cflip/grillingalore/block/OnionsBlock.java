package net.cflip.grillingalore.block;

import net.cflip.grillingalore.registry.ModItems;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class OnionsBlock extends CropBlock {
	public OnionsBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return ModItems.ONION;
	}
}

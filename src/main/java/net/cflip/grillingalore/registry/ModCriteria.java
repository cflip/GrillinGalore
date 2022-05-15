package net.cflip.grillingalore.registry;

import net.cflip.grillingalore.advancement.criterion.ConsumeRibBlockCriterion;
import net.cflip.grillingalore.advancement.criterion.UseGrillCriterion;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

public class ModCriteria {
	public static ConsumeRibBlockCriterion CONSUME_RIB_BLOCK;
	public static UseGrillCriterion USE_GRILL;

	public static void register() {
		CONSUME_RIB_BLOCK = CriterionRegistry.register(new ConsumeRibBlockCriterion());
		USE_GRILL = CriterionRegistry.register(new UseGrillCriterion());
	}
}

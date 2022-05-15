package net.cflip.grillingalore.advancement.criterion;

import com.google.gson.JsonObject;
import net.cflip.grillingalore.GrillinGalore;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class UseGrillCriterion extends AbstractCriterion<UseGrillCriterion.Conditions> {
	public static final Identifier ID = new Identifier(GrillinGalore.MODID, "use_grill");

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
		return new Conditions(playerPredicate, BlockPredicate.fromJson(obj.get("block")));
	}

	public void trigger(ServerPlayerEntity player, ServerWorld world, BlockPos pos) {
		trigger(player, conditions -> conditions.matches(world, pos));
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final BlockPredicate block;

		public Conditions(EntityPredicate.Extended playerPredicate, BlockPredicate block) {
			super(ID, playerPredicate);
			this.block = block;
		}

		public boolean matches(ServerWorld world, BlockPos pos) {
			return block.test(world, pos);
		}
	}
}

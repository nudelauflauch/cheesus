package net.stehschnitzel.cheesus.datagen.triggers;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusCriteriaInit;

import java.util.Optional;

public class PlacedAltitudeCheeseInBigHeightsTrigger extends SimpleCriterionTrigger<PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance> {

    public void trigger(ServerPlayer player, BlockState state, BlockPos pos) {
        if (state.is(BlockInit.CHEESE.get()) && pos.getY() > 128) {
            this.trigger(player, instance -> true);
        }
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return null;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance::player),
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("location").forGetter(PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance::location)
                        )
                        .apply(instance, PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance::new)
        );



        public static Criterion<PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance> instance(ContextAwarePredicate player, ContextAwarePredicate location) {
            return CheesusCriteriaInit.PLACED_ALTITUDE_CHEESE
                    .createCriterion(new PlacedAltitudeCheeseInBigHeightsTrigger.TriggerInstance(Optional.of(player), Optional.of(location)));
        }

        public boolean matches(ContextAwarePredicate location) {
            // Since ItemPredicate matches a stack, we use a stack as the input here.
            return this.location.equals(location);
        }

    }
}

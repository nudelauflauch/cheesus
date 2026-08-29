package net.stehschnitzel.cheesus.datagen.triggers;

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

public class PlacedMoldCheeseInDarkTrigger extends SimpleCriterionTrigger<PlacedMoldCheeseInDarkTrigger.TriggerInstance> {

    public void trigger(ServerPlayer player, BlockState state, BlockPos pos) {
        if (state.is(BlockInit.CHEESE.get()) && player.level().getRawBrightness(pos, 0) < 5) {
            this.trigger(player, instance -> true);
        }
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<PlacedMoldCheeseInDarkTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PlacedMoldCheeseInDarkTrigger.TriggerInstance::player),
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("location").forGetter(PlacedMoldCheeseInDarkTrigger.TriggerInstance::location)
            )
                    .apply(instance, PlacedMoldCheeseInDarkTrigger.TriggerInstance::new)
        );

        public static Criterion<PlacedMoldCheeseInDarkTrigger.TriggerInstance> placedMoldCheeseInDark() {
            return CheesusCriteriaInit.PLACED_MOLD_CHEESE_DARK
                    .createCriterion(new PlacedMoldCheeseInDarkTrigger.TriggerInstance(Optional.empty(), Optional.empty()));
        }
    }
}

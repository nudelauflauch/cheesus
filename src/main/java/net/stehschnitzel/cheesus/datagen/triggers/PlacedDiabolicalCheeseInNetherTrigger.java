package net.stehschnitzel.cheesus.datagen.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusCriteriaInit;

import java.util.Optional;

public class PlacedDiabolicalCheeseInNetherTrigger extends SimpleCriterionTrigger<PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance> {

    @Override
    public Codec<PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance> codec() {
        return PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, BlockState state, BlockPos pos) {
        if (state.is(BlockInit.CHEESE.get()) && player.level().dimensionTypeRegistration() == BuiltinDimensionTypes.NETHER) {
            this.trigger(player, instance -> true);
        }
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance::player),
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("location").forGetter(PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance::location)
                        )
                        .apply(instance, PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance::new)
        );

        public static Criterion<TriggerInstance> placedDiabolicalCheeseInNether() {
            return CheesusCriteriaInit.PLACED_DIABOLICAL_CHEESE_NETHER
                    .createCriterion(new PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance(Optional.empty(), Optional.empty()));
        }
    }
}

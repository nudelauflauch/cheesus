package net.stehschnitzel.cheesus.datagen.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.init.BlockInit;
import net.stehschnitzel.cheesus.init.CheesusCriteriaInit;

import java.util.Optional;

public class RightClickedBlueMoldCheeseTrigger extends SimpleCriterionTrigger<RightClickedBlueMoldCheeseTrigger.TriggerInstance> {

    public void trigger(ServerPlayer player, BlockState state) {
        if (state.is(BlockInit.CHEESE.get()) && player.getItemInHand(player.getUsedItemHand()).is(ItemTags.SWORDS)) {
            this.trigger(player, instance -> true);
        }
    }

    @Override
    public Codec<RightClickedBlueMoldCheeseTrigger.TriggerInstance> codec() {
        return RightClickedBlueMoldCheeseTrigger.TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> location)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<RightClickedBlueMoldCheeseTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(RightClickedBlueMoldCheeseTrigger.TriggerInstance::player),
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("location").forGetter(RightClickedBlueMoldCheeseTrigger.TriggerInstance::location)
                        )
                        .apply(instance, RightClickedBlueMoldCheeseTrigger.TriggerInstance::new)
        );

        public static Criterion<RightClickedBlueMoldCheeseTrigger.TriggerInstance> placedMoldCheeseInDark() {
            return CheesusCriteriaInit.RIGHT_CLICKED_BLUE_MOLD_CHEESE
                    .createCriterion(new RightClickedBlueMoldCheeseTrigger.TriggerInstance(Optional.empty(), Optional.empty()));
        }
    }
}

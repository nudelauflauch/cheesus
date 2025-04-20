package net.stehschnitzel.cheesus.datagen.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;

public class PlacedAltitudeCheeseTrigger extends SimpleCriterionTrigger<PlacedAltitudeCheeseTrigger.TriggerInstance> {

    public static final ResourceLocation ID = ResourceLocation.tryBuild(Cheesus.MOD_ID,"placed_altitude_cheese");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate playerPredicate, DeserializationContext context) {
        return new TriggerInstance(playerPredicate);
    }

    public void trigger(ServerPlayer player, BlockState state, BlockPos pos) {
        System.out.println(pos.getY());
        if (state.is(BlockInit.CHEESE.get()) && pos.getY() > 150) {
            this.trigger(player, instance -> true);
        }
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate playerPredicate) {
            super(ID, playerPredicate);
        }

        public static TriggerInstance placedAltitudeCheese() {
            return new TriggerInstance(ContextAwarePredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            return super.serializeToJson(context);
        }
    }
}

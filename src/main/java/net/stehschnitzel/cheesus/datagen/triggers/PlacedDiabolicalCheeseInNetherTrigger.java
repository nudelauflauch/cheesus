package net.stehschnitzel.cheesus.datagen.triggers;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.BlockInit;

public class PlacedDiabolicalCheeseInNetherTrigger extends SimpleCriterionTrigger<PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance> {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Cheesus.MOD_ID,"placed_diabolical_cheese_nether");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate playerPredicate, DeserializationContext context) {
        return new PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance(playerPredicate);
    }

    public void trigger(ServerPlayer player, BlockState state, BlockPos pos) {
        if (state.is(BlockInit.CHEESE.get()) && player.serverLevel().dimensionTypeId() == BuiltinDimensionTypes.NETHER) {
            this.trigger(player, instance -> true);
        }
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate playerPredicate) {
            super(ID, playerPredicate);
        }

        public static PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance placedDiabolicalCheeseInNether() {
            return new PlacedDiabolicalCheeseInNetherTrigger.TriggerInstance(ContextAwarePredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            return super.serializeToJson(context);
        }
    }
}

package net.stehschnitzel.cheesus.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.stehschnitzel.cheesus.Cheesus;
import net.stehschnitzel.cheesus.init.CheesusCriteriaInit;

//@EventBusSubscriber(modid = Cheesus.MOD_ID)
public class CheesusEvents {

//    @SubscribeEvent
//    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
//        if (event.getEntity() instanceof ServerPlayer player) {
//            BlockState state = event.getPlacedBlock();
//            BlockPos pos = event.getPos();
//
//            CheesusCriteriaInit.PLACED_MOLD_CHEESE_DARK.trigger(player, state, pos);
//            CheesusCriteriaInit.PLACED_DIABOLICAL_CHEESE_NETHER.trigger(player, state, pos);
//            CheesusCriteriaInit.PLACED_ALTITUDE_CHEESE.trigger(player, state, pos);
//        }
//    }
//
//
//    @SubscribeEvent
//    public static void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
//        if (event.getEntity() instanceof ServerPlayer player) {
//            CheesusCriteriaInit.RIGHT_CLICKED_BLUE_MOLD_CHEESE.trigger(player, event.getLevel().getBlockState(event.getPos()));
//        }
//    }
}

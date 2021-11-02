package net.minecraft.network;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAddVibrationSignalPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockBreakAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientInformationPacket;
import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundJigsawGeneratePacket;
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookChangeSettingsPacket;
import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
import net.minecraft.network.protocol.game.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import net.minecraft.network.protocol.game.ServerboundSetJigsawBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import org.apache.logging.log4j.LogManager;

public enum ConnectionProtocol {
   HANDSHAKING(-1, m_129600_().m_129625_(PacketFlow.SERVERBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.handshake.ServerHandshakePacketListener>()).m_178330_(ClientIntentionPacket.class, ClientIntentionPacket::new))),
   PLAY(0, m_129600_().m_129625_(PacketFlow.CLIENTBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.game.ClientGamePacketListener>()).m_178330_(ClientboundAddEntityPacket.class, ClientboundAddEntityPacket::new).m_178330_(ClientboundAddExperienceOrbPacket.class, ClientboundAddExperienceOrbPacket::new).m_178330_(ClientboundAddMobPacket.class, ClientboundAddMobPacket::new).m_178330_(ClientboundAddPaintingPacket.class, ClientboundAddPaintingPacket::new).m_178330_(ClientboundAddPlayerPacket.class, ClientboundAddPlayerPacket::new).m_178330_(ClientboundAddVibrationSignalPacket.class, ClientboundAddVibrationSignalPacket::new).m_178330_(ClientboundAnimatePacket.class, ClientboundAnimatePacket::new).m_178330_(ClientboundAwardStatsPacket.class, ClientboundAwardStatsPacket::new).m_178330_(ClientboundBlockBreakAckPacket.class, ClientboundBlockBreakAckPacket::new).m_178330_(ClientboundBlockDestructionPacket.class, ClientboundBlockDestructionPacket::new).m_178330_(ClientboundBlockEntityDataPacket.class, ClientboundBlockEntityDataPacket::new).m_178330_(ClientboundBlockEventPacket.class, ClientboundBlockEventPacket::new).m_178330_(ClientboundBlockUpdatePacket.class, ClientboundBlockUpdatePacket::new).m_178330_(ClientboundBossEventPacket.class, ClientboundBossEventPacket::new).m_178330_(ClientboundChangeDifficultyPacket.class, ClientboundChangeDifficultyPacket::new).m_178330_(ClientboundChatPacket.class, ClientboundChatPacket::new).m_178330_(ClientboundClearTitlesPacket.class, ClientboundClearTitlesPacket::new).m_178330_(ClientboundCommandSuggestionsPacket.class, ClientboundCommandSuggestionsPacket::new).m_178330_(ClientboundCommandsPacket.class, ClientboundCommandsPacket::new).m_178330_(ClientboundContainerClosePacket.class, ClientboundContainerClosePacket::new).m_178330_(ClientboundContainerSetContentPacket.class, ClientboundContainerSetContentPacket::new).m_178330_(ClientboundContainerSetDataPacket.class, ClientboundContainerSetDataPacket::new).m_178330_(ClientboundContainerSetSlotPacket.class, ClientboundContainerSetSlotPacket::new).m_178330_(ClientboundCooldownPacket.class, ClientboundCooldownPacket::new).m_178330_(ClientboundCustomPayloadPacket.class, ClientboundCustomPayloadPacket::new).m_178330_(ClientboundCustomSoundPacket.class, ClientboundCustomSoundPacket::new).m_178330_(ClientboundDisconnectPacket.class, ClientboundDisconnectPacket::new).m_178330_(ClientboundEntityEventPacket.class, ClientboundEntityEventPacket::new).m_178330_(ClientboundExplodePacket.class, ClientboundExplodePacket::new).m_178330_(ClientboundForgetLevelChunkPacket.class, ClientboundForgetLevelChunkPacket::new).m_178330_(ClientboundGameEventPacket.class, ClientboundGameEventPacket::new).m_178330_(ClientboundHorseScreenOpenPacket.class, ClientboundHorseScreenOpenPacket::new).m_178330_(ClientboundInitializeBorderPacket.class, ClientboundInitializeBorderPacket::new).m_178330_(ClientboundKeepAlivePacket.class, ClientboundKeepAlivePacket::new).m_178330_(ClientboundLevelChunkPacket.class, ClientboundLevelChunkPacket::new).m_178330_(ClientboundLevelEventPacket.class, ClientboundLevelEventPacket::new).m_178330_(ClientboundLevelParticlesPacket.class, ClientboundLevelParticlesPacket::new).m_178330_(ClientboundLightUpdatePacket.class, ClientboundLightUpdatePacket::new).m_178330_(ClientboundLoginPacket.class, ClientboundLoginPacket::new).m_178330_(ClientboundMapItemDataPacket.class, ClientboundMapItemDataPacket::new).m_178330_(ClientboundMerchantOffersPacket.class, ClientboundMerchantOffersPacket::new).m_178330_(ClientboundMoveEntityPacket.Pos.class, ClientboundMoveEntityPacket.Pos::m_179000_).m_178330_(ClientboundMoveEntityPacket.PosRot.class, ClientboundMoveEntityPacket.PosRot::m_179002_).m_178330_(ClientboundMoveEntityPacket.Rot.class, ClientboundMoveEntityPacket.Rot::m_179004_).m_178330_(ClientboundMoveVehiclePacket.class, ClientboundMoveVehiclePacket::new).m_178330_(ClientboundOpenBookPacket.class, ClientboundOpenBookPacket::new).m_178330_(ClientboundOpenScreenPacket.class, ClientboundOpenScreenPacket::new).m_178330_(ClientboundOpenSignEditorPacket.class, ClientboundOpenSignEditorPacket::new).m_178330_(ClientboundPingPacket.class, ClientboundPingPacket::new).m_178330_(ClientboundPlaceGhostRecipePacket.class, ClientboundPlaceGhostRecipePacket::new).m_178330_(ClientboundPlayerAbilitiesPacket.class, ClientboundPlayerAbilitiesPacket::new).m_178330_(ClientboundPlayerCombatEndPacket.class, ClientboundPlayerCombatEndPacket::new).m_178330_(ClientboundPlayerCombatEnterPacket.class, ClientboundPlayerCombatEnterPacket::new).m_178330_(ClientboundPlayerCombatKillPacket.class, ClientboundPlayerCombatKillPacket::new).m_178330_(ClientboundPlayerInfoPacket.class, ClientboundPlayerInfoPacket::new).m_178330_(ClientboundPlayerLookAtPacket.class, ClientboundPlayerLookAtPacket::new).m_178330_(ClientboundPlayerPositionPacket.class, ClientboundPlayerPositionPacket::new).m_178330_(ClientboundRecipePacket.class, ClientboundRecipePacket::new).m_178330_(ClientboundRemoveEntitiesPacket.class, ClientboundRemoveEntitiesPacket::new).m_178330_(ClientboundRemoveMobEffectPacket.class, ClientboundRemoveMobEffectPacket::new).m_178330_(ClientboundResourcePackPacket.class, ClientboundResourcePackPacket::new).m_178330_(ClientboundRespawnPacket.class, ClientboundRespawnPacket::new).m_178330_(ClientboundRotateHeadPacket.class, ClientboundRotateHeadPacket::new).m_178330_(ClientboundSectionBlocksUpdatePacket.class, ClientboundSectionBlocksUpdatePacket::new).m_178330_(ClientboundSelectAdvancementsTabPacket.class, ClientboundSelectAdvancementsTabPacket::new).m_178330_(ClientboundSetActionBarTextPacket.class, ClientboundSetActionBarTextPacket::new).m_178330_(ClientboundSetBorderCenterPacket.class, ClientboundSetBorderCenterPacket::new).m_178330_(ClientboundSetBorderLerpSizePacket.class, ClientboundSetBorderLerpSizePacket::new).m_178330_(ClientboundSetBorderSizePacket.class, ClientboundSetBorderSizePacket::new).m_178330_(ClientboundSetBorderWarningDelayPacket.class, ClientboundSetBorderWarningDelayPacket::new).m_178330_(ClientboundSetBorderWarningDistancePacket.class, ClientboundSetBorderWarningDistancePacket::new).m_178330_(ClientboundSetCameraPacket.class, ClientboundSetCameraPacket::new).m_178330_(ClientboundSetCarriedItemPacket.class, ClientboundSetCarriedItemPacket::new).m_178330_(ClientboundSetChunkCacheCenterPacket.class, ClientboundSetChunkCacheCenterPacket::new).m_178330_(ClientboundSetChunkCacheRadiusPacket.class, ClientboundSetChunkCacheRadiusPacket::new).m_178330_(ClientboundSetDefaultSpawnPositionPacket.class, ClientboundSetDefaultSpawnPositionPacket::new).m_178330_(ClientboundSetDisplayObjectivePacket.class, ClientboundSetDisplayObjectivePacket::new).m_178330_(ClientboundSetEntityDataPacket.class, ClientboundSetEntityDataPacket::new).m_178330_(ClientboundSetEntityLinkPacket.class, ClientboundSetEntityLinkPacket::new).m_178330_(ClientboundSetEntityMotionPacket.class, ClientboundSetEntityMotionPacket::new).m_178330_(ClientboundSetEquipmentPacket.class, ClientboundSetEquipmentPacket::new).m_178330_(ClientboundSetExperiencePacket.class, ClientboundSetExperiencePacket::new).m_178330_(ClientboundSetHealthPacket.class, ClientboundSetHealthPacket::new).m_178330_(ClientboundSetObjectivePacket.class, ClientboundSetObjectivePacket::new).m_178330_(ClientboundSetPassengersPacket.class, ClientboundSetPassengersPacket::new).m_178330_(ClientboundSetPlayerTeamPacket.class, ClientboundSetPlayerTeamPacket::new).m_178330_(ClientboundSetScorePacket.class, ClientboundSetScorePacket::new).m_178330_(ClientboundSetSubtitleTextPacket.class, ClientboundSetSubtitleTextPacket::new).m_178330_(ClientboundSetTimePacket.class, ClientboundSetTimePacket::new).m_178330_(ClientboundSetTitleTextPacket.class, ClientboundSetTitleTextPacket::new).m_178330_(ClientboundSetTitlesAnimationPacket.class, ClientboundSetTitlesAnimationPacket::new).m_178330_(ClientboundSoundEntityPacket.class, ClientboundSoundEntityPacket::new).m_178330_(ClientboundSoundPacket.class, ClientboundSoundPacket::new).m_178330_(ClientboundStopSoundPacket.class, ClientboundStopSoundPacket::new).m_178330_(ClientboundTabListPacket.class, ClientboundTabListPacket::new).m_178330_(ClientboundTagQueryPacket.class, ClientboundTagQueryPacket::new).m_178330_(ClientboundTakeItemEntityPacket.class, ClientboundTakeItemEntityPacket::new).m_178330_(ClientboundTeleportEntityPacket.class, ClientboundTeleportEntityPacket::new).m_178330_(ClientboundUpdateAdvancementsPacket.class, ClientboundUpdateAdvancementsPacket::new).m_178330_(ClientboundUpdateAttributesPacket.class, ClientboundUpdateAttributesPacket::new).m_178330_(ClientboundUpdateMobEffectPacket.class, ClientboundUpdateMobEffectPacket::new).m_178330_(ClientboundUpdateRecipesPacket.class, ClientboundUpdateRecipesPacket::new).m_178330_(ClientboundUpdateTagsPacket.class, ClientboundUpdateTagsPacket::new)).m_129625_(PacketFlow.SERVERBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.game.ServerGamePacketListener>()).m_178330_(ServerboundAcceptTeleportationPacket.class, ServerboundAcceptTeleportationPacket::new).m_178330_(ServerboundBlockEntityTagQuery.class, ServerboundBlockEntityTagQuery::new).m_178330_(ServerboundChangeDifficultyPacket.class, ServerboundChangeDifficultyPacket::new).m_178330_(ServerboundChatPacket.class, ServerboundChatPacket::new).m_178330_(ServerboundClientCommandPacket.class, ServerboundClientCommandPacket::new).m_178330_(ServerboundClientInformationPacket.class, ServerboundClientInformationPacket::new).m_178330_(ServerboundCommandSuggestionPacket.class, ServerboundCommandSuggestionPacket::new).m_178330_(ServerboundContainerButtonClickPacket.class, ServerboundContainerButtonClickPacket::new).m_178330_(ServerboundContainerClickPacket.class, ServerboundContainerClickPacket::new).m_178330_(ServerboundContainerClosePacket.class, ServerboundContainerClosePacket::new).m_178330_(ServerboundCustomPayloadPacket.class, ServerboundCustomPayloadPacket::new).m_178330_(ServerboundEditBookPacket.class, ServerboundEditBookPacket::new).m_178330_(ServerboundEntityTagQuery.class, ServerboundEntityTagQuery::new).m_178330_(ServerboundInteractPacket.class, ServerboundInteractPacket::new).m_178330_(ServerboundJigsawGeneratePacket.class, ServerboundJigsawGeneratePacket::new).m_178330_(ServerboundKeepAlivePacket.class, ServerboundKeepAlivePacket::new).m_178330_(ServerboundLockDifficultyPacket.class, ServerboundLockDifficultyPacket::new).m_178330_(ServerboundMovePlayerPacket.Pos.class, ServerboundMovePlayerPacket.Pos::m_179685_).m_178330_(ServerboundMovePlayerPacket.PosRot.class, ServerboundMovePlayerPacket.PosRot::m_179687_).m_178330_(ServerboundMovePlayerPacket.Rot.class, ServerboundMovePlayerPacket.Rot::m_179689_).m_178330_(ServerboundMovePlayerPacket.StatusOnly.class, ServerboundMovePlayerPacket.StatusOnly::m_179697_).m_178330_(ServerboundMoveVehiclePacket.class, ServerboundMoveVehiclePacket::new).m_178330_(ServerboundPaddleBoatPacket.class, ServerboundPaddleBoatPacket::new).m_178330_(ServerboundPickItemPacket.class, ServerboundPickItemPacket::new).m_178330_(ServerboundPlaceRecipePacket.class, ServerboundPlaceRecipePacket::new).m_178330_(ServerboundPlayerAbilitiesPacket.class, ServerboundPlayerAbilitiesPacket::new).m_178330_(ServerboundPlayerActionPacket.class, ServerboundPlayerActionPacket::new).m_178330_(ServerboundPlayerCommandPacket.class, ServerboundPlayerCommandPacket::new).m_178330_(ServerboundPlayerInputPacket.class, ServerboundPlayerInputPacket::new).m_178330_(ServerboundPongPacket.class, ServerboundPongPacket::new).m_178330_(ServerboundRecipeBookChangeSettingsPacket.class, ServerboundRecipeBookChangeSettingsPacket::new).m_178330_(ServerboundRecipeBookSeenRecipePacket.class, ServerboundRecipeBookSeenRecipePacket::new).m_178330_(ServerboundRenameItemPacket.class, ServerboundRenameItemPacket::new).m_178330_(ServerboundResourcePackPacket.class, ServerboundResourcePackPacket::new).m_178330_(ServerboundSeenAdvancementsPacket.class, ServerboundSeenAdvancementsPacket::new).m_178330_(ServerboundSelectTradePacket.class, ServerboundSelectTradePacket::new).m_178330_(ServerboundSetBeaconPacket.class, ServerboundSetBeaconPacket::new).m_178330_(ServerboundSetCarriedItemPacket.class, ServerboundSetCarriedItemPacket::new).m_178330_(ServerboundSetCommandBlockPacket.class, ServerboundSetCommandBlockPacket::new).m_178330_(ServerboundSetCommandMinecartPacket.class, ServerboundSetCommandMinecartPacket::new).m_178330_(ServerboundSetCreativeModeSlotPacket.class, ServerboundSetCreativeModeSlotPacket::new).m_178330_(ServerboundSetJigsawBlockPacket.class, ServerboundSetJigsawBlockPacket::new).m_178330_(ServerboundSetStructureBlockPacket.class, ServerboundSetStructureBlockPacket::new).m_178330_(ServerboundSignUpdatePacket.class, ServerboundSignUpdatePacket::new).m_178330_(ServerboundSwingPacket.class, ServerboundSwingPacket::new).m_178330_(ServerboundTeleportToEntityPacket.class, ServerboundTeleportToEntityPacket::new).m_178330_(ServerboundUseItemOnPacket.class, ServerboundUseItemOnPacket::new).m_178330_(ServerboundUseItemPacket.class, ServerboundUseItemPacket::new))),
   STATUS(1, m_129600_().m_129625_(PacketFlow.SERVERBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.status.ServerStatusPacketListener>()).m_178330_(ServerboundStatusRequestPacket.class, ServerboundStatusRequestPacket::new).m_178330_(ServerboundPingRequestPacket.class, ServerboundPingRequestPacket::new)).m_129625_(PacketFlow.CLIENTBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.status.ClientStatusPacketListener>()).m_178330_(ClientboundStatusResponsePacket.class, ClientboundStatusResponsePacket::new).m_178330_(ClientboundPongResponsePacket.class, ClientboundPongResponsePacket::new))),
   LOGIN(2, m_129600_().m_129625_(PacketFlow.CLIENTBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.login.ClientLoginPacketListener>()).m_178330_(ClientboundLoginDisconnectPacket.class, ClientboundLoginDisconnectPacket::new).m_178330_(ClientboundHelloPacket.class, ClientboundHelloPacket::new).m_178330_(ClientboundGameProfilePacket.class, ClientboundGameProfilePacket::new).m_178330_(ClientboundLoginCompressionPacket.class, ClientboundLoginCompressionPacket::new).m_178330_(ClientboundCustomQueryPacket.class, ClientboundCustomQueryPacket::new)).m_129625_(PacketFlow.SERVERBOUND, (new ConnectionProtocol.PacketSet<net.minecraft.network.protocol.login.ServerLoginPacketListener>()).m_178330_(ServerboundHelloPacket.class, ServerboundHelloPacket::new).m_178330_(ServerboundKeyPacket.class, ServerboundKeyPacket::new).m_178330_(ServerboundCustomQueryPacket.class, ServerboundCustomQueryPacket::new)));

   private static final int f_178316_ = -1;
   private static final int f_178317_ = 2;
   private static final ConnectionProtocol[] f_129571_ = new ConnectionProtocol[4];
   private static final Map<Class<? extends Packet<?>>, ConnectionProtocol> f_129572_ = Maps.newHashMap();
   private final int f_129573_;
   private final Map<PacketFlow, ? extends ConnectionProtocol.PacketSet<?>> f_129574_;

   private static ConnectionProtocol.ProtocolBuilder m_129600_() {
      return new ConnectionProtocol.ProtocolBuilder();
   }

   private ConnectionProtocol(int p_129580_, ConnectionProtocol.ProtocolBuilder p_129581_) {
      this.f_129573_ = p_129580_;
      this.f_129574_ = p_129581_.f_129619_;
   }

   @Nullable
   public Integer m_129597_(PacketFlow p_129598_, Packet<?> p_129599_) {
      return this.f_129574_.get(p_129598_).m_129614_(p_129599_.getClass());
   }

   @Nullable
   public Packet<?> m_178321_(PacketFlow p_178322_, int p_178323_, FriendlyByteBuf p_178324_) {
      return this.f_129574_.get(p_178322_).m_178327_(p_178323_, p_178324_);
   }

   public int m_129582_() {
      return this.f_129573_;
   }

   @Nullable
   public static ConnectionProtocol m_129583_(int p_129584_) {
      return p_129584_ >= -1 && p_129584_ <= 2 ? f_129571_[p_129584_ - -1] : null;
   }

   public static ConnectionProtocol m_129592_(Packet<?> p_129593_) {
      return f_129572_.get(p_129593_.getClass());
   }

   static {
      for(ConnectionProtocol connectionprotocol : values()) {
         int i = connectionprotocol.m_129582_();
         if (i < -1 || i > 2) {
            throw new Error("Invalid protocol ID " + i);
         }

         f_129571_[i - -1] = connectionprotocol;
         connectionprotocol.f_129574_.forEach((p_129590_, p_129591_) -> {
            p_129591_.m_129609_().forEach((p_178320_) -> {
               if (f_129572_.containsKey(p_178320_) && f_129572_.get(p_178320_) != connectionprotocol) {
                  throw new IllegalStateException("Packet " + p_178320_ + " is already assigned to protocol " + f_129572_.get(p_178320_) + " - can't reassign to " + connectionprotocol);
               } else {
                  f_129572_.put(p_178320_, connectionprotocol);
               }
            });
         });
      }

   }

   static class PacketSet<T extends PacketListener> {
      private final Object2IntMap<Class<? extends Packet<T>>> f_129604_ = Util.m_137469_(new Object2IntOpenHashMap<>(), (p_129613_) -> {
         p_129613_.defaultReturnValue(-1);
      });
      private final List<Function<FriendlyByteBuf, ? extends Packet<T>>> f_178326_ = Lists.newArrayList();

      public <P extends Packet<T>> ConnectionProtocol.PacketSet<T> m_178330_(Class<P> p_178331_, Function<FriendlyByteBuf, P> p_178332_) {
         int i = this.f_178326_.size();
         int j = this.f_129604_.put(p_178331_, i);
         if (j != -1) {
            String s = "Packet " + p_178331_ + " is already registered to ID " + j;
            LogManager.getLogger().fatal(s);
            throw new IllegalArgumentException(s);
         } else {
            this.f_178326_.add(p_178332_);
            return this;
         }
      }

      @Nullable
      public Integer m_129614_(Class<?> p_129615_) {
         int i = this.f_129604_.getInt(p_129615_);
         return i == -1 ? null : i;
      }

      @Nullable
      public Packet<?> m_178327_(int p_178328_, FriendlyByteBuf p_178329_) {
         Function<FriendlyByteBuf, ? extends Packet<T>> function = this.f_178326_.get(p_178328_);
         return function != null ? function.apply(p_178329_) : null;
      }

      public Iterable<Class<? extends Packet<?>>> m_129609_() {
         return Iterables.unmodifiableIterable(this.f_129604_.keySet());
      }
   }

   static class ProtocolBuilder {
      final Map<PacketFlow, ConnectionProtocol.PacketSet<?>> f_129619_ = Maps.newEnumMap(PacketFlow.class);

      public <T extends PacketListener> ConnectionProtocol.ProtocolBuilder m_129625_(PacketFlow p_129626_, ConnectionProtocol.PacketSet<T> p_129627_) {
         this.f_129619_.put(p_129626_, p_129627_);
         return this;
      }
   }
}
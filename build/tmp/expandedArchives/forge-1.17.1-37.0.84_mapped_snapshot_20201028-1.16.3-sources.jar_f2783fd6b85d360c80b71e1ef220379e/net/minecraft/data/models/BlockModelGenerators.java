package net.minecraft.data.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.level.block.state.properties.WallSide;

public class BlockModelGenerators {
   final Consumer<BlockStateGenerator> f_124477_;
   final BiConsumer<ResourceLocation, Supplier<JsonElement>> f_124478_;
   private final Consumer<Item> f_124479_;
   final List<Block> f_176080_ = ImmutableList.of(Blocks.f_50216_, Blocks.f_50221_, Blocks.f_50376_);
   final Map<Block, BlockModelGenerators.BlockStateGeneratorSupplier> f_176081_ = ImmutableMap.<Block, BlockModelGenerators.BlockStateGeneratorSupplier>builder().put(Blocks.f_50069_, BlockModelGenerators::m_176109_).put(Blocks.f_152550_, BlockModelGenerators::m_176179_).build();
   final Map<Block, TexturedModel> f_176082_ = ImmutableMap.<Block, TexturedModel>builder().put(Blocks.f_50062_, TexturedModel.f_125924_.m_125964_(Blocks.f_50062_)).put(Blocks.f_50394_, TexturedModel.f_125924_.m_125964_(Blocks.f_50394_)).put(Blocks.f_50471_, TexturedModel.m_125949_(TextureMapping.m_125753_(Blocks.f_50062_, "_top"))).put(Blocks.f_50473_, TexturedModel.m_125949_(TextureMapping.m_125753_(Blocks.f_50394_, "_top"))).put(Blocks.f_50064_, TexturedModel.f_125907_.m_125964_(Blocks.f_50062_).m_125940_((p_176223_) -> {
      p_176223_.m_125758_(TextureSlot.f_125875_, TextureMapping.m_125740_(Blocks.f_50064_));
   })).put(Blocks.f_50396_, TexturedModel.f_125907_.m_125964_(Blocks.f_50394_).m_125940_((p_176211_) -> {
      p_176211_.m_125758_(TextureSlot.f_125875_, TextureMapping.m_125740_(Blocks.f_50396_));
   })).put(Blocks.f_50333_, TexturedModel.f_125907_.m_125964_(Blocks.f_50333_)).put(Blocks.f_50472_, TexturedModel.m_125949_(TextureMapping.m_125753_(Blocks.f_50333_, "_bottom"))).put(Blocks.f_50730_, TexturedModel.f_125925_.m_125964_(Blocks.f_50730_)).put(Blocks.f_152550_, TexturedModel.f_125925_.m_125964_(Blocks.f_152550_)).put(Blocks.f_50282_, TexturedModel.f_125907_.m_125964_(Blocks.f_50282_).m_125940_((p_176202_) -> {
      p_176202_.m_125758_(TextureSlot.f_125875_, TextureMapping.m_125740_(Blocks.f_50282_));
   })).put(Blocks.f_50063_, TexturedModel.f_125907_.m_125964_(Blocks.f_50063_).m_125940_((p_176190_) -> {
      p_176190_.m_125758_(TextureSlot.f_125870_, TextureMapping.m_125753_(Blocks.f_50062_, "_top"));
      p_176190_.m_125758_(TextureSlot.f_125875_, TextureMapping.m_125740_(Blocks.f_50063_));
   })).put(Blocks.f_50395_, TexturedModel.f_125907_.m_125964_(Blocks.f_50395_).m_125940_((p_176145_) -> {
      p_176145_.m_125758_(TextureSlot.f_125870_, TextureMapping.m_125753_(Blocks.f_50394_, "_top"));
      p_176145_.m_125758_(TextureSlot.f_125875_, TextureMapping.m_125740_(Blocks.f_50395_));
   })).build();
   static final Map<BlockFamily.Variant, BiConsumer<BlockModelGenerators.BlockFamilyProvider, Block>> f_176083_ = ImmutableMap.<BlockFamily.Variant, BiConsumer<BlockModelGenerators.BlockFamilyProvider, Block>>builder().put(BlockFamily.Variant.BUTTON, BlockModelGenerators.BlockFamilyProvider::m_125035_).put(BlockFamily.Variant.DOOR, BlockModelGenerators.BlockFamilyProvider::m_176273_).put(BlockFamily.Variant.CHISELED, BlockModelGenerators.BlockFamilyProvider::m_176271_).put(BlockFamily.Variant.CRACKED, BlockModelGenerators.BlockFamilyProvider::m_176271_).put(BlockFamily.Variant.FENCE, BlockModelGenerators.BlockFamilyProvider::m_125047_).put(BlockFamily.Variant.FENCE_GATE, BlockModelGenerators.BlockFamilyProvider::m_125049_).put(BlockFamily.Variant.SIGN, BlockModelGenerators.BlockFamilyProvider::m_176269_).put(BlockFamily.Variant.SLAB, BlockModelGenerators.BlockFamilyProvider::m_125053_).put(BlockFamily.Variant.STAIRS, BlockModelGenerators.BlockFamilyProvider::m_125055_).put(BlockFamily.Variant.PRESSURE_PLATE, BlockModelGenerators.BlockFamilyProvider::m_125051_).put(BlockFamily.Variant.TRAPDOOR, BlockModelGenerators.BlockFamilyProvider::m_176275_).put(BlockFamily.Variant.WALL, BlockModelGenerators.BlockFamilyProvider::m_125045_).build();
   public static final Map<BooleanProperty, Function<ResourceLocation, Variant>> f_176079_ = Util.m_137469_(Maps.newHashMap(), (p_176141_) -> {
      p_176141_.put(BlockStateProperties.f_61368_, (p_176234_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176234_);
      });
      p_176141_.put(BlockStateProperties.f_61369_, (p_176229_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176229_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true);
      });
      p_176141_.put(BlockStateProperties.f_61370_, (p_176225_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176225_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true);
      });
      p_176141_.put(BlockStateProperties.f_61371_, (p_176213_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176213_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true);
      });
      p_176141_.put(BlockStateProperties.f_61366_, (p_176204_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176204_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true);
      });
      p_176141_.put(BlockStateProperties.f_61367_, (p_176195_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176195_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true);
      });
   });

   private static BlockStateGenerator m_176109_(Block p_176110_, ResourceLocation p_176111_, TextureMapping p_176112_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_176113_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125693_.m_125592_(p_176110_, p_176112_, p_176113_);
      return m_124862_(p_176110_, p_176111_, resourcelocation);
   }

   private static BlockStateGenerator m_176179_(Block p_176180_, ResourceLocation p_176181_, TextureMapping p_176182_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_176183_) {
      ResourceLocation resourcelocation = ModelTemplates.f_176473_.m_125592_(p_176180_, p_176182_, p_176183_);
      return m_124862_(p_176180_, p_176181_, resourcelocation).m_125271_(m_124875_());
   }

   public BlockModelGenerators(Consumer<BlockStateGenerator> p_124481_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_124482_, Consumer<Item> p_124483_) {
      this.f_124477_ = p_124481_;
      this.f_124478_ = p_124482_;
      this.f_124479_ = p_124483_;
   }

   void m_124524_(Block p_124525_) {
      this.f_124479_.accept(p_124525_.m_5456_());
   }

   void m_124797_(Block p_124798_, ResourceLocation p_124799_) {
      this.f_124478_.accept(ModelLocationUtils.m_125571_(p_124798_.m_5456_()), new DelegatedModel(p_124799_));
   }

   private void m_124519_(Item p_124520_, ResourceLocation p_124521_) {
      this.f_124478_.accept(ModelLocationUtils.m_125571_(p_124520_), new DelegatedModel(p_124521_));
   }

   void m_124517_(Item p_124518_) {
      ModelTemplates.f_125658_.m_125612_(ModelLocationUtils.m_125571_(p_124518_), TextureMapping.m_125766_(p_124518_), this.f_124478_);
   }

   private void m_124728_(Block p_124729_) {
      Item item = p_124729_.m_5456_();
      if (item != Items.f_41852_) {
         ModelTemplates.f_125658_.m_125612_(ModelLocationUtils.m_125571_(item), TextureMapping.m_125738_(p_124729_), this.f_124478_);
      }

   }

   private void m_124575_(Block p_124576_, String p_124577_) {
      Item item = p_124576_.m_5456_();
      ModelTemplates.f_125658_.m_125612_(ModelLocationUtils.m_125571_(item), TextureMapping.m_125820_(TextureMapping.m_125753_(p_124576_, p_124577_)), this.f_124478_);
   }

   private static PropertyDispatch m_124727_() {
      return PropertyDispatch.m_125294_(BlockStateProperties.f_61374_).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125329_(Direction.NORTH, Variant.m_125501_());
   }

   private static PropertyDispatch m_124785_() {
      return PropertyDispatch.m_125294_(BlockStateProperties.f_61374_).m_125329_(Direction.SOUTH, Variant.m_125501_()).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270));
   }

   private static PropertyDispatch m_124822_() {
      return PropertyDispatch.m_125294_(BlockStateProperties.f_61374_).m_125329_(Direction.EAST, Variant.m_125501_()).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270));
   }

   private static PropertyDispatch m_124850_() {
      return PropertyDispatch.m_125294_(BlockStateProperties.f_61372_).m_125329_(Direction.DOWN, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90)).m_125329_(Direction.UP, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270)).m_125329_(Direction.NORTH, Variant.m_125501_()).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90));
   }

   private static MultiVariantGenerator m_124831_(Block p_124832_, ResourceLocation p_124833_) {
      return MultiVariantGenerator.m_125259_(p_124832_, m_124688_(p_124833_));
   }

   private static Variant[] m_124688_(ResourceLocation p_124689_) {
      return new Variant[]{Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124689_), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124689_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124689_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124689_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)};
   }

   private static MultiVariantGenerator m_124862_(Block p_124863_, ResourceLocation p_124864_, ResourceLocation p_124865_) {
      return MultiVariantGenerator.m_125259_(p_124863_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124864_), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124865_), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124864_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124865_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180));
   }

   private static PropertyDispatch m_124622_(BooleanProperty p_124623_, ResourceLocation p_124624_, ResourceLocation p_124625_) {
      return PropertyDispatch.m_125294_(p_124623_).m_125329_(true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124624_)).m_125329_(false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124625_));
   }

   private void m_124786_(Block p_124787_) {
      ResourceLocation resourcelocation = TexturedModel.f_125905_.m_125956_(p_124787_, this.f_124478_);
      ResourceLocation resourcelocation1 = TexturedModel.f_125906_.m_125956_(p_124787_, this.f_124478_);
      this.f_124477_.accept(m_124862_(p_124787_, resourcelocation, resourcelocation1));
   }

   private void m_124823_(Block p_124824_) {
      ResourceLocation resourcelocation = TexturedModel.f_125905_.m_125956_(p_124824_, this.f_124478_);
      this.f_124477_.accept(m_124831_(p_124824_, resourcelocation));
   }

   static BlockStateGenerator m_124884_(Block p_124885_, ResourceLocation p_124886_, ResourceLocation p_124887_) {
      return MultiVariantGenerator.m_125254_(p_124885_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61448_).m_125329_(false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124886_)).m_125329_(true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124887_))).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61376_, BlockStateProperties.f_61374_).m_125350_(AttachFace.FLOOR, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.FLOOR, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(AttachFace.FLOOR, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.FLOOR, Direction.NORTH, Variant.m_125501_()).m_125350_(AttachFace.WALL, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125350_(AttachFace.WALL, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125350_(AttachFace.WALL, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125350_(AttachFace.WALL, Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125350_(AttachFace.CEILING, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)));
   }

   private static PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> m_124650_(PropertyDispatch.C4<Direction, DoubleBlockHalf, DoorHingeSide, Boolean> p_124651_, DoubleBlockHalf p_124652_, ResourceLocation p_124653_, ResourceLocation p_124654_) {
      return p_124651_.m_125429_(Direction.EAST, p_124652_, DoorHingeSide.LEFT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_)).m_125429_(Direction.SOUTH, p_124652_, DoorHingeSide.LEFT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125429_(Direction.WEST, p_124652_, DoorHingeSide.LEFT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125429_(Direction.NORTH, p_124652_, DoorHingeSide.LEFT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125429_(Direction.EAST, p_124652_, DoorHingeSide.RIGHT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_)).m_125429_(Direction.SOUTH, p_124652_, DoorHingeSide.RIGHT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125429_(Direction.WEST, p_124652_, DoorHingeSide.RIGHT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125429_(Direction.NORTH, p_124652_, DoorHingeSide.RIGHT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125429_(Direction.EAST, p_124652_, DoorHingeSide.LEFT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125429_(Direction.SOUTH, p_124652_, DoorHingeSide.LEFT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125429_(Direction.WEST, p_124652_, DoorHingeSide.LEFT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125429_(Direction.NORTH, p_124652_, DoorHingeSide.LEFT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124654_)).m_125429_(Direction.EAST, p_124652_, DoorHingeSide.RIGHT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125429_(Direction.SOUTH, p_124652_, DoorHingeSide.RIGHT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_)).m_125429_(Direction.WEST, p_124652_, DoorHingeSide.RIGHT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125429_(Direction.NORTH, p_124652_, DoorHingeSide.RIGHT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124653_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180));
   }

   private static BlockStateGenerator m_124759_(Block p_124760_, ResourceLocation p_124761_, ResourceLocation p_124762_, ResourceLocation p_124763_, ResourceLocation p_124764_) {
      return MultiVariantGenerator.m_125254_(p_124760_).m_125271_(m_124650_(m_124650_(PropertyDispatch.m_125303_(BlockStateProperties.f_61374_, BlockStateProperties.f_61401_, BlockStateProperties.f_61394_, BlockStateProperties.f_61446_), DoubleBlockHalf.LOWER, p_124761_, p_124762_), DoubleBlockHalf.UPPER, p_124763_, p_124764_));
   }

   static BlockStateGenerator m_124904_(Block p_124905_, ResourceLocation p_124906_, ResourceLocation p_124907_) {
      return MultiPartGenerator.m_125204_(p_124905_).m_125218_(Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124906_)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124907_).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124907_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124907_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124907_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true));
   }

   static BlockStateGenerator m_124838_(Block p_124839_, ResourceLocation p_124840_, ResourceLocation p_124841_, ResourceLocation p_124842_) {
      return MultiPartGenerator.m_125204_(p_124839_).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61366_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124840_)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61379_, WallSide.LOW), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124841_).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61378_, WallSide.LOW), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124841_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61380_, WallSide.LOW), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124841_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61381_, WallSide.LOW), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124841_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61379_, WallSide.TALL), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124842_).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61378_, WallSide.TALL), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124842_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61380_, WallSide.TALL), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124842_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61381_, WallSide.TALL), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124842_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true));
   }

   static BlockStateGenerator m_124809_(Block p_124810_, ResourceLocation p_124811_, ResourceLocation p_124812_, ResourceLocation p_124813_, ResourceLocation p_124814_) {
      return MultiVariantGenerator.m_125256_(p_124810_, Variant.m_125501_().m_125511_(VariantProperties.f_125521_, true)).m_125271_(m_124785_()).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61442_, BlockStateProperties.f_61446_).m_125350_(false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124812_)).m_125350_(true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124814_)).m_125350_(false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124811_)).m_125350_(true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124813_)));
   }

   static BlockStateGenerator m_124866_(Block p_124867_, ResourceLocation p_124868_, ResourceLocation p_124869_, ResourceLocation p_124870_) {
      return MultiVariantGenerator.m_125254_(p_124867_).m_125271_(PropertyDispatch.m_125299_(BlockStateProperties.f_61374_, BlockStateProperties.f_61402_, BlockStateProperties.f_61398_).m_125391_(Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_)).m_125391_(Direction.WEST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.BOTTOM, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.BOTTOM, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_)).m_125391_(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_)).m_125391_(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_)).m_125391_(Direction.WEST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_)).m_125391_(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.TOP, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.TOP, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.TOP, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.TOP, StairsShape.STRAIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124869_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.TOP, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.TOP, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.TOP, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.TOP, StairsShape.OUTER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.TOP, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.TOP, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.TOP, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.TOP, StairsShape.OUTER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124870_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.TOP, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.TOP, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.TOP, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.TOP, StairsShape.INNER_RIGHT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.EAST, Half.TOP, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.WEST, Half.TOP, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.SOUTH, Half.TOP, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125391_(Direction.NORTH, Half.TOP, StairsShape.INNER_LEFT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124868_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)));
   }

   private static BlockStateGenerator m_124888_(Block p_124889_, ResourceLocation p_124890_, ResourceLocation p_124891_, ResourceLocation p_124892_) {
      return MultiVariantGenerator.m_125254_(p_124889_).m_125271_(PropertyDispatch.m_125299_(BlockStateProperties.f_61374_, BlockStateProperties.f_61402_, BlockStateProperties.f_61446_).m_125391_(Direction.NORTH, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124891_)).m_125391_(Direction.SOUTH, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124891_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125391_(Direction.EAST, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124891_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125391_(Direction.WEST, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124891_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125391_(Direction.NORTH, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124890_)).m_125391_(Direction.SOUTH, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124890_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125391_(Direction.EAST, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124890_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125391_(Direction.WEST, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124890_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125391_(Direction.NORTH, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_)).m_125391_(Direction.SOUTH, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125391_(Direction.EAST, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125391_(Direction.WEST, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125391_(Direction.NORTH, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125391_(Direction.SOUTH, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R0)).m_125391_(Direction.EAST, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125391_(Direction.WEST, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124892_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)));
   }

   private static BlockStateGenerator m_124908_(Block p_124909_, ResourceLocation p_124910_, ResourceLocation p_124911_, ResourceLocation p_124912_) {
      return MultiVariantGenerator.m_125254_(p_124909_).m_125271_(PropertyDispatch.m_125299_(BlockStateProperties.f_61374_, BlockStateProperties.f_61402_, BlockStateProperties.f_61446_).m_125391_(Direction.NORTH, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124911_)).m_125391_(Direction.SOUTH, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124911_)).m_125391_(Direction.EAST, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124911_)).m_125391_(Direction.WEST, Half.BOTTOM, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124911_)).m_125391_(Direction.NORTH, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124910_)).m_125391_(Direction.SOUTH, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124910_)).m_125391_(Direction.EAST, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124910_)).m_125391_(Direction.WEST, Half.TOP, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124910_)).m_125391_(Direction.NORTH, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_)).m_125391_(Direction.SOUTH, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125391_(Direction.EAST, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125391_(Direction.WEST, Half.BOTTOM, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125391_(Direction.NORTH, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_)).m_125391_(Direction.SOUTH, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125391_(Direction.EAST, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125391_(Direction.WEST, Half.TOP, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124912_).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)));
   }

   static MultiVariantGenerator m_124859_(Block p_124860_, ResourceLocation p_124861_) {
      return MultiVariantGenerator.m_125256_(p_124860_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124861_));
   }

   private static PropertyDispatch m_124875_() {
      return PropertyDispatch.m_125294_(BlockStateProperties.f_61365_).m_125329_(Direction.Axis.Y, Variant.m_125501_()).m_125329_(Direction.Axis.Z, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90)).m_125329_(Direction.Axis.X, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90));
   }

   static BlockStateGenerator m_124881_(Block p_124882_, ResourceLocation p_124883_) {
      return MultiVariantGenerator.m_125256_(p_124882_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124883_)).m_125271_(m_124875_());
   }

   private void m_124901_(Block p_124902_, ResourceLocation p_124903_) {
      this.f_124477_.accept(m_124881_(p_124902_, p_124903_));
   }

   private void m_124586_(Block p_124587_, TexturedModel.Provider p_124588_) {
      ResourceLocation resourcelocation = p_124588_.m_125956_(p_124587_, this.f_124478_);
      this.f_124477_.accept(m_124881_(p_124587_, resourcelocation));
   }

   private void m_124744_(Block p_124745_, TexturedModel.Provider p_124746_) {
      ResourceLocation resourcelocation = p_124746_.m_125956_(p_124745_, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124745_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125271_(m_124727_()));
   }

   static BlockStateGenerator m_124924_(Block p_124925_, ResourceLocation p_124926_, ResourceLocation p_124927_) {
      return MultiVariantGenerator.m_125254_(p_124925_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61365_).m_125329_(Direction.Axis.Y, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124926_)).m_125329_(Direction.Axis.Z, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124927_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90)).m_125329_(Direction.Axis.X, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124927_).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)));
   }

   private void m_124589_(Block p_124590_, TexturedModel.Provider p_124591_, TexturedModel.Provider p_124592_) {
      ResourceLocation resourcelocation = p_124591_.m_125956_(p_124590_, this.f_124478_);
      ResourceLocation resourcelocation1 = p_124592_.m_125956_(p_124590_, this.f_124478_);
      this.f_124477_.accept(m_124924_(p_124590_, resourcelocation, resourcelocation1));
   }

   private ResourceLocation m_124578_(Block p_124579_, String p_124580_, ModelTemplate p_124581_, Function<ResourceLocation, TextureMapping> p_124582_) {
      return p_124581_.m_125596_(p_124579_, p_124580_, p_124582_.apply(TextureMapping.m_125753_(p_124579_, p_124580_)), this.f_124478_);
   }

   static BlockStateGenerator m_124941_(Block p_124942_, ResourceLocation p_124943_, ResourceLocation p_124944_) {
      return MultiVariantGenerator.m_125254_(p_124942_).m_125271_(m_124622_(BlockStateProperties.f_61448_, p_124944_, p_124943_));
   }

   static BlockStateGenerator m_124928_(Block p_124929_, ResourceLocation p_124930_, ResourceLocation p_124931_, ResourceLocation p_124932_) {
      return MultiVariantGenerator.m_125254_(p_124929_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61397_).m_125329_(SlabType.BOTTOM, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124930_)).m_125329_(SlabType.TOP, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124931_)).m_125329_(SlabType.DOUBLE, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124932_)));
   }

   private void m_124851_(Block p_124852_) {
      this.m_124794_(p_124852_, TexturedModel.f_125905_);
   }

   private void m_124794_(Block p_124795_, TexturedModel.Provider p_124796_) {
      this.f_124477_.accept(m_124859_(p_124795_, p_124796_.m_125956_(p_124795_, this.f_124478_)));
   }

   private void m_124567_(Block p_124568_, TextureMapping p_124569_, ModelTemplate p_124570_) {
      ResourceLocation resourcelocation = p_124570_.m_125592_(p_124568_, p_124569_, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124568_, resourcelocation));
   }

   private BlockModelGenerators.BlockFamilyProvider m_124876_(Block p_124877_) {
      TexturedModel texturedmodel = this.f_176082_.getOrDefault(p_124877_, TexturedModel.f_125905_.m_125964_(p_124877_));
      return (new BlockModelGenerators.BlockFamilyProvider(texturedmodel.m_125951_())).m_125040_(p_124877_, texturedmodel.m_125932_());
   }

   void m_124896_(Block p_124897_) {
      TextureMapping texturemapping = TextureMapping.m_125832_(p_124897_);
      ResourceLocation resourcelocation = ModelTemplates.f_125704_.m_125592_(p_124897_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125705_.m_125592_(p_124897_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125706_.m_125592_(p_124897_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125707_.m_125592_(p_124897_, texturemapping, this.f_124478_);
      this.m_124517_(p_124897_.m_5456_());
      this.f_124477_.accept(m_124759_(p_124897_, resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3));
   }

   void m_124916_(Block p_124917_) {
      TextureMapping texturemapping = TextureMapping.m_125768_(p_124917_);
      ResourceLocation resourcelocation = ModelTemplates.f_125636_.m_125592_(p_124917_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125637_.m_125592_(p_124917_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125638_.m_125592_(p_124917_, texturemapping, this.f_124478_);
      this.f_124477_.accept(m_124888_(p_124917_, resourcelocation, resourcelocation1, resourcelocation2));
      this.m_124797_(p_124917_, resourcelocation1);
   }

   void m_124936_(Block p_124937_) {
      TextureMapping texturemapping = TextureMapping.m_125768_(p_124937_);
      ResourceLocation resourcelocation = ModelTemplates.f_125633_.m_125592_(p_124937_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125634_.m_125592_(p_124937_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125635_.m_125592_(p_124937_, texturemapping, this.f_124478_);
      this.f_124477_.accept(m_124908_(p_124937_, resourcelocation, resourcelocation1, resourcelocation2));
      this.m_124797_(p_124937_, resourcelocation1);
   }

   private void m_176230_() {
      this.m_124524_(Blocks.f_152545_);
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_152545_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_152545_, "_partial_tilt");
      ResourceLocation resourcelocation2 = ModelLocationUtils.m_125578_(Blocks.f_152545_, "_full_tilt");
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152545_).m_125271_(m_124727_()).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_155996_).m_125329_(Tilt.NONE, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125329_(Tilt.UNSTABLE, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125329_(Tilt.PARTIAL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125329_(Tilt.FULL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2))));
   }

   private BlockModelGenerators.WoodProvider m_124948_(Block p_124949_) {
      return new BlockModelGenerators.WoodProvider(TextureMapping.m_125824_(p_124949_));
   }

   private void m_124960_(Block p_124961_) {
      this.m_124533_(p_124961_, p_124961_);
   }

   private void m_124533_(Block p_124534_, Block p_124535_) {
      this.f_124477_.accept(m_124859_(p_124534_, ModelLocationUtils.m_125576_(p_124535_)));
   }

   private void m_124557_(Block p_124558_, BlockModelGenerators.TintState p_124559_) {
      this.m_124728_(p_124558_);
      this.m_124737_(p_124558_, p_124559_);
   }

   private void m_124560_(Block p_124561_, BlockModelGenerators.TintState p_124562_, TextureMapping p_124563_) {
      this.m_124728_(p_124561_);
      this.m_124740_(p_124561_, p_124562_, p_124563_);
   }

   private void m_124737_(Block p_124738_, BlockModelGenerators.TintState p_124739_) {
      TextureMapping texturemapping = TextureMapping.m_125780_(p_124738_);
      this.m_124740_(p_124738_, p_124739_, texturemapping);
   }

   private void m_124740_(Block p_124741_, BlockModelGenerators.TintState p_124742_, TextureMapping p_124743_) {
      ResourceLocation resourcelocation = p_124742_.m_125064_().m_125592_(p_124741_, p_124743_, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124741_, resourcelocation));
   }

   private void m_124545_(Block p_124546_, Block p_124547_, BlockModelGenerators.TintState p_124548_) {
      this.m_124557_(p_124546_, p_124548_);
      TextureMapping texturemapping = TextureMapping.m_125790_(p_124546_);
      ResourceLocation resourcelocation = p_124548_.m_125065_().m_125592_(p_124547_, texturemapping, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124547_, resourcelocation));
   }

   private void m_124730_(Block p_124731_, Block p_124732_) {
      TexturedModel texturedmodel = TexturedModel.f_125915_.m_125964_(p_124731_);
      ResourceLocation resourcelocation = texturedmodel.m_125937_(p_124731_, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124731_, resourcelocation));
      ResourceLocation resourcelocation1 = ModelTemplates.f_125667_.m_125592_(p_124732_, texturedmodel.m_125951_(), this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124732_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125271_(m_124727_()));
      this.m_124728_(p_124731_);
   }

   private void m_124788_(Block p_124789_, Block p_124790_) {
      this.m_124517_(p_124789_.m_5456_());
      TextureMapping texturemapping = TextureMapping.m_125806_(p_124789_);
      TextureMapping texturemapping1 = TextureMapping.m_125750_(p_124789_, p_124790_);
      ResourceLocation resourcelocation = ModelTemplates.f_125679_.m_125592_(p_124790_, texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124790_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61374_).m_125329_(Direction.WEST, Variant.m_125501_()).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125329_(Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180))));
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124789_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61409_).m_125335_((p_176108_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125678_[p_176108_].m_125592_(p_124789_, texturemapping, this.f_124478_));
      })));
   }

   private void m_124536_(Block p_124537_, Block p_124538_, Block p_124539_, Block p_124540_, Block p_124541_, Block p_124542_, Block p_124543_, Block p_124544_) {
      this.m_124557_(p_124537_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124557_(p_124538_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124851_(p_124539_);
      this.m_124851_(p_124540_);
      this.m_124730_(p_124541_, p_124543_);
      this.m_124730_(p_124542_, p_124544_);
   }

   private void m_124791_(Block p_124792_, BlockModelGenerators.TintState p_124793_) {
      this.m_124575_(p_124792_, "_top");
      ResourceLocation resourcelocation = this.m_124578_(p_124792_, "_top", p_124793_.m_125064_(), TextureMapping::m_125788_);
      ResourceLocation resourcelocation1 = this.m_124578_(p_124792_, "_bottom", p_124793_.m_125064_(), TextureMapping::m_125788_);
      this.m_124953_(p_124792_, resourcelocation, resourcelocation1);
   }

   private void m_124895_() {
      this.m_124575_(Blocks.f_50355_, "_front");
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_50355_, "_top");
      ResourceLocation resourcelocation1 = this.m_124578_(Blocks.f_50355_, "_bottom", BlockModelGenerators.TintState.NOT_TINTED.m_125064_(), TextureMapping::m_125788_);
      this.m_124953_(Blocks.f_50355_, resourcelocation, resourcelocation1);
   }

   private void m_124915_() {
      ResourceLocation resourcelocation = this.m_124578_(Blocks.f_50038_, "_top", ModelTemplates.f_125652_, TextureMapping::m_125761_);
      ResourceLocation resourcelocation1 = this.m_124578_(Blocks.f_50038_, "_bottom", ModelTemplates.f_125652_, TextureMapping::m_125761_);
      this.m_124953_(Blocks.f_50038_, resourcelocation, resourcelocation1);
   }

   private void m_176241_() {
      this.m_124524_(Blocks.f_152547_);
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_152547_, "_top");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_152547_, "_bottom");
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152547_).m_125271_(m_124727_()).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61401_).m_125329_(DoubleBlockHalf.LOWER, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125329_(DoubleBlockHalf.UPPER, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation))));
   }

   private void m_124953_(Block p_124954_, ResourceLocation p_124955_, ResourceLocation p_124956_) {
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124954_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61401_).m_125329_(DoubleBlockHalf.LOWER, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124956_)).m_125329_(DoubleBlockHalf.UPPER, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124955_))));
   }

   private void m_124968_(Block p_124969_) {
      TextureMapping texturemapping = TextureMapping.m_125800_(p_124969_);
      TextureMapping texturemapping1 = TextureMapping.m_125802_(TextureMapping.m_125753_(p_124969_, "_corner"));
      ResourceLocation resourcelocation = ModelTemplates.f_125643_.m_125592_(p_124969_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125644_.m_125592_(p_124969_, texturemapping1, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125645_.m_125592_(p_124969_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125646_.m_125592_(p_124969_, texturemapping, this.f_124478_);
      this.m_124728_(p_124969_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124969_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61403_).m_125329_(RailShape.NORTH_SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125329_(RailShape.EAST_WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(RailShape.ASCENDING_EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(RailShape.ASCENDING_WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(RailShape.ASCENDING_NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2)).m_125329_(RailShape.ASCENDING_SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3)).m_125329_(RailShape.SOUTH_EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125329_(RailShape.SOUTH_WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(RailShape.NORTH_WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(RailShape.NORTH_EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270))));
   }

   private void m_124974_(Block p_124975_) {
      ResourceLocation resourcelocation = this.m_124578_(p_124975_, "", ModelTemplates.f_125643_, TextureMapping::m_125802_);
      ResourceLocation resourcelocation1 = this.m_124578_(p_124975_, "", ModelTemplates.f_125645_, TextureMapping::m_125802_);
      ResourceLocation resourcelocation2 = this.m_124578_(p_124975_, "", ModelTemplates.f_125646_, TextureMapping::m_125802_);
      ResourceLocation resourcelocation3 = this.m_124578_(p_124975_, "_on", ModelTemplates.f_125643_, TextureMapping::m_125802_);
      ResourceLocation resourcelocation4 = this.m_124578_(p_124975_, "_on", ModelTemplates.f_125645_, TextureMapping::m_125802_);
      ResourceLocation resourcelocation5 = this.m_124578_(p_124975_, "_on", ModelTemplates.f_125646_, TextureMapping::m_125802_);
      PropertyDispatch propertydispatch = PropertyDispatch.m_125296_(BlockStateProperties.f_61448_, BlockStateProperties.f_61404_).m_125362_((p_176166_, p_176167_) -> {
         switch(p_176167_) {
         case NORTH_SOUTH:
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176166_ ? resourcelocation3 : resourcelocation);
         case EAST_WEST:
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176166_ ? resourcelocation3 : resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
         case ASCENDING_EAST:
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176166_ ? resourcelocation4 : resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
         case ASCENDING_WEST:
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176166_ ? resourcelocation5 : resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
         case ASCENDING_NORTH:
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176166_ ? resourcelocation4 : resourcelocation1);
         case ASCENDING_SOUTH:
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176166_ ? resourcelocation5 : resourcelocation2);
         default:
            throw new UnsupportedOperationException("Fix you generator!");
         }
      });
      this.m_124728_(p_124975_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124975_).m_125271_(propertydispatch));
   }

   private BlockModelGenerators.BlockEntityModelGenerator m_124690_(ResourceLocation p_124691_, Block p_124692_) {
      return new BlockModelGenerators.BlockEntityModelGenerator(p_124691_, p_124692_);
   }

   private BlockModelGenerators.BlockEntityModelGenerator m_124825_(Block p_124826_, Block p_124827_) {
      return new BlockModelGenerators.BlockEntityModelGenerator(ModelLocationUtils.m_125576_(p_124826_), p_124827_);
   }

   private void m_124530_(Block p_124531_, Item p_124532_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125626_.m_125592_(p_124531_, TextureMapping.m_125743_(p_124532_), this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124531_, resourcelocation));
   }

   private void m_124921_(Block p_124922_, ResourceLocation p_124923_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125626_.m_125592_(p_124922_, TextureMapping.m_125812_(p_124923_), this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124922_, resourcelocation));
   }

   private void m_176217_(Block p_176218_, Block p_176219_) {
      this.m_124851_(p_176218_);
      ResourceLocation resourcelocation = TexturedModel.f_125913_.m_125964_(p_176218_).m_125937_(p_176219_, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_176219_, resourcelocation));
   }

   private void m_124685_(TexturedModel.Provider p_124686_, Block... p_124687_) {
      for(Block block : p_124687_) {
         ResourceLocation resourcelocation = p_124686_.m_125956_(block, this.f_124478_);
         this.f_124477_.accept(m_124831_(block, resourcelocation));
      }

   }

   private void m_124777_(TexturedModel.Provider p_124778_, Block... p_124779_) {
      for(Block block : p_124779_) {
         ResourceLocation resourcelocation = p_124778_.m_125956_(block, this.f_124478_);
         this.f_124477_.accept(MultiVariantGenerator.m_125256_(block, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125271_(m_124785_()));
      }

   }

   private void m_124878_(Block p_124879_, Block p_124880_) {
      this.m_124851_(p_124879_);
      TextureMapping texturemapping = TextureMapping.m_125770_(p_124879_, p_124880_);
      ResourceLocation resourcelocation = ModelTemplates.f_125673_.m_125592_(p_124880_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125674_.m_125592_(p_124880_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125675_.m_125592_(p_124880_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125671_.m_125592_(p_124880_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation4 = ModelTemplates.f_125672_.m_125592_(p_124880_, texturemapping, this.f_124478_);
      Item item = p_124880_.m_5456_();
      ModelTemplates.f_125658_.m_125612_(ModelLocationUtils.m_125571_(item), TextureMapping.m_125738_(p_124879_), this.f_124478_);
      this.f_124477_.accept(MultiPartGenerator.m_125204_(p_124880_).m_125218_(Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)));
   }

   private void m_124977_(Block p_124978_) {
      TextureMapping texturemapping = TextureMapping.m_125844_(p_124978_);
      ResourceLocation resourcelocation = ModelTemplates.f_125676_.m_125592_(p_124978_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = this.m_124578_(p_124978_, "_conditional", ModelTemplates.f_125676_, (p_176193_) -> {
         return texturemapping.m_125785_(TextureSlot.f_125875_, p_176193_);
      });
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124978_).m_125271_(m_124622_(BlockStateProperties.f_61428_, resourcelocation1, resourcelocation)).m_125271_(m_124850_()));
   }

   private void m_124980_(Block p_124981_) {
      ResourceLocation resourcelocation = TexturedModel.f_125917_.m_125956_(p_124981_, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124981_, resourcelocation).m_125271_(m_124785_()));
   }

   private List<Variant> m_124511_(int p_124512_) {
      String s = "_age" + p_124512_;
      return IntStream.range(1, 5).mapToObj((p_176139_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50571_, p_176139_ + s));
      }).collect(Collectors.toList());
   }

   private void m_124935_() {
      this.m_124524_(Blocks.f_50571_);
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50571_).m_125212_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61405_, 0), this.m_124511_(0)).m_125212_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61405_, 1), this.m_124511_(1)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61400_, BambooLeaves.SMALL), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50571_, "_small_leaves"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61400_, BambooLeaves.LARGE), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50571_, "_large_leaves"))));
   }

   private PropertyDispatch m_124947_() {
      return PropertyDispatch.m_125294_(BlockStateProperties.f_61372_).m_125329_(Direction.DOWN, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125329_(Direction.UP, Variant.m_125501_()).m_125329_(Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90)).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90));
   }

   private void m_124959_() {
      ResourceLocation resourcelocation = TextureMapping.m_125753_(Blocks.f_50618_, "_top_open");
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50618_).m_125271_(this.m_124947_()).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61446_).m_125329_(false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TexturedModel.f_125909_.m_125956_(Blocks.f_50618_, this.f_124478_))).m_125329_(true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TexturedModel.f_125909_.m_125964_(Blocks.f_50618_).m_125940_((p_176216_) -> {
         p_176216_.m_125758_(TextureSlot.f_125872_, resourcelocation);
      }).m_125933_(Blocks.f_50618_, "_open", this.f_124478_)))));
   }

   private static <T extends Comparable<T>> PropertyDispatch m_124626_(Property<T> p_124627_, T p_124628_, ResourceLocation p_124629_, ResourceLocation p_124630_) {
      Variant variant = Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124629_);
      Variant variant1 = Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_124630_);
      return PropertyDispatch.m_125294_(p_124627_).m_125335_((p_176130_) -> {
         boolean flag = p_176130_.compareTo(p_124628_) >= 0;
         return flag ? variant : variant1;
      });
   }

   private void m_124583_(Block p_124584_, Function<Block, TextureMapping> p_124585_) {
      TextureMapping texturemapping = p_124585_.apply(p_124584_).m_125773_(TextureSlot.f_125875_, TextureSlot.f_125869_);
      TextureMapping texturemapping1 = texturemapping.m_125785_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_124584_, "_front_honey"));
      ResourceLocation resourcelocation = ModelTemplates.f_125699_.m_125592_(p_124584_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125699_.m_125596_(p_124584_, "_honey", texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124584_).m_125271_(m_124727_()).m_125271_(m_124626_(BlockStateProperties.f_61421_, 5, resourcelocation1, resourcelocation)));
   }

   private void m_124553_(Block p_124554_, Property<Integer> p_124555_, int... p_124556_) {
      if (p_124555_.m_6908_().size() != p_124556_.length) {
         throw new IllegalArgumentException();
      } else {
         Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
         PropertyDispatch propertydispatch = PropertyDispatch.m_125294_(p_124555_).m_125335_((p_176172_) -> {
            int i = p_124556_[p_176172_];
            ResourceLocation resourcelocation = int2objectmap.computeIfAbsent(i, (p_176098_) -> {
               return this.m_124578_(p_124554_, "_stage" + i, ModelTemplates.f_125680_, TextureMapping::m_125808_);
            });
            return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation);
         });
         this.m_124517_(p_124554_.m_5456_());
         this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124554_).m_125271_(propertydispatch));
      }
   }

   private void m_124967_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_50680_, "_floor");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50680_, "_ceiling");
      ResourceLocation resourcelocation2 = ModelLocationUtils.m_125578_(Blocks.f_50680_, "_wall");
      ResourceLocation resourcelocation3 = ModelLocationUtils.m_125578_(Blocks.f_50680_, "_between_walls");
      this.m_124517_(Items.f_42777_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50680_).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61374_, BlockStateProperties.f_61377_).m_125350_(Direction.NORTH, BellAttachType.FLOOR, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125350_(Direction.SOUTH, BellAttachType.FLOOR, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(Direction.EAST, BellAttachType.FLOOR, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(Direction.WEST, BellAttachType.FLOOR, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(Direction.NORTH, BellAttachType.CEILING, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125350_(Direction.SOUTH, BellAttachType.CEILING, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(Direction.EAST, BellAttachType.CEILING, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(Direction.WEST, BellAttachType.CEILING, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(Direction.NORTH, BellAttachType.SINGLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(Direction.SOUTH, BellAttachType.SINGLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(Direction.EAST, BellAttachType.SINGLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2)).m_125350_(Direction.WEST, BellAttachType.SINGLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(Direction.SOUTH, BellAttachType.DOUBLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(Direction.NORTH, BellAttachType.DOUBLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(Direction.EAST, BellAttachType.DOUBLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3)).m_125350_(Direction.WEST, BellAttachType.DOUBLE_WALL, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180))));
   }

   private void m_124973_() {
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(Blocks.f_50623_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(Blocks.f_50623_))).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61376_, BlockStateProperties.f_61374_).m_125350_(AttachFace.FLOOR, Direction.NORTH, Variant.m_125501_()).m_125350_(AttachFace.FLOOR, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.FLOOR, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.FLOOR, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(AttachFace.WALL, Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.WALL, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.WALL, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.WALL, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(AttachFace.CEILING, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.CEILING, Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270))));
   }

   private void m_124856_(Block p_124857_, TexturedModel.Provider p_124858_) {
      ResourceLocation resourcelocation = p_124858_.m_125956_(p_124857_, this.f_124478_);
      ResourceLocation resourcelocation1 = TextureMapping.m_125753_(p_124857_, "_front_on");
      ResourceLocation resourcelocation2 = p_124858_.m_125964_(p_124857_).m_125940_((p_176207_) -> {
         p_176207_.m_125758_(TextureSlot.f_125873_, resourcelocation1);
      }).m_125933_(p_124857_, "_on", this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124857_).m_125271_(m_124622_(BlockStateProperties.f_61443_, resourcelocation2, resourcelocation)).m_125271_(m_124727_()));
   }

   private void m_124713_(Block... p_124714_) {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125581_("campfire_off");

      for(Block block : p_124714_) {
         ResourceLocation resourcelocation1 = ModelTemplates.f_125687_.m_125592_(block, TextureMapping.m_125736_(block), this.f_124478_);
         this.m_124517_(block.m_5456_());
         this.f_124477_.accept(MultiVariantGenerator.m_125254_(block).m_125271_(m_124622_(BlockStateProperties.f_61443_, resourcelocation1, resourcelocation)).m_125271_(m_124785_()));
      }

   }

   private void m_176247_(Block p_176248_) {
      ResourceLocation resourcelocation = ModelTemplates.f_176466_.m_125592_(p_176248_, TextureMapping.m_125822_(p_176248_), this.f_124478_);
      this.f_124477_.accept(m_124859_(p_176248_, resourcelocation));
   }

   private void m_176249_(Block p_176250_) {
      ResourceLocation resourcelocation = ModelTemplates.f_176467_.m_125592_(p_176250_, TextureMapping.m_125822_(p_176250_), this.f_124478_);
      this.f_124477_.accept(m_124859_(p_176250_, resourcelocation));
   }

   private void m_124976_() {
      TextureMapping texturemapping = TextureMapping.m_125763_(TextureMapping.m_125740_(Blocks.f_50078_), TextureMapping.m_125740_(Blocks.f_50705_));
      ResourceLocation resourcelocation = ModelTemplates.f_125694_.m_125592_(Blocks.f_50078_, texturemapping, this.f_124478_);
      this.f_124477_.accept(m_124859_(Blocks.f_50078_, resourcelocation));
   }

   private void m_124979_() {
      this.m_124517_(Items.f_42451_);
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50088_).m_125209_(Condition.m_125137_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61383_, RedstoneSide.NONE).m_125176_(BlockStateProperties.f_61382_, RedstoneSide.NONE).m_125176_(BlockStateProperties.f_61384_, RedstoneSide.NONE).m_125176_(BlockStateProperties.f_61385_, RedstoneSide.NONE), Condition.m_125135_().m_125179_(BlockStateProperties.f_61383_, RedstoneSide.SIDE, RedstoneSide.UP).m_125179_(BlockStateProperties.f_61382_, RedstoneSide.SIDE, RedstoneSide.UP), Condition.m_125135_().m_125179_(BlockStateProperties.f_61382_, RedstoneSide.SIDE, RedstoneSide.UP).m_125179_(BlockStateProperties.f_61384_, RedstoneSide.SIDE, RedstoneSide.UP), Condition.m_125135_().m_125179_(BlockStateProperties.f_61384_, RedstoneSide.SIDE, RedstoneSide.UP).m_125179_(BlockStateProperties.f_61385_, RedstoneSide.SIDE, RedstoneSide.UP), Condition.m_125135_().m_125179_(BlockStateProperties.f_61385_, RedstoneSide.SIDE, RedstoneSide.UP).m_125179_(BlockStateProperties.f_61383_, RedstoneSide.SIDE, RedstoneSide.UP)), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_dot"))).m_125209_(Condition.m_125135_().m_125179_(BlockStateProperties.f_61383_, RedstoneSide.SIDE, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_side0"))).m_125209_(Condition.m_125135_().m_125179_(BlockStateProperties.f_61384_, RedstoneSide.SIDE, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_side_alt0"))).m_125209_(Condition.m_125135_().m_125179_(BlockStateProperties.f_61382_, RedstoneSide.SIDE, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_side_alt1")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125209_(Condition.m_125135_().m_125179_(BlockStateProperties.f_61385_, RedstoneSide.SIDE, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_side1")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61383_, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_up"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61382_, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_up")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61384_, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_up")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61385_, RedstoneSide.UP), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125581_("redstone_dust_up")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)));
   }

   private void m_124982_() {
      this.m_124517_(Items.f_42351_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50328_).m_125271_(m_124785_()).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61393_, BlockStateProperties.f_61448_).m_125350_(ComparatorMode.COMPARE, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(Blocks.f_50328_))).m_125350_(ComparatorMode.COMPARE, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50328_, "_on"))).m_125350_(ComparatorMode.SUBTRACT, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50328_, "_subtract"))).m_125350_(ComparatorMode.SUBTRACT, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50328_, "_on_subtract")))));
   }

   private void m_124985_() {
      TextureMapping texturemapping = TextureMapping.m_125748_(Blocks.f_50470_);
      TextureMapping texturemapping1 = TextureMapping.m_125763_(TextureMapping.m_125753_(Blocks.f_50405_, "_side"), texturemapping.m_125756_(TextureSlot.f_125872_));
      ResourceLocation resourcelocation = ModelTemplates.f_125627_.m_125592_(Blocks.f_50405_, texturemapping1, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125628_.m_125592_(Blocks.f_50405_, texturemapping1, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125694_.m_125616_(Blocks.f_50405_, "_double", texturemapping1, this.f_124478_);
      this.f_124477_.accept(m_124928_(Blocks.f_50405_, resourcelocation, resourcelocation1, resourcelocation2));
      this.f_124477_.accept(m_124859_(Blocks.f_50470_, ModelTemplates.f_125692_.m_125592_(Blocks.f_50470_, texturemapping, this.f_124478_)));
   }

   private void m_124988_() {
      this.m_124517_(Items.f_42543_);
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50255_).m_125218_(Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125740_(Blocks.f_50255_))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61436_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50255_, "_bottle0"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61437_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50255_, "_bottle1"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61438_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50255_, "_bottle2"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61436_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50255_, "_empty0"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61437_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50255_, "_empty1"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61438_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50255_, "_empty2"))));
   }

   private void m_124983_(Block p_124984_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125657_.m_125592_(p_124984_, TextureMapping.m_125768_(p_124984_), this.f_124478_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125581_("mushroom_block_inside");
      this.f_124477_.accept(MultiPartGenerator.m_125204_(p_124984_).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61366_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61367_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, false)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, false)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, false)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61366_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, false)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61367_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, false)));
      this.m_124797_(p_124984_, TexturedModel.f_125905_.m_125952_(p_124984_, "_inventory", this.f_124478_));
   }

   private void m_124991_() {
      this.m_124517_(Items.f_42502_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50145_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61412_).m_125329_(0, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(Blocks.f_50145_))).m_125329_(1, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50145_, "_slice1"))).m_125329_(2, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50145_, "_slice2"))).m_125329_(3, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50145_, "_slice3"))).m_125329_(4, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50145_, "_slice4"))).m_125329_(5, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50145_, "_slice5"))).m_125329_(6, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50145_, "_slice6")))));
   }

   private void m_124994_() {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(Blocks.f_50621_, "_side3")).m_125758_(TextureSlot.f_125881_, TextureMapping.m_125740_(Blocks.f_50745_)).m_125758_(TextureSlot.f_125880_, TextureMapping.m_125753_(Blocks.f_50621_, "_top")).m_125758_(TextureSlot.f_125876_, TextureMapping.m_125753_(Blocks.f_50621_, "_side3")).m_125758_(TextureSlot.f_125878_, TextureMapping.m_125753_(Blocks.f_50621_, "_side3")).m_125758_(TextureSlot.f_125877_, TextureMapping.m_125753_(Blocks.f_50621_, "_side1")).m_125758_(TextureSlot.f_125879_, TextureMapping.m_125753_(Blocks.f_50621_, "_side2"));
      this.f_124477_.accept(m_124859_(Blocks.f_50621_, ModelTemplates.f_125647_.m_125592_(Blocks.f_50621_, texturemapping, this.f_124478_)));
   }

   private void m_124997_() {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125869_, TextureMapping.m_125753_(Blocks.f_50625_, "_front")).m_125758_(TextureSlot.f_125881_, TextureMapping.m_125753_(Blocks.f_50625_, "_bottom")).m_125758_(TextureSlot.f_125880_, TextureMapping.m_125753_(Blocks.f_50625_, "_top")).m_125758_(TextureSlot.f_125876_, TextureMapping.m_125753_(Blocks.f_50625_, "_front")).m_125758_(TextureSlot.f_125877_, TextureMapping.m_125753_(Blocks.f_50625_, "_front")).m_125758_(TextureSlot.f_125878_, TextureMapping.m_125753_(Blocks.f_50625_, "_side")).m_125758_(TextureSlot.f_125879_, TextureMapping.m_125753_(Blocks.f_50625_, "_side"));
      this.f_124477_.accept(m_124859_(Blocks.f_50625_, ModelTemplates.f_125647_.m_125592_(Blocks.f_50625_, texturemapping, this.f_124478_)));
   }

   private void m_124549_(Block p_124550_, Block p_124551_, BiFunction<Block, Block, TextureMapping> p_124552_) {
      TextureMapping texturemapping = p_124552_.apply(p_124550_, p_124551_);
      this.f_124477_.accept(m_124859_(p_124550_, ModelTemplates.f_125647_.m_125592_(p_124550_, texturemapping, this.f_124478_)));
   }

   private void m_125000_() {
      TextureMapping texturemapping = TextureMapping.m_125818_(Blocks.f_50133_);
      this.f_124477_.accept(m_124859_(Blocks.f_50133_, ModelLocationUtils.m_125576_(Blocks.f_50133_)));
      this.m_124564_(Blocks.f_50143_, texturemapping);
      this.m_124564_(Blocks.f_50144_, texturemapping);
   }

   private void m_124564_(Block p_124565_, TextureMapping p_124566_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125698_.m_125592_(p_124565_, p_124566_.m_125785_(TextureSlot.f_125873_, TextureMapping.m_125740_(p_124565_)), this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124565_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125271_(m_124727_()));
   }

   private void m_176253_() {
      this.m_124517_(Items.f_42544_);
      this.m_124960_(Blocks.f_50256_);
      this.f_124477_.accept(m_124859_(Blocks.f_152477_, ModelTemplates.f_176465_.m_125592_(Blocks.f_152477_, TextureMapping.m_176488_(TextureMapping.m_125753_(Blocks.f_49991_, "_still")), this.f_124478_)));
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152476_).m_125271_(PropertyDispatch.m_125294_(LayeredCauldronBlock.f_153514_).m_125329_(1, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176463_.m_125596_(Blocks.f_152476_, "_level1", TextureMapping.m_176488_(TextureMapping.m_125753_(Blocks.f_49990_, "_still")), this.f_124478_))).m_125329_(2, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176464_.m_125596_(Blocks.f_152476_, "_level2", TextureMapping.m_176488_(TextureMapping.m_125753_(Blocks.f_49990_, "_still")), this.f_124478_))).m_125329_(3, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176465_.m_125596_(Blocks.f_152476_, "_full", TextureMapping.m_176488_(TextureMapping.m_125753_(Blocks.f_49990_, "_still")), this.f_124478_)))));
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152478_).m_125271_(PropertyDispatch.m_125294_(LayeredCauldronBlock.f_153514_).m_125329_(1, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176463_.m_125596_(Blocks.f_152478_, "_level1", TextureMapping.m_176488_(TextureMapping.m_125740_(Blocks.f_152499_)), this.f_124478_))).m_125329_(2, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176464_.m_125596_(Blocks.f_152478_, "_level2", TextureMapping.m_176488_(TextureMapping.m_125740_(Blocks.f_152499_)), this.f_124478_))).m_125329_(3, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176465_.m_125596_(Blocks.f_152478_, "_full", TextureMapping.m_176488_(TextureMapping.m_125740_(Blocks.f_152499_)), this.f_124478_)))));
   }

   private void m_125006_() {
      TextureMapping texturemapping = TextureMapping.m_125768_(Blocks.f_50491_);
      ResourceLocation resourcelocation = ModelTemplates.f_125669_.m_125592_(Blocks.f_50491_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = this.m_124578_(Blocks.f_50491_, "_dead", ModelTemplates.f_125669_, (p_176148_) -> {
         return texturemapping.m_125785_(TextureSlot.f_125868_, p_176148_);
      });
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50491_).m_125271_(m_124626_(BlockStateProperties.f_61408_, 5, resourcelocation1, resourcelocation)));
   }

   private void m_124986_(Block p_124987_) {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50094_, "_top")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50094_, "_side")).m_125758_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_124987_, "_front"));
      TextureMapping texturemapping1 = (new TextureMapping()).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50094_, "_top")).m_125758_(TextureSlot.f_125873_, TextureMapping.m_125753_(p_124987_, "_front_vertical"));
      ResourceLocation resourcelocation = ModelTemplates.f_125698_.m_125592_(p_124987_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125700_.m_125592_(p_124987_, texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124987_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61372_).m_125329_(Direction.DOWN, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125329_(Direction.UP, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125329_(Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270))));
   }

   private void m_125009_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_50258_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50258_, "_filled");
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50258_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61433_).m_125329_(false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125329_(true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1))).m_125271_(m_124785_()));
   }

   private void m_125012_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_50490_, "_side");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50490_, "_noside");
      ResourceLocation resourcelocation2 = ModelLocationUtils.m_125578_(Blocks.f_50490_, "_noside1");
      ResourceLocation resourcelocation3 = ModelLocationUtils.m_125578_(Blocks.f_50490_, "_noside2");
      ResourceLocation resourcelocation4 = ModelLocationUtils.m_125578_(Blocks.f_50490_, "_noside3");
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50490_).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61366_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61367_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125215_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125522_, 2), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4)).m_125215_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125522_, 2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)).m_125215_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125522_, 2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125521_, true)).m_125215_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125522_, 2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125215_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61366_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125522_, 2).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125521_, true)).m_125215_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61367_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125522_, 2).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125521_, true)));
   }

   private void m_124484_() {
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50715_).m_125218_(Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125740_(Blocks.f_50715_))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 1), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents1"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 2), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents2"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 3), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents3"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 4), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents4"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 5), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents5"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 6), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents6"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 7), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents7"))).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61419_, 8), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50715_, "_contents_ready"))));
   }

   private void m_176251_(Block p_176252_) {
      this.m_124524_(p_176252_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_176252_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125639_.m_125592_(p_176252_, TextureMapping.m_125780_(p_176252_), this.f_124478_))).m_125271_(this.m_124947_()));
   }

   private void m_176087_() {
      this.m_176251_(Blocks.f_152495_);
      this.m_176251_(Blocks.f_152494_);
      this.m_176251_(Blocks.f_152493_);
      this.m_176251_(Blocks.f_152492_);
   }

   private void m_176088_() {
      this.m_124517_(Blocks.f_152588_.m_5456_());
      PropertyDispatch.C2<Direction, DripstoneThickness> c2 = PropertyDispatch.m_125296_(BlockStateProperties.f_155997_, BlockStateProperties.f_155998_);

      for(DripstoneThickness dripstonethickness : DripstoneThickness.values()) {
         c2.m_125350_(Direction.UP, dripstonethickness, this.m_176116_(Direction.UP, dripstonethickness));
      }

      for(DripstoneThickness dripstonethickness1 : DripstoneThickness.values()) {
         c2.m_125350_(Direction.DOWN, dripstonethickness1, this.m_176116_(Direction.DOWN, dripstonethickness1));
      }

      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152588_).m_125271_(c2));
   }

   private Variant m_176116_(Direction p_176117_, DripstoneThickness p_176118_) {
      String s = "_" + p_176117_.m_7912_() + "_" + p_176118_.m_7912_();
      TextureMapping texturemapping = TextureMapping.m_125788_(TextureMapping.m_125753_(Blocks.f_152588_, s));
      return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_176462_.m_125596_(Blocks.f_152588_, s, texturemapping, this.f_124478_));
   }

   private void m_124989_(Block p_124990_) {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125740_(Blocks.f_50134_)).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125740_(p_124990_)).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(p_124990_, "_side"));
      this.f_124477_.accept(m_124859_(p_124990_, ModelTemplates.f_125697_.m_125592_(p_124990_, texturemapping, this.f_124478_)));
   }

   private void m_124485_() {
      ResourceLocation resourcelocation = TextureMapping.m_125753_(Blocks.f_50329_, "_side");
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50329_, "_top")).m_125758_(TextureSlot.f_125875_, resourcelocation);
      TextureMapping texturemapping1 = (new TextureMapping()).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50329_, "_inverted_top")).m_125758_(TextureSlot.f_125875_, resourcelocation);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50329_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61441_).m_125329_(false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125670_.m_125592_(Blocks.f_50329_, texturemapping, this.f_124478_))).m_125329_(true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125670_.m_125612_(ModelLocationUtils.m_125578_(Blocks.f_50329_, "_inverted"), texturemapping1, this.f_124478_)))));
   }

   private void m_124992_(Block p_124993_) {
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124993_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(p_124993_))).m_125271_(this.m_124947_()));
   }

   private void m_176089_() {
      Block block = Blocks.f_152587_;
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(block, "_on");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125576_(block);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(block, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(block))).m_125271_(this.m_124947_()).m_125271_(m_124622_(BlockStateProperties.f_61448_, resourcelocation, resourcelocation1)));
   }

   private void m_124486_() {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125857_, TextureMapping.m_125740_(Blocks.f_50493_)).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125740_(Blocks.f_50093_));
      TextureMapping texturemapping1 = (new TextureMapping()).m_125758_(TextureSlot.f_125857_, TextureMapping.m_125740_(Blocks.f_50493_)).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50093_, "_moist"));
      ResourceLocation resourcelocation = ModelTemplates.f_125681_.m_125592_(Blocks.f_50093_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125681_.m_125612_(TextureMapping.m_125753_(Blocks.f_50093_, "_moist"), texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50093_).m_125271_(m_124626_(BlockStateProperties.f_61423_, 7, resourcelocation1, resourcelocation)));
   }

   private List<ResourceLocation> m_124995_(Block p_124996_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125682_.m_125612_(ModelLocationUtils.m_125578_(p_124996_, "_floor0"), TextureMapping.m_125836_(p_124996_), this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125682_.m_125612_(ModelLocationUtils.m_125578_(p_124996_, "_floor1"), TextureMapping.m_125838_(p_124996_), this.f_124478_);
      return ImmutableList.of(resourcelocation, resourcelocation1);
   }

   private List<ResourceLocation> m_124998_(Block p_124999_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125683_.m_125612_(ModelLocationUtils.m_125578_(p_124999_, "_side0"), TextureMapping.m_125836_(p_124999_), this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125683_.m_125612_(ModelLocationUtils.m_125578_(p_124999_, "_side1"), TextureMapping.m_125838_(p_124999_), this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125684_.m_125612_(ModelLocationUtils.m_125578_(p_124999_, "_side_alt0"), TextureMapping.m_125836_(p_124999_), this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125684_.m_125612_(ModelLocationUtils.m_125578_(p_124999_, "_side_alt1"), TextureMapping.m_125838_(p_124999_), this.f_124478_);
      return ImmutableList.of(resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3);
   }

   private List<ResourceLocation> m_125001_(Block p_125002_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125685_.m_125612_(ModelLocationUtils.m_125578_(p_125002_, "_up0"), TextureMapping.m_125836_(p_125002_), this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125685_.m_125612_(ModelLocationUtils.m_125578_(p_125002_, "_up1"), TextureMapping.m_125838_(p_125002_), this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125686_.m_125612_(ModelLocationUtils.m_125578_(p_125002_, "_up_alt0"), TextureMapping.m_125836_(p_125002_), this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125686_.m_125612_(ModelLocationUtils.m_125578_(p_125002_, "_up_alt1"), TextureMapping.m_125838_(p_125002_), this.f_124478_);
      return ImmutableList.of(resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3);
   }

   private static List<Variant> m_124682_(List<ResourceLocation> p_124683_, UnaryOperator<Variant> p_124684_) {
      return p_124683_.stream().map((p_176238_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176238_);
      }).map(p_124684_).collect(Collectors.toList());
   }

   private void m_124487_() {
      Condition condition = Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false).m_125176_(BlockStateProperties.f_61369_, false).m_125176_(BlockStateProperties.f_61370_, false).m_125176_(BlockStateProperties.f_61371_, false).m_125176_(BlockStateProperties.f_61366_, false);
      List<ResourceLocation> list = this.m_124995_(Blocks.f_50083_);
      List<ResourceLocation> list1 = this.m_124998_(Blocks.f_50083_);
      List<ResourceLocation> list2 = this.m_125001_(Blocks.f_50083_);
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50083_).m_125212_(condition, m_124682_(list, (p_124894_) -> {
         return p_124894_;
      })).m_125212_(Condition.m_125137_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true), condition), m_124682_(list1, (p_176243_) -> {
         return p_176243_;
      })).m_125212_(Condition.m_125137_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, true), condition), m_124682_(list1, (p_176240_) -> {
         return p_176240_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
      })).m_125212_(Condition.m_125137_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, true), condition), m_124682_(list1, (p_176236_) -> {
         return p_176236_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180);
      })).m_125212_(Condition.m_125137_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, true), condition), m_124682_(list1, (p_176232_) -> {
         return p_176232_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270);
      })).m_125212_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61366_, true), m_124682_(list2, (p_176227_) -> {
         return p_176227_;
      })));
   }

   private void m_124488_() {
      List<ResourceLocation> list = this.m_124995_(Blocks.f_50084_);
      List<ResourceLocation> list1 = this.m_124998_(Blocks.f_50084_);
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50084_).m_125220_(m_124682_(list, (p_176221_) -> {
         return p_176221_;
      })).m_125220_(m_124682_(list1, (p_176209_) -> {
         return p_176209_;
      })).m_125220_(m_124682_(list1, (p_176200_) -> {
         return p_176200_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
      })).m_125220_(m_124682_(list1, (p_176188_) -> {
         return p_176188_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180);
      })).m_125220_(m_124682_(list1, (p_176143_) -> {
         return p_176143_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270);
      })));
   }

   private void m_125004_(Block p_125005_) {
      ResourceLocation resourcelocation = TexturedModel.f_125919_.m_125956_(p_125005_, this.f_124478_);
      ResourceLocation resourcelocation1 = TexturedModel.f_125920_.m_125956_(p_125005_, this.f_124478_);
      this.m_124517_(p_125005_.m_5456_());
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_125005_).m_125271_(m_124622_(BlockStateProperties.f_61435_, resourcelocation1, resourcelocation)));
   }

   private void m_124489_() {
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50449_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61407_).m_125329_(0, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, this.m_124578_(Blocks.f_50449_, "_0", ModelTemplates.f_125692_, TextureMapping::m_125776_))).m_125329_(1, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, this.m_124578_(Blocks.f_50449_, "_1", ModelTemplates.f_125692_, TextureMapping::m_125776_))).m_125329_(2, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, this.m_124578_(Blocks.f_50449_, "_2", ModelTemplates.f_125692_, TextureMapping::m_125776_))).m_125329_(3, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, this.m_124578_(Blocks.f_50449_, "_3", ModelTemplates.f_125692_, TextureMapping::m_125776_)))));
   }

   private void m_124490_() {
      ResourceLocation resourcelocation = TextureMapping.m_125740_(Blocks.f_50493_);
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125871_, resourcelocation).m_125773_(TextureSlot.f_125871_, TextureSlot.f_125869_).m_125758_(TextureSlot.f_125872_, TextureMapping.m_125753_(Blocks.f_50440_, "_top")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50440_, "_snow"));
      Variant variant = Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125697_.m_125596_(Blocks.f_50440_, "_snow", texturemapping, this.f_124478_));
      this.m_124599_(Blocks.f_50440_, ModelLocationUtils.m_125576_(Blocks.f_50440_), variant);
      ResourceLocation resourcelocation1 = TexturedModel.f_125909_.m_125964_(Blocks.f_50195_).m_125940_((p_176198_) -> {
         p_176198_.m_125758_(TextureSlot.f_125871_, resourcelocation);
      }).m_125937_(Blocks.f_50195_, this.f_124478_);
      this.m_124599_(Blocks.f_50195_, resourcelocation1, variant);
      ResourceLocation resourcelocation2 = TexturedModel.f_125909_.m_125964_(Blocks.f_50599_).m_125940_((p_176154_) -> {
         p_176154_.m_125758_(TextureSlot.f_125871_, resourcelocation);
      }).m_125937_(Blocks.f_50599_, this.f_124478_);
      this.m_124599_(Blocks.f_50599_, resourcelocation2, variant);
   }

   private void m_124599_(Block p_124600_, ResourceLocation p_124601_, Variant p_124602_) {
      List<Variant> list = Arrays.asList(m_124688_(p_124601_));
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124600_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61451_).m_125329_(true, p_124602_).m_125332_(false, list)));
   }

   private void m_124491_() {
      this.m_124517_(Items.f_42533_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50262_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61406_).m_125329_(0, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50262_, "_stage0"))).m_125329_(1, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50262_, "_stage1"))).m_125329_(2, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50262_, "_stage2")))).m_125271_(m_124785_()));
   }

   private void m_176090_() {
      this.f_124477_.accept(m_124831_(Blocks.f_152481_, ModelLocationUtils.m_125576_(Blocks.f_152481_)));
   }

   private void m_124918_(Block p_124919_, Block p_124920_) {
      TextureMapping texturemapping = TextureMapping.m_125768_(p_124920_);
      ResourceLocation resourcelocation = ModelTemplates.f_125624_.m_125592_(p_124919_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125625_.m_125592_(p_124919_, texturemapping, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124919_).m_125271_(m_124626_(BlockStateProperties.f_61426_, 1, resourcelocation1, resourcelocation)));
   }

   private void m_124493_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_50332_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50332_, "_side");
      this.m_124517_(Items.f_42155_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50332_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61373_).m_125329_(Direction.DOWN, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125329_(Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125329_(Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125329_(Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125329_(Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270))));
   }

   private void m_124938_(Block p_124939_, Block p_124940_) {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(p_124939_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124940_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)));
      this.m_124797_(p_124940_, resourcelocation);
   }

   private void m_124494_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_50183_, "_post_ends");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50183_, "_post");
      ResourceLocation resourcelocation2 = ModelLocationUtils.m_125578_(Blocks.f_50183_, "_cap");
      ResourceLocation resourcelocation3 = ModelLocationUtils.m_125578_(Blocks.f_50183_, "_cap_alt");
      ResourceLocation resourcelocation4 = ModelLocationUtils.m_125578_(Blocks.f_50183_, "_side");
      ResourceLocation resourcelocation5 = ModelLocationUtils.m_125578_(Blocks.f_50183_, "_side_alt");
      this.f_124477_.accept(MultiPartGenerator.m_125204_(Blocks.f_50183_).m_125218_(Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false).m_125176_(BlockStateProperties.f_61369_, false).m_125176_(BlockStateProperties.f_61370_, false).m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true).m_125176_(BlockStateProperties.f_61369_, false).m_125176_(BlockStateProperties.f_61370_, false).m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false).m_125176_(BlockStateProperties.f_61369_, true).m_125176_(BlockStateProperties.f_61370_, false).m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false).m_125176_(BlockStateProperties.f_61369_, false).m_125176_(BlockStateProperties.f_61370_, true).m_125176_(BlockStateProperties.f_61371_, false), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, false).m_125176_(BlockStateProperties.f_61369_, false).m_125176_(BlockStateProperties.f_61370_, false).m_125176_(BlockStateProperties.f_61371_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61368_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61369_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61370_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation5)).m_125209_(Condition.m_125135_().m_125176_(BlockStateProperties.f_61371_, true), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation5).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)));
      this.m_124728_(Blocks.f_50183_);
   }

   private void m_125007_(Block p_125008_) {
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_125008_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(p_125008_))).m_125271_(m_124727_()));
   }

   private void m_124495_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_50164_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50164_, "_on");
      this.m_124728_(Blocks.f_50164_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50164_).m_125271_(m_124622_(BlockStateProperties.f_61448_, resourcelocation, resourcelocation1)).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61376_, BlockStateProperties.f_61374_).m_125350_(AttachFace.CEILING, Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(AttachFace.CEILING, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.CEILING, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.FLOOR, Direction.NORTH, Variant.m_125501_()).m_125350_(AttachFace.FLOOR, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.FLOOR, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.FLOOR, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125350_(AttachFace.WALL, Direction.NORTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.WALL, Direction.EAST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125350_(AttachFace.WALL, Direction.SOUTH, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125350_(AttachFace.WALL, Direction.WEST, Variant.m_125501_().m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270))));
   }

   private void m_124496_() {
      this.m_124728_(Blocks.f_50196_);
      this.f_124477_.accept(m_124831_(Blocks.f_50196_, ModelLocationUtils.m_125576_(Blocks.f_50196_)));
   }

   private void m_124497_() {
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50142_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61364_).m_125329_(Direction.Axis.X, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50142_, "_ns"))).m_125329_(Direction.Axis.Z, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50142_, "_ew")))));
   }

   private void m_124498_() {
      ResourceLocation resourcelocation = TexturedModel.f_125905_.m_125956_(Blocks.f_50134_, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125259_(Blocks.f_50134_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R180), Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270)));
   }

   private void m_124499_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_50455_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50455_, "_on");
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50455_).m_125271_(m_124622_(BlockStateProperties.f_61448_, resourcelocation1, resourcelocation)).m_125271_(m_124850_()));
   }

   private void m_124500_() {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125871_, TextureMapping.m_125753_(Blocks.f_50039_, "_bottom")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50039_, "_side"));
      ResourceLocation resourcelocation = TextureMapping.m_125753_(Blocks.f_50039_, "_top_sticky");
      ResourceLocation resourcelocation1 = TextureMapping.m_125753_(Blocks.f_50039_, "_top");
      TextureMapping texturemapping1 = texturemapping.m_125785_(TextureSlot.f_125860_, resourcelocation);
      TextureMapping texturemapping2 = texturemapping.m_125785_(TextureSlot.f_125860_, resourcelocation1);
      ResourceLocation resourcelocation2 = ModelLocationUtils.m_125578_(Blocks.f_50039_, "_base");
      this.m_124603_(Blocks.f_50039_, resourcelocation2, texturemapping2);
      this.m_124603_(Blocks.f_50032_, resourcelocation2, texturemapping1);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125697_.m_125596_(Blocks.f_50039_, "_inventory", texturemapping.m_125785_(TextureSlot.f_125872_, resourcelocation1), this.f_124478_);
      ResourceLocation resourcelocation4 = ModelTemplates.f_125697_.m_125596_(Blocks.f_50032_, "_inventory", texturemapping.m_125785_(TextureSlot.f_125872_, resourcelocation), this.f_124478_);
      this.m_124797_(Blocks.f_50039_, resourcelocation3);
      this.m_124797_(Blocks.f_50032_, resourcelocation4);
   }

   private void m_124603_(Block p_124604_, ResourceLocation p_124605_, TextureMapping p_124606_) {
      ResourceLocation resourcelocation = ModelTemplates.f_125649_.m_125592_(p_124604_, p_124606_, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_124604_).m_125271_(m_124622_(BlockStateProperties.f_61432_, p_124605_, resourcelocation)).m_125271_(m_124850_()));
   }

   private void m_124501_() {
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125861_, TextureMapping.m_125753_(Blocks.f_50039_, "_top")).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50039_, "_side"));
      TextureMapping texturemapping1 = texturemapping.m_125785_(TextureSlot.f_125860_, TextureMapping.m_125753_(Blocks.f_50039_, "_top_sticky"));
      TextureMapping texturemapping2 = texturemapping.m_125785_(TextureSlot.f_125860_, TextureMapping.m_125753_(Blocks.f_50039_, "_top"));
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50040_).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61449_, BlockStateProperties.f_61396_).m_125350_(false, PistonType.DEFAULT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125650_.m_125596_(Blocks.f_50039_, "_head", texturemapping2, this.f_124478_))).m_125350_(false, PistonType.STICKY, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125650_.m_125596_(Blocks.f_50039_, "_head_sticky", texturemapping1, this.f_124478_))).m_125350_(true, PistonType.DEFAULT, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125651_.m_125596_(Blocks.f_50039_, "_head_short", texturemapping2, this.f_124478_))).m_125350_(true, PistonType.STICKY, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125651_.m_125596_(Blocks.f_50039_, "_head_short_sticky", texturemapping1, this.f_124478_)))).m_125271_(m_124850_()));
   }

   private void m_176091_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_152500_, "_inactive");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_152500_, "_active");
      this.m_124797_(Blocks.f_152500_, resourcelocation);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152500_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_155999_).m_125335_((p_176158_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176158_ == SculkSensorPhase.ACTIVE ? resourcelocation1 : resourcelocation);
      })));
   }

   private void m_124502_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125578_(Blocks.f_50616_, "_stable");
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50616_, "_unstable");
      this.m_124797_(Blocks.f_50616_, resourcelocation);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50616_).m_125271_(m_124622_(BlockStateProperties.f_61427_, resourcelocation1, resourcelocation)));
   }

   private void m_176092_() {
      ResourceLocation resourcelocation = this.m_124578_(Blocks.f_152538_, "", ModelTemplates.f_125639_, TextureMapping::m_125788_);
      ResourceLocation resourcelocation1 = this.m_124578_(Blocks.f_152538_, "_lit", ModelTemplates.f_125639_, TextureMapping::m_125788_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152538_).m_125271_(m_124622_(BlockStateProperties.f_155977_, resourcelocation1, resourcelocation)));
      ResourceLocation resourcelocation2 = this.m_124578_(Blocks.f_152539_, "", ModelTemplates.f_125639_, TextureMapping::m_125788_);
      ResourceLocation resourcelocation3 = this.m_124578_(Blocks.f_152539_, "_lit", ModelTemplates.f_125639_, TextureMapping::m_125788_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_152539_).m_125271_(m_124622_(BlockStateProperties.f_155977_, resourcelocation3, resourcelocation2)));
   }

   private void m_124503_() {
      ResourceLocation resourcelocation = TexturedModel.f_125905_.m_125956_(Blocks.f_50261_, this.f_124478_);
      ResourceLocation resourcelocation1 = this.m_124578_(Blocks.f_50261_, "_on", ModelTemplates.f_125692_, TextureMapping::m_125776_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50261_).m_125271_(m_124622_(BlockStateProperties.f_61443_, resourcelocation1, resourcelocation)));
   }

   private void m_124950_(Block p_124951_, Block p_124952_) {
      TextureMapping texturemapping = TextureMapping.m_125842_(p_124951_);
      this.f_124477_.accept(m_124859_(p_124951_, ModelTemplates.f_125690_.m_125592_(p_124951_, texturemapping, this.f_124478_)));
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(p_124952_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelTemplates.f_125648_.m_125592_(p_124952_, texturemapping, this.f_124478_))).m_125271_(m_124822_()));
      this.m_124728_(p_124951_);
      this.m_124524_(p_124952_);
   }

   private void m_124504_() {
      TextureMapping texturemapping = TextureMapping.m_125842_(Blocks.f_50174_);
      TextureMapping texturemapping1 = TextureMapping.m_125816_(TextureMapping.m_125753_(Blocks.f_50174_, "_off"));
      ResourceLocation resourcelocation = ModelTemplates.f_125690_.m_125592_(Blocks.f_50174_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_125690_.m_125596_(Blocks.f_50174_, "_off", texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50174_).m_125271_(m_124622_(BlockStateProperties.f_61443_, resourcelocation, resourcelocation1)));
      ResourceLocation resourcelocation2 = ModelTemplates.f_125648_.m_125592_(Blocks.f_50123_, texturemapping, this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_125648_.m_125596_(Blocks.f_50123_, "_off", texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50123_).m_125271_(m_124622_(BlockStateProperties.f_61443_, resourcelocation2, resourcelocation3)).m_125271_(m_124822_()));
      this.m_124728_(Blocks.f_50174_);
      this.m_124524_(Blocks.f_50123_);
   }

   private void m_124505_() {
      this.m_124517_(Items.f_42350_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50146_).m_125271_(PropertyDispatch.m_125299_(BlockStateProperties.f_61413_, BlockStateProperties.f_61444_, BlockStateProperties.f_61448_).m_125389_((p_176134_, p_176135_, p_176136_) -> {
         StringBuilder stringbuilder = new StringBuilder();
         stringbuilder.append('_').append((Object)p_176134_).append("tick");
         if (p_176136_) {
            stringbuilder.append("_on");
         }

         if (p_176135_) {
            stringbuilder.append("_locked");
         }

         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50146_, stringbuilder.toString()));
      })).m_125271_(m_124785_()));
   }

   private void m_124506_() {
      this.m_124517_(Items.f_41868_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50567_).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61425_, BlockStateProperties.f_61362_).m_125354_(1, false, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("dead_sea_pickle")))).m_125354_(2, false, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("two_dead_sea_pickles")))).m_125354_(3, false, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("three_dead_sea_pickles")))).m_125354_(4, false, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("four_dead_sea_pickles")))).m_125354_(1, true, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("sea_pickle")))).m_125354_(2, true, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("two_sea_pickles")))).m_125354_(3, true, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("three_sea_pickles")))).m_125354_(4, true, Arrays.asList(m_124688_(ModelLocationUtils.m_125581_("four_sea_pickles"))))));
   }

   private void m_124507_() {
      TextureMapping texturemapping = TextureMapping.m_125748_(Blocks.f_50125_);
      ResourceLocation resourcelocation = ModelTemplates.f_125692_.m_125592_(Blocks.f_50127_, texturemapping, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50125_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61417_).m_125335_((p_176151_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, p_176151_ < 8 ? ModelLocationUtils.m_125578_(Blocks.f_50125_, "_height" + p_176151_ * 2) : resourcelocation);
      })));
      this.m_124797_(Blocks.f_50125_, ModelLocationUtils.m_125578_(Blocks.f_50125_, "_height2"));
      this.f_124477_.accept(m_124859_(Blocks.f_50127_, resourcelocation));
   }

   private void m_124508_() {
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(Blocks.f_50679_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125576_(Blocks.f_50679_))).m_125271_(m_124727_()));
   }

   private void m_124509_() {
      ResourceLocation resourcelocation = TexturedModel.f_125905_.m_125956_(Blocks.f_50677_, this.f_124478_);
      this.m_124797_(Blocks.f_50677_, resourcelocation);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50677_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61399_).m_125335_((p_176115_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, this.m_124578_(Blocks.f_50677_, "_" + p_176115_.m_7912_(), ModelTemplates.f_125692_, TextureMapping::m_125776_));
      })));
   }

   private void m_124718_() {
      this.m_124517_(Items.f_42780_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50685_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61407_).m_125335_((p_176132_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, this.m_124578_(Blocks.f_50685_, "_stage" + p_176132_, ModelTemplates.f_125639_, TextureMapping::m_125788_));
      })));
   }

   private void m_124719_() {
      this.m_124517_(Items.f_42401_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50267_).m_125271_(PropertyDispatch.m_125308_(BlockStateProperties.f_61386_, BlockStateProperties.f_61369_, BlockStateProperties.f_61368_, BlockStateProperties.f_61370_, BlockStateProperties.f_61371_).m_125460_(false, false, false, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ns"))).m_125460_(false, true, false, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_n")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(false, false, true, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_n"))).m_125460_(false, false, false, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_n")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125460_(false, false, false, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_n")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125460_(false, true, true, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ne"))).m_125460_(false, true, false, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ne")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(false, false, false, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ne")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125460_(false, false, true, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ne")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125460_(false, false, true, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ns"))).m_125460_(false, true, false, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_ns")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(false, true, true, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_nse"))).m_125460_(false, true, false, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_nse")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(false, false, true, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_nse")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125460_(false, true, true, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_nse")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125460_(false, true, true, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_nsew"))).m_125460_(true, false, false, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ns"))).m_125460_(true, false, true, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_n"))).m_125460_(true, false, false, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_n")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125460_(true, true, false, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_n")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(true, false, false, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_n")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125460_(true, true, true, false, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ne"))).m_125460_(true, true, false, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ne")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(true, false, false, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ne")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125460_(true, false, true, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ne")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125460_(true, false, true, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ns"))).m_125460_(true, true, false, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_ns")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(true, true, true, true, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_nse"))).m_125460_(true, true, false, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_nse")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90)).m_125460_(true, false, true, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_nse")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180)).m_125460_(true, true, true, false, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_nse")).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270)).m_125460_(true, true, true, true, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, ModelLocationUtils.m_125578_(Blocks.f_50267_, "_attached_nsew")))));
   }

   private void m_124720_() {
      this.m_124728_(Blocks.f_50266_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50266_).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61386_, BlockStateProperties.f_61448_).m_125362_((p_176124_, p_176125_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, TextureMapping.m_125753_(Blocks.f_50266_, (p_176124_ ? "_attached" : "") + (p_176125_ ? "_on" : "")));
      })).m_125271_(m_124727_()));
   }

   private ResourceLocation m_124513_(int p_124514_, String p_124515_, TextureMapping p_124516_) {
      switch(p_124514_) {
      case 1:
         return ModelTemplates.f_125653_.m_125612_(ModelLocationUtils.m_125581_(p_124515_ + "turtle_egg"), p_124516_, this.f_124478_);
      case 2:
         return ModelTemplates.f_125654_.m_125612_(ModelLocationUtils.m_125581_("two_" + p_124515_ + "turtle_eggs"), p_124516_, this.f_124478_);
      case 3:
         return ModelTemplates.f_125655_.m_125612_(ModelLocationUtils.m_125581_("three_" + p_124515_ + "turtle_eggs"), p_124516_, this.f_124478_);
      case 4:
         return ModelTemplates.f_125656_.m_125612_(ModelLocationUtils.m_125581_("four_" + p_124515_ + "turtle_eggs"), p_124516_, this.f_124478_);
      default:
         throw new UnsupportedOperationException();
      }
   }

   private ResourceLocation m_124676_(Integer p_124677_, Integer p_124678_) {
      switch(p_124678_) {
      case 0:
         return this.m_124513_(p_124677_, "", TextureMapping.m_125776_(TextureMapping.m_125740_(Blocks.f_50578_)));
      case 1:
         return this.m_124513_(p_124677_, "slightly_cracked_", TextureMapping.m_125776_(TextureMapping.m_125753_(Blocks.f_50578_, "_slightly_cracked")));
      case 2:
         return this.m_124513_(p_124677_, "very_cracked_", TextureMapping.m_125776_(TextureMapping.m_125753_(Blocks.f_50578_, "_very_cracked")));
      default:
         throw new UnsupportedOperationException();
      }
   }

   private void m_124721_() {
      this.m_124517_(Items.f_42279_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50578_).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_61415_, BlockStateProperties.f_61416_).m_125372_((p_176185_, p_176186_) -> {
         return Arrays.asList(m_124688_(this.m_124676_(p_176185_, p_176186_)));
      })));
   }

   private void m_176085_(Block p_176086_) {
      this.m_124728_(p_176086_);
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(p_176086_);
      MultiPartGenerator multipartgenerator = MultiPartGenerator.m_125204_(p_176086_);
      Condition.TerminalCondition condition$terminalcondition = Util.m_137469_(Condition.m_125135_(), (p_181456_) -> {
         f_176079_.forEach((p_181460_, p_181461_) -> {
            if (p_176086_.m_49966_().m_61138_(p_181460_)) {
               p_181456_.m_125176_(p_181460_, false);
            }

         });
      });
      f_176079_.forEach((p_181467_, p_181468_) -> {
         if (p_176086_.m_49966_().m_61138_(p_181467_)) {
            multipartgenerator.m_125209_(Condition.m_125135_().m_125176_(p_181467_, true), p_181468_.apply(resourcelocation));
            multipartgenerator.m_125209_(condition$terminalcondition, p_181468_.apply(resourcelocation));
         }

      });
      this.f_124477_.accept(multipartgenerator);
   }

   private void m_124723_() {
      this.f_124477_.accept(m_124859_(Blocks.f_50450_, ModelTemplates.f_125692_.m_125592_(Blocks.f_50450_, TextureMapping.m_125776_(ModelLocationUtils.m_125581_("magma")), this.f_124478_)));
   }

   private void m_125010_(Block p_125011_) {
      this.m_124794_(p_125011_, TexturedModel.f_125916_);
      ModelTemplates.f_125661_.m_125612_(ModelLocationUtils.m_125571_(p_125011_.m_5456_()), TextureMapping.m_125834_(p_125011_), this.f_124478_);
   }

   private void m_124733_(Block p_124734_, Block p_124735_, BlockModelGenerators.TintState p_124736_) {
      this.m_124737_(p_124734_, p_124736_);
      this.m_124737_(p_124735_, p_124736_);
   }

   private void m_124962_(Block p_124963_, Block p_124964_) {
      ModelTemplates.f_125662_.m_125612_(ModelLocationUtils.m_125571_(p_124963_.m_5456_()), TextureMapping.m_125834_(p_124964_), this.f_124478_);
   }

   private void m_124724_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_50069_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_50069_, "_mirrored");
      this.f_124477_.accept(m_124862_(Blocks.f_50226_, resourcelocation, resourcelocation1));
      this.m_124797_(Blocks.f_50226_, resourcelocation);
   }

   private void m_176176_() {
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(Blocks.f_152550_);
      ResourceLocation resourcelocation1 = ModelLocationUtils.m_125578_(Blocks.f_152550_, "_mirrored");
      this.f_124477_.accept(m_124862_(Blocks.f_152596_, resourcelocation, resourcelocation1).m_125271_(m_124875_()));
      this.m_124797_(Blocks.f_152596_, resourcelocation);
   }

   private void m_124970_(Block p_124971_, Block p_124972_) {
      this.m_124557_(p_124971_, BlockModelGenerators.TintState.NOT_TINTED);
      TextureMapping texturemapping = TextureMapping.m_125798_(TextureMapping.m_125753_(p_124971_, "_pot"));
      ResourceLocation resourcelocation = BlockModelGenerators.TintState.NOT_TINTED.m_125065_().m_125592_(p_124972_, texturemapping, this.f_124478_);
      this.f_124477_.accept(m_124859_(p_124972_, resourcelocation));
   }

   private void m_124725_() {
      ResourceLocation resourcelocation = TextureMapping.m_125753_(Blocks.f_50724_, "_bottom");
      ResourceLocation resourcelocation1 = TextureMapping.m_125753_(Blocks.f_50724_, "_top_off");
      ResourceLocation resourcelocation2 = TextureMapping.m_125753_(Blocks.f_50724_, "_top");
      ResourceLocation[] aresourcelocation = new ResourceLocation[5];

      for(int i = 0; i < 5; ++i) {
         TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125871_, resourcelocation).m_125758_(TextureSlot.f_125872_, i == 0 ? resourcelocation1 : resourcelocation2).m_125758_(TextureSlot.f_125875_, TextureMapping.m_125753_(Blocks.f_50724_, "_side" + i));
         aresourcelocation[i] = ModelTemplates.f_125697_.m_125596_(Blocks.f_50724_, "_" + i, texturemapping, this.f_124478_);
      }

      this.f_124477_.accept(MultiVariantGenerator.m_125254_(Blocks.f_50724_).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61389_).m_125335_((p_176175_) -> {
         return Variant.m_125501_().m_125511_(VariantProperties.f_125520_, aresourcelocation[p_176175_]);
      })));
      this.m_124519_(Items.f_42767_, aresourcelocation[0]);
   }

   private Variant m_124635_(FrontAndTop p_124636_, Variant p_124637_) {
      switch(p_124636_) {
      case DOWN_NORTH:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90);
      case DOWN_SOUTH:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180);
      case DOWN_WEST:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270);
      case DOWN_EAST:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R90).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
      case UP_NORTH:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180);
      case UP_SOUTH:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270);
      case UP_WEST:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
      case UP_EAST:
         return p_124637_.m_125511_(VariantProperties.f_125518_, VariantProperties.Rotation.R270).m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270);
      case NORTH_UP:
         return p_124637_;
      case SOUTH_UP:
         return p_124637_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R180);
      case WEST_UP:
         return p_124637_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R270);
      case EAST_UP:
         return p_124637_.m_125511_(VariantProperties.f_125519_, VariantProperties.Rotation.R90);
      default:
         throw new UnsupportedOperationException("Rotation " + p_124636_ + " can't be expressed with existing x and y values");
      }
   }

   private void m_124726_() {
      ResourceLocation resourcelocation = TextureMapping.m_125753_(Blocks.f_50678_, "_top");
      ResourceLocation resourcelocation1 = TextureMapping.m_125753_(Blocks.f_50678_, "_bottom");
      ResourceLocation resourcelocation2 = TextureMapping.m_125753_(Blocks.f_50678_, "_side");
      ResourceLocation resourcelocation3 = TextureMapping.m_125753_(Blocks.f_50678_, "_lock");
      TextureMapping texturemapping = (new TextureMapping()).m_125758_(TextureSlot.f_125881_, resourcelocation2).m_125758_(TextureSlot.f_125879_, resourcelocation2).m_125758_(TextureSlot.f_125878_, resourcelocation2).m_125758_(TextureSlot.f_125869_, resourcelocation).m_125758_(TextureSlot.f_125876_, resourcelocation).m_125758_(TextureSlot.f_125877_, resourcelocation1).m_125758_(TextureSlot.f_125880_, resourcelocation3);
      ResourceLocation resourcelocation4 = ModelTemplates.f_125691_.m_125592_(Blocks.f_50678_, texturemapping, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125256_(Blocks.f_50678_, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4)).m_125271_(PropertyDispatch.m_125294_(BlockStateProperties.f_61375_).m_125335_((p_176120_) -> {
         return this.m_124635_(p_176120_, Variant.m_125501_());
      })));
   }

   private void m_176177_() {
      Block block = Blocks.f_50705_;
      ResourceLocation resourcelocation = ModelLocationUtils.m_125576_(block);
      TexturedModel texturedmodel = TexturedModel.f_125905_.m_125964_(block);
      Block block1 = Blocks.f_50408_;
      ResourceLocation resourcelocation1 = ModelTemplates.f_125627_.m_125592_(block1, texturedmodel.m_125951_(), this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_125628_.m_125592_(block1, texturedmodel.m_125951_(), this.f_124478_);
      this.f_124477_.accept(m_124928_(block1, resourcelocation1, resourcelocation2, resourcelocation));
   }

   public void m_124510_() {
      BlockFamilies.m_175934_().filter(BlockFamily::m_175955_).forEach((p_176122_) -> {
         this.m_124876_(p_176122_.m_175951_()).m_176259_(p_176122_);
      });
      this.m_124876_(Blocks.f_152510_).m_176259_(BlockFamilies.f_175923_).m_176264_(Blocks.f_152578_).m_176259_(BlockFamilies.f_175925_);
      this.m_124876_(Blocks.f_152509_).m_176259_(BlockFamilies.f_175927_).m_176264_(Blocks.f_152577_).m_176259_(BlockFamilies.f_175929_);
      this.m_124876_(Blocks.f_152508_).m_176259_(BlockFamilies.f_175931_).m_176264_(Blocks.f_152576_).m_176259_(BlockFamilies.f_175871_);
      this.m_124876_(Blocks.f_152507_).m_176259_(BlockFamilies.f_175873_).m_176264_(Blocks.f_152575_).m_176259_(BlockFamilies.f_175875_);
      this.m_124960_(Blocks.f_50016_);
      this.m_124533_(Blocks.f_50627_, Blocks.f_50016_);
      this.m_124533_(Blocks.f_50626_, Blocks.f_50016_);
      this.m_124960_(Blocks.f_50273_);
      this.m_124960_(Blocks.f_50128_);
      this.m_124533_(Blocks.f_50628_, Blocks.f_49990_);
      this.m_124960_(Blocks.f_50260_);
      this.m_124960_(Blocks.f_50577_);
      this.m_124960_(Blocks.f_50201_);
      this.m_124960_(Blocks.f_50276_);
      this.m_124517_(Items.f_42618_);
      this.m_124960_(Blocks.f_50719_);
      this.m_124960_(Blocks.f_49990_);
      this.m_124960_(Blocks.f_49991_);
      this.m_124960_(Blocks.f_50374_);
      this.m_124517_(Items.f_42026_);
      this.m_176244_(Blocks.f_152483_, Blocks.f_152526_);
      this.m_176244_(Blocks.f_152484_, Blocks.f_152527_);
      this.m_176244_(Blocks.f_152511_, Blocks.f_152528_);
      this.m_176244_(Blocks.f_152512_, Blocks.f_152529_);
      this.m_176244_(Blocks.f_152513_, Blocks.f_152530_);
      this.m_176244_(Blocks.f_152514_, Blocks.f_152531_);
      this.m_176244_(Blocks.f_152515_, Blocks.f_152532_);
      this.m_176244_(Blocks.f_152516_, Blocks.f_152533_);
      this.m_176244_(Blocks.f_152517_, Blocks.f_152534_);
      this.m_176244_(Blocks.f_152518_, Blocks.f_152535_);
      this.m_176244_(Blocks.f_152519_, Blocks.f_152536_);
      this.m_176244_(Blocks.f_152520_, Blocks.f_152485_);
      this.m_176244_(Blocks.f_152521_, Blocks.f_152486_);
      this.m_176244_(Blocks.f_152522_, Blocks.f_152487_);
      this.m_176244_(Blocks.f_152523_, Blocks.f_152488_);
      this.m_176244_(Blocks.f_152524_, Blocks.f_152489_);
      this.m_176244_(Blocks.f_152482_, Blocks.f_152525_);
      this.m_124960_(Blocks.f_50572_);
      this.m_124960_(Blocks.f_50248_);
      this.m_124960_(Blocks.f_152499_);
      this.m_124960_(Blocks.f_152540_);
      this.m_176247_(Blocks.f_152541_);
      this.m_176247_(Blocks.f_152542_);
      this.m_176249_(Blocks.f_152601_);
      this.m_176249_(Blocks.f_152602_);
      this.m_176092_();
      this.m_176217_(Blocks.f_152544_, Blocks.f_152543_);
      this.m_124530_(Blocks.f_50375_, Items.f_42127_);
      this.m_124517_(Items.f_42127_);
      this.m_124530_(Blocks.f_152480_, Items.f_151033_);
      this.m_176178_();
      this.m_124530_(Blocks.f_50454_, Items.f_42263_);
      this.m_124517_(Items.f_42263_);
      this.m_124921_(Blocks.f_50110_, TextureMapping.m_125753_(Blocks.f_50039_, "_side"));
      this.m_124851_(Blocks.f_49997_);
      this.m_124851_(Blocks.f_152469_);
      this.m_124851_(Blocks.f_50353_);
      this.m_124851_(Blocks.f_50089_);
      this.m_124851_(Blocks.f_152474_);
      this.m_124851_(Blocks.f_50090_);
      this.m_124851_(Blocks.f_50264_);
      this.m_124851_(Blocks.f_152479_);
      this.m_124851_(Blocks.f_50268_);
      this.m_124851_(Blocks.f_49995_);
      this.m_124851_(Blocks.f_49998_);
      this.m_124851_(Blocks.f_152467_);
      this.m_124851_(Blocks.f_50074_);
      this.m_124851_(Blocks.f_49996_);
      this.m_124851_(Blocks.f_152468_);
      this.m_124851_(Blocks.f_50075_);
      this.m_124794_(Blocks.f_50722_, TexturedModel.f_125907_);
      this.m_124851_(Blocks.f_50721_);
      this.m_124851_(Blocks.f_50059_);
      this.m_124851_(Blocks.f_152472_);
      this.m_124851_(Blocks.f_50060_);
      this.m_124851_(Blocks.f_50331_);
      this.m_124851_(Blocks.f_50173_);
      this.m_124851_(Blocks.f_152473_);
      this.m_124851_(Blocks.f_50330_);
      this.m_124851_(Blocks.f_50706_);
      this.m_124851_(Blocks.f_50568_);
      this.m_124851_(Blocks.f_50129_);
      this.m_124851_(Blocks.f_50546_);
      this.m_124851_(Blocks.f_50723_);
      this.m_124851_(Blocks.f_50259_);
      this.m_124851_(Blocks.f_50141_);
      this.m_124851_(Blocks.f_49994_);
      this.m_124851_(Blocks.f_50720_);
      this.m_124851_(Blocks.f_50126_);
      this.m_124794_(Blocks.f_50131_, TexturedModel.f_125910_);
      this.m_124794_(Blocks.f_50729_, TexturedModel.f_125907_);
      this.m_124794_(Blocks.f_50186_, TexturedModel.f_125907_);
      this.m_124851_(Blocks.f_50451_);
      this.m_124851_(Blocks.f_50065_);
      this.m_124851_(Blocks.f_50354_);
      this.m_124851_(Blocks.f_50080_);
      this.m_124851_(Blocks.f_50714_);
      this.m_124851_(Blocks.f_50386_);
      this.m_124851_(Blocks.f_50701_);
      this.m_124851_(Blocks.f_50135_);
      this.m_124851_(Blocks.f_50136_);
      this.m_124851_(Blocks.f_50085_);
      this.m_124851_(Blocks.f_50056_);
      this.m_124794_(Blocks.f_50037_, TexturedModel.f_125921_);
      this.m_124517_(Items.f_41867_);
      this.m_124794_(Blocks.f_50077_, TexturedModel.f_125909_);
      this.m_124794_(Blocks.f_50716_, TexturedModel.f_125907_);
      this.m_124851_(Blocks.f_50692_);
      this.m_124851_(Blocks.f_50057_);
      this.m_124851_(Blocks.f_152490_);
      this.m_124851_(Blocks.f_152491_);
      this.m_124851_(Blocks.f_152497_);
      this.m_124851_(Blocks.f_152496_);
      this.m_124851_(Blocks.f_152537_);
      this.m_124851_(Blocks.f_152598_);
      this.m_124851_(Blocks.f_152599_);
      this.m_124851_(Blocks.f_152600_);
      this.m_176177_();
      this.m_124851_(Blocks.f_152505_);
      this.m_124851_(Blocks.f_152506_);
      this.m_124851_(Blocks.f_152504_);
      this.m_124851_(Blocks.f_152503_);
      this.m_124851_(Blocks.f_152502_);
      this.m_124851_(Blocks.f_152501_);
      this.m_124938_(Blocks.f_152504_, Blocks.f_152571_);
      this.m_124938_(Blocks.f_152503_, Blocks.f_152573_);
      this.m_124938_(Blocks.f_152502_, Blocks.f_152572_);
      this.m_124938_(Blocks.f_152501_, Blocks.f_152574_);
      this.m_124918_(Blocks.f_50326_, Blocks.f_50074_);
      this.m_124918_(Blocks.f_50327_, Blocks.f_50075_);
      this.m_176087_();
      this.m_124976_();
      this.m_124988_();
      this.m_124991_();
      this.m_124713_(Blocks.f_50683_, Blocks.f_50684_);
      this.m_124994_();
      this.m_176253_();
      this.m_125006_();
      this.m_125012_();
      this.m_124484_();
      this.m_124485_();
      this.m_125009_();
      this.m_124992_(Blocks.f_50489_);
      this.m_176089_();
      this.m_124486_();
      this.m_124487_();
      this.m_124488_();
      this.m_124489_();
      this.m_124490_();
      this.m_124491_();
      this.m_176090_();
      this.m_124973_();
      this.m_124493_();
      this.m_124494_();
      this.m_124495_();
      this.m_124496_();
      this.m_124497_();
      this.m_124498_();
      this.m_124499_();
      this.m_124500_();
      this.m_124501_();
      this.m_124502_();
      this.m_124504_();
      this.m_124503_();
      this.m_124505_();
      this.m_124506_();
      this.m_124997_();
      this.m_124507_();
      this.m_124508_();
      this.m_124509_();
      this.m_124718_();
      this.m_124719_();
      this.m_124720_();
      this.m_124721_();
      this.m_176085_(Blocks.f_50191_);
      this.m_176085_(Blocks.f_152475_);
      this.m_124723_();
      this.m_124726_();
      this.m_176091_();
      this.m_125007_(Blocks.f_50155_);
      this.m_124728_(Blocks.f_50155_);
      this.m_125007_(Blocks.f_50624_);
      this.m_176230_();
      this.m_125007_(Blocks.f_152546_);
      this.m_124950_(Blocks.f_50081_, Blocks.f_50082_);
      this.m_124950_(Blocks.f_50139_, Blocks.f_50140_);
      this.m_124549_(Blocks.f_50091_, Blocks.f_50705_, TextureMapping::m_125782_);
      this.m_124549_(Blocks.f_50622_, Blocks.f_50742_, TextureMapping::m_125792_);
      this.m_124989_(Blocks.f_50699_);
      this.m_124989_(Blocks.f_50690_);
      this.m_124986_(Blocks.f_50061_);
      this.m_124986_(Blocks.f_50286_);
      this.m_125004_(Blocks.f_50681_);
      this.m_125004_(Blocks.f_50682_);
      this.m_124901_(Blocks.f_50184_, ModelLocationUtils.m_125576_(Blocks.f_50184_));
      this.m_124586_(Blocks.f_50137_, TexturedModel.f_125907_);
      this.m_124586_(Blocks.f_50138_, TexturedModel.f_125907_);
      this.m_124851_(Blocks.f_152597_);
      this.m_124586_(Blocks.f_50453_, TexturedModel.f_125907_);
      this.m_124823_(Blocks.f_50493_);
      this.m_124823_(Blocks.f_152549_);
      this.m_124823_(Blocks.f_49992_);
      this.m_124823_(Blocks.f_49993_);
      this.m_124786_(Blocks.f_50752_);
      this.m_124589_(Blocks.f_50335_, TexturedModel.f_125907_, TexturedModel.f_125908_);
      this.m_124589_(Blocks.f_50441_, TexturedModel.f_125922_, TexturedModel.f_125923_);
      this.m_124589_(Blocks.f_50283_, TexturedModel.f_125922_, TexturedModel.f_125923_);
      this.m_124744_(Blocks.f_50617_, TexturedModel.f_125912_);
      this.m_125000_();
      this.m_124583_(Blocks.f_50717_, TextureMapping::m_125846_);
      this.m_124583_(Blocks.f_50718_, TextureMapping::m_125850_);
      this.m_124553_(Blocks.f_50444_, BlockStateProperties.f_61407_, 0, 1, 2, 3);
      this.m_124553_(Blocks.f_50249_, BlockStateProperties.f_61409_, 0, 0, 1, 1, 2, 2, 2, 3);
      this.m_124553_(Blocks.f_50200_, BlockStateProperties.f_61407_, 0, 1, 1, 2);
      this.m_124553_(Blocks.f_50250_, BlockStateProperties.f_61409_, 0, 0, 1, 1, 2, 2, 2, 3);
      this.m_124553_(Blocks.f_50092_, BlockStateProperties.f_61409_, 0, 1, 2, 3, 4, 5, 6, 7);
      this.m_124690_(ModelLocationUtils.m_125581_("banner"), Blocks.f_50705_).m_125022_(ModelTemplates.f_125663_, Blocks.f_50414_, Blocks.f_50415_, Blocks.f_50416_, Blocks.f_50417_, Blocks.f_50418_, Blocks.f_50419_, Blocks.f_50420_, Blocks.f_50421_, Blocks.f_50422_, Blocks.f_50423_, Blocks.f_50424_, Blocks.f_50425_, Blocks.f_50426_, Blocks.f_50427_, Blocks.f_50428_, Blocks.f_50429_).m_125027_(Blocks.f_50430_, Blocks.f_50431_, Blocks.f_50432_, Blocks.f_50433_, Blocks.f_50434_, Blocks.f_50435_, Blocks.f_50436_, Blocks.f_50437_, Blocks.f_50438_, Blocks.f_50439_, Blocks.f_50388_, Blocks.f_50389_, Blocks.f_50390_, Blocks.f_50391_, Blocks.f_50392_, Blocks.f_50393_);
      this.m_124690_(ModelLocationUtils.m_125581_("bed"), Blocks.f_50705_).m_125027_(Blocks.f_50066_, Blocks.f_50067_, Blocks.f_50068_, Blocks.f_50017_, Blocks.f_50018_, Blocks.f_50019_, Blocks.f_50020_, Blocks.f_50021_, Blocks.f_50022_, Blocks.f_50023_, Blocks.f_50024_, Blocks.f_50025_, Blocks.f_50026_, Blocks.f_50027_, Blocks.f_50028_, Blocks.f_50029_);
      this.m_124962_(Blocks.f_50066_, Blocks.f_50041_);
      this.m_124962_(Blocks.f_50067_, Blocks.f_50042_);
      this.m_124962_(Blocks.f_50068_, Blocks.f_50096_);
      this.m_124962_(Blocks.f_50017_, Blocks.f_50097_);
      this.m_124962_(Blocks.f_50018_, Blocks.f_50098_);
      this.m_124962_(Blocks.f_50019_, Blocks.f_50099_);
      this.m_124962_(Blocks.f_50020_, Blocks.f_50100_);
      this.m_124962_(Blocks.f_50021_, Blocks.f_50101_);
      this.m_124962_(Blocks.f_50022_, Blocks.f_50102_);
      this.m_124962_(Blocks.f_50023_, Blocks.f_50103_);
      this.m_124962_(Blocks.f_50024_, Blocks.f_50104_);
      this.m_124962_(Blocks.f_50025_, Blocks.f_50105_);
      this.m_124962_(Blocks.f_50026_, Blocks.f_50106_);
      this.m_124962_(Blocks.f_50027_, Blocks.f_50107_);
      this.m_124962_(Blocks.f_50028_, Blocks.f_50108_);
      this.m_124962_(Blocks.f_50029_, Blocks.f_50109_);
      this.m_124690_(ModelLocationUtils.m_125581_("skull"), Blocks.f_50135_).m_125022_(ModelTemplates.f_125664_, Blocks.f_50318_, Blocks.f_50316_, Blocks.f_50314_, Blocks.f_50310_, Blocks.f_50312_).m_125025_(Blocks.f_50320_).m_125027_(Blocks.f_50319_, Blocks.f_50321_, Blocks.f_50317_, Blocks.f_50315_, Blocks.f_50311_, Blocks.f_50313_);
      this.m_125010_(Blocks.f_50456_);
      this.m_125010_(Blocks.f_50457_);
      this.m_125010_(Blocks.f_50458_);
      this.m_125010_(Blocks.f_50459_);
      this.m_125010_(Blocks.f_50460_);
      this.m_125010_(Blocks.f_50461_);
      this.m_125010_(Blocks.f_50462_);
      this.m_125010_(Blocks.f_50463_);
      this.m_125010_(Blocks.f_50464_);
      this.m_125010_(Blocks.f_50465_);
      this.m_125010_(Blocks.f_50466_);
      this.m_125010_(Blocks.f_50520_);
      this.m_125010_(Blocks.f_50521_);
      this.m_125010_(Blocks.f_50522_);
      this.m_125010_(Blocks.f_50523_);
      this.m_125010_(Blocks.f_50524_);
      this.m_125010_(Blocks.f_50525_);
      this.m_124794_(Blocks.f_50569_, TexturedModel.f_125916_);
      this.m_124524_(Blocks.f_50569_);
      this.m_124690_(ModelLocationUtils.m_125581_("chest"), Blocks.f_50705_).m_125027_(Blocks.f_50087_, Blocks.f_50325_);
      this.m_124690_(ModelLocationUtils.m_125581_("ender_chest"), Blocks.f_50080_).m_125027_(Blocks.f_50265_);
      this.m_124825_(Blocks.f_50257_, Blocks.f_50080_).m_125025_(Blocks.f_50257_, Blocks.f_50446_);
      this.m_124851_(Blocks.f_152470_);
      this.m_124851_(Blocks.f_152471_);
      this.m_124851_(Blocks.f_50542_);
      this.m_124851_(Blocks.f_50543_);
      this.m_124851_(Blocks.f_50544_);
      this.m_124851_(Blocks.f_50545_);
      this.m_124851_(Blocks.f_50494_);
      this.m_124851_(Blocks.f_50495_);
      this.m_124851_(Blocks.f_50496_);
      this.m_124851_(Blocks.f_50497_);
      this.m_124851_(Blocks.f_50498_);
      this.m_124851_(Blocks.f_50499_);
      this.m_124851_(Blocks.f_50500_);
      this.m_124851_(Blocks.f_50501_);
      this.m_124851_(Blocks.f_50502_);
      this.m_124851_(Blocks.f_50503_);
      this.m_124851_(Blocks.f_50504_);
      this.m_124851_(Blocks.f_50505_);
      this.m_124685_(TexturedModel.f_125905_, Blocks.f_50506_, Blocks.f_50507_, Blocks.f_50508_, Blocks.f_50509_, Blocks.f_50510_, Blocks.f_50511_, Blocks.f_50512_, Blocks.f_50513_, Blocks.f_50514_, Blocks.f_50515_, Blocks.f_50516_, Blocks.f_50517_, Blocks.f_50518_, Blocks.f_50519_, Blocks.f_50573_, Blocks.f_50574_);
      this.m_124851_(Blocks.f_50352_);
      this.m_124851_(Blocks.f_50287_);
      this.m_124851_(Blocks.f_50288_);
      this.m_124851_(Blocks.f_50289_);
      this.m_124851_(Blocks.f_50290_);
      this.m_124851_(Blocks.f_50291_);
      this.m_124851_(Blocks.f_50292_);
      this.m_124851_(Blocks.f_50293_);
      this.m_124851_(Blocks.f_50294_);
      this.m_124851_(Blocks.f_50295_);
      this.m_124851_(Blocks.f_50296_);
      this.m_124851_(Blocks.f_50297_);
      this.m_124851_(Blocks.f_50298_);
      this.m_124851_(Blocks.f_50299_);
      this.m_124851_(Blocks.f_50300_);
      this.m_124851_(Blocks.f_50301_);
      this.m_124851_(Blocks.f_50302_);
      this.m_124851_(Blocks.f_152498_);
      this.m_124878_(Blocks.f_50058_, Blocks.f_50185_);
      this.m_124878_(Blocks.f_50147_, Blocks.f_50303_);
      this.m_124878_(Blocks.f_50148_, Blocks.f_50304_);
      this.m_124878_(Blocks.f_50202_, Blocks.f_50305_);
      this.m_124878_(Blocks.f_50203_, Blocks.f_50306_);
      this.m_124878_(Blocks.f_50204_, Blocks.f_50307_);
      this.m_124878_(Blocks.f_50205_, Blocks.f_50361_);
      this.m_124878_(Blocks.f_50206_, Blocks.f_50362_);
      this.m_124878_(Blocks.f_50207_, Blocks.f_50363_);
      this.m_124878_(Blocks.f_50208_, Blocks.f_50364_);
      this.m_124878_(Blocks.f_50209_, Blocks.f_50365_);
      this.m_124878_(Blocks.f_50210_, Blocks.f_50366_);
      this.m_124878_(Blocks.f_50211_, Blocks.f_50367_);
      this.m_124878_(Blocks.f_50212_, Blocks.f_50368_);
      this.m_124878_(Blocks.f_50213_, Blocks.f_50369_);
      this.m_124878_(Blocks.f_50214_, Blocks.f_50370_);
      this.m_124878_(Blocks.f_50215_, Blocks.f_50371_);
      this.m_124777_(TexturedModel.f_125914_, Blocks.f_50526_, Blocks.f_50527_, Blocks.f_50528_, Blocks.f_50529_, Blocks.f_50530_, Blocks.f_50531_, Blocks.f_50532_, Blocks.f_50533_, Blocks.f_50534_, Blocks.f_50535_, Blocks.f_50536_, Blocks.f_50537_, Blocks.f_50538_, Blocks.f_50539_, Blocks.f_50540_, Blocks.f_50541_);
      this.m_176217_(Blocks.f_50041_, Blocks.f_50336_);
      this.m_176217_(Blocks.f_50042_, Blocks.f_50337_);
      this.m_176217_(Blocks.f_50096_, Blocks.f_50338_);
      this.m_176217_(Blocks.f_50097_, Blocks.f_50339_);
      this.m_176217_(Blocks.f_50098_, Blocks.f_50340_);
      this.m_176217_(Blocks.f_50099_, Blocks.f_50341_);
      this.m_176217_(Blocks.f_50100_, Blocks.f_50342_);
      this.m_176217_(Blocks.f_50101_, Blocks.f_50343_);
      this.m_176217_(Blocks.f_50102_, Blocks.f_50344_);
      this.m_176217_(Blocks.f_50103_, Blocks.f_50345_);
      this.m_176217_(Blocks.f_50104_, Blocks.f_50346_);
      this.m_176217_(Blocks.f_50105_, Blocks.f_50347_);
      this.m_176217_(Blocks.f_50106_, Blocks.f_50348_);
      this.m_176217_(Blocks.f_50107_, Blocks.f_50349_);
      this.m_176217_(Blocks.f_50108_, Blocks.f_50350_);
      this.m_176217_(Blocks.f_50109_, Blocks.f_50351_);
      this.m_124545_(Blocks.f_50035_, Blocks.f_50231_, BlockModelGenerators.TintState.TINTED);
      this.m_124545_(Blocks.f_50111_, Blocks.f_50232_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50112_, Blocks.f_50233_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50113_, Blocks.f_50234_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50114_, Blocks.f_50235_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50115_, Blocks.f_50236_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50116_, Blocks.f_50237_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50117_, Blocks.f_50238_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50118_, Blocks.f_50239_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50119_, Blocks.f_50240_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50120_, Blocks.f_50241_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50121_, Blocks.f_50242_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50071_, Blocks.f_50243_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50070_, Blocks.f_50244_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50073_, Blocks.f_50245_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50072_, Blocks.f_50246_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124545_(Blocks.f_50036_, Blocks.f_50247_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_176088_();
      this.m_124983_(Blocks.f_50180_);
      this.m_124983_(Blocks.f_50181_);
      this.m_124983_(Blocks.f_50182_);
      this.m_124557_(Blocks.f_50034_, BlockModelGenerators.TintState.TINTED);
      this.m_124737_(Blocks.f_50130_, BlockModelGenerators.TintState.TINTED);
      this.m_124517_(Items.f_41909_);
      this.m_124733_(Blocks.f_50575_, Blocks.f_50576_, BlockModelGenerators.TintState.TINTED);
      this.m_124517_(Items.f_41910_);
      this.m_124524_(Blocks.f_50576_);
      this.m_124737_(Blocks.f_152548_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124524_(Blocks.f_152548_);
      this.m_124524_(Blocks.f_152539_);
      this.m_124733_(Blocks.f_50702_, Blocks.f_50703_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124733_(Blocks.f_50704_, Blocks.f_50653_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124575_(Blocks.f_50702_, "_plant");
      this.m_124524_(Blocks.f_50703_);
      this.m_124575_(Blocks.f_50704_, "_plant");
      this.m_124524_(Blocks.f_50653_);
      this.m_124560_(Blocks.f_50570_, BlockModelGenerators.TintState.TINTED, TextureMapping.m_125788_(TextureMapping.m_125753_(Blocks.f_50571_, "_stage0")));
      this.m_124935_();
      this.m_124557_(Blocks.f_50033_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124791_(Blocks.f_50356_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124791_(Blocks.f_50357_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124791_(Blocks.f_50358_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124791_(Blocks.f_50359_, BlockModelGenerators.TintState.TINTED);
      this.m_124791_(Blocks.f_50360_, BlockModelGenerators.TintState.TINTED);
      this.m_124895_();
      this.m_124915_();
      this.m_176241_();
      this.m_124536_(Blocks.f_50594_, Blocks.f_50589_, Blocks.f_50584_, Blocks.f_50579_, Blocks.f_50552_, Blocks.f_50547_, Blocks.f_50562_, Blocks.f_50557_);
      this.m_124536_(Blocks.f_50595_, Blocks.f_50590_, Blocks.f_50585_, Blocks.f_50580_, Blocks.f_50553_, Blocks.f_50548_, Blocks.f_50563_, Blocks.f_50558_);
      this.m_124536_(Blocks.f_50596_, Blocks.f_50591_, Blocks.f_50586_, Blocks.f_50581_, Blocks.f_50554_, Blocks.f_50549_, Blocks.f_50564_, Blocks.f_50559_);
      this.m_124536_(Blocks.f_50597_, Blocks.f_50592_, Blocks.f_50587_, Blocks.f_50582_, Blocks.f_50555_, Blocks.f_50550_, Blocks.f_50565_, Blocks.f_50560_);
      this.m_124536_(Blocks.f_50598_, Blocks.f_50593_, Blocks.f_50588_, Blocks.f_50583_, Blocks.f_50556_, Blocks.f_50551_, Blocks.f_50566_, Blocks.f_50561_);
      this.m_124788_(Blocks.f_50190_, Blocks.f_50188_);
      this.m_124788_(Blocks.f_50189_, Blocks.f_50187_);
      this.m_124948_(Blocks.f_50003_).m_125078_(Blocks.f_50003_).m_125074_(Blocks.f_50015_);
      this.m_124948_(Blocks.f_50008_).m_125078_(Blocks.f_50008_).m_125074_(Blocks.f_50048_);
      this.m_124545_(Blocks.f_50750_, Blocks.f_50229_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124794_(Blocks.f_50054_, TexturedModel.f_125918_);
      this.m_124948_(Blocks.f_50001_).m_125078_(Blocks.f_50001_).m_125074_(Blocks.f_50013_);
      this.m_124948_(Blocks.f_50006_).m_125078_(Blocks.f_50006_).m_125074_(Blocks.f_50046_);
      this.m_124545_(Blocks.f_50748_, Blocks.f_50279_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124794_(Blocks.f_50052_, TexturedModel.f_125918_);
      this.m_124948_(Blocks.f_49999_).m_125078_(Blocks.f_49999_).m_125074_(Blocks.f_50011_);
      this.m_124948_(Blocks.f_50010_).m_125078_(Blocks.f_50010_).m_125074_(Blocks.f_50044_);
      this.m_124545_(Blocks.f_50746_, Blocks.f_50277_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124794_(Blocks.f_50050_, TexturedModel.f_125918_);
      this.m_124948_(Blocks.f_50000_).m_125078_(Blocks.f_50000_).m_125074_(Blocks.f_50012_);
      this.m_124948_(Blocks.f_50005_).m_125078_(Blocks.f_50005_).m_125074_(Blocks.f_50045_);
      this.m_124545_(Blocks.f_50747_, Blocks.f_50278_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124794_(Blocks.f_50051_, TexturedModel.f_125918_);
      this.m_124948_(Blocks.f_50004_).m_125078_(Blocks.f_50004_).m_125074_(Blocks.f_50043_);
      this.m_124948_(Blocks.f_50009_).m_125078_(Blocks.f_50009_).m_125074_(Blocks.f_50049_);
      this.m_124545_(Blocks.f_50751_, Blocks.f_50230_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124794_(Blocks.f_50055_, TexturedModel.f_125918_);
      this.m_124948_(Blocks.f_50002_).m_125078_(Blocks.f_50002_).m_125074_(Blocks.f_50014_);
      this.m_124948_(Blocks.f_50007_).m_125078_(Blocks.f_50007_).m_125074_(Blocks.f_50047_);
      this.m_124545_(Blocks.f_50749_, Blocks.f_50280_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124794_(Blocks.f_50053_, TexturedModel.f_125918_);
      this.m_124948_(Blocks.f_50695_).m_125076_(Blocks.f_50695_).m_125074_(Blocks.f_50697_);
      this.m_124948_(Blocks.f_50696_).m_125076_(Blocks.f_50696_).m_125074_(Blocks.f_50698_);
      this.m_124545_(Blocks.f_50700_, Blocks.f_50725_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124970_(Blocks.f_50654_, Blocks.f_50727_);
      this.m_124948_(Blocks.f_50686_).m_125076_(Blocks.f_50686_).m_125074_(Blocks.f_50688_);
      this.m_124948_(Blocks.f_50687_).m_125076_(Blocks.f_50687_).m_125074_(Blocks.f_50689_);
      this.m_124545_(Blocks.f_50691_, Blocks.f_50726_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124970_(Blocks.f_50693_, Blocks.f_50728_);
      this.m_124737_(Blocks.f_50694_, BlockModelGenerators.TintState.NOT_TINTED);
      this.m_124517_(Items.f_41906_);
      this.m_124896_(Blocks.f_50166_);
      this.m_124936_(Blocks.f_50376_);
      this.m_124985_();
      this.m_124968_(Blocks.f_50156_);
      this.m_124974_(Blocks.f_50030_);
      this.m_124974_(Blocks.f_50031_);
      this.m_124974_(Blocks.f_50285_);
      this.m_124982_();
      this.m_124977_(Blocks.f_50272_);
      this.m_124977_(Blocks.f_50447_);
      this.m_124977_(Blocks.f_50448_);
      this.m_124980_(Blocks.f_50322_);
      this.m_124980_(Blocks.f_50323_);
      this.m_124980_(Blocks.f_50324_);
      this.m_124959_();
      this.m_124967_();
      this.m_124856_(Blocks.f_50094_, TexturedModel.f_125911_);
      this.m_124856_(Blocks.f_50620_, TexturedModel.f_125911_);
      this.m_124856_(Blocks.f_50619_, TexturedModel.f_125912_);
      this.m_124979_();
      this.m_124725_();
      this.m_124938_(Blocks.f_50225_, Blocks.f_50179_);
      this.m_124938_(Blocks.f_50652_, Blocks.f_50227_);
      this.m_124938_(Blocks.f_50224_, Blocks.f_50178_);
      this.m_124938_(Blocks.f_50223_, Blocks.f_50177_);
      this.m_124724_();
      this.m_124938_(Blocks.f_50222_, Blocks.f_50176_);
      this.m_176176_();
      SpawnEggItem.m_43233_().forEach((p_176094_) -> {
         this.m_124519_(p_176094_, ModelLocationUtils.m_125583_("template_spawn_egg"));
      });
   }

   private void m_176178_() {
      this.m_124524_(Blocks.f_152480_);

      for(int i = 0; i < 16; ++i) {
         String s = String.format("_%02d", i);
         ModelTemplates.f_125658_.m_125612_(ModelLocationUtils.m_125573_(Items.f_151033_, s), TextureMapping.m_125820_(TextureMapping.m_125745_(Items.f_151033_, s)), this.f_124478_);
      }

   }

   private void m_176244_(Block p_176245_, Block p_176246_) {
      this.m_124517_(p_176245_.m_5456_());
      TextureMapping texturemapping = TextureMapping.m_125776_(TextureMapping.m_125740_(p_176245_));
      TextureMapping texturemapping1 = TextureMapping.m_125776_(TextureMapping.m_125753_(p_176245_, "_lit"));
      ResourceLocation resourcelocation = ModelTemplates.f_176468_.m_125596_(p_176245_, "_one_candle", texturemapping, this.f_124478_);
      ResourceLocation resourcelocation1 = ModelTemplates.f_176469_.m_125596_(p_176245_, "_two_candles", texturemapping, this.f_124478_);
      ResourceLocation resourcelocation2 = ModelTemplates.f_176470_.m_125596_(p_176245_, "_three_candles", texturemapping, this.f_124478_);
      ResourceLocation resourcelocation3 = ModelTemplates.f_176471_.m_125596_(p_176245_, "_four_candles", texturemapping, this.f_124478_);
      ResourceLocation resourcelocation4 = ModelTemplates.f_176468_.m_125596_(p_176245_, "_one_candle_lit", texturemapping1, this.f_124478_);
      ResourceLocation resourcelocation5 = ModelTemplates.f_176469_.m_125596_(p_176245_, "_two_candles_lit", texturemapping1, this.f_124478_);
      ResourceLocation resourcelocation6 = ModelTemplates.f_176470_.m_125596_(p_176245_, "_three_candles_lit", texturemapping1, this.f_124478_);
      ResourceLocation resourcelocation7 = ModelTemplates.f_176471_.m_125596_(p_176245_, "_four_candles_lit", texturemapping1, this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_176245_).m_125271_(PropertyDispatch.m_125296_(BlockStateProperties.f_155994_, BlockStateProperties.f_61443_).m_125350_(1, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation)).m_125350_(2, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation1)).m_125350_(3, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation2)).m_125350_(4, false, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation3)).m_125350_(1, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation4)).m_125350_(2, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation5)).m_125350_(3, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation6)).m_125350_(4, true, Variant.m_125501_().m_125511_(VariantProperties.f_125520_, resourcelocation7))));
      ResourceLocation resourcelocation8 = ModelTemplates.f_176472_.m_125592_(p_176246_, TextureMapping.m_181476_(p_176245_, false), this.f_124478_);
      ResourceLocation resourcelocation9 = ModelTemplates.f_176472_.m_125596_(p_176246_, "_lit", TextureMapping.m_181476_(p_176245_, true), this.f_124478_);
      this.f_124477_.accept(MultiVariantGenerator.m_125254_(p_176246_).m_125271_(m_124622_(BlockStateProperties.f_61443_, resourcelocation9, resourcelocation8)));
   }

   class BlockEntityModelGenerator {
      private final ResourceLocation f_125017_;

      public BlockEntityModelGenerator(ResourceLocation p_125020_, Block p_125021_) {
         this.f_125017_ = ModelTemplates.f_125626_.m_125612_(p_125020_, TextureMapping.m_125834_(p_125021_), BlockModelGenerators.this.f_124478_);
      }

      public BlockModelGenerators.BlockEntityModelGenerator m_125025_(Block... p_125026_) {
         for(Block block : p_125026_) {
            BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124859_(block, this.f_125017_));
         }

         return this;
      }

      public BlockModelGenerators.BlockEntityModelGenerator m_125027_(Block... p_125028_) {
         for(Block block : p_125028_) {
            BlockModelGenerators.this.m_124524_(block);
         }

         return this.m_125025_(p_125028_);
      }

      public BlockModelGenerators.BlockEntityModelGenerator m_125022_(ModelTemplate p_125023_, Block... p_125024_) {
         for(Block block : p_125024_) {
            p_125023_.m_125612_(ModelLocationUtils.m_125571_(block.m_5456_()), TextureMapping.m_125834_(block), BlockModelGenerators.this.f_124478_);
         }

         return this.m_125025_(p_125024_);
      }
   }

   class BlockFamilyProvider {
      private final TextureMapping f_125030_;
      private final Map<ModelTemplate, ResourceLocation> f_176254_ = Maps.newHashMap();
      @Nullable
      private BlockFamily f_176255_;
      @Nullable
      private ResourceLocation f_125031_;

      public BlockFamilyProvider(TextureMapping p_125034_) {
         this.f_125030_ = p_125034_;
      }

      public BlockModelGenerators.BlockFamilyProvider m_125040_(Block p_125041_, ModelTemplate p_125042_) {
         this.f_125031_ = p_125042_.m_125592_(p_125041_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         if (BlockModelGenerators.this.f_176081_.containsKey(p_125041_)) {
            BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.this.f_176081_.get(p_125041_).m_176277_(p_125041_, this.f_125031_, this.f_125030_, BlockModelGenerators.this.f_124478_));
         } else {
            BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124859_(p_125041_, this.f_125031_));
         }

         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider m_176264_(Block... p_176265_) {
         if (this.f_125031_ == null) {
            throw new IllegalStateException("Full block not generated yet");
         } else {
            for(Block block : p_176265_) {
               BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124859_(block, this.f_125031_));
               BlockModelGenerators.this.m_124797_(block, this.f_125031_);
            }

            return this;
         }
      }

      public BlockModelGenerators.BlockFamilyProvider m_125035_(Block p_125036_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125701_.m_125592_(p_125036_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation1 = ModelTemplates.f_125702_.m_125592_(p_125036_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124884_(p_125036_, resourcelocation, resourcelocation1));
         ResourceLocation resourcelocation2 = ModelTemplates.f_125703_.m_125592_(p_125036_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.m_124797_(p_125036_, resourcelocation2);
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider m_125045_(Block p_125046_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125711_.m_125592_(p_125046_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation1 = ModelTemplates.f_125712_.m_125592_(p_125046_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation2 = ModelTemplates.f_125713_.m_125592_(p_125046_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124838_(p_125046_, resourcelocation, resourcelocation1, resourcelocation2));
         ResourceLocation resourcelocation3 = ModelTemplates.f_125714_.m_125592_(p_125046_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.m_124797_(p_125046_, resourcelocation3);
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider m_125047_(Block p_125048_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125708_.m_125592_(p_125048_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation1 = ModelTemplates.f_125709_.m_125592_(p_125048_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124904_(p_125048_, resourcelocation, resourcelocation1));
         ResourceLocation resourcelocation2 = ModelTemplates.f_125710_.m_125592_(p_125048_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.m_124797_(p_125048_, resourcelocation2);
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider m_125049_(Block p_125050_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125621_.m_125592_(p_125050_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation1 = ModelTemplates.f_125715_.m_125592_(p_125050_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation2 = ModelTemplates.f_125623_.m_125592_(p_125050_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation3 = ModelTemplates.f_125622_.m_125592_(p_125050_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124809_(p_125050_, resourcelocation, resourcelocation1, resourcelocation2, resourcelocation3));
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider m_125051_(Block p_125052_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125624_.m_125592_(p_125052_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation1 = ModelTemplates.f_125625_.m_125592_(p_125052_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124941_(p_125052_, resourcelocation, resourcelocation1));
         return this;
      }

      public BlockModelGenerators.BlockFamilyProvider m_176269_(Block p_176270_) {
         if (this.f_176255_ == null) {
            throw new IllegalStateException("Family not defined");
         } else {
            Block block = this.f_176255_.m_175954_().get(BlockFamily.Variant.WALL_SIGN);
            ResourceLocation resourcelocation = ModelTemplates.f_125626_.m_125592_(p_176270_, this.f_125030_, BlockModelGenerators.this.f_124478_);
            BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124859_(p_176270_, resourcelocation));
            BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124859_(block, resourcelocation));
            BlockModelGenerators.this.m_124517_(p_176270_.m_5456_());
            BlockModelGenerators.this.m_124524_(block);
            return this;
         }
      }

      public BlockModelGenerators.BlockFamilyProvider m_125053_(Block p_125054_) {
         if (this.f_125031_ == null) {
            throw new IllegalStateException("Full block not generated yet");
         } else {
            ResourceLocation resourcelocation = this.m_176261_(ModelTemplates.f_125627_, p_125054_);
            ResourceLocation resourcelocation1 = this.m_176261_(ModelTemplates.f_125628_, p_125054_);
            BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124928_(p_125054_, resourcelocation, resourcelocation1, this.f_125031_));
            BlockModelGenerators.this.m_124797_(p_125054_, resourcelocation);
            return this;
         }
      }

      public BlockModelGenerators.BlockFamilyProvider m_125055_(Block p_125056_) {
         ResourceLocation resourcelocation = this.m_176261_(ModelTemplates.f_125631_, p_125056_);
         ResourceLocation resourcelocation1 = this.m_176261_(ModelTemplates.f_125630_, p_125056_);
         ResourceLocation resourcelocation2 = this.m_176261_(ModelTemplates.f_125632_, p_125056_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124866_(p_125056_, resourcelocation, resourcelocation1, resourcelocation2));
         BlockModelGenerators.this.m_124797_(p_125056_, resourcelocation1);
         return this;
      }

      private BlockModelGenerators.BlockFamilyProvider m_176271_(Block p_176272_) {
         TexturedModel texturedmodel = BlockModelGenerators.this.f_176082_.getOrDefault(p_176272_, TexturedModel.f_125905_.m_125964_(p_176272_));
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124859_(p_176272_, texturedmodel.m_125937_(p_176272_, BlockModelGenerators.this.f_124478_)));
         return this;
      }

      private BlockModelGenerators.BlockFamilyProvider m_176273_(Block p_176274_) {
         BlockModelGenerators.this.m_124896_(p_176274_);
         return this;
      }

      private void m_176275_(Block p_176276_) {
         if (BlockModelGenerators.this.f_176080_.contains(p_176276_)) {
            BlockModelGenerators.this.m_124936_(p_176276_);
         } else {
            BlockModelGenerators.this.m_124916_(p_176276_);
         }

      }

      private ResourceLocation m_176261_(ModelTemplate p_176262_, Block p_176263_) {
         return this.f_176254_.computeIfAbsent(p_176262_, (p_176268_) -> {
            return p_176268_.m_125592_(p_176263_, this.f_125030_, BlockModelGenerators.this.f_124478_);
         });
      }

      public BlockModelGenerators.BlockFamilyProvider m_176259_(BlockFamily p_176260_) {
         this.f_176255_ = p_176260_;
         p_176260_.m_175954_().forEach((p_176257_, p_176258_) -> {
            BiConsumer<BlockModelGenerators.BlockFamilyProvider, Block> biconsumer = BlockModelGenerators.f_176083_.get(p_176257_);
            if (biconsumer != null) {
               biconsumer.accept(this, p_176258_);
            }

         });
         return this;
      }
   }

   @FunctionalInterface
   interface BlockStateGeneratorSupplier {
      BlockStateGenerator m_176277_(Block p_176278_, ResourceLocation p_176279_, TextureMapping p_176280_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_176281_);
   }

   static enum TintState {
      TINTED,
      NOT_TINTED;

      public ModelTemplate m_125064_() {
         return this == TINTED ? ModelTemplates.f_125640_ : ModelTemplates.f_125639_;
      }

      public ModelTemplate m_125065_() {
         return this == TINTED ? ModelTemplates.f_125642_ : ModelTemplates.f_125641_;
      }
   }

   class WoodProvider {
      private final TextureMapping f_125070_;

      public WoodProvider(TextureMapping p_125073_) {
         this.f_125070_ = p_125073_;
      }

      public BlockModelGenerators.WoodProvider m_125074_(Block p_125075_) {
         TextureMapping texturemapping = this.f_125070_.m_125785_(TextureSlot.f_125870_, this.f_125070_.m_125756_(TextureSlot.f_125875_));
         ResourceLocation resourcelocation = ModelTemplates.f_125694_.m_125592_(p_125075_, texturemapping, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124881_(p_125075_, resourcelocation));
         return this;
      }

      public BlockModelGenerators.WoodProvider m_125076_(Block p_125077_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125694_.m_125592_(p_125077_, this.f_125070_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124881_(p_125077_, resourcelocation));
         return this;
      }

      public BlockModelGenerators.WoodProvider m_125078_(Block p_125079_) {
         ResourceLocation resourcelocation = ModelTemplates.f_125694_.m_125592_(p_125079_, this.f_125070_, BlockModelGenerators.this.f_124478_);
         ResourceLocation resourcelocation1 = ModelTemplates.f_125695_.m_125592_(p_125079_, this.f_125070_, BlockModelGenerators.this.f_124478_);
         BlockModelGenerators.this.f_124477_.accept(BlockModelGenerators.m_124924_(p_125079_, resourcelocation, resourcelocation1));
         return this;
      }
   }
}
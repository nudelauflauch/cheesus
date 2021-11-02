package net.minecraft.world.entity.npc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class VillagerTrades {
   private static final int f_150029_ = 12;
   private static final int f_150030_ = 16;
   private static final int f_150031_ = 3;
   private static final int f_150032_ = 1;
   private static final int f_150033_ = 2;
   private static final int f_150034_ = 5;
   private static final int f_150035_ = 10;
   private static final int f_150036_ = 10;
   private static final int f_150037_ = 20;
   private static final int f_150038_ = 15;
   private static final int f_150039_ = 30;
   private static final int f_150040_ = 30;
   private static final float f_150041_ = 0.05F;
   private static final float f_150042_ = 0.2F;
   public static final Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ItemListing[]>> f_35627_ = Util.m_137469_(Maps.newHashMap(), (p_35633_) -> {
      p_35633_.put(VillagerProfession.f_35590_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42405_, 20, 16, 2), new VillagerTrades.EmeraldForItems(Items.f_42620_, 26, 16, 2), new VillagerTrades.EmeraldForItems(Items.f_42619_, 22, 16, 2), new VillagerTrades.EmeraldForItems(Items.f_42732_, 15, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42406_, 1, 6, 16, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Blocks.f_50133_, 6, 12, 10), new VillagerTrades.ItemsForEmeralds(Items.f_42687_, 1, 4, 5), new VillagerTrades.ItemsForEmeralds(Items.f_42410_, 1, 4, 16, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42572_, 3, 18, 10), new VillagerTrades.EmeraldForItems(Blocks.f_50186_, 4, 12, 20)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Blocks.f_50145_, 1, 1, 12, 15), new VillagerTrades.SuspisciousStewForEmerald(MobEffects.f_19611_, 100, 15), new VillagerTrades.SuspisciousStewForEmerald(MobEffects.f_19603_, 160, 15), new VillagerTrades.SuspisciousStewForEmerald(MobEffects.f_19613_, 140, 15), new VillagerTrades.SuspisciousStewForEmerald(MobEffects.f_19610_, 120, 15), new VillagerTrades.SuspisciousStewForEmerald(MobEffects.f_19614_, 280, 15), new VillagerTrades.SuspisciousStewForEmerald(MobEffects.f_19618_, 7, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42677_, 3, 3, 30), new VillagerTrades.ItemsForEmeralds(Items.f_42546_, 4, 3, 30)})));
      p_35633_.put(VillagerProfession.f_35591_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42401_, 20, 16, 2), new VillagerTrades.EmeraldForItems(Items.f_42413_, 10, 16, 2), new VillagerTrades.ItemsAndEmeraldsToItems(Items.f_42526_, 6, Items.f_42530_, 6, 16, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42458_, 3, 1, 16, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42526_, 15, 16, 10), new VillagerTrades.ItemsAndEmeraldsToItems(Items.f_42527_, 6, Items.f_42531_, 6, 16, 5), new VillagerTrades.ItemsForEmeralds(Items.f_42781_, 2, 1, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42527_, 13, 16, 20), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42523_, 3, 3, 10, 0.2F)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42528_, 6, 12, 30)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42529_, 4, 12, 30), new VillagerTrades.EmeraldsForVillagerTypeItem(1, 12, 30, ImmutableMap.<VillagerType, Item>builder().put(VillagerType.f_35821_, Items.f_42453_).put(VillagerType.f_35825_, Items.f_42742_).put(VillagerType.f_35823_, Items.f_42742_).put(VillagerType.f_35819_, Items.f_42744_).put(VillagerType.f_35820_, Items.f_42744_).put(VillagerType.f_35822_, Items.f_42745_).put(VillagerType.f_35824_, Items.f_42746_).build())})));
      p_35633_.put(VillagerProfession.f_35597_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Blocks.f_50041_, 18, 16, 2), new VillagerTrades.EmeraldForItems(Blocks.f_50106_, 18, 16, 2), new VillagerTrades.EmeraldForItems(Blocks.f_50109_, 18, 16, 2), new VillagerTrades.EmeraldForItems(Blocks.f_50101_, 18, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42574_, 2, 1, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42535_, 12, 16, 10), new VillagerTrades.EmeraldForItems(Items.f_42490_, 12, 16, 10), new VillagerTrades.EmeraldForItems(Items.f_42498_, 12, 16, 10), new VillagerTrades.EmeraldForItems(Items.f_42538_, 12, 16, 10), new VillagerTrades.EmeraldForItems(Items.f_42540_, 12, 16, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50041_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50042_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50096_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50097_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50098_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50099_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50100_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50101_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50102_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50103_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50104_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50105_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50106_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50107_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50108_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50109_, 1, 1, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50336_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50337_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50338_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50339_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50340_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50341_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50342_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50343_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50344_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50345_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50346_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50347_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50348_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50349_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50350_, 1, 4, 16, 5), new VillagerTrades.ItemsForEmeralds(Blocks.f_50351_, 1, 4, 16, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42539_, 12, 16, 20), new VillagerTrades.EmeraldForItems(Items.f_42491_, 12, 16, 20), new VillagerTrades.EmeraldForItems(Items.f_42536_, 12, 16, 20), new VillagerTrades.EmeraldForItems(Items.f_42497_, 12, 16, 20), new VillagerTrades.EmeraldForItems(Items.f_42489_, 12, 16, 20), new VillagerTrades.ItemsForEmeralds(Blocks.f_50066_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50018_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50028_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50029_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50025_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50026_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50023_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50021_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50027_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50017_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50022_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50019_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50068_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50067_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50020_, 3, 1, 12, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50024_, 3, 1, 12, 10)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42495_, 12, 16, 30), new VillagerTrades.EmeraldForItems(Items.f_42493_, 12, 16, 30), new VillagerTrades.EmeraldForItems(Items.f_42494_, 12, 16, 30), new VillagerTrades.EmeraldForItems(Items.f_42496_, 12, 16, 30), new VillagerTrades.EmeraldForItems(Items.f_42537_, 12, 16, 30), new VillagerTrades.EmeraldForItems(Items.f_42492_, 12, 16, 30), new VillagerTrades.ItemsForEmeralds(Items.f_42660_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42671_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42663_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42727_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42666_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42673_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42665_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42667_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42728_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42670_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42662_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42669_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42672_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42664_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42661_, 3, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42668_, 3, 1, 12, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42487_, 2, 3, 30)})));
      p_35633_.put(VillagerProfession.f_35592_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42398_, 32, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42412_, 1, 16, 1), new VillagerTrades.ItemsAndEmeraldsToItems(Blocks.f_49994_, 10, Items.f_42484_, 10, 12, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42484_, 26, 12, 10), new VillagerTrades.ItemsForEmeralds(Items.f_42411_, 2, 1, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42401_, 14, 16, 20), new VillagerTrades.ItemsForEmeralds(Items.f_42717_, 3, 1, 10)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42402_, 24, 16, 30), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42411_, 2, 3, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42109_, 8, 12, 30), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42717_, 3, 3, 15), new VillagerTrades.TippedArrowForItemsAndEmeralds(Items.f_42412_, 5, Items.f_42738_, 5, 2, 12, 30)})));
      p_35633_.put(VillagerProfession.f_35594_, m_35630_(ImmutableMap.<Integer, VillagerTrades.ItemListing[]>builder().put(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42516_, 24, 16, 2), new VillagerTrades.EnchantBookForEmeralds(1), new VillagerTrades.ItemsForEmeralds(Blocks.f_50078_, 9, 1, 12, 1)}).put(2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42517_, 4, 12, 10), new VillagerTrades.EnchantBookForEmeralds(5), new VillagerTrades.ItemsForEmeralds(Items.f_42778_, 1, 1, 5)}).put(3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42532_, 5, 12, 20), new VillagerTrades.EnchantBookForEmeralds(10), new VillagerTrades.ItemsForEmeralds(Items.f_41904_, 1, 4, 10)}).put(4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42614_, 2, 12, 30), new VillagerTrades.EnchantBookForEmeralds(15), new VillagerTrades.ItemsForEmeralds(Items.f_42524_, 5, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42522_, 4, 1, 15)}).put(5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42656_, 20, 1, 30)}).build()));
      p_35633_.put(VillagerProfession.f_35588_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42516_, 24, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42676_, 7, 1, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42027_, 11, 16, 10), new VillagerTrades.TreasureMapForEmeralds(13, StructureFeature.f_67023_, MapDecoration.Type.MONUMENT, 12, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42522_, 1, 12, 20), new VillagerTrades.TreasureMapForEmeralds(14, StructureFeature.f_67015_, MapDecoration.Type.MANSION, 12, 10)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42617_, 7, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42660_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42671_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42663_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42727_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42666_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42673_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42665_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42667_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42728_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42670_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42662_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42669_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42672_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42664_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42661_, 3, 1, 15), new VillagerTrades.ItemsForEmeralds(Items.f_42668_, 3, 1, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42724_, 8, 1, 30)})));
      p_35633_.put(VillagerProfession.f_35589_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42583_, 32, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42451_, 1, 2, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42417_, 3, 12, 10), new VillagerTrades.ItemsForEmeralds(Items.f_42534_, 1, 1, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42648_, 2, 12, 20), new VillagerTrades.ItemsForEmeralds(Blocks.f_50141_, 4, 1, 12, 10)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42355_, 4, 12, 30), new VillagerTrades.EmeraldForItems(Items.f_42590_, 9, 12, 30), new VillagerTrades.ItemsForEmeralds(Items.f_42584_, 5, 1, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42588_, 22, 12, 30), new VillagerTrades.ItemsForEmeralds(Items.f_42612_, 3, 1, 30)})));
      p_35633_.put(VillagerProfession.f_35586_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42413_, 15, 16, 2), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42470_), 7, 1, 12, 1, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42471_), 4, 1, 12, 1, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42468_), 5, 1, 12, 1, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42469_), 9, 1, 12, 1, 0.2F)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42416_, 4, 12, 10), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42777_), 36, 1, 12, 5, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42467_), 1, 1, 12, 5, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42466_), 3, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42448_, 1, 12, 20), new VillagerTrades.EmeraldForItems(Items.f_42415_, 1, 12, 20), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42464_), 1, 1, 12, 10, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42465_), 4, 1, 12, 10, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42740_), 5, 1, 12, 10, 0.2F)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EnchantedItemForEmeralds(Items.f_42474_, 14, 3, 15, 0.2F), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42475_, 8, 3, 15, 0.2F)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EnchantedItemForEmeralds(Items.f_42472_, 8, 3, 30, 0.2F), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42473_, 16, 3, 30, 0.2F)})));
      p_35633_.put(VillagerProfession.f_35599_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42413_, 15, 16, 2), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42386_), 3, 1, 12, 1, 0.2F), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42383_, 2, 3, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42416_, 4, 12, 10), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42777_), 36, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42484_, 24, 12, 20)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42415_, 1, 12, 30), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42391_, 12, 3, 15, 0.2F)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EnchantedItemForEmeralds(Items.f_42388_, 8, 3, 30, 0.2F)})));
      p_35633_.put(VillagerProfession.f_35598_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42413_, 15, 16, 2), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42428_), 1, 1, 12, 1, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42426_), 1, 1, 12, 1, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42427_), 1, 1, 12, 1, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42429_), 1, 1, 12, 1, 0.2F)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42416_, 4, 12, 10), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42777_), 36, 1, 12, 5, 0.2F)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42484_, 30, 12, 20), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42386_, 1, 3, 10, 0.2F), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42384_, 2, 3, 10, 0.2F), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42385_, 3, 3, 10, 0.2F), new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42392_), 4, 1, 3, 10, 0.2F)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42415_, 1, 12, 30), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42391_, 12, 3, 15, 0.2F), new VillagerTrades.EnchantedItemForEmeralds(Items.f_42389_, 5, 3, 15, 0.2F)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EnchantedItemForEmeralds(Items.f_42390_, 13, 3, 30, 0.2F)})));
      p_35633_.put(VillagerProfession.f_35587_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42581_, 14, 16, 2), new VillagerTrades.EmeraldForItems(Items.f_42485_, 7, 16, 2), new VillagerTrades.EmeraldForItems(Items.f_42697_, 4, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42699_, 1, 1, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42413_, 15, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42486_, 1, 5, 16, 5), new VillagerTrades.ItemsForEmeralds(Items.f_42582_, 1, 8, 16, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42658_, 7, 16, 20), new VillagerTrades.EmeraldForItems(Items.f_42579_, 10, 16, 20)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42515_, 10, 12, 30)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42780_, 10, 12, 30)})));
      p_35633_.put(VillagerProfession.f_35593_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42454_, 6, 16, 2), new VillagerTrades.DyedArmorForEmeralds(Items.f_42462_, 3), new VillagerTrades.DyedArmorForEmeralds(Items.f_42408_, 7)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42484_, 26, 12, 10), new VillagerTrades.DyedArmorForEmeralds(Items.f_42407_, 5, 12, 5), new VillagerTrades.DyedArmorForEmeralds(Items.f_42463_, 4, 12, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42649_, 9, 12, 20), new VillagerTrades.DyedArmorForEmeralds(Items.f_42408_, 7)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42355_, 4, 12, 30), new VillagerTrades.DyedArmorForEmeralds(Items.f_42654_, 6, 12, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.f_42450_), 6, 1, 12, 30, 0.2F), new VillagerTrades.DyedArmorForEmeralds(Items.f_42407_, 5, 12, 30)})));
      p_35633_.put(VillagerProfession.f_35595_, m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42461_, 10, 16, 2), new VillagerTrades.ItemsForEmeralds(Items.f_42460_, 1, 10, 16, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Blocks.f_50069_, 20, 16, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50225_, 1, 4, 16, 5)}, 3, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Blocks.f_50122_, 16, 16, 20), new VillagerTrades.EmeraldForItems(Blocks.f_50334_, 16, 16, 20), new VillagerTrades.EmeraldForItems(Blocks.f_50228_, 16, 16, 20), new VillagerTrades.ItemsForEmeralds(Blocks.f_152537_, 1, 4, 16, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50387_, 1, 4, 16, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50281_, 1, 4, 16, 10), new VillagerTrades.ItemsForEmeralds(Blocks.f_50175_, 1, 4, 16, 10)}, 4, new VillagerTrades.ItemListing[]{new VillagerTrades.EmeraldForItems(Items.f_42692_, 12, 12, 30), new VillagerTrades.ItemsForEmeralds(Blocks.f_50288_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50287_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50298_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50290_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50294_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50295_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50302_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50301_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50293_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50289_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50292_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50300_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50296_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50297_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50291_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50299_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50527_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50526_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50537_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50529_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50533_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50534_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50541_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50540_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50532_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50528_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50531_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50539_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50535_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50536_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50530_, 1, 1, 12, 15), new VillagerTrades.ItemsForEmeralds(Blocks.f_50538_, 1, 1, 12, 15)}, 5, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Blocks.f_50283_, 1, 1, 12, 30), new VillagerTrades.ItemsForEmeralds(Blocks.f_50333_, 1, 1, 12, 30)})));
   });
   public static final Int2ObjectMap<VillagerTrades.ItemListing[]> f_35628_ = m_35630_(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_41868_, 2, 1, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42518_, 4, 1, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42054_, 2, 1, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42715_, 5, 1, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41865_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41909_, 1, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42046_, 1, 1, 4, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41910_, 3, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41982_, 3, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41939_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41940_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41941_, 1, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41942_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41943_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41944_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41945_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41946_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41947_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41948_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41949_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41950_, 1, 1, 7, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42404_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42733_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42577_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42578_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41827_, 5, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42801_, 5, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41828_, 5, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41826_, 5, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42799_, 5, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42800_, 5, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42497_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42535_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42494_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42489_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42498_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42496_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42491_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42537_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42539_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42490_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42493_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42538_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42540_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42536_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42495_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42492_, 1, 3, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42286_, 3, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42287_, 3, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42288_, 3, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42289_, 3, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42285_, 3, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42029_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41952_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41953_, 1, 1, 12, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42094_, 1, 2, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_151019_, 1, 2, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41830_, 1, 8, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_41831_, 1, 4, 6, 1), new VillagerTrades.ItemsForEmeralds(Items.f_151087_, 1, 2, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_151064_, 1, 2, 5, 1), new VillagerTrades.ItemsForEmeralds(Items.f_151016_, 1, 2, 5, 1)}, 2, new VillagerTrades.ItemListing[]{new VillagerTrades.ItemsForEmeralds(Items.f_42459_, 5, 1, 4, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42456_, 5, 1, 4, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42201_, 3, 1, 6, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42363_, 6, 1, 6, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42403_, 1, 1, 8, 1), new VillagerTrades.ItemsForEmeralds(Items.f_42435_, 3, 3, 6, 1)}));

   private static Int2ObjectMap<VillagerTrades.ItemListing[]> m_35630_(ImmutableMap<Integer, VillagerTrades.ItemListing[]> p_35631_) {
      return new Int2ObjectOpenHashMap<>(p_35631_);
   }

   static class DyedArmorForEmeralds implements VillagerTrades.ItemListing {
      private final Item f_35634_;
      private final int f_35635_;
      private final int f_35636_;
      private final int f_35637_;

      public DyedArmorForEmeralds(Item p_35639_, int p_35640_) {
         this(p_35639_, p_35640_, 12, 1);
      }

      public DyedArmorForEmeralds(Item p_35642_, int p_35643_, int p_35644_, int p_35645_) {
         this.f_35634_ = p_35642_;
         this.f_35635_ = p_35643_;
         this.f_35636_ = p_35644_;
         this.f_35637_ = p_35645_;
      }

      public MerchantOffer m_5670_(Entity p_35647_, Random p_35648_) {
         ItemStack itemstack = new ItemStack(Items.f_42616_, this.f_35635_);
         ItemStack itemstack1 = new ItemStack(this.f_35634_);
         if (this.f_35634_ instanceof DyeableArmorItem) {
            List<DyeItem> list = Lists.newArrayList();
            list.add(m_35649_(p_35648_));
            if (p_35648_.nextFloat() > 0.7F) {
               list.add(m_35649_(p_35648_));
            }

            if (p_35648_.nextFloat() > 0.8F) {
               list.add(m_35649_(p_35648_));
            }

            itemstack1 = DyeableLeatherItem.m_41118_(itemstack1, list);
         }

         return new MerchantOffer(itemstack, itemstack1, this.f_35636_, this.f_35637_, 0.2F);
      }

      private static DyeItem m_35649_(Random p_35650_) {
         return DyeItem.m_41082_(DyeColor.m_41053_(p_35650_.nextInt(16)));
      }
   }

   static class EmeraldForItems implements VillagerTrades.ItemListing {
      private final Item f_35651_;
      private final int f_35652_;
      private final int f_35653_;
      private final int f_35654_;
      private final float f_35655_;

      public EmeraldForItems(ItemLike p_35657_, int p_35658_, int p_35659_, int p_35660_) {
         this.f_35651_ = p_35657_.m_5456_();
         this.f_35652_ = p_35658_;
         this.f_35653_ = p_35659_;
         this.f_35654_ = p_35660_;
         this.f_35655_ = 0.05F;
      }

      public MerchantOffer m_5670_(Entity p_35662_, Random p_35663_) {
         ItemStack itemstack = new ItemStack(this.f_35651_, this.f_35652_);
         return new MerchantOffer(itemstack, new ItemStack(Items.f_42616_), this.f_35653_, this.f_35654_, this.f_35655_);
      }
   }

   static class EmeraldsForVillagerTypeItem implements VillagerTrades.ItemListing {
      private final Map<VillagerType, Item> f_35664_;
      private final int f_35665_;
      private final int f_35666_;
      private final int f_35667_;

      public EmeraldsForVillagerTypeItem(int p_35669_, int p_35670_, int p_35671_, Map<VillagerType, Item> p_35672_) {
         Registry.f_122868_.m_123024_().filter((p_35680_) -> {
            return !p_35672_.containsKey(p_35680_);
         }).findAny().ifPresent((p_35677_) -> {
            throw new IllegalStateException("Missing trade for villager type: " + Registry.f_122868_.m_7981_(p_35677_));
         });
         this.f_35664_ = p_35672_;
         this.f_35665_ = p_35669_;
         this.f_35666_ = p_35670_;
         this.f_35667_ = p_35671_;
      }

      @Nullable
      public MerchantOffer m_5670_(Entity p_35674_, Random p_35675_) {
         if (p_35674_ instanceof VillagerDataHolder) {
            ItemStack itemstack = new ItemStack(this.f_35664_.get(((VillagerDataHolder)p_35674_).m_7141_().m_35560_()), this.f_35665_);
            return new MerchantOffer(itemstack, new ItemStack(Items.f_42616_), this.f_35666_, this.f_35667_, 0.05F);
         } else {
            return null;
         }
      }
   }

   static class EnchantBookForEmeralds implements VillagerTrades.ItemListing {
      private final int f_35681_;

      public EnchantBookForEmeralds(int p_35683_) {
         this.f_35681_ = p_35683_;
      }

      public MerchantOffer m_5670_(Entity p_35685_, Random p_35686_) {
         List<Enchantment> list = Registry.f_122825_.m_123024_().filter(Enchantment::m_6594_).collect(Collectors.toList());
         Enchantment enchantment = list.get(p_35686_.nextInt(list.size()));
         int i = Mth.m_14072_(p_35686_, enchantment.m_44702_(), enchantment.m_6586_());
         ItemStack itemstack = EnchantedBookItem.m_41161_(new EnchantmentInstance(enchantment, i));
         int j = 2 + p_35686_.nextInt(5 + i * 10) + 3 * i;
         if (enchantment.m_6591_()) {
            j *= 2;
         }

         if (j > 64) {
            j = 64;
         }

         return new MerchantOffer(new ItemStack(Items.f_42616_, j), new ItemStack(Items.f_42517_), itemstack, 12, this.f_35681_, 0.2F);
      }
   }

   static class EnchantedItemForEmeralds implements VillagerTrades.ItemListing {
      private final ItemStack f_35687_;
      private final int f_35688_;
      private final int f_35689_;
      private final int f_35690_;
      private final float f_35691_;

      public EnchantedItemForEmeralds(Item p_35693_, int p_35694_, int p_35695_, int p_35696_) {
         this(p_35693_, p_35694_, p_35695_, p_35696_, 0.05F);
      }

      public EnchantedItemForEmeralds(Item p_35698_, int p_35699_, int p_35700_, int p_35701_, float p_35702_) {
         this.f_35687_ = new ItemStack(p_35698_);
         this.f_35688_ = p_35699_;
         this.f_35689_ = p_35700_;
         this.f_35690_ = p_35701_;
         this.f_35691_ = p_35702_;
      }

      public MerchantOffer m_5670_(Entity p_35704_, Random p_35705_) {
         int i = 5 + p_35705_.nextInt(15);
         ItemStack itemstack = EnchantmentHelper.m_44877_(p_35705_, new ItemStack(this.f_35687_.m_41720_()), i, false);
         int j = Math.min(this.f_35688_ + i, 64);
         ItemStack itemstack1 = new ItemStack(Items.f_42616_, j);
         return new MerchantOffer(itemstack1, itemstack, this.f_35689_, this.f_35690_, this.f_35691_);
      }
   }

   public interface ItemListing {
      @Nullable
      MerchantOffer m_5670_(Entity p_35706_, Random p_35707_);
   }

   static class ItemsAndEmeraldsToItems implements VillagerTrades.ItemListing {
      private final ItemStack f_35708_;
      private final int f_35709_;
      private final int f_35710_;
      private final ItemStack f_35711_;
      private final int f_35712_;
      private final int f_35713_;
      private final int f_35714_;
      private final float f_35715_;

      public ItemsAndEmeraldsToItems(ItemLike p_35725_, int p_35726_, Item p_35727_, int p_35728_, int p_35729_, int p_35730_) {
         this(p_35725_, p_35726_, 1, p_35727_, p_35728_, p_35729_, p_35730_);
      }

      public ItemsAndEmeraldsToItems(ItemLike p_35717_, int p_35718_, int p_35719_, Item p_35720_, int p_35721_, int p_35722_, int p_35723_) {
         this.f_35708_ = new ItemStack(p_35717_);
         this.f_35709_ = p_35718_;
         this.f_35710_ = p_35719_;
         this.f_35711_ = new ItemStack(p_35720_);
         this.f_35712_ = p_35721_;
         this.f_35713_ = p_35722_;
         this.f_35714_ = p_35723_;
         this.f_35715_ = 0.05F;
      }

      @Nullable
      public MerchantOffer m_5670_(Entity p_35732_, Random p_35733_) {
         return new MerchantOffer(new ItemStack(Items.f_42616_, this.f_35710_), new ItemStack(this.f_35708_.m_41720_(), this.f_35709_), new ItemStack(this.f_35711_.m_41720_(), this.f_35712_), this.f_35713_, this.f_35714_, this.f_35715_);
      }
   }

   static class ItemsForEmeralds implements VillagerTrades.ItemListing {
      private final ItemStack f_35734_;
      private final int f_35735_;
      private final int f_35736_;
      private final int f_35737_;
      private final int f_35738_;
      private final float f_35739_;

      public ItemsForEmeralds(Block p_35765_, int p_35766_, int p_35767_, int p_35768_, int p_35769_) {
         this(new ItemStack(p_35765_), p_35766_, p_35767_, p_35768_, p_35769_);
      }

      public ItemsForEmeralds(Item p_35741_, int p_35742_, int p_35743_, int p_35744_) {
         this(new ItemStack(p_35741_), p_35742_, p_35743_, 12, p_35744_);
      }

      public ItemsForEmeralds(Item p_35746_, int p_35747_, int p_35748_, int p_35749_, int p_35750_) {
         this(new ItemStack(p_35746_), p_35747_, p_35748_, p_35749_, p_35750_);
      }

      public ItemsForEmeralds(ItemStack p_35752_, int p_35753_, int p_35754_, int p_35755_, int p_35756_) {
         this(p_35752_, p_35753_, p_35754_, p_35755_, p_35756_, 0.05F);
      }

      public ItemsForEmeralds(ItemStack p_35758_, int p_35759_, int p_35760_, int p_35761_, int p_35762_, float p_35763_) {
         this.f_35734_ = p_35758_;
         this.f_35735_ = p_35759_;
         this.f_35736_ = p_35760_;
         this.f_35737_ = p_35761_;
         this.f_35738_ = p_35762_;
         this.f_35739_ = p_35763_;
      }

      public MerchantOffer m_5670_(Entity p_35771_, Random p_35772_) {
         return new MerchantOffer(new ItemStack(Items.f_42616_, this.f_35735_), new ItemStack(this.f_35734_.m_41720_(), this.f_35736_), this.f_35737_, this.f_35738_, this.f_35739_);
      }
   }

   static class SuspisciousStewForEmerald implements VillagerTrades.ItemListing {
      final MobEffect f_35773_;
      final int f_35774_;
      final int f_35775_;
      private final float f_35776_;

      public SuspisciousStewForEmerald(MobEffect p_35778_, int p_35779_, int p_35780_) {
         this.f_35773_ = p_35778_;
         this.f_35774_ = p_35779_;
         this.f_35775_ = p_35780_;
         this.f_35776_ = 0.05F;
      }

      @Nullable
      public MerchantOffer m_5670_(Entity p_35782_, Random p_35783_) {
         ItemStack itemstack = new ItemStack(Items.f_42718_, 1);
         SuspiciousStewItem.m_43258_(itemstack, this.f_35773_, this.f_35774_);
         return new MerchantOffer(new ItemStack(Items.f_42616_, 1), itemstack, 12, this.f_35775_, this.f_35776_);
      }
   }

   static class TippedArrowForItemsAndEmeralds implements VillagerTrades.ItemListing {
      private final ItemStack f_35784_;
      private final int f_35785_;
      private final int f_35786_;
      private final int f_35787_;
      private final int f_35788_;
      private final Item f_35789_;
      private final int f_35790_;
      private final float f_35791_;

      public TippedArrowForItemsAndEmeralds(Item p_35793_, int p_35794_, Item p_35795_, int p_35796_, int p_35797_, int p_35798_, int p_35799_) {
         this.f_35784_ = new ItemStack(p_35795_);
         this.f_35786_ = p_35797_;
         this.f_35787_ = p_35798_;
         this.f_35788_ = p_35799_;
         this.f_35789_ = p_35793_;
         this.f_35790_ = p_35794_;
         this.f_35785_ = p_35796_;
         this.f_35791_ = 0.05F;
      }

      public MerchantOffer m_5670_(Entity p_35801_, Random p_35802_) {
         ItemStack itemstack = new ItemStack(Items.f_42616_, this.f_35786_);
         List<Potion> list = Registry.f_122828_.m_123024_().filter((p_35804_) -> {
            return !p_35804_.m_43488_().isEmpty() && PotionBrewing.m_43511_(p_35804_);
         }).collect(Collectors.toList());
         Potion potion = list.get(p_35802_.nextInt(list.size()));
         ItemStack itemstack1 = PotionUtils.m_43549_(new ItemStack(this.f_35784_.m_41720_(), this.f_35785_), potion);
         return new MerchantOffer(itemstack, new ItemStack(this.f_35789_, this.f_35790_), itemstack1, this.f_35787_, this.f_35788_, this.f_35791_);
      }
   }

   static class TreasureMapForEmeralds implements VillagerTrades.ItemListing {
      private final int f_35805_;
      private final StructureFeature<?> f_35806_;
      private final MapDecoration.Type f_35807_;
      private final int f_35808_;
      private final int f_35809_;

      public TreasureMapForEmeralds(int p_35811_, StructureFeature<?> p_35812_, MapDecoration.Type p_35813_, int p_35814_, int p_35815_) {
         this.f_35805_ = p_35811_;
         this.f_35806_ = p_35812_;
         this.f_35807_ = p_35813_;
         this.f_35808_ = p_35814_;
         this.f_35809_ = p_35815_;
      }

      @Nullable
      public MerchantOffer m_5670_(Entity p_35817_, Random p_35818_) {
         if (!(p_35817_.f_19853_ instanceof ServerLevel)) {
            return null;
         } else {
            ServerLevel serverlevel = (ServerLevel)p_35817_.f_19853_;
            BlockPos blockpos = serverlevel.m_8717_(this.f_35806_, p_35817_.m_142538_(), 100, true);
            if (blockpos != null) {
               ItemStack itemstack = MapItem.m_42886_(serverlevel, blockpos.m_123341_(), blockpos.m_123343_(), (byte)2, true, true);
               MapItem.m_42850_(serverlevel, itemstack);
               MapItemSavedData.m_77925_(itemstack, blockpos, "+", this.f_35807_);
               itemstack.m_41714_(new TranslatableComponent("filled_map." + this.f_35806_.m_67098_().toLowerCase(Locale.ROOT)));
               return new MerchantOffer(new ItemStack(Items.f_42616_, this.f_35805_), new ItemStack(Items.f_42522_), itemstack, this.f_35808_, this.f_35809_, 0.2F);
            } else {
               return null;
            }
         }
      }
   }
}
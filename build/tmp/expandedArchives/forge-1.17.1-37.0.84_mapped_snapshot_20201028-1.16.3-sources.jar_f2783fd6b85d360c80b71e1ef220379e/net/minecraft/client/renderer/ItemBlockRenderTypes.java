package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemBlockRenderTypes {
   @Deprecated
   private static final Map<Block, RenderType> f_109275_ = Util.m_137469_(Maps.newHashMap(), (p_109296_) -> {
      RenderType rendertype = RenderType.m_110503_();
      p_109296_.put(Blocks.f_50267_, rendertype);
      RenderType rendertype1 = RenderType.m_110457_();
      p_109296_.put(Blocks.f_50440_, rendertype1);
      p_109296_.put(Blocks.f_50183_, rendertype1);
      p_109296_.put(Blocks.f_50185_, rendertype1);
      p_109296_.put(Blocks.f_50266_, rendertype1);
      p_109296_.put(Blocks.f_50332_, rendertype1);
      p_109296_.put(Blocks.f_50184_, rendertype1);
      p_109296_.put(Blocks.f_50053_, rendertype1);
      p_109296_.put(Blocks.f_50050_, rendertype1);
      p_109296_.put(Blocks.f_50051_, rendertype1);
      p_109296_.put(Blocks.f_50054_, rendertype1);
      p_109296_.put(Blocks.f_50052_, rendertype1);
      p_109296_.put(Blocks.f_50055_, rendertype1);
      p_109296_.put(Blocks.f_152470_, rendertype1);
      p_109296_.put(Blocks.f_152471_, rendertype1);
      RenderType rendertype2 = RenderType.m_110463_();
      p_109296_.put(Blocks.f_50746_, rendertype2);
      p_109296_.put(Blocks.f_50747_, rendertype2);
      p_109296_.put(Blocks.f_50748_, rendertype2);
      p_109296_.put(Blocks.f_50749_, rendertype2);
      p_109296_.put(Blocks.f_50750_, rendertype2);
      p_109296_.put(Blocks.f_50751_, rendertype2);
      p_109296_.put(Blocks.f_50058_, rendertype2);
      p_109296_.put(Blocks.f_50066_, rendertype2);
      p_109296_.put(Blocks.f_50067_, rendertype2);
      p_109296_.put(Blocks.f_50068_, rendertype2);
      p_109296_.put(Blocks.f_50017_, rendertype2);
      p_109296_.put(Blocks.f_50018_, rendertype2);
      p_109296_.put(Blocks.f_50019_, rendertype2);
      p_109296_.put(Blocks.f_50020_, rendertype2);
      p_109296_.put(Blocks.f_50021_, rendertype2);
      p_109296_.put(Blocks.f_50022_, rendertype2);
      p_109296_.put(Blocks.f_50023_, rendertype2);
      p_109296_.put(Blocks.f_50024_, rendertype2);
      p_109296_.put(Blocks.f_50025_, rendertype2);
      p_109296_.put(Blocks.f_50026_, rendertype2);
      p_109296_.put(Blocks.f_50027_, rendertype2);
      p_109296_.put(Blocks.f_50028_, rendertype2);
      p_109296_.put(Blocks.f_50029_, rendertype2);
      p_109296_.put(Blocks.f_50030_, rendertype2);
      p_109296_.put(Blocks.f_50031_, rendertype2);
      p_109296_.put(Blocks.f_50033_, rendertype2);
      p_109296_.put(Blocks.f_50034_, rendertype2);
      p_109296_.put(Blocks.f_50035_, rendertype2);
      p_109296_.put(Blocks.f_50036_, rendertype2);
      p_109296_.put(Blocks.f_50037_, rendertype2);
      p_109296_.put(Blocks.f_50038_, rendertype2);
      p_109296_.put(Blocks.f_50111_, rendertype2);
      p_109296_.put(Blocks.f_50112_, rendertype2);
      p_109296_.put(Blocks.f_50113_, rendertype2);
      p_109296_.put(Blocks.f_50114_, rendertype2);
      p_109296_.put(Blocks.f_50115_, rendertype2);
      p_109296_.put(Blocks.f_50116_, rendertype2);
      p_109296_.put(Blocks.f_50117_, rendertype2);
      p_109296_.put(Blocks.f_50118_, rendertype2);
      p_109296_.put(Blocks.f_50119_, rendertype2);
      p_109296_.put(Blocks.f_50120_, rendertype2);
      p_109296_.put(Blocks.f_50121_, rendertype2);
      p_109296_.put(Blocks.f_50070_, rendertype2);
      p_109296_.put(Blocks.f_50071_, rendertype2);
      p_109296_.put(Blocks.f_50072_, rendertype2);
      p_109296_.put(Blocks.f_50073_, rendertype2);
      p_109296_.put(Blocks.f_50081_, rendertype2);
      p_109296_.put(Blocks.f_50082_, rendertype2);
      p_109296_.put(Blocks.f_50139_, rendertype2);
      p_109296_.put(Blocks.f_50140_, rendertype2);
      p_109296_.put(Blocks.f_50083_, rendertype2);
      p_109296_.put(Blocks.f_50084_, rendertype2);
      p_109296_.put(Blocks.f_50085_, rendertype2);
      p_109296_.put(Blocks.f_50088_, rendertype2);
      p_109296_.put(Blocks.f_50092_, rendertype2);
      p_109296_.put(Blocks.f_50154_, rendertype2);
      p_109296_.put(Blocks.f_50155_, rendertype2);
      p_109296_.put(Blocks.f_50156_, rendertype2);
      p_109296_.put(Blocks.f_50166_, rendertype2);
      p_109296_.put(Blocks.f_50174_, rendertype2);
      p_109296_.put(Blocks.f_50123_, rendertype2);
      p_109296_.put(Blocks.f_50128_, rendertype2);
      p_109296_.put(Blocks.f_50130_, rendertype2);
      p_109296_.put(Blocks.f_50146_, rendertype2);
      p_109296_.put(Blocks.f_50216_, rendertype2);
      p_109296_.put(Blocks.f_50217_, rendertype2);
      p_109296_.put(Blocks.f_50218_, rendertype2);
      p_109296_.put(Blocks.f_50219_, rendertype2);
      p_109296_.put(Blocks.f_50220_, rendertype2);
      p_109296_.put(Blocks.f_50221_, rendertype2);
      p_109296_.put(Blocks.f_50663_, rendertype2);
      p_109296_.put(Blocks.f_50664_, rendertype2);
      p_109296_.put(Blocks.f_50187_, rendertype2);
      p_109296_.put(Blocks.f_50188_, rendertype2);
      p_109296_.put(Blocks.f_50189_, rendertype2);
      p_109296_.put(Blocks.f_50190_, rendertype2);
      p_109296_.put(Blocks.f_50191_, rendertype2);
      p_109296_.put(Blocks.f_152475_, rendertype2);
      p_109296_.put(Blocks.f_50196_, rendertype2);
      p_109296_.put(Blocks.f_50200_, rendertype2);
      p_109296_.put(Blocks.f_50255_, rendertype2);
      p_109296_.put(Blocks.f_50262_, rendertype2);
      p_109296_.put(Blocks.f_50273_, rendertype2);
      p_109296_.put(Blocks.f_50276_, rendertype2);
      p_109296_.put(Blocks.f_50277_, rendertype2);
      p_109296_.put(Blocks.f_50278_, rendertype2);
      p_109296_.put(Blocks.f_50279_, rendertype2);
      p_109296_.put(Blocks.f_50280_, rendertype2);
      p_109296_.put(Blocks.f_50229_, rendertype2);
      p_109296_.put(Blocks.f_50230_, rendertype2);
      p_109296_.put(Blocks.f_50231_, rendertype2);
      p_109296_.put(Blocks.f_50232_, rendertype2);
      p_109296_.put(Blocks.f_50233_, rendertype2);
      p_109296_.put(Blocks.f_50234_, rendertype2);
      p_109296_.put(Blocks.f_50235_, rendertype2);
      p_109296_.put(Blocks.f_50236_, rendertype2);
      p_109296_.put(Blocks.f_50237_, rendertype2);
      p_109296_.put(Blocks.f_50238_, rendertype2);
      p_109296_.put(Blocks.f_50239_, rendertype2);
      p_109296_.put(Blocks.f_50240_, rendertype2);
      p_109296_.put(Blocks.f_50241_, rendertype2);
      p_109296_.put(Blocks.f_50242_, rendertype2);
      p_109296_.put(Blocks.f_50243_, rendertype2);
      p_109296_.put(Blocks.f_50244_, rendertype2);
      p_109296_.put(Blocks.f_50245_, rendertype2);
      p_109296_.put(Blocks.f_50246_, rendertype2);
      p_109296_.put(Blocks.f_50247_, rendertype2);
      p_109296_.put(Blocks.f_50248_, rendertype2);
      p_109296_.put(Blocks.f_152601_, rendertype2);
      p_109296_.put(Blocks.f_152602_, rendertype2);
      p_109296_.put(Blocks.f_50249_, rendertype2);
      p_109296_.put(Blocks.f_50250_, rendertype2);
      p_109296_.put(Blocks.f_50328_, rendertype2);
      p_109296_.put(Blocks.f_50285_, rendertype2);
      p_109296_.put(Blocks.f_50376_, rendertype2);
      p_109296_.put(Blocks.f_50355_, rendertype2);
      p_109296_.put(Blocks.f_50356_, rendertype2);
      p_109296_.put(Blocks.f_50357_, rendertype2);
      p_109296_.put(Blocks.f_50358_, rendertype2);
      p_109296_.put(Blocks.f_50359_, rendertype2);
      p_109296_.put(Blocks.f_50360_, rendertype2);
      p_109296_.put(Blocks.f_50484_, rendertype2);
      p_109296_.put(Blocks.f_50485_, rendertype2);
      p_109296_.put(Blocks.f_50486_, rendertype2);
      p_109296_.put(Blocks.f_50487_, rendertype2);
      p_109296_.put(Blocks.f_50488_, rendertype2);
      p_109296_.put(Blocks.f_50489_, rendertype2);
      p_109296_.put(Blocks.f_50490_, rendertype2);
      p_109296_.put(Blocks.f_50491_, rendertype2);
      p_109296_.put(Blocks.f_50444_, rendertype2);
      p_109296_.put(Blocks.f_50575_, rendertype2);
      p_109296_.put(Blocks.f_50576_, rendertype2);
      p_109296_.put(Blocks.f_50578_, rendertype2);
      p_109296_.put(Blocks.f_50589_, rendertype2);
      p_109296_.put(Blocks.f_50590_, rendertype2);
      p_109296_.put(Blocks.f_50591_, rendertype2);
      p_109296_.put(Blocks.f_50592_, rendertype2);
      p_109296_.put(Blocks.f_50593_, rendertype2);
      p_109296_.put(Blocks.f_50594_, rendertype2);
      p_109296_.put(Blocks.f_50595_, rendertype2);
      p_109296_.put(Blocks.f_50596_, rendertype2);
      p_109296_.put(Blocks.f_50597_, rendertype2);
      p_109296_.put(Blocks.f_50598_, rendertype2);
      p_109296_.put(Blocks.f_50547_, rendertype2);
      p_109296_.put(Blocks.f_50548_, rendertype2);
      p_109296_.put(Blocks.f_50549_, rendertype2);
      p_109296_.put(Blocks.f_50550_, rendertype2);
      p_109296_.put(Blocks.f_50551_, rendertype2);
      p_109296_.put(Blocks.f_50552_, rendertype2);
      p_109296_.put(Blocks.f_50553_, rendertype2);
      p_109296_.put(Blocks.f_50554_, rendertype2);
      p_109296_.put(Blocks.f_50555_, rendertype2);
      p_109296_.put(Blocks.f_50556_, rendertype2);
      p_109296_.put(Blocks.f_50557_, rendertype2);
      p_109296_.put(Blocks.f_50558_, rendertype2);
      p_109296_.put(Blocks.f_50559_, rendertype2);
      p_109296_.put(Blocks.f_50560_, rendertype2);
      p_109296_.put(Blocks.f_50561_, rendertype2);
      p_109296_.put(Blocks.f_50562_, rendertype2);
      p_109296_.put(Blocks.f_50563_, rendertype2);
      p_109296_.put(Blocks.f_50564_, rendertype2);
      p_109296_.put(Blocks.f_50565_, rendertype2);
      p_109296_.put(Blocks.f_50566_, rendertype2);
      p_109296_.put(Blocks.f_50567_, rendertype2);
      p_109296_.put(Blocks.f_50569_, rendertype2);
      p_109296_.put(Blocks.f_50570_, rendertype2);
      p_109296_.put(Blocks.f_50571_, rendertype2);
      p_109296_.put(Blocks.f_50572_, rendertype2);
      p_109296_.put(Blocks.f_50616_, rendertype2);
      p_109296_.put(Blocks.f_50679_, rendertype2);
      p_109296_.put(Blocks.f_50681_, rendertype2);
      p_109296_.put(Blocks.f_50682_, rendertype2);
      p_109296_.put(Blocks.f_50683_, rendertype2);
      p_109296_.put(Blocks.f_50684_, rendertype2);
      p_109296_.put(Blocks.f_50685_, rendertype2);
      p_109296_.put(Blocks.f_50702_, rendertype2);
      p_109296_.put(Blocks.f_50703_, rendertype2);
      p_109296_.put(Blocks.f_50704_, rendertype2);
      p_109296_.put(Blocks.f_50653_, rendertype2);
      p_109296_.put(Blocks.f_50694_, rendertype2);
      p_109296_.put(Blocks.f_50700_, rendertype2);
      p_109296_.put(Blocks.f_50691_, rendertype2);
      p_109296_.put(Blocks.f_50654_, rendertype2);
      p_109296_.put(Blocks.f_50693_, rendertype2);
      p_109296_.put(Blocks.f_50725_, rendertype2);
      p_109296_.put(Blocks.f_50726_, rendertype2);
      p_109296_.put(Blocks.f_50727_, rendertype2);
      p_109296_.put(Blocks.f_50728_, rendertype2);
      p_109296_.put(Blocks.f_50671_, rendertype2);
      p_109296_.put(Blocks.f_50672_, rendertype2);
      p_109296_.put(Blocks.f_152588_, rendertype2);
      p_109296_.put(Blocks.f_152495_, rendertype2);
      p_109296_.put(Blocks.f_152494_, rendertype2);
      p_109296_.put(Blocks.f_152493_, rendertype2);
      p_109296_.put(Blocks.f_152492_, rendertype2);
      p_109296_.put(Blocks.f_152587_, rendertype2);
      p_109296_.put(Blocks.f_152538_, rendertype2);
      p_109296_.put(Blocks.f_152539_, rendertype2);
      p_109296_.put(Blocks.f_152540_, rendertype2);
      p_109296_.put(Blocks.f_152542_, rendertype2);
      p_109296_.put(Blocks.f_152541_, rendertype2);
      p_109296_.put(Blocks.f_152543_, rendertype2);
      p_109296_.put(Blocks.f_152545_, rendertype2);
      p_109296_.put(Blocks.f_152546_, rendertype2);
      p_109296_.put(Blocks.f_152547_, rendertype2);
      p_109296_.put(Blocks.f_152548_, rendertype2);
      p_109296_.put(Blocks.f_152500_, rendertype2);
      RenderType rendertype3 = RenderType.m_110466_();
      p_109296_.put(Blocks.f_50126_, rendertype3);
      p_109296_.put(Blocks.f_50142_, rendertype3);
      p_109296_.put(Blocks.f_50147_, rendertype3);
      p_109296_.put(Blocks.f_50148_, rendertype3);
      p_109296_.put(Blocks.f_50202_, rendertype3);
      p_109296_.put(Blocks.f_50203_, rendertype3);
      p_109296_.put(Blocks.f_50204_, rendertype3);
      p_109296_.put(Blocks.f_50205_, rendertype3);
      p_109296_.put(Blocks.f_50206_, rendertype3);
      p_109296_.put(Blocks.f_50207_, rendertype3);
      p_109296_.put(Blocks.f_50208_, rendertype3);
      p_109296_.put(Blocks.f_50209_, rendertype3);
      p_109296_.put(Blocks.f_50210_, rendertype3);
      p_109296_.put(Blocks.f_50211_, rendertype3);
      p_109296_.put(Blocks.f_50212_, rendertype3);
      p_109296_.put(Blocks.f_50213_, rendertype3);
      p_109296_.put(Blocks.f_50214_, rendertype3);
      p_109296_.put(Blocks.f_50215_, rendertype3);
      p_109296_.put(Blocks.f_50303_, rendertype3);
      p_109296_.put(Blocks.f_50304_, rendertype3);
      p_109296_.put(Blocks.f_50305_, rendertype3);
      p_109296_.put(Blocks.f_50306_, rendertype3);
      p_109296_.put(Blocks.f_50307_, rendertype3);
      p_109296_.put(Blocks.f_50361_, rendertype3);
      p_109296_.put(Blocks.f_50362_, rendertype3);
      p_109296_.put(Blocks.f_50363_, rendertype3);
      p_109296_.put(Blocks.f_50364_, rendertype3);
      p_109296_.put(Blocks.f_50365_, rendertype3);
      p_109296_.put(Blocks.f_50366_, rendertype3);
      p_109296_.put(Blocks.f_50367_, rendertype3);
      p_109296_.put(Blocks.f_50368_, rendertype3);
      p_109296_.put(Blocks.f_50369_, rendertype3);
      p_109296_.put(Blocks.f_50370_, rendertype3);
      p_109296_.put(Blocks.f_50371_, rendertype3);
      p_109296_.put(Blocks.f_50374_, rendertype3);
      p_109296_.put(Blocks.f_50719_, rendertype3);
      p_109296_.put(Blocks.f_50449_, rendertype3);
      p_109296_.put(Blocks.f_50628_, rendertype3);
      p_109296_.put(Blocks.f_152498_, rendertype3);
   });
   @Deprecated
   private static final Map<Fluid, RenderType> f_109276_ = Util.m_137469_(Maps.newHashMap(), (p_109290_) -> {
      RenderType rendertype = RenderType.m_110466_();
      p_109290_.put(Fluids.f_76192_, rendertype);
      p_109290_.put(Fluids.f_76193_, rendertype);
   });
   private static boolean f_109277_;

   @Deprecated // FORGE: Use canRenderInLayer
   public static RenderType m_109282_(BlockState p_109283_) {
      Block block = p_109283_.m_60734_();
      if (block instanceof LeavesBlock) {
         return f_109277_ ? RenderType.m_110457_() : RenderType.m_110451_();
      } else {
         RenderType rendertype = f_109275_.get(block);
         return rendertype != null ? rendertype : RenderType.m_110451_();
      }
   }

   @Deprecated // FORGE: Use canRenderInLayer
   public static RenderType m_109293_(BlockState p_109294_) {
      Block block = p_109294_.m_60734_();
      if (block instanceof LeavesBlock) {
         return f_109277_ ? RenderType.m_110457_() : RenderType.m_110451_();
      } else {
         RenderType rendertype = f_109275_.get(block);
         if (rendertype != null) {
            return rendertype == RenderType.m_110466_() ? RenderType.m_110469_() : rendertype;
         } else {
            return RenderType.m_110451_();
         }
      }
   }

   public static RenderType m_109284_(BlockState p_109285_, boolean p_109286_) {
      if (canRenderInLayer(p_109285_, RenderType.m_110466_())) {
         if (!Minecraft.m_91085_()) {
            return Sheets.m_110792_();
         } else {
            return p_109286_ ? Sheets.m_110792_() : Sheets.m_110791_();
         }
      } else {
         return Sheets.m_110790_();
      }
   }

   public static RenderType m_109279_(ItemStack p_109280_, boolean p_109281_) {
      Item item = p_109280_.m_41720_();
      if (item instanceof BlockItem) {
         Block block = ((BlockItem)item).m_40614_();
         return m_109284_(block.m_49966_(), p_109281_);
      } else {
         return p_109281_ ? Sheets.m_110792_() : Sheets.m_110791_();
      }
   }

   @Deprecated // FORGE: Use canRenderInLayer
   public static RenderType m_109287_(FluidState p_109288_) {
      RenderType rendertype = f_109276_.get(p_109288_.m_76152_());
      return rendertype != null ? rendertype : RenderType.m_110451_();
   }

   // FORGE START

   private static final Map<net.minecraftforge.registries.IRegistryDelegate<Block>, java.util.function.Predicate<RenderType>> blockRenderChecks = Maps.newHashMap();
   private static final Map<net.minecraftforge.registries.IRegistryDelegate<Fluid>, java.util.function.Predicate<RenderType>> fluidRenderChecks = Maps.newHashMap();
   static {
      f_109275_.forEach(ItemBlockRenderTypes::setRenderLayer);
      f_109276_.forEach(ItemBlockRenderTypes::setRenderLayer);
   }

   public static boolean canRenderInLayer(BlockState state, RenderType type) {
      Block block = state.m_60734_();
      if (block instanceof LeavesBlock) {
         return f_109277_ ? type == RenderType.m_110457_() : type == RenderType.m_110451_();
      } else {
         java.util.function.Predicate<RenderType> rendertype;
         synchronized (ItemBlockRenderTypes.class) {
            rendertype = blockRenderChecks.get(block.delegate);
         }
         return rendertype != null ? rendertype.test(type) : type == RenderType.m_110451_();
      }
   }

   public static boolean canRenderInLayer(FluidState fluid, RenderType type) {
      java.util.function.Predicate<RenderType> rendertype;
      synchronized (ItemBlockRenderTypes.class) {
         rendertype = fluidRenderChecks.get(fluid.m_76152_().delegate);
      }
      return rendertype != null ? rendertype.test(type) : type == RenderType.m_110451_();
   }

   public static void setRenderLayer(Block block, RenderType type) {
      java.util.Objects.requireNonNull(type);
      setRenderLayer(block, type::equals);
   }

   public static synchronized void setRenderLayer(Block block, java.util.function.Predicate<RenderType> predicate) {
      blockRenderChecks.put(block.delegate, predicate);
   }

   public static void setRenderLayer(Fluid fluid, RenderType type) {
      java.util.Objects.requireNonNull(type);
      setRenderLayer(fluid, type::equals);
   }

   public static synchronized void setRenderLayer(Fluid fluid, java.util.function.Predicate<RenderType> predicate) {
      fluidRenderChecks.put(fluid.delegate, predicate);
   }

   public static void m_109291_(boolean p_109292_) {
      f_109277_ = p_109292_;
   }
}

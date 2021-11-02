package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlackstoneReplaceProcessor extends StructureProcessor {
   public static final Codec<BlackstoneReplaceProcessor> f_73993_;
   public static final BlackstoneReplaceProcessor f_73994_ = new BlackstoneReplaceProcessor();
   private final Map<Block, Block> f_73995_ = Util.m_137469_(Maps.newHashMap(), (p_74007_) -> {
      p_74007_.put(Blocks.f_50652_, Blocks.f_50730_);
      p_74007_.put(Blocks.f_50079_, Blocks.f_50730_);
      p_74007_.put(Blocks.f_50069_, Blocks.f_50734_);
      p_74007_.put(Blocks.f_50222_, Blocks.f_50735_);
      p_74007_.put(Blocks.f_50223_, Blocks.f_50735_);
      p_74007_.put(Blocks.f_50157_, Blocks.f_50731_);
      p_74007_.put(Blocks.f_50633_, Blocks.f_50731_);
      p_74007_.put(Blocks.f_50635_, Blocks.f_50707_);
      p_74007_.put(Blocks.f_50194_, Blocks.f_50739_);
      p_74007_.put(Blocks.f_50631_, Blocks.f_50739_);
      p_74007_.put(Blocks.f_50409_, Blocks.f_50733_);
      p_74007_.put(Blocks.f_50647_, Blocks.f_50733_);
      p_74007_.put(Blocks.f_50405_, Blocks.f_50708_);
      p_74007_.put(Blocks.f_50404_, Blocks.f_50708_);
      p_74007_.put(Blocks.f_50411_, Blocks.f_50738_);
      p_74007_.put(Blocks.f_50645_, Blocks.f_50738_);
      p_74007_.put(Blocks.f_50609_, Blocks.f_50740_);
      p_74007_.put(Blocks.f_50607_, Blocks.f_50740_);
      p_74007_.put(Blocks.f_50274_, Blocks.f_50732_);
      p_74007_.put(Blocks.f_50275_, Blocks.f_50732_);
      p_74007_.put(Blocks.f_50225_, Blocks.f_50737_);
      p_74007_.put(Blocks.f_50224_, Blocks.f_50736_);
      p_74007_.put(Blocks.f_50183_, Blocks.f_50184_);
   });

   private BlackstoneReplaceProcessor() {
   }

   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74000_, BlockPos p_74001_, BlockPos p_74002_, StructureTemplate.StructureBlockInfo p_74003_, StructureTemplate.StructureBlockInfo p_74004_, StructurePlaceSettings p_74005_) {
      Block block = this.f_73995_.get(p_74004_.f_74676_.m_60734_());
      if (block == null) {
         return p_74004_;
      } else {
         BlockState blockstate = p_74004_.f_74676_;
         BlockState blockstate1 = block.m_49966_();
         if (blockstate.m_61138_(StairBlock.f_56841_)) {
            blockstate1 = blockstate1.m_61124_(StairBlock.f_56841_, blockstate.m_61143_(StairBlock.f_56841_));
         }

         if (blockstate.m_61138_(StairBlock.f_56842_)) {
            blockstate1 = blockstate1.m_61124_(StairBlock.f_56842_, blockstate.m_61143_(StairBlock.f_56842_));
         }

         if (blockstate.m_61138_(SlabBlock.f_56353_)) {
            blockstate1 = blockstate1.m_61124_(SlabBlock.f_56353_, blockstate.m_61143_(SlabBlock.f_56353_));
         }

         return new StructureTemplate.StructureBlockInfo(p_74004_.f_74675_, blockstate1, p_74004_.f_74677_);
      }
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74463_;
   }

   static {
      f_73993_ = Codec.unit(() -> {
         return f_73994_;
      });
   }
}
package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.BitSet;

public class RegionBitmap {
   private final BitSet f_63608_ = new BitSet();

   public void m_63612_(int p_63613_, int p_63614_) {
      this.f_63608_.set(p_63613_, p_63613_ + p_63614_);
   }

   public void m_63615_(int p_63616_, int p_63617_) {
      this.f_63608_.clear(p_63616_, p_63616_ + p_63617_);
   }

   public int m_63610_(int p_63611_) {
      int i = 0;

      while(true) {
         int j = this.f_63608_.nextClearBit(i);
         int k = this.f_63608_.nextSetBit(j);
         if (k == -1 || k - j >= p_63611_) {
            this.m_63612_(j, p_63611_);
            return j;
         }

         i = k;
      }
   }

   @VisibleForTesting
   public IntSet m_156603_() {
      return this.f_63608_.stream().collect(IntArraySet::new, IntCollection::add, IntCollection::addAll);
   }
}
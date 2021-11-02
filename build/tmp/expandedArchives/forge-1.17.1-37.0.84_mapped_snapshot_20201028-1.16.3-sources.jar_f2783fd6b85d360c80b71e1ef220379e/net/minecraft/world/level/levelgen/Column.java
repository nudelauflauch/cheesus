package net.minecraft.world.level.levelgen;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;

public abstract class Column {
   public static Column.Range m_158164_(int p_158165_, int p_158166_) {
      return new Column.Range(p_158165_ - 1, p_158166_ + 1);
   }

   public static Column.Range m_158188_(int p_158189_, int p_158190_) {
      return new Column.Range(p_158189_, p_158190_);
   }

   public static Column m_158162_(int p_158163_) {
      return new Column.Ray(p_158163_, false);
   }

   public static Column m_158186_(int p_158187_) {
      return new Column.Ray(p_158187_ + 1, false);
   }

   public static Column m_158193_(int p_158194_) {
      return new Column.Ray(p_158194_, true);
   }

   public static Column m_158195_(int p_158196_) {
      return new Column.Ray(p_158196_ - 1, true);
   }

   public static Column m_158161_() {
      return Column.Line.f_158197_;
   }

   public static Column m_158183_(OptionalInt p_158184_, OptionalInt p_158185_) {
      if (p_158184_.isPresent() && p_158185_.isPresent()) {
         return m_158188_(p_158184_.getAsInt(), p_158185_.getAsInt());
      } else if (p_158184_.isPresent()) {
         return m_158193_(p_158184_.getAsInt());
      } else {
         return p_158185_.isPresent() ? m_158162_(p_158185_.getAsInt()) : m_158161_();
      }
   }

   public abstract OptionalInt m_142011_();

   public abstract OptionalInt m_142009_();

   public abstract OptionalInt m_142030_();

   public Column m_158181_(OptionalInt p_158182_) {
      return m_158183_(p_158182_, this.m_142011_());
   }

   public Column m_158191_(OptionalInt p_158192_) {
      return m_158183_(this.m_142009_(), p_158192_);
   }

   public static Optional<Column> m_158175_(LevelSimulatedReader p_158176_, BlockPos p_158177_, int p_158178_, Predicate<BlockState> p_158179_, Predicate<BlockState> p_158180_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_158177_.m_122032_();
      if (!p_158176_.m_7433_(p_158177_, p_158179_)) {
         return Optional.empty();
      } else {
         int i = p_158177_.m_123342_();
         OptionalInt optionalint = m_158167_(p_158176_, p_158178_, p_158179_, p_158180_, blockpos$mutableblockpos, i, Direction.UP);
         OptionalInt optionalint1 = m_158167_(p_158176_, p_158178_, p_158179_, p_158180_, blockpos$mutableblockpos, i, Direction.DOWN);
         return Optional.of(m_158183_(optionalint1, optionalint));
      }
   }

   private static OptionalInt m_158167_(LevelSimulatedReader p_158168_, int p_158169_, Predicate<BlockState> p_158170_, Predicate<BlockState> p_158171_, BlockPos.MutableBlockPos p_158172_, int p_158173_, Direction p_158174_) {
      p_158172_.m_142448_(p_158173_);

      for(int i = 1; i < p_158169_ && p_158168_.m_7433_(p_158172_, p_158170_); ++i) {
         p_158172_.m_122173_(p_158174_);
      }

      return p_158168_.m_7433_(p_158172_, p_158171_) ? OptionalInt.of(p_158172_.m_123342_()) : OptionalInt.empty();
   }

   public static final class Line extends Column {
      static final Column.Line f_158197_ = new Column.Line();

      private Line() {
      }

      public OptionalInt m_142011_() {
         return OptionalInt.empty();
      }

      public OptionalInt m_142009_() {
         return OptionalInt.empty();
      }

      public OptionalInt m_142030_() {
         return OptionalInt.empty();
      }

      public String toString() {
         return "C(-)";
      }
   }

   public static final class Range extends Column {
      private final int f_158204_;
      private final int f_158205_;

      protected Range(int p_158207_, int p_158208_) {
         this.f_158204_ = p_158207_;
         this.f_158205_ = p_158208_;
         if (this.m_158214_() < 0) {
            throw new IllegalArgumentException("Column of negative height: " + this);
         }
      }

      public OptionalInt m_142011_() {
         return OptionalInt.of(this.f_158205_);
      }

      public OptionalInt m_142009_() {
         return OptionalInt.of(this.f_158204_);
      }

      public OptionalInt m_142030_() {
         return OptionalInt.of(this.m_158214_());
      }

      public int m_158212_() {
         return this.f_158205_;
      }

      public int m_158213_() {
         return this.f_158204_;
      }

      public int m_158214_() {
         return this.f_158205_ - this.f_158204_ - 1;
      }

      public String toString() {
         return "C(" + this.f_158205_ + "-" + this.f_158204_ + ")";
      }
   }

   public static final class Ray extends Column {
      private final int f_158216_;
      private final boolean f_158217_;

      public Ray(int p_158219_, boolean p_158220_) {
         this.f_158216_ = p_158219_;
         this.f_158217_ = p_158220_;
      }

      public OptionalInt m_142011_() {
         return this.f_158217_ ? OptionalInt.empty() : OptionalInt.of(this.f_158216_);
      }

      public OptionalInt m_142009_() {
         return this.f_158217_ ? OptionalInt.of(this.f_158216_) : OptionalInt.empty();
      }

      public OptionalInt m_142030_() {
         return OptionalInt.empty();
      }

      public String toString() {
         return this.f_158217_ ? "C(" + this.f_158216_ + "-)" : "C(-" + this.f_158216_ + ")";
      }
   }
}
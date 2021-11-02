package net.minecraft.commands;

import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.TranslatableComponent;

public class BrigadierExceptions implements BuiltInExceptionProvider {
   private static final Dynamic2CommandExceptionType f_77129_ = new Dynamic2CommandExceptionType((p_77203_, p_77204_) -> {
      return new TranslatableComponent("argument.double.low", p_77204_, p_77203_);
   });
   private static final Dynamic2CommandExceptionType f_77130_ = new Dynamic2CommandExceptionType((p_77198_, p_77199_) -> {
      return new TranslatableComponent("argument.double.big", p_77199_, p_77198_);
   });
   private static final Dynamic2CommandExceptionType f_77131_ = new Dynamic2CommandExceptionType((p_77191_, p_77192_) -> {
      return new TranslatableComponent("argument.float.low", p_77192_, p_77191_);
   });
   private static final Dynamic2CommandExceptionType f_77132_ = new Dynamic2CommandExceptionType((p_77186_, p_77187_) -> {
      return new TranslatableComponent("argument.float.big", p_77187_, p_77186_);
   });
   private static final Dynamic2CommandExceptionType f_77133_ = new Dynamic2CommandExceptionType((p_77175_, p_77176_) -> {
      return new TranslatableComponent("argument.integer.low", p_77176_, p_77175_);
   });
   private static final Dynamic2CommandExceptionType f_77134_ = new Dynamic2CommandExceptionType((p_77170_, p_77171_) -> {
      return new TranslatableComponent("argument.integer.big", p_77171_, p_77170_);
   });
   private static final Dynamic2CommandExceptionType f_77135_ = new Dynamic2CommandExceptionType((p_77165_, p_77166_) -> {
      return new TranslatableComponent("argument.long.low", p_77166_, p_77165_);
   });
   private static final Dynamic2CommandExceptionType f_77136_ = new Dynamic2CommandExceptionType((p_77160_, p_77161_) -> {
      return new TranslatableComponent("argument.long.big", p_77161_, p_77160_);
   });
   private static final DynamicCommandExceptionType f_77137_ = new DynamicCommandExceptionType((p_77206_) -> {
      return new TranslatableComponent("argument.literal.incorrect", p_77206_);
   });
   private static final SimpleCommandExceptionType f_77138_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.quote.expected.start"));
   private static final SimpleCommandExceptionType f_77139_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.quote.expected.end"));
   private static final DynamicCommandExceptionType f_77140_ = new DynamicCommandExceptionType((p_77201_) -> {
      return new TranslatableComponent("parsing.quote.escape", p_77201_);
   });
   private static final DynamicCommandExceptionType f_77141_ = new DynamicCommandExceptionType((p_77196_) -> {
      return new TranslatableComponent("parsing.bool.invalid", p_77196_);
   });
   private static final DynamicCommandExceptionType f_77142_ = new DynamicCommandExceptionType((p_77189_) -> {
      return new TranslatableComponent("parsing.int.invalid", p_77189_);
   });
   private static final SimpleCommandExceptionType f_77143_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.int.expected"));
   private static final DynamicCommandExceptionType f_77144_ = new DynamicCommandExceptionType((p_77184_) -> {
      return new TranslatableComponent("parsing.long.invalid", p_77184_);
   });
   private static final SimpleCommandExceptionType f_77145_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.long.expected"));
   private static final DynamicCommandExceptionType f_77146_ = new DynamicCommandExceptionType((p_77173_) -> {
      return new TranslatableComponent("parsing.double.invalid", p_77173_);
   });
   private static final SimpleCommandExceptionType f_77147_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.double.expected"));
   private static final DynamicCommandExceptionType f_77148_ = new DynamicCommandExceptionType((p_77168_) -> {
      return new TranslatableComponent("parsing.float.invalid", p_77168_);
   });
   private static final SimpleCommandExceptionType f_77149_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.float.expected"));
   private static final SimpleCommandExceptionType f_77150_ = new SimpleCommandExceptionType(new TranslatableComponent("parsing.bool.expected"));
   private static final DynamicCommandExceptionType f_77151_ = new DynamicCommandExceptionType((p_77163_) -> {
      return new TranslatableComponent("parsing.expected", p_77163_);
   });
   private static final SimpleCommandExceptionType f_77152_ = new SimpleCommandExceptionType(new TranslatableComponent("command.unknown.command"));
   private static final SimpleCommandExceptionType f_77153_ = new SimpleCommandExceptionType(new TranslatableComponent("command.unknown.argument"));
   private static final SimpleCommandExceptionType f_77154_ = new SimpleCommandExceptionType(new TranslatableComponent("command.expected.separator"));
   private static final DynamicCommandExceptionType f_77128_ = new DynamicCommandExceptionType((p_77158_) -> {
      return new TranslatableComponent("command.exception", p_77158_);
   });

   public Dynamic2CommandExceptionType doubleTooLow() {
      return f_77129_;
   }

   public Dynamic2CommandExceptionType doubleTooHigh() {
      return f_77130_;
   }

   public Dynamic2CommandExceptionType floatTooLow() {
      return f_77131_;
   }

   public Dynamic2CommandExceptionType floatTooHigh() {
      return f_77132_;
   }

   public Dynamic2CommandExceptionType integerTooLow() {
      return f_77133_;
   }

   public Dynamic2CommandExceptionType integerTooHigh() {
      return f_77134_;
   }

   public Dynamic2CommandExceptionType longTooLow() {
      return f_77135_;
   }

   public Dynamic2CommandExceptionType longTooHigh() {
      return f_77136_;
   }

   public DynamicCommandExceptionType literalIncorrect() {
      return f_77137_;
   }

   public SimpleCommandExceptionType readerExpectedStartOfQuote() {
      return f_77138_;
   }

   public SimpleCommandExceptionType readerExpectedEndOfQuote() {
      return f_77139_;
   }

   public DynamicCommandExceptionType readerInvalidEscape() {
      return f_77140_;
   }

   public DynamicCommandExceptionType readerInvalidBool() {
      return f_77141_;
   }

   public DynamicCommandExceptionType readerInvalidInt() {
      return f_77142_;
   }

   public SimpleCommandExceptionType readerExpectedInt() {
      return f_77143_;
   }

   public DynamicCommandExceptionType readerInvalidLong() {
      return f_77144_;
   }

   public SimpleCommandExceptionType readerExpectedLong() {
      return f_77145_;
   }

   public DynamicCommandExceptionType readerInvalidDouble() {
      return f_77146_;
   }

   public SimpleCommandExceptionType readerExpectedDouble() {
      return f_77147_;
   }

   public DynamicCommandExceptionType readerInvalidFloat() {
      return f_77148_;
   }

   public SimpleCommandExceptionType readerExpectedFloat() {
      return f_77149_;
   }

   public SimpleCommandExceptionType readerExpectedBool() {
      return f_77150_;
   }

   public DynamicCommandExceptionType readerExpectedSymbol() {
      return f_77151_;
   }

   public SimpleCommandExceptionType dispatcherUnknownCommand() {
      return f_77152_;
   }

   public SimpleCommandExceptionType dispatcherUnknownArgument() {
      return f_77153_;
   }

   public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
      return f_77154_;
   }

   public DynamicCommandExceptionType dispatcherParseException() {
      return f_77128_;
   }
}
package net.minecraft.commands;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.gametest.framework.TestCommand;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.server.commands.AdvancementCommands;
import net.minecraft.server.commands.AttributeCommand;
import net.minecraft.server.commands.BanIpCommands;
import net.minecraft.server.commands.BanListCommands;
import net.minecraft.server.commands.BanPlayerCommands;
import net.minecraft.server.commands.BossBarCommands;
import net.minecraft.server.commands.ClearInventoryCommands;
import net.minecraft.server.commands.CloneCommands;
import net.minecraft.server.commands.DataPackCommand;
import net.minecraft.server.commands.DeOpCommands;
import net.minecraft.server.commands.DebugCommand;
import net.minecraft.server.commands.DefaultGameModeCommands;
import net.minecraft.server.commands.DifficultyCommand;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.server.commands.EmoteCommands;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.server.commands.ExecuteCommand;
import net.minecraft.server.commands.ExperienceCommand;
import net.minecraft.server.commands.FillCommand;
import net.minecraft.server.commands.ForceLoadCommand;
import net.minecraft.server.commands.FunctionCommand;
import net.minecraft.server.commands.GameModeCommand;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.commands.HelpCommand;
import net.minecraft.server.commands.ItemCommands;
import net.minecraft.server.commands.KickCommand;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.commands.ListPlayersCommand;
import net.minecraft.server.commands.LocateBiomeCommand;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.commands.OpCommand;
import net.minecraft.server.commands.PardonCommand;
import net.minecraft.server.commands.PardonIpCommand;
import net.minecraft.server.commands.ParticleCommand;
import net.minecraft.server.commands.PerfCommand;
import net.minecraft.server.commands.PlaySoundCommand;
import net.minecraft.server.commands.PublishCommand;
import net.minecraft.server.commands.RecipeCommand;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.commands.SaveAllCommand;
import net.minecraft.server.commands.SaveOffCommand;
import net.minecraft.server.commands.SaveOnCommand;
import net.minecraft.server.commands.SayCommand;
import net.minecraft.server.commands.ScheduleCommand;
import net.minecraft.server.commands.ScoreboardCommand;
import net.minecraft.server.commands.SeedCommand;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.commands.SetPlayerIdleTimeoutCommand;
import net.minecraft.server.commands.SetSpawnCommand;
import net.minecraft.server.commands.SetWorldSpawnCommand;
import net.minecraft.server.commands.SpectateCommand;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.server.commands.StopCommand;
import net.minecraft.server.commands.StopSoundCommand;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.commands.TagCommand;
import net.minecraft.server.commands.TeamCommand;
import net.minecraft.server.commands.TeamMsgCommand;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.commands.TellRawCommand;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.commands.TitleCommand;
import net.minecraft.server.commands.TriggerCommand;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.commands.WhitelistCommand;
import net.minecraft.server.commands.WorldBorderCommand;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Commands {
   private static final Logger f_82089_ = LogManager.getLogger();
   public static final int f_165682_ = 0;
   public static final int f_165683_ = 1;
   public static final int f_165684_ = 2;
   public static final int f_165685_ = 3;
   public static final int f_165686_ = 4;
   private final CommandDispatcher<CommandSourceStack> f_82090_ = new CommandDispatcher<>();

   public Commands(Commands.CommandSelection p_82093_) {
      AdvancementCommands.m_136310_(this.f_82090_);
      AttributeCommand.m_136444_(this.f_82090_);
      ExecuteCommand.m_137042_(this.f_82090_);
      BossBarCommands.m_136582_(this.f_82090_);
      ClearInventoryCommands.m_136699_(this.f_82090_);
      CloneCommands.m_136729_(this.f_82090_);
      DataCommands.m_139365_(this.f_82090_);
      DataPackCommand.m_136808_(this.f_82090_);
      DebugCommand.m_136905_(this.f_82090_);
      DefaultGameModeCommands.m_136926_(this.f_82090_);
      DifficultyCommand.m_136938_(this.f_82090_);
      EffectCommands.m_136953_(this.f_82090_);
      EmoteCommands.m_136985_(this.f_82090_);
      EnchantCommand.m_137008_(this.f_82090_);
      ExperienceCommand.m_137306_(this.f_82090_);
      FillCommand.m_137379_(this.f_82090_);
      ForceLoadCommand.m_137676_(this.f_82090_);
      FunctionCommand.m_137714_(this.f_82090_);
      GameModeCommand.m_137729_(this.f_82090_);
      GameRuleCommand.m_137744_(this.f_82090_);
      GiveCommand.m_137772_(this.f_82090_);
      HelpCommand.m_137787_(this.f_82090_);
      ItemCommands.m_180248_(this.f_82090_);
      KickCommand.m_137795_(this.f_82090_);
      KillCommand.m_137807_(this.f_82090_);
      ListPlayersCommand.m_137820_(this.f_82090_);
      LocateCommand.m_137858_(this.f_82090_);
      LocateBiomeCommand.m_137836_(this.f_82090_);
      LootCommand.m_137897_(this.f_82090_);
      MsgCommand.m_138060_(this.f_82090_);
      ParticleCommand.m_138122_(this.f_82090_);
      PlaySoundCommand.m_138156_(this.f_82090_);
      ReloadCommand.m_138226_(this.f_82090_);
      RecipeCommand.m_138200_(this.f_82090_);
      SayCommand.m_138409_(this.f_82090_);
      ScheduleCommand.m_138419_(this.f_82090_);
      ScoreboardCommand.m_138468_(this.f_82090_);
      SeedCommand.m_138589_(this.f_82090_, p_82093_ != Commands.CommandSelection.INTEGRATED);
      SetBlockCommand.m_138601_(this.f_82090_);
      SetSpawnCommand.m_138643_(this.f_82090_);
      SetWorldSpawnCommand.m_138660_(this.f_82090_);
      SpectateCommand.m_138677_(this.f_82090_);
      SpreadPlayersCommand.m_138696_(this.f_82090_);
      StopSoundCommand.m_138794_(this.f_82090_);
      SummonCommand.m_138814_(this.f_82090_);
      TagCommand.m_138836_(this.f_82090_);
      TeamCommand.m_138877_(this.f_82090_);
      TeamMsgCommand.m_138999_(this.f_82090_);
      TeleportCommand.m_139008_(this.f_82090_);
      TellRawCommand.m_139063_(this.f_82090_);
      TimeCommand.m_139071_(this.f_82090_);
      TitleCommand.m_139102_(this.f_82090_);
      TriggerCommand.m_139141_(this.f_82090_);
      WeatherCommand.m_139166_(this.f_82090_);
      WorldBorderCommand.m_139246_(this.f_82090_);
      if (SharedConstants.f_136183_) {
         TestCommand.m_127946_(this.f_82090_);
      }

      if (p_82093_.f_82145_) {
         BanIpCommands.m_136527_(this.f_82090_);
         BanListCommands.m_136543_(this.f_82090_);
         BanPlayerCommands.m_136558_(this.f_82090_);
         DeOpCommands.m_136888_(this.f_82090_);
         OpCommand.m_138079_(this.f_82090_);
         PardonCommand.m_138093_(this.f_82090_);
         PardonIpCommand.m_138108_(this.f_82090_);
         PerfCommand.m_180437_(this.f_82090_);
         SaveAllCommand.m_138271_(this.f_82090_);
         SaveOffCommand.m_138284_(this.f_82090_);
         SaveOnCommand.m_138292_(this.f_82090_);
         SetPlayerIdleTimeoutCommand.m_138634_(this.f_82090_);
         StopCommand.m_138785_(this.f_82090_);
         WhitelistCommand.m_139201_(this.f_82090_);
      }

      if (p_82093_.f_82144_) {
         PublishCommand.m_138184_(this.f_82090_);
      }
      net.minecraftforge.event.ForgeEventFactory.onCommandRegister(this.f_82090_, p_82093_);

      this.f_82090_.findAmbiguities((p_82108_, p_82109_, p_82110_, p_82111_) -> {
         f_82089_.warn("Ambiguity between arguments {} and {} with inputs: {}", this.f_82090_.getPath(p_82109_), this.f_82090_.getPath(p_82110_), p_82111_);
      });
      this.f_82090_.setConsumer((p_82104_, p_82105_, p_82106_) -> {
         p_82104_.getSource().m_81342_(p_82104_, p_82105_, p_82106_);
      });
   }

   public int m_82117_(CommandSourceStack p_82118_, String p_82119_) {
      StringReader stringreader = new StringReader(p_82119_);
      if (stringreader.canRead() && stringreader.peek() == '/') {
         stringreader.skip();
      }

      p_82118_.m_81377_().m_129905_().m_6180_(p_82119_);

      try {
         try {
            ParseResults<CommandSourceStack> parse = this.f_82090_.parse(stringreader, p_82118_);
            net.minecraftforge.event.CommandEvent event = new net.minecraftforge.event.CommandEvent(parse);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) {
               if (event.getException() != null) {
                  com.google.common.base.Throwables.throwIfUnchecked(event.getException());
               }
               return 1;
            }
            return this.f_82090_.execute(event.getParseResults());
         } catch (CommandRuntimeException commandruntimeexception) {
            p_82118_.m_81352_(commandruntimeexception.m_79226_());
            return 0;
         } catch (CommandSyntaxException commandsyntaxexception) {
            p_82118_.m_81352_(ComponentUtils.m_130729_(commandsyntaxexception.getRawMessage()));
            if (commandsyntaxexception.getInput() != null && commandsyntaxexception.getCursor() >= 0) {
               int j = Math.min(commandsyntaxexception.getInput().length(), commandsyntaxexception.getCursor());
               MutableComponent mutablecomponent1 = (new TextComponent("")).m_130940_(ChatFormatting.GRAY).m_130938_((p_82134_) -> {
                  return p_82134_.m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, p_82119_));
               });
               if (j > 10) {
                  mutablecomponent1.m_130946_("...");
               }

               mutablecomponent1.m_130946_(commandsyntaxexception.getInput().substring(Math.max(0, j - 10), j));
               if (j < commandsyntaxexception.getInput().length()) {
                  Component component = (new TextComponent(commandsyntaxexception.getInput().substring(j))).m_130944_(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.UNDERLINE});
                  mutablecomponent1.m_7220_(component);
               }

               mutablecomponent1.m_7220_((new TranslatableComponent("command.context.here")).m_130944_(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.ITALIC}));
               p_82118_.m_81352_(mutablecomponent1);
            }
         } catch (Exception exception) {
            MutableComponent mutablecomponent = new TextComponent(exception.getMessage() == null ? exception.getClass().getName() : exception.getMessage());
            if (f_82089_.isDebugEnabled()) {
               f_82089_.error("Command exception: {}", p_82119_, exception);
               StackTraceElement[] astacktraceelement = exception.getStackTrace();

               for(int i = 0; i < Math.min(astacktraceelement.length, 3); ++i) {
                  mutablecomponent.m_130946_("\n\n").m_130946_(astacktraceelement[i].getMethodName()).m_130946_("\n ").m_130946_(astacktraceelement[i].getFileName()).m_130946_(":").m_130946_(String.valueOf(astacktraceelement[i].getLineNumber()));
               }
            }

            p_82118_.m_81352_((new TranslatableComponent("command.failed")).m_130938_((p_82137_) -> {
               return p_82137_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, mutablecomponent));
            }));
            if (SharedConstants.f_136183_) {
               p_82118_.m_81352_(new TextComponent(Util.m_137575_(exception)));
               f_82089_.error("'{}' threw an exception", p_82119_, exception);
            }

            return 0;
         }

         return 0;
      } finally {
         p_82118_.m_81377_().m_129905_().m_7238_();
      }
   }

   public void m_82095_(ServerPlayer p_82096_) {
      Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> map = Maps.newHashMap();
      RootCommandNode<SharedSuggestionProvider> rootcommandnode = new RootCommandNode<>();
      map.put(this.f_82090_.getRoot(), rootcommandnode);
      this.m_82112_(this.f_82090_.getRoot(), rootcommandnode, p_82096_.m_20203_(), map);
      p_82096_.f_8906_.m_141995_(new ClientboundCommandsPacket(rootcommandnode));
   }

   private void m_82112_(CommandNode<CommandSourceStack> p_82113_, CommandNode<SharedSuggestionProvider> p_82114_, CommandSourceStack p_82115_, Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> p_82116_) {
      for(CommandNode<CommandSourceStack> commandnode : p_82113_.getChildren()) {
         if (commandnode.canUse(p_82115_)) {
            ArgumentBuilder<SharedSuggestionProvider, ?> argumentbuilder = (ArgumentBuilder)commandnode.createBuilder();
            argumentbuilder.requires((p_82126_) -> {
               return true;
            });
            if (argumentbuilder.getCommand() != null) {
               argumentbuilder.executes((p_82102_) -> {
                  return 0;
               });
            }

            if (argumentbuilder instanceof RequiredArgumentBuilder) {
               RequiredArgumentBuilder<SharedSuggestionProvider, ?> requiredargumentbuilder = (RequiredArgumentBuilder)argumentbuilder;
               if (requiredargumentbuilder.getSuggestionsProvider() != null) {
                  requiredargumentbuilder.suggests(SuggestionProviders.m_121664_(requiredargumentbuilder.getSuggestionsProvider()));
               }
            }

            if (argumentbuilder.getRedirect() != null) {
               argumentbuilder.redirect(p_82116_.get(argumentbuilder.getRedirect()));
            }

            CommandNode<SharedSuggestionProvider> commandnode1 = argumentbuilder.build();
            p_82116_.put(commandnode, commandnode1);
            p_82114_.addChild(commandnode1);
            if (!commandnode.getChildren().isEmpty()) {
               this.m_82112_(commandnode, commandnode1, p_82115_, p_82116_);
            }
         }
      }

   }

   public static LiteralArgumentBuilder<CommandSourceStack> m_82127_(String p_82128_) {
      return LiteralArgumentBuilder.literal(p_82128_);
   }

   public static <T> RequiredArgumentBuilder<CommandSourceStack, T> m_82129_(String p_82130_, ArgumentType<T> p_82131_) {
      return RequiredArgumentBuilder.argument(p_82130_, p_82131_);
   }

   public static Predicate<String> m_82120_(Commands.ParseFunction p_82121_) {
      return (p_82124_) -> {
         try {
            p_82121_.m_82160_(new StringReader(p_82124_));
            return true;
         } catch (CommandSyntaxException commandsyntaxexception) {
            return false;
         }
      };
   }

   public CommandDispatcher<CommandSourceStack> m_82094_() {
      return this.f_82090_;
   }

   @Nullable
   public static <S> CommandSyntaxException m_82097_(ParseResults<S> p_82098_) {
      if (!p_82098_.getReader().canRead()) {
         return null;
      } else if (p_82098_.getExceptions().size() == 1) {
         return p_82098_.getExceptions().values().iterator().next();
      } else {
         return p_82098_.getContext().getRange().isEmpty() ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(p_82098_.getReader()) : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(p_82098_.getReader());
      }
   }

   public static void m_82138_() {
      RootCommandNode<CommandSourceStack> rootcommandnode = (new Commands(Commands.CommandSelection.ALL)).m_82094_().getRoot();
      Set<ArgumentType<?>> set = ArgumentTypes.m_121595_(rootcommandnode);
      Set<ArgumentType<?>> set1 = set.stream().filter((p_82140_) -> {
         return !ArgumentTypes.m_121593_(p_82140_);
      }).collect(Collectors.toSet());
      if (!set1.isEmpty()) {
         f_82089_.warn("Missing type registration for following arguments:\n {}", set1.stream().map((p_82100_) -> {
            return "\t" + p_82100_;
         }).collect(Collectors.joining(",\n")));
         throw new IllegalStateException("Unregistered argument types");
      }
   }

   public static enum CommandSelection {
      ALL(true, true),
      DEDICATED(false, true),
      INTEGRATED(true, false);

      final boolean f_82144_;
      final boolean f_82145_;

      private CommandSelection(boolean p_82151_, boolean p_82152_) {
         this.f_82144_ = p_82151_;
         this.f_82145_ = p_82152_;
      }
   }

   @FunctionalInterface
   public interface ParseFunction {
      void m_82160_(StringReader p_82161_) throws CommandSyntaxException;
   }
}

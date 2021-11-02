/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.server.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.WorldWorkerManager;

class CommandGenerate
{
    static ArgumentBuilder<CommandSourceStack, ?> register()
    {
        return Commands.m_82127_("generate")
            .requires(cs->cs.m_6761_(4)) //permission
            .then(Commands.m_82129_("pos", BlockPosArgument.m_118239_())
                .then(Commands.m_82129_("count", IntegerArgumentType.integer(1))
                    .then(Commands.m_82129_("dim", DimensionArgument.m_88805_())
                        .then(Commands.m_82129_("interval", IntegerArgumentType.integer())
                            .executes(ctx -> execute(ctx.getSource(), BlockPosArgument.m_174395_(ctx, "pos"), getInt(ctx, "count"), DimensionArgument.m_88808_(ctx, "dim"), getInt(ctx, "interval")))
                        )
                        .executes(ctx -> execute(ctx.getSource(), BlockPosArgument.m_174395_(ctx, "pos"), getInt(ctx, "count"), DimensionArgument.m_88808_(ctx, "dim"), -1))
                    )
                    .executes(ctx -> execute(ctx.getSource(), BlockPosArgument.m_174395_(ctx, "pos"), getInt(ctx, "count"), ctx.getSource().m_81372_(), -1))
                )
            );
    }

    private static int getInt(CommandContext<CommandSourceStack> ctx, String name)
    {
        return IntegerArgumentType.getInteger(ctx, name);
    }

    private static int execute(CommandSourceStack source, BlockPos pos, int count, ServerLevel dim, int interval) throws CommandRuntimeException
    {
        BlockPos chunkpos = new BlockPos(pos.m_123341_() >> 4, 0, pos.m_123343_() >> 4);

        ChunkGenWorker worker = new ChunkGenWorker(source, chunkpos, count, dim, interval);
        source.m_81354_(worker.getStartMessage(source), true);
        WorldWorkerManager.addWorker(worker);

        return 0;
    }
}

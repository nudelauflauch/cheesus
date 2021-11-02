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

package net.minecraftforge.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.common.ticket.AABBTicket;
import net.minecraftforge.common.ticket.ChunkTicketManager;
import net.minecraftforge.common.ticket.SimpleTicket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class FarmlandWaterManager
{
    private static final boolean DEBUG = Boolean.parseBoolean(System.getProperty("forge.debugFarmlandWaterManager", "false"));
    private static final Map<LevelReader, Map<ChunkPos, ChunkTicketManager<Vec3>>> customWaterHandler = new WeakHashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Adds a custom ticket.
     * Use {@link #addAABBTicket(World, AxisAlignedBB)} if you just need a ticket that can water a certain area.
     * <br>
     * If you don't want to water the region anymore, call {@link SimpleTicket#invalidate()}. Also call this
     * when the region this is unloaded (e.g. your TE is unloaded or the block is removed), and validate once it is loaded
     * @param world The world where the region should be marked. Only server-side worlds are allowed
     * @param ticket Your ticket you want to have registered
     * @param masterChunk The chunk pos that is controls when the ticket may be unloaded. The ticket should originate from here.
     * @param additionalChunks The chunks in that this ticket wants to operate as well.
     * @return The ticket for your requested region.
     */
    @SuppressWarnings("unchecked")
    public static<T extends SimpleTicket<Vec3>> T addCustomTicket(Level world, T ticket, ChunkPos masterChunk, ChunkPos... additionalChunks)
    {
        Preconditions.checkArgument(!world.f_46443_, "Water region is only determined server-side");
        Map<ChunkPos, ChunkTicketManager<Vec3>> ticketMap =  customWaterHandler.computeIfAbsent(world, id -> new MapMaker().weakValues().makeMap());
        ChunkTicketManager<Vec3>[] additionalTickets = new ChunkTicketManager[additionalChunks.length];
        for (int i = 0; i < additionalChunks.length; i++)
            additionalTickets[i] = ticketMap.computeIfAbsent(additionalChunks[i], ChunkTicketManager::new);
        ticket.setManager(ticketMap.computeIfAbsent(masterChunk, ChunkTicketManager::new), additionalTickets);
        ticket.validate();
        return ticket;
    }

    /**
     * Convenience method to add a ticket that is backed by an AABB.
     * <br>
     * If you don't want to water the region anymore, call {@link SimpleTicket#invalidate()}. Also call this
     * when the region this is unloaded (e.g. your TE is unloaded or the block is removed), and validate once it is loaded
     * <br>
     * The AABB in the ticket is immutable
     * @param world The world where the region should be marked. Only server-side worlds are allowed
     * @param aabb The region where blocks should be watered
     * @return The ticket for your requested region.
     */
    public static AABBTicket addAABBTicket(Level world, AABB aabb)
    {
        if (DEBUG)
            LOGGER.info("FarmlandWaterManager: New AABBTicket, aabb={}", aabb);
        //First calculate all chunks the aabb is in
        ChunkPos leftUp = new ChunkPos(((int) aabb.f_82288_) >> 4, ((int) aabb.f_82290_) >> 4);
        ChunkPos rightDown = new ChunkPos(((int) aabb.f_82291_) >> 4, ((int) aabb.f_82293_) >> 4);
        Set<ChunkPos> posSet = new HashSet<>();
        for (int x = leftUp.f_45578_; x <= rightDown.f_45578_; x++)
        {
            for (int z = leftUp.f_45579_; z <= rightDown.f_45579_; z++)
            {
                posSet.add(new ChunkPos(x, z));
            }
        }
        ChunkPos masterPos = null;
        double masterDistance = Double.MAX_VALUE;
        for (ChunkPos pos : posSet) //Find the chunkPos with the lowest distance to the center and choose it as the master pos
        {
            double distToCenter = getDistanceSq(pos, aabb.m_82399_());
            if (distToCenter < masterDistance)
            {
                if (DEBUG)
                    LOGGER.info("FarmlandWaterManager: New better pos then {}: {}, prev dist {}, new dist {}", masterPos, pos, masterDistance, distToCenter);
                masterPos = pos;
                masterDistance = distToCenter;
            }
        }
        posSet.remove(masterPos);
        if (DEBUG)
            LOGGER.info("FarmlandWaterManager: {} center pos, {} dummy posses. Dist to center {}", masterPos, posSet.toArray(new ChunkPos[0]), masterDistance);
        return addCustomTicket(world, new AABBTicket(aabb), masterPos, posSet.toArray(new ChunkPos[0]));
    }

    private static double getDistanceSq(ChunkPos pos, Vec3 vec3d)
    {
        //See ChunkPos#getDistanceSq
        double d0 = (double)(pos.f_45578_ * 16 + 8);
        double d1 = (double)(pos.f_45579_ * 16 + 8);
        double d2 = d0 - vec3d.f_82479_;
        double d3 = d1 - vec3d.f_82481_;
        return  d2 * d2 + d3 * d3;
    }

    /**
     * Tests if a block is in a region that is watered by blocks. This does not check vanilla water, see {@link net.minecraft.block.FarmlandBlock#hasWater(IWorldReader, BlockPos)}
     * @return true if there is a ticket with an AABB that includes your block
     */
    public static boolean hasBlockWaterTicket(LevelReader world, BlockPos pos)
    {
        ChunkTicketManager<Vec3> ticketManager = getTicketManager(new ChunkPos(pos.m_123341_() >> 4, pos.m_123343_() >> 4), world);
        if (ticketManager != null)
        {
            Vec3 posAsVec3d = new Vec3(pos.m_123341_() + 0.5, pos.m_123342_() + 0.5, pos.m_123343_() + 0.5);
            for (SimpleTicket<Vec3> ticket : ticketManager.getTickets()) {
                if (ticket.matches(posAsVec3d))
                    return true;
            }
        }
        return false;
    }

    static void removeTickets(ChunkAccess chunk)
    {
        ChunkTicketManager<Vec3> ticketManager = getTicketManager(chunk.m_7697_(), chunk.getWorldForge());
        if (ticketManager != null)
        {
            if (DEBUG)
                LOGGER.info("FarmlandWaterManager: got tickets {} at {} before", ticketManager.getTickets().size(), ticketManager.pos);
            ticketManager.getTickets().removeIf(next -> next.unload(ticketManager)); //remove if this is the master manager of the ticket
            if (DEBUG)
                LOGGER.info("FarmlandWaterManager: got tickets {} at {} after", ticketManager.getTickets().size(), ticketManager.pos);
        }
    }

    private static ChunkTicketManager<Vec3> getTicketManager(ChunkPos pos, LevelReader world) {
        Preconditions.checkArgument(!world.m_5776_(), "Water region is only determined server-side");
        Map<ChunkPos, ChunkTicketManager<Vec3>> ticketMap = customWaterHandler.get(world);
        if (ticketMap == null)
        {
            return null;
        }
        return ticketMap.get(pos);
    }
}

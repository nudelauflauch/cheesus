package net.minecraft.server.packs.repository;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;

public class PackRepository implements AutoCloseable {
   private final Set<RepositorySource> f_10497_;
   private Map<String, Pack> f_10498_ = ImmutableMap.of();
   private List<Pack> f_10499_ = ImmutableList.of();
   private final Pack.PackConstructor f_10500_;

   public PackRepository(Pack.PackConstructor p_10502_, RepositorySource... p_10503_) {
      this.f_10500_ = p_10502_;
      this.f_10497_ = new java.util.HashSet<>(java.util.Arrays.asList(p_10503_));
   }

   public PackRepository(PackType p_143890_, RepositorySource... p_143891_) {
      this((p_143894_, p_143895_, p_143896_, p_143897_, p_143898_, p_143899_, p_143900_, hidden) -> {
         return new Pack(p_143894_, p_143895_, p_143896_, p_143897_, p_143898_, p_143890_, p_143899_, p_143900_, hidden);
      }, p_143891_);
      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.event.AddPackFindersEvent(p_143890_, f_10497_::add));
   }

   public void m_10506_() {
      List<String> list = this.f_10499_.stream().map(Pack::m_10446_).collect(ImmutableList.toImmutableList());
      this.close();
      this.f_10498_ = this.m_10526_();
      this.f_10499_ = this.m_10517_(list);
   }

   private Map<String, Pack> m_10526_() {
      Map<String, Pack> map = Maps.newTreeMap();

      for(RepositorySource repositorysource : this.f_10497_) {
         repositorysource.m_7686_((p_143903_) -> {
            map.put(p_143903_.m_10446_(), p_143903_);
         }, this.f_10500_);
      }

      return ImmutableMap.copyOf(map);
   }

   public void m_10509_(Collection<String> p_10510_) {
      this.f_10499_ = this.m_10517_(p_10510_);
   }

   private List<Pack> m_10517_(Collection<String> p_10518_) {
      List<Pack> list = this.m_10520_(p_10518_).collect(Collectors.toList());

      for(Pack pack : this.f_10498_.values()) {
         if (pack.m_10449_() && !list.contains(pack)) {
            pack.m_10451_().m_10470_(list, pack, Functions.identity(), false);
         }
      }

      return ImmutableList.copyOf(list);
   }

   private Stream<Pack> m_10520_(Collection<String> p_10521_) {
      return p_10521_.stream().map(this.f_10498_::get).filter(Objects::nonNull);
   }

   public Collection<String> m_10514_() {
      return this.f_10498_.keySet();
   }

   public Collection<Pack> m_10519_() {
      return this.f_10498_.values();
   }

   public Collection<String> m_10523_() {
      return this.f_10499_.stream().map(Pack::m_10446_).collect(ImmutableSet.toImmutableSet());
   }

   public Collection<Pack> m_10524_() {
      return this.f_10499_;
   }

   @Nullable
   public Pack m_10507_(String p_10508_) {
      return this.f_10498_.get(p_10508_);
   }

   public synchronized void addPackFinder(RepositorySource packFinder) {
      this.f_10497_.add(packFinder);
   }

   public void close() {
      this.f_10498_.values().forEach(Pack::close);
   }

   public boolean m_10515_(String p_10516_) {
      return this.f_10498_.containsKey(p_10516_);
   }

   public List<PackResources> m_10525_() {
      return this.f_10499_.stream().map(Pack::m_10445_).collect(ImmutableList.toImmutableList());
   }
}

package net.minecraft.network.protocol.handshake;

import net.minecraft.SharedConstants;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientIntentionPacket implements Packet<ServerHandshakePacketListener> {
   private static final int f_179799_ = 255;
   private final int f_134720_;
   private final String f_134721_;
   private final int f_134722_;
   private final ConnectionProtocol f_134723_;
   private String fmlVersion = net.minecraftforge.fmllegacy.network.FMLNetworkConstants.NETVERSION;

   public ClientIntentionPacket(String p_134726_, int p_134727_, ConnectionProtocol p_134728_) {
      this.f_134720_ = SharedConstants.m_136187_().getProtocolVersion();
      this.f_134721_ = p_134726_;
      this.f_134722_ = p_134727_;
      this.f_134723_ = p_134728_;
   }

   public ClientIntentionPacket(FriendlyByteBuf p_179801_) {
      this.f_134720_ = p_179801_.m_130242_();
      String hostName = p_179801_.m_130136_(255);
      this.f_134722_ = p_179801_.readUnsignedShort();
      this.f_134723_ = ConnectionProtocol.m_129583_(p_179801_.m_130242_());
      this.fmlVersion = net.minecraftforge.fmllegacy.network.NetworkHooks.getFMLVersion(hostName);
      this.f_134721_ = hostName.split("\0")[0];
   }

   public void m_5779_(FriendlyByteBuf p_134737_) {
      p_134737_.m_130130_(this.f_134720_);
      p_134737_.m_130070_(this.f_134721_ + "\0"+ net.minecraftforge.fmllegacy.network.FMLNetworkConstants.NETVERSION+"\0");
      p_134737_.writeShort(this.f_134722_);
      p_134737_.m_130130_(this.f_134723_.m_129582_());
   }

   public void m_5797_(ServerHandshakePacketListener p_134734_) {
      p_134734_.m_7322_(this);
   }

   public ConnectionProtocol m_134735_() {
      return this.f_134723_;
   }

   public int m_134738_() {
      return this.f_134720_;
   }

   public String m_179802_() {
      return this.f_134721_;
   }

   public int m_179803_() {
      return this.f_134722_;
   }

   public String getFMLVersion() {
      return this.fmlVersion;
   }
}

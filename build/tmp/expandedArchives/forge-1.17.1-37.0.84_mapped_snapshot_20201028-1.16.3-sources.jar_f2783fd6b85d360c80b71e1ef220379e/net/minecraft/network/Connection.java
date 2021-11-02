package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Connection extends SimpleChannelInboundHandler<Packet<?>> {
   private static final float f_178299_ = 0.75F;
   private static final Logger f_129465_ = LogManager.getLogger();
   public static final Marker f_129459_ = MarkerManager.getMarker("NETWORK");
   public static final Marker f_129460_ = MarkerManager.getMarker("NETWORK_PACKETS", f_129459_);
   public static final AttributeKey<ConnectionProtocol> f_129461_ = AttributeKey.valueOf("protocol");
   public static final LazyLoadedValue<NioEventLoopGroup> f_129462_ = new LazyLoadedValue<>(() -> {
      return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
   });
   public static final LazyLoadedValue<EpollEventLoopGroup> f_129463_ = new LazyLoadedValue<>(() -> {
      return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
   });
   public static final LazyLoadedValue<DefaultEventLoopGroup> f_129464_ = new LazyLoadedValue<>(() -> {
      return new DefaultEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
   });
   private final PacketFlow f_129466_;
   private final Queue<Connection.PacketHolder> f_129467_ = Queues.newConcurrentLinkedQueue();
   private Channel f_129468_;
   private SocketAddress f_129469_;
   private PacketListener f_129470_;
   private Component f_129471_;
   private boolean f_129472_;
   private boolean f_129473_;
   private int f_129474_;
   private int f_129475_;
   private float f_129476_;
   private float f_129477_;
   private int f_129478_;
   private boolean f_129479_;
   private java.util.function.Consumer<Connection> activationHandler;

   public Connection(PacketFlow p_129482_) {
      this.f_129466_ = p_129482_;
   }

   public void channelActive(ChannelHandlerContext p_129525_) throws Exception {
      super.channelActive(p_129525_);
      this.f_129468_ = p_129525_.channel();
      this.f_129469_ = this.f_129468_.remoteAddress();
      if (activationHandler != null) activationHandler.accept(this);

      try {
         this.m_129498_(ConnectionProtocol.HANDSHAKING);
      } catch (Throwable throwable) {
         f_129465_.fatal(throwable);
      }

   }

   public void m_129498_(ConnectionProtocol p_129499_) {
      this.f_129468_.attr(f_129461_).set(p_129499_);
      this.f_129468_.config().setAutoRead(true);
      f_129465_.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext p_129527_) {
      this.m_129507_(new TranslatableComponent("disconnect.endOfStream"));
   }

   public void exceptionCaught(ChannelHandlerContext p_129533_, Throwable p_129534_) {
      if (p_129534_ instanceof SkipPacketException) {
         f_129465_.debug("Skipping packet due to errors", p_129534_.getCause());
      } else {
         boolean flag = !this.f_129479_;
         this.f_129479_ = true;
         if (this.f_129468_.isOpen()) {
            if (p_129534_ instanceof TimeoutException) {
               f_129465_.debug("Timeout", p_129534_);
               this.m_129507_(new TranslatableComponent("disconnect.timeout"));
            } else {
               Component component = new TranslatableComponent("disconnect.genericReason", "Internal Exception: " + p_129534_);
               if (flag) {
                  f_129465_.debug("Failed to sent packet", p_129534_);
                  ConnectionProtocol connectionprotocol = this.m_178315_();
                  Packet<?> packet = (Packet<?>)(connectionprotocol == ConnectionProtocol.LOGIN ? new ClientboundLoginDisconnectPacket(component) : new ClientboundDisconnectPacket(component));
                  this.m_129514_(packet, (p_129511_) -> {
                     this.m_129507_(component);
                  });
                  this.m_129540_();
               } else {
                  f_129465_.debug("Double fault", p_129534_);
                  this.m_129507_(component);
               }
            }

         }
      }
   }

   protected void channelRead0(ChannelHandlerContext p_129487_, Packet<?> p_129488_) {
      if (this.f_129468_.isOpen()) {
         try {
            m_129517_(p_129488_, this.f_129470_);
         } catch (RunningOnDifferentThreadException runningondifferentthreadexception) {
         } catch (ClassCastException classcastexception) {
            f_129465_.error("Received {} that couldn't be processed", p_129488_.getClass(), classcastexception);
            this.m_129507_(new TranslatableComponent("multiplayer.disconnect.invalid_packet"));
         }

         ++this.f_129474_;
      }

   }

   private static <T extends PacketListener> void m_129517_(Packet<T> p_129518_, PacketListener p_129519_) {
      p_129518_.m_5797_((T)p_129519_);
   }

   public void m_129505_(PacketListener p_129506_) {
      Validate.notNull(p_129506_, "packetListener");
      this.f_129470_ = p_129506_;
   }

   public void m_129512_(Packet<?> p_129513_) {
      this.m_129514_(p_129513_, (GenericFutureListener<? extends Future<? super Void>>)null);
   }

   public void m_129514_(Packet<?> p_129515_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_129516_) {
      if (this.m_129536_()) {
         this.m_129544_();
         this.m_129520_(p_129515_, p_129516_);
      } else {
         this.f_129467_.add(new Connection.PacketHolder(p_129515_, p_129516_));
      }

   }

   private void m_129520_(Packet<?> p_129521_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_129522_) {
      ConnectionProtocol connectionprotocol = ConnectionProtocol.m_129592_(p_129521_);
      ConnectionProtocol connectionprotocol1 = this.m_178315_();
      ++this.f_129475_;
      if (connectionprotocol1 != connectionprotocol) {
         f_129465_.debug("Disabled auto read");
         this.f_129468_.eventLoop().execute(()->this.f_129468_.config().setAutoRead(false));
      }

      if (this.f_129468_.eventLoop().inEventLoop()) {
         this.m_178303_(p_129521_, p_129522_, connectionprotocol, connectionprotocol1);
      } else {
         this.f_129468_.eventLoop().execute(() -> {
            this.m_178303_(p_129521_, p_129522_, connectionprotocol, connectionprotocol1);
         });
      }

   }

   private void m_178303_(Packet<?> p_178304_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_178305_, ConnectionProtocol p_178306_, ConnectionProtocol p_178307_) {
      if (p_178306_ != p_178307_) {
         this.m_129498_(p_178306_);
      }

      ChannelFuture channelfuture = this.f_129468_.writeAndFlush(p_178304_);
      if (p_178305_ != null) {
         channelfuture.addListener(p_178305_);
      }

      channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
   }

   private ConnectionProtocol m_178315_() {
      return this.f_129468_.attr(f_129461_).get();
   }

   private void m_129544_() {
      if (this.f_129468_ != null && this.f_129468_.isOpen()) {
         synchronized(this.f_129467_) {
            Connection.PacketHolder connection$packetholder;
            while((connection$packetholder = this.f_129467_.poll()) != null) {
               this.m_129520_(connection$packetholder.f_129558_, connection$packetholder.f_129559_);
            }

         }
      }
   }

   public void m_129483_() {
      this.m_129544_();
      if (this.f_129470_ instanceof ServerLoginPacketListenerImpl) {
         ((ServerLoginPacketListenerImpl)this.f_129470_).m_10050_();
      }

      if (this.f_129470_ instanceof ServerGamePacketListenerImpl) {
         ((ServerGamePacketListenerImpl)this.f_129470_).m_9933_();
      }

      if (!this.m_129536_() && !this.f_129473_) {
         this.m_129541_();
      }

      if (this.f_129468_ != null) {
         this.f_129468_.flush();
      }

      if (this.f_129478_++ % 20 == 0) {
         this.m_7073_();
      }

   }

   protected void m_7073_() {
      this.f_129477_ = Mth.m_14179_(0.75F, (float)this.f_129475_, this.f_129477_);
      this.f_129476_ = Mth.m_14179_(0.75F, (float)this.f_129474_, this.f_129476_);
      this.f_129475_ = 0;
      this.f_129474_ = 0;
   }

   public SocketAddress m_129523_() {
      return this.f_129469_;
   }

   public void m_129507_(Component p_129508_) {
      if (this.f_129468_.isOpen()) {
         this.f_129468_.close().awaitUninterruptibly();
         this.f_129471_ = p_129508_;
      }

   }

   public boolean m_129531_() {
      return this.f_129468_ instanceof LocalChannel || this.f_129468_ instanceof LocalServerChannel;
   }

   public PacketFlow m_178313_() {
      return this.f_129466_;
   }

   public PacketFlow m_178314_() {
      return this.f_129466_.m_178539_();
   }

   public static Connection m_178300_(InetSocketAddress p_178301_, boolean p_178302_) {
      if (p_178301_.getAddress() instanceof java.net.Inet6Address) System.setProperty("java.net.preferIPv4Stack", "false");
      final Connection connection = new Connection(PacketFlow.CLIENTBOUND);
      connection.activationHandler = net.minecraftforge.fmllegacy.network.NetworkHooks::registerClientLoginChannel;
      Class<? extends SocketChannel> oclass;
      LazyLoadedValue<? extends EventLoopGroup> lazyloadedvalue;
      if (Epoll.isAvailable() && p_178302_) {
         oclass = EpollSocketChannel.class;
         lazyloadedvalue = f_129463_;
      } else {
         oclass = NioSocketChannel.class;
         lazyloadedvalue = f_129462_;
      }

      (new Bootstrap()).group(lazyloadedvalue.m_13971_()).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel p_129552_) {
            try {
               p_129552_.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException channelexception) {
            }

            p_129552_.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("splitter", new Varint21FrameDecoder()).addLast("decoder", new PacketDecoder(PacketFlow.CLIENTBOUND)).addLast("prepender", new Varint21LengthFieldPrepender()).addLast("encoder", new PacketEncoder(PacketFlow.SERVERBOUND)).addLast("packet_handler", connection);
         }
      }).channel(oclass).connect(p_178301_.getAddress(), p_178301_.getPort()).syncUninterruptibly();
      return connection;
   }

   public static Connection m_129493_(SocketAddress p_129494_) {
      final Connection connection = new Connection(PacketFlow.CLIENTBOUND);
      connection.activationHandler = net.minecraftforge.fmllegacy.network.NetworkHooks::registerClientLoginChannel;
      (new Bootstrap()).group(f_129464_.m_13971_()).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel p_129557_) {
            p_129557_.pipeline().addLast("packet_handler", connection);
         }
      }).channel(LocalChannel.class).connect(p_129494_).syncUninterruptibly();
      return connection;
   }

   public void m_129495_(Cipher p_129496_, Cipher p_129497_) {
      this.f_129472_ = true;
      this.f_129468_.pipeline().addBefore("splitter", "decrypt", new CipherDecoder(p_129496_));
      this.f_129468_.pipeline().addBefore("prepender", "encrypt", new CipherEncoder(p_129497_));
   }

   public boolean m_129535_() {
      return this.f_129472_;
   }

   public boolean m_129536_() {
      return this.f_129468_ != null && this.f_129468_.isOpen();
   }

   public boolean m_129537_() {
      return this.f_129468_ == null;
   }

   public PacketListener m_129538_() {
      return this.f_129470_;
   }

   @Nullable
   public Component m_129539_() {
      return this.f_129471_;
   }

   public void m_129540_() {
      this.f_129468_.config().setAutoRead(false);
   }

   public void m_129484_(int p_129485_, boolean p_182682_) {
      if (p_129485_ >= 0) {
         if (this.f_129468_.pipeline().get("decompress") instanceof CompressionDecoder) {
            ((CompressionDecoder)this.f_129468_.pipeline().get("decompress")).m_182677_(p_129485_, p_182682_);
         } else {
            this.f_129468_.pipeline().addBefore("decoder", "decompress", new CompressionDecoder(p_129485_, p_182682_));
         }

         if (this.f_129468_.pipeline().get("compress") instanceof CompressionEncoder) {
            ((CompressionEncoder)this.f_129468_.pipeline().get("compress")).m_129449_(p_129485_);
         } else {
            this.f_129468_.pipeline().addBefore("encoder", "compress", new CompressionEncoder(p_129485_));
         }
      } else {
         if (this.f_129468_.pipeline().get("decompress") instanceof CompressionDecoder) {
            this.f_129468_.pipeline().remove("decompress");
         }

         if (this.f_129468_.pipeline().get("compress") instanceof CompressionEncoder) {
            this.f_129468_.pipeline().remove("compress");
         }
      }

   }

   public void m_129541_() {
      if (this.f_129468_ != null && !this.f_129468_.isOpen()) {
         if (this.f_129473_) {
            f_129465_.warn("handleDisconnection() called twice");
         } else {
            this.f_129473_ = true;
            if (this.m_129539_() != null) {
               this.m_129538_().m_7026_(this.m_129539_());
            } else if (this.m_129538_() != null) {
               this.m_129538_().m_7026_(new TranslatableComponent("multiplayer.disconnect.generic"));
            }
         }

      }
   }

   public float m_129542_() {
      return this.f_129476_;
   }

   public float m_129543_() {
      return this.f_129477_;
   }

   public Channel channel() {
      return f_129468_;
   }

   public PacketFlow getDirection() {
      return this.f_129466_;
   }

   static class PacketHolder {
      final Packet<?> f_129558_;
      @Nullable
      final GenericFutureListener<? extends Future<? super Void>> f_129559_;

      public PacketHolder(Packet<?> p_129561_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_129562_) {
         this.f_129558_ = p_129561_;
         this.f_129559_ = p_129562_;
      }
   }
}

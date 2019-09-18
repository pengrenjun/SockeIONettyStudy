/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package Netty.simpledemo.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Echoes back any received data from a client.
 * 代码基本就是模板代码，每次使用都是这一个套路，唯一需要我们开发的部分是 handler(…) 和 childHandler(…)
 * 方法中指定的各个 handler，如 EchoServerHandler 和 EchoClientHandler，
 * 当然 Netty 源码也给我们提供了很多的 handler，比如上面的 LoggingHandler，它就是 Netty 源码中为我们提供的，
 * 需要的时候直接拿过来用就好了
 */
public final class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the demoserver.
        //两个 EventLoopGroup：bossGroup 和 workerGroup，
        //它们涉及的是 Netty 的线程模型，可以看到服务端有两个 group，而客户端只有一个，它们就是 Netty 中的线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap 类用于创建服务端实例
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             //Netty 中的 Channel，没有直接使用 Java 原生的 ServerSocketChannel 和 SocketChannel，
                    // 而是包装了 NioServerSocketChannel 和 NioSocketChannel 与之对应
                    //当然，也有对其他协议的支持，如支持 UDP 协议的 NioDatagramChannel
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     if (sslCtx != null) {
                         p.addLast(sslCtx.newHandler(ch.alloc()));
                     }
                     //p.addLast(new LoggingHandler(LogLevel.INFO));
                     p.addLast(new EchoServerHandler());
                 }
             });

            // Start the demoserver.
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the demoserver socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

package NettyAction.websocket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WebSocketServer {

    private static String ip = "0.0.0.1";

    static {
        try {
            //��ȡ����IP
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LoggingHandler());
                            pipeline.addLast("http-codec", new HttpServerCodec());//�������Ӧ��������Ϊhttp��Ϣ
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));//��http��Ϣ�Ķ���������ϳ�һ����������Ϣ
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());//ChunkedWriteHandler֧���첽�������������������ļ����䣩����ռ�ù����ڴ棬��ֹ�ڴ����
                            pipeline.addLast("handler", new WebSocketServerHandler());
                        }
                    });
//            Channel ch = b.bind(port).sync().channel();
//            System.out.println("websocketserver start port at " + port);
//            ch.closeFuture().sync();
            ChannelFuture future = b.bind(ip, port).sync();
            System.out.println("websocketserver����: " + "ws://" + ip + ":"
                    + port );
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.valueOf(args[0]);

        }
        new WebSocketServer().run(port);

    }
}

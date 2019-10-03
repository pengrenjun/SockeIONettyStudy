package NettyAction.http.fileServerDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpFileBootServer {

    private static final String DEFAULT_URL = "/src/main/java";

    private static String ip = "0.0.0.1";

    static {
        try {
            //获取本地IP
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());
                            //添加请求消息解码器，对HTTP 请求消息解码
                            ch.pipeline().addLast("http-decoder",
                                    new HttpRequestDecoder());
                            //添加HttpObjectAggregator解码器，将多个消息转为单一的FullHttpRequest或者FullHttpResponse
                            //因为HTTP解码器会在每个HTTP消息中生成多个消息对象：
                            /*
                            1、HttpRequest/HttpResponse
                            2、HttpContent
                            3、LastHttpContent
                             */
                            ch.pipeline().addLast("http-aggregator",
                                    new HttpObjectAggregator(65536));
                            //添加响应编码器
                            ch.pipeline().addLast("http-encoder",
                                    new HttpResponseEncoder());
                            //ChunkedWriteHandler支持异步传输大的码流（例如大的文件传输），不占用过多内存，防止内存溢出
                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());
                            //添加文件服务器业务处理器
                            ch.pipeline().addLast("fileServerHandler",
                                    new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = b.bind(ip, port).sync();
            System.out.println("HTTP文件目录服务器启动，网址是 : " + "http://" + ip + ":"
                    + port + url);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if (args.length > 1) {
            url = args[1];
        }
        new HttpFileBootServer().run(port, url);
    }

}



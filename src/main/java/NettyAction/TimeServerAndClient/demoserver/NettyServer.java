package NettyAction.TimeServerAndClient.demoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description Netty入门程序：Netty服务端启动类
 * @Date 2019/9/18 0018 上午 11:17
 * @Created by Pengrenjun
 */
public class NettyServer {

    private ServerBootstrap serverBootstrap=new ServerBootstrap();

    private final int DEFAULT_SERVER_PORT=8001;

    private int serverPort=DEFAULT_SERVER_PORT;

    //服务端接收客户端的连接
    private EventLoopGroup bossGroup=new NioEventLoopGroup();
    //网络读写
    private EventLoopGroup workGroup=new NioEventLoopGroup();

    //端口绑定
    public NettyServer initServerAndBindPort(int port){

        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100) //队列中可存入的客户端连接数
                .childOption(ChannelOption.SO_REUSEADDR,true)//绑定的端口和地址可重复使用
                .handler(new LoggingHandler(LogLevel.INFO));
        serverPort=port;
        return this;
    }

    //添加处理handles
    public NettyServer addChildHandlers(ChannelHandlerAdapter...channelHandlerAdapters){
        serverBootstrap.childHandler(new SeverChildChannelHandlers(channelHandlerAdapters));
        return this;
    }

    //启动服务
    public void start()  {
        try {
            ChannelFuture channelFuture=serverBootstrap.bind(serverPort).sync();//同步阻塞等待端口地址绑定
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new NettyServer()
                .initServerAndBindPort(8002)
                .addChildHandlers(new TimeServerHandler())//按序添加handlers
                .start();
    }


}

package NettyAction.TimeServerAndClient.democlient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description 客户端
 * @Date 2019/9/18 0018 下午 3:32
 * @Created by Pengrenjun
 */
public class TimeClient {

    Bootstrap bootstrap=new Bootstrap();

    private String connectHost="localhost";

    private int connectPort=8001;
    //客户端工作线程组
    private EventLoopGroup cliGroup=new NioEventLoopGroup();


    //客户端连接引导初始化
    public TimeClient connect(String host,int port ){

        bootstrap.group(cliGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,Boolean.TRUE);
        connectHost=host;
        connectPort=port;
        return this;

    }

    //添加handlers处理器
    public TimeClient addHandlers(ChannelHandlerAdapter...channelHandlerAdapters){
        bootstrap.handler(new ClientChildChannelHandlers(channelHandlerAdapters));
        return this;
    }

    //建立与服务器的连接
    public void start(){
        try {
            ChannelFuture future = bootstrap.connect(connectHost, connectPort).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cliGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TimeClient().connect("localhost",8002).addHandlers(new TimeClientHandler()).start();
    }
}

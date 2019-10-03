package NettyAction.udp;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpServer {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(UdpServer.class);

    public void run(int port){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    //采用NioDatagramChannel
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .handler(new UdpServerHandler());
            System.out.println("udp sever started");
            bootstrap.bind(port).sync().channel().closeFuture().await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        //启动Udp服务
        new UdpServer().run(8089);
    }
}

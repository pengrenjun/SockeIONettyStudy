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
 * @Description Netty���ų���Netty�����������
 * @Date 2019/9/18 0018 ���� 11:17
 * @Created by Pengrenjun
 */
public class NettyServer {

    private ServerBootstrap serverBootstrap=new ServerBootstrap();

    private final int DEFAULT_SERVER_PORT=8001;

    private int serverPort=DEFAULT_SERVER_PORT;

    //����˽��տͻ��˵�����
    private EventLoopGroup bossGroup=new NioEventLoopGroup();
    //�����д
    private EventLoopGroup workGroup=new NioEventLoopGroup();

    //�˿ڰ�
    public NettyServer initServerAndBindPort(int port){

        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100) //�����пɴ���Ŀͻ���������
                .childOption(ChannelOption.SO_REUSEADDR,true)//�󶨵Ķ˿ں͵�ַ���ظ�ʹ��
                .handler(new LoggingHandler(LogLevel.INFO));
        serverPort=port;
        return this;
    }

    //��Ӵ���handles
    public NettyServer addChildHandlers(ChannelHandlerAdapter...channelHandlerAdapters){
        serverBootstrap.childHandler(new SeverChildChannelHandlers(channelHandlerAdapters));
        return this;
    }

    //��������
    public void start()  {
        try {
            ChannelFuture channelFuture=serverBootstrap.bind(serverPort).sync();//ͬ�������ȴ��˿ڵ�ַ��
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //�ͷ���Դ
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new NettyServer()
                .initServerAndBindPort(8002)
                .addChildHandlers(new TimeServerHandler())//�������handlers
                .start();
    }


}

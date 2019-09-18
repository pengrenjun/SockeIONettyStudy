package NettyAction.TimeServerAndClient.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description ʱ�������Զ���Ĵ�������·
 * @Date 2019/9/18 0018 ���� 11:29
 * @Created by Pengrenjun
 */
public class ChildChannelHandlers extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TimeServerHandler());
    }
}

package NettyAction.TimeServerAndClient.demoserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description ʱ�������Զ���Ĵ�������·
 * @Date 2019/9/18 0018 ���� 11:29
 * @Created by Pengrenjun
 */
public class SeverChildChannelHandlers extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TimeServerHandler());
    }
}

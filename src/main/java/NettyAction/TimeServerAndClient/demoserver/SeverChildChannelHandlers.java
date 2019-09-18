package NettyAction.TimeServerAndClient.demoserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description 时间服务端自定义的处理器链路
 * @Date 2019/9/18 0018 上午 11:29
 * @Created by Pengrenjun
 */
public class SeverChildChannelHandlers extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TimeServerHandler());
    }
}

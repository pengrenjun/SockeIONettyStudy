package NettyAction.TimeServerAndClient.democlient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description TODO
 * @Date 2019/9/18 0018 обнГ 4:31
 * @Created by Pengrenjun
 */
public class ClientChildChannelHandlers extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new LoggingHandler(LogLevel.INFO))
                .addLast(new TimeClientHandler());
    }
}

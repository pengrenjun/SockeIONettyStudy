package NettyAction.TimeServerAndClient.democlient;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description TODO
 * @Date 2019/9/18 0018 ÏÂÎç 4:31
 * @Created by Pengrenjun
 */
public class ClientChildChannelHandlers extends ChannelInitializer<SocketChannel> {

    //Ìí¼ÓpipelinedµÄhandlers
    private ChannelHandlerAdapter[] ChannelHandlerAdapters=new ChannelHandlerAdapter[]{};

    public ClientChildChannelHandlers(ChannelHandlerAdapter[] channelHandlerAdapters) {
        this.ChannelHandlerAdapters=channelHandlerAdapters;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        for(ChannelHandlerAdapter handlerAdapter:ChannelHandlerAdapters){
            pipeline.addLast(handlerAdapter);
        }
    }
}

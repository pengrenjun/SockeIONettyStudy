package NettyAction.TimeServerAndClient.demoserver;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description 时间服务端自定义的处理器链路
 * @Date 2019/9/18 0018 上午 11:29
 * @Created by Pengrenjun
 */
public class SeverChildChannelHandlers extends ChannelInitializer<SocketChannel> {

    //添加pipelined的handlers
    private ChannelHandlerAdapter[] ChannelHandlerAdapters=new ChannelHandlerAdapter[]{};

    public SeverChildChannelHandlers(ChannelHandlerAdapter[] channelHandlerAdapters) {

        this.ChannelHandlerAdapters=channelHandlerAdapters;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        for(ChannelHandlerAdapter handlerAdapter:ChannelHandlerAdapters){
            pipeline.addLast(handlerAdapter);
        }
    }
}

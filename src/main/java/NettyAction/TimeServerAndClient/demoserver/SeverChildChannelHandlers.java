package NettyAction.TimeServerAndClient.demoserver;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description ʱ�������Զ���Ĵ�������·
 * @Date 2019/9/18 0018 ���� 11:29
 * @Created by Pengrenjun
 */
public class SeverChildChannelHandlers extends ChannelInitializer<SocketChannel> {

    //���pipelined��handlers
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

package NettyAction.TimeServerAndClient.democlient;

import NettyAction.TimeServerAndClient.demoserver.TimeServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description 时间客户端处理器
 * @Date 2019/9/18 0018 下午 3:47
 * @Created by Pengrenjun
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(TimeServerHandler.class);

    //获取服务器端系统时间指令
    private final byte[] GET_SERVER_SYSTIME_BYTES="qct".getBytes();


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        logger.info("channelActive-> 客户端TimeClient与服务器端 {} 建立了连接",ctx.channel().remoteAddress());

        ByteBuf sendBuf=Unpooled.buffer();
        //建立连接后发送指令 获取服务器端系统时间
        sendBuf.writeBytes(GET_SERVER_SYSTIME_BYTES);
        //发送指令
        logger.info("channelActive->向服务器端发送指令..");
        ctx.writeAndFlush(sendBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //super.channelInactive(ctx);
        logger.info("channelInactive-> 客户端TimeClient关闭与服务器端 {}的连接",ctx.channel().remoteAddress());


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ByteBuf buf=(ByteBuf)  msg;
        byte[] reciveBytes=new byte[buf.readableBytes()];
        buf.readBytes(reciveBytes);
        String body=new String(reciveBytes, CharsetUtil.UTF_8);
        logger.info("channelRead->客户端接收到服务端响应 {}",body);

        //每隔两秒发送一次请求
        Thread.sleep(2000);
        logger.info("channelRead->客户端向服务端发送指令..");
        ByteBuf sendBuf=Unpooled.buffer();
        sendBuf.writeBytes(GET_SERVER_SYSTIME_BYTES);
        ctx.writeAndFlush(sendBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

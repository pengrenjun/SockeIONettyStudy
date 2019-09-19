package NettyAction.packageQustion.fixlengthBaseDecode;

import NettyAction.packageQustion.delimiterBaseDecode.DlimiterClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 使用FixedLengthFrameDecoder进行解析的服务端handler
 *              实现按照固定长度解析多包报文
 * @Date 2019/9/19 0019 下午 7:16
 * @Created by Pengrenjun
 */
public class FixLenServerHandler extends ChannelHandlerAdapter {
    Logger log= LoggerFactory.getLogger(FixLenServerHandler.class);
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //10个字符作为一条消息
        String body=(String)msg;
        log.info("服务端接收到客户端的数据：{}",body);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        //ctx.flush();
    }
}

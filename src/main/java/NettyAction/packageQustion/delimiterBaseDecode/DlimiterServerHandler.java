package NettyAction.packageQustion.delimiterBaseDecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 使用DlimiterDecode进行解析的服务端handler
 *              实现按照特定分隔符解析多包报文，并且将接收的报文指令原样返给客户端
 * @Date 2019/9/19 0019 下午 7:16
 * @Created by Pengrenjun
 */
public class DlimiterServerHandler extends ChannelHandlerAdapter {
    Logger log= LoggerFactory.getLogger(DlimiterServerHandler.class);
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
        //super.channelRead(ctx, msg);
        String body=(String)msg;
        log.info("服务端接收到客户端的数据：{}",body);

        //返回给客户端响应数据 客户端以"&"作为分隔符 服务端发送的多条消息需要以&结尾
        String sendMsg="Server ack to clientMsg："+body+DlimiterClientHandler.DELIMITER_FLAG;

        ByteBuf sendBuf= Unpooled.buffer();
        //建立连接后发送指令 获取服务器端系统时间
        sendBuf.writeBytes(sendMsg.getBytes());
        ctx.write(sendBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       // super.channelReadComplete(ctx);
        ctx.flush();
    }
}

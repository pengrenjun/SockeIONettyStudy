package NettyAction.packageQustion.delimiterBaseDecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 使用DlimiterDecode进行解析的客户端handler
 * @Date 2019/9/19 0019 下午 7:16
 * @Created by Pengrenjun
 */
public class DlimiterClientHandler extends ChannelHandlerAdapter {

    Logger log= LoggerFactory.getLogger(DlimiterClientHandler.class);

    //消息分割符
    public static final String DELIMITER_FLAG= "&";

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //客户端启动后 向服务端发送报文信息 模拟多包发送 以&作为单条消息分隔符

        for(int i=0;i<10;i++){
            String sendMsg="client send msgContent:"+i+DELIMITER_FLAG;
            ByteBuf sendBuf= Unpooled.buffer();
            //建立连接后发送指令 获取服务器端系统时间
            sendBuf.writeBytes(sendMsg.getBytes());
            ctx.writeAndFlush(sendBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        //客户端接收到服务端响应数据
        String body=(String)msg;
        log.info("客户端接收到的服务端返回的数据：{}",body);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}

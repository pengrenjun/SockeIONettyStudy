package NettyAction.TimeServerAndClient.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * @Description 时间服务器处理器
 * @Date 2019/9/18 0018 上午 11:33
 * @Created by Pengrenjun
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(TimeServerHandler.class);

    private static final String QUREY_CURRENT_TIME="qct";

    private static String send2CliMsg="";

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        logger.info("channelActive-> {}:客户端建立连接 ",ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       // super.channelInactive(ctx);
        logger.info("channelInactive-> {}:客户端关闭与服务端连接 ",ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String body=new String(bytes, CharsetUtil.UTF_8);
        logger.info("channelRead->时间服务器接收到客户端发送的指令:{}",body);

        if(body.equalsIgnoreCase(QUREY_CURRENT_TIME)){

            String sendTime= DateFormat.getDateTimeInstance().format(new Date());
            send2CliMsg=sendTime;
            ByteBuf sendBuf= Unpooled.copiedBuffer(sendTime.getBytes());
            //发送数据缓存到通道
            ctx.write(sendBuf);
        }else {
            throw new RuntimeException("上报指令格式错误");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete ->服务端响应数据：{}",send2CliMsg);
        //将缓冲区的消息全部写入到SocketChannel中进行发送
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //处理异常
        logger.error("exceptionCaught->服务端处理异常：{}",cause.getMessage());
        ctx.close();
    }
}

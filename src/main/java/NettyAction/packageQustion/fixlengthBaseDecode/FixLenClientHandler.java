package NettyAction.packageQustion.fixlengthBaseDecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @Description 使用FixedLengthFrameDecoder进行解析的客户端handler
 * @Date 2019/9/19 0019 下午 7:16
 * @Created by Pengrenjun
 */
public class FixLenClientHandler extends ChannelHandlerAdapter {

    Logger log= LoggerFactory.getLogger(FixLenClientHandler.class);



    //消息的固定长度
    public static final int MSG_FIX_LEN= 10;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //客户端启动后 向服务端发送报文信息
        String[] strArr=new String[]{"a","b","c","d","e","f","g","h","i","j"};
        Random random = new Random();

        for(int i=0;i<100;i++){
            //每次发送一个字符 服务端会实现按10个字符作为一条消息进行解析
            String sendMsg=strArr[random.nextInt(10)];
            ByteBuf sendBuf= Unpooled.buffer();
            //建立连接后发送指令 获取服务器端系统时间
            sendBuf.writeBytes(sendMsg.getBytes());
            ctx.writeAndFlush(sendBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

}

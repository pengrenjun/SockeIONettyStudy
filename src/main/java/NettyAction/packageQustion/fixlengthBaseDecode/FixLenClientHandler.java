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
 * @Description ʹ��FixedLengthFrameDecoder���н����Ŀͻ���handler
 * @Date 2019/9/19 0019 ���� 7:16
 * @Created by Pengrenjun
 */
public class FixLenClientHandler extends ChannelHandlerAdapter {

    Logger log= LoggerFactory.getLogger(FixLenClientHandler.class);



    //��Ϣ�Ĺ̶�����
    public static final int MSG_FIX_LEN= 10;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //�ͻ��������� �����˷��ͱ�����Ϣ
        String[] strArr=new String[]{"a","b","c","d","e","f","g","h","i","j"};
        Random random = new Random();

        for(int i=0;i<100;i++){
            //ÿ�η���һ���ַ� ����˻�ʵ�ְ�10���ַ���Ϊһ����Ϣ���н���
            String sendMsg=strArr[random.nextInt(10)];
            ByteBuf sendBuf= Unpooled.buffer();
            //�������Ӻ���ָ�� ��ȡ��������ϵͳʱ��
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

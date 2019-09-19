package NettyAction.packageQustion.delimiterBaseDecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description ʹ��DlimiterDecode���н����Ŀͻ���handler
 * @Date 2019/9/19 0019 ���� 7:16
 * @Created by Pengrenjun
 */
public class DlimiterClientHandler extends ChannelHandlerAdapter {

    Logger log= LoggerFactory.getLogger(DlimiterClientHandler.class);

    //��Ϣ�ָ��
    public static final String DELIMITER_FLAG= "&";

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //�ͻ��������� �����˷��ͱ�����Ϣ ģ�������� ��&��Ϊ������Ϣ�ָ���

        for(int i=0;i<10;i++){
            String sendMsg="client send msgContent:"+i+DELIMITER_FLAG;
            ByteBuf sendBuf= Unpooled.buffer();
            //�������Ӻ���ָ�� ��ȡ��������ϵͳʱ��
            sendBuf.writeBytes(sendMsg.getBytes());
            ctx.writeAndFlush(sendBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        //�ͻ��˽��յ��������Ӧ����
        String body=(String)msg;
        log.info("�ͻ��˽��յ��ķ���˷��ص����ݣ�{}",body);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}

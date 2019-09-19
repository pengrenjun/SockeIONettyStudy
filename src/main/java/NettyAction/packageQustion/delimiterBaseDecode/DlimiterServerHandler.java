package NettyAction.packageQustion.delimiterBaseDecode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description ʹ��DlimiterDecode���н����ķ����handler
 *              ʵ�ְ����ض��ָ�������������ģ����ҽ����յı���ָ��ԭ�������ͻ���
 * @Date 2019/9/19 0019 ���� 7:16
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
        log.info("����˽��յ��ͻ��˵����ݣ�{}",body);

        //���ظ��ͻ�����Ӧ���� �ͻ�����"&"��Ϊ�ָ��� ����˷��͵Ķ�����Ϣ��Ҫ��&��β
        String sendMsg="Server ack to clientMsg��"+body+DlimiterClientHandler.DELIMITER_FLAG;

        ByteBuf sendBuf= Unpooled.buffer();
        //�������Ӻ���ָ�� ��ȡ��������ϵͳʱ��
        sendBuf.writeBytes(sendMsg.getBytes());
        ctx.write(sendBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       // super.channelReadComplete(ctx);
        ctx.flush();
    }
}

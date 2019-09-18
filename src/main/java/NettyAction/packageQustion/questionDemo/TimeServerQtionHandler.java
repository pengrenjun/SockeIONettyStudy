package NettyAction.packageQustion.questionDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description ʱ�������������(δ����������)
 * @Date 2019/9/18 0018 ���� 11:33
 * @Created by Pengrenjun
 */
@ChannelHandler.Sharable
public class TimeServerQtionHandler extends ChannelHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(TimeServerQtionHandler.class);

    private static final String QUREY_CURRENT_TIME="qct";

    private static String send2CliMsg="";

    private AtomicInteger atomicInteger=new AtomicInteger(0);

    //�س����з��ĳ���
    private int separatorLen=System.getProperty("line.separator").length();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        logger.info("channelActive-> {}:�ͻ��˽������� ",ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       // super.channelInactive(ctx);
        logger.info("channelInactive-> {}:�ͻ��˹ر����������� ",ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        //���͵�ָ���Իس����з���β
        String body=new String(bytes, CharsetUtil.UTF_8).substring(0,bytes.length-separatorLen);

        //���յ�һ��ָ�� counter��1 ͳ�ƽ��յ���ָ������
        int count = atomicInteger.incrementAndGet();
        logger.info("channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:{}, counter number:{}",body,count);

        if(body.equalsIgnoreCase(QUREY_CURRENT_TIME)){

            String sendTime= DateFormat.getDateTimeInstance().format(new Date());
            send2CliMsg=sendTime;
            ByteBuf sendBuf= Unpooled.copiedBuffer(sendTime.getBytes());
            //�������ݻ��浽ͨ��
            ctx.write(sendBuf);
        }else {
            throw new RuntimeException("�ϱ�ָ���ʽ����");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete ->�������Ӧ���ݣ�{}",send2CliMsg);
        //������������Ϣȫ��д�뵽SocketChannel�н��з���
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //�����쳣
        logger.error("exceptionCaught->����˴����쳣��{}",cause.getMessage());
        ctx.close();
    }
}

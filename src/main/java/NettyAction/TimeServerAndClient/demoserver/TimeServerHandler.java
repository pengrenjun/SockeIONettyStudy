package NettyAction.TimeServerAndClient.demoserver;

import com.emrubik.codec.generator.convertor.Convertor;
import com.emrubik.codec.generator.convertor.HexDumper;
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

/**
 * @Description ʱ�������������
 * @Date 2019/9/18 0018 ���� 11:33
 * @Created by Pengrenjun
 */
@ChannelHandler.Sharable
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


        double reading=Double.valueOf(String.format("%.6f",Float.intBitsToFloat(Convertor.bytesToInt(bytes)))).doubleValue();


        String body=new String(bytes, CharsetUtil.UTF_8);
        logger.info("channelRead->ʱ����������յ��ͻ��˷��͵�ָ��:{}",body);

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

    private static int reverse(int a) {
        //105987998
        int j = (1 << 8) - 1;
        int i = a & j;
        int ii = a & j << 8;
        int iii = a & j << 16;
        int iiii = a & j << 24;
        return i << 24 | ii << 8 | iii >>> 8 | iiii >>> 24;
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

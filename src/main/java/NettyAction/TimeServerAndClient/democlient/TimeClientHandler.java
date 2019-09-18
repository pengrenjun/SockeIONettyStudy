package NettyAction.TimeServerAndClient.democlient;

import NettyAction.TimeServerAndClient.demoserver.TimeServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description ʱ��ͻ��˴�����
 * @Date 2019/9/18 0018 ���� 3:47
 * @Created by Pengrenjun
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    Logger logger= LoggerFactory.getLogger(TimeServerHandler.class);

    //��ȡ��������ϵͳʱ��ָ��
    private final byte[] GET_SERVER_SYSTIME_BYTES="qct".getBytes();


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        logger.info("channelActive-> �ͻ���TimeClient��������� {} ����������",ctx.channel().remoteAddress());

        ByteBuf sendBuf=Unpooled.buffer();
        //�������Ӻ���ָ�� ��ȡ��������ϵͳʱ��
        sendBuf.writeBytes(GET_SERVER_SYSTIME_BYTES);
        //����ָ��
        logger.info("channelActive->��������˷���ָ��..");
        ctx.writeAndFlush(sendBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //super.channelInactive(ctx);
        logger.info("channelInactive-> �ͻ���TimeClient�ر���������� {}������",ctx.channel().remoteAddress());


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ByteBuf buf=(ByteBuf)  msg;
        byte[] reciveBytes=new byte[buf.readableBytes()];
        buf.readBytes(reciveBytes);
        String body=new String(reciveBytes, CharsetUtil.UTF_8);
        logger.info("channelRead->�ͻ��˽��յ��������Ӧ {}",body);

        //ÿ�����뷢��һ������
        Thread.sleep(2000);
        logger.info("channelRead->�ͻ��������˷���ָ��..");
        ByteBuf sendBuf=Unpooled.buffer();
        sendBuf.writeBytes(GET_SERVER_SYSTIME_BYTES);
        ctx.writeAndFlush(sendBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

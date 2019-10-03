package NettyAction.udp;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String[] DICT = {
            "ֻҪ������,����ĥ����"
            , "��ʱ��л��ǰ��,����Ѱ�����ռ�"
            , "��������������,һƬ���������"
            , "һ�����һ���,�����������"
            , "��������,־��ǧ��.��ʿĺ��,׳�Ĳ���!"
    };


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String req = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println("�յ�������:"+req);
        if ("�����ֵ��ѯ?".equals(req)) {
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("�����ѯ���:" + nextQueue(), CharsetUtil.UTF_8), msg.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private String nextQueue() {
        //ThreadLocalRandom �̰߳�ȫ�����
        int quoteId = ThreadLocalRandom.current().nextInt(DICT.length);
        return DICT[quoteId];
    }

}

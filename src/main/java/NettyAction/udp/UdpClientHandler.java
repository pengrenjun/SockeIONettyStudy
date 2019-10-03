package NettyAction.udp;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String resp = msg.content().toString(CharsetUtil.UTF_8);
        if (resp.startsWith("—Ë”Ô≤È—ØΩ·π˚:")){
            System.out.println(resp);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        ctx.close();
    }
}

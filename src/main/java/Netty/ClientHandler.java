package Netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			//do something msg
			/*ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");*/
			String request=(String) msg;

			System.out.println("get Server response:"+request);
			//ctx.writeAndFlush(Unpooled.copiedBuffer(("Client msg:"+request+" Client Handle Time: "+System.currentTimeMillis()).getBytes()));
			/*addListener 方法:添加client端的监听,当Client端返回数据后 会自动关闭线程与Server端的连接*/
			//.addListener(ChannelFutureListener.CLOSE);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}

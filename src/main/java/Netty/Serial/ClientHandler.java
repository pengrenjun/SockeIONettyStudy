package Netty.Serial;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {

			Response response=(Response) msg;

			System.out.println("get Server response:"+response.toString());
			//ctx.writeAndFlush(Unpooled.copiedBuffer(("Client msg:"+request+" Client Handle Time: "+System.currentTimeMillis()).getBytes()));
			/*addListener ����:���client�˵ļ���,��Client�˷������ݺ� ���Զ��ر��߳���Server�˵�����*/
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

package Netty.Serial;

import Netty.Utils.GizpUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ObjectUtil;

import java.io.File;

public class ServerHandler  extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.name()+"actived>>>>>>");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
			//do something msg
			Request request=(Request) msg;
			System.out.println("Server: " + request.toString());

			if(request.getAttachment().length>0){
				//读取保存文件
				String filepath=System.getProperty("user.dir")+ File.separator+"receive"+File.separator+request.getName();

				GizpUtils.loadFileByGZIPbytesAndFilePath(filepath,request.getAttachment());
			}




			//写给客户端 在执行writeAndFlush方法的同时 msg资源已经释放了
            Response response = new Response();
			response.setId(request.getId());
			response.setName("response" + request.getName());
			response.setResponseMessage("响应内容" + request.getId());
			ctx.writeAndFlush(response);

			//.addListener(ChannelFutureListener.CLOSE);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}

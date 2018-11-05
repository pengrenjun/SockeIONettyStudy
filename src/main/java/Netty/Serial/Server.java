package Netty.Serial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

	public static void main(String[] args) throws Exception {
		//1 第一个线程组 是用于接收Client端连接的
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//2 第二个线程组 是用于实际的业务处理操作的
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		//3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的配置
		ServerBootstrap b = new ServerBootstrap(); 
		//把俩个工作线程组加入进来
		b.group(bossGroup, workerGroup)
		//我要指定使用NioServerSocketChannel这种类型的通道
		 .channel(NioServerSocketChannel.class)
		 //设置打印日志
		 .handler(new LoggingHandler(LogLevel.INFO))
		//一定要使用 childHandler 去绑定具体的 事件处理器
		 .childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				/*解决TCP粘包 拆包的问题*//*
				//设置特殊的分隔符
				ByteBuf buf= Unpooled.copiedBuffer("&".getBytes());
				//DelimiterBasedFrameDecoder 按照特殊分隔符处理数据流
				sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
				//将数据流解析为字符串格式 Handler端可以直接将msg转化为String类型
				sc.pipeline().addLast(new StringDecoder());*/

				//添加JbossMarshelling的对象字节编码解码解析器 相当于自定义协议处理数据流 不能与DelimiterBasedFrameDecoder同时使用
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

				sc.pipeline().addLast(new ServerHandler());
			}
		});

		//绑定指定的端口 进行监听
		ChannelFuture f = b.bind(8551).sync();
		//可以绑定多个端口,增强数据的接受能力,但数据的处理能力取决于ServerHandler
		ChannelFuture f2 = b.bind(8552).sync();


		System.out.println(b.toString()+">>>>>> start ok!");
		//Thread.sleep(1000000);
		f.channel().closeFuture().sync();
		f2.channel().closeFuture().sync();
		
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		 
		
		
	}
	
}

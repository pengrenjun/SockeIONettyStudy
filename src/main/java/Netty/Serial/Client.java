package Netty.Serial;

import Netty.Utils.GizpUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Client {

	public static void main(String[] args) throws Exception {
		
		EventLoopGroup workgroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workgroup)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				/*���TCPճ�� ���������*//*
				//��������ķָ���
				ByteBuf buf= Unpooled.copiedBuffer("&".getBytes());
				//DelimiterBasedFrameDecoder ��������ָ�������������
				sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
				//������������Ϊ�ַ�����ʽ Handler�˿���ֱ�ӽ�msgת��ΪString����
				sc.pipeline().addLast(new StringDecoder());*/

				//���JbossMarshelling�Ķ����ֽڱ�����������
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

				sc.pipeline().addLast(new ClientHandler());


			}
		});
		
		ChannelFuture cf1 = b.connect("127.0.0.2", 8551).sync();

		//����Server�˵�����һ���˽ӿ�
		ChannelFuture cf2 = b.connect("127.0.0.2", 8552).sync();
		
		//buf ����������ַ� �������޷����͵�
		for(int i=0;i<5;i++){
			Request request = new Request();
			request.setId("" + i);
			request.setName("00"+(i+1)+".jpg");
			request.setRequestMessage("request message:" + i);

			String filepath=System.getProperty("user.dir")+ File.separator+"sources"+File.separator+request.getName();

			request.setAttachment(GizpUtils.gizpByFilePath(filepath));

			cf1.channel().writeAndFlush(request);
			TimeUnit.SECONDS.sleep(4);
		}

		/*for(int i=0;i<5;i++){
			Request request = new Request();
			request.setId("" + i);
			request.setName("pro clint2" + i);
			request.setRequestMessage("request message:" + i);
			cf2.channel().writeAndFlush(request);
			TimeUnit.SECONDS.sleep(4);
		}*/
		
		cf1.channel().closeFuture().sync();
		cf2.channel().closeFuture().sync();
		workgroup.shutdownGracefully();
		
	}
}

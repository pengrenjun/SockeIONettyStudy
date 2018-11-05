package Netty;

import Netty.Serial.MarshallingCodeCFactory;
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

public class Client {

	public static void main(String[] args) throws Exception {
		
		EventLoopGroup workgroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workgroup)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				/*���TCPճ�� ���������*/
				//��������ķָ���
				ByteBuf buf= Unpooled.copiedBuffer("&".getBytes());
				//DelimiterBasedFrameDecoder ��������ָ�������������
				sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
				//������������Ϊ�ַ�����ʽ Handler�˿���ֱ�ӽ�msgת��ΪString����
				sc.pipeline().addLast(new StringDecoder());

				//���JbossMarshelling�Ķ����ֽڱ�����������
				//sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				//sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

				sc.pipeline().addLast(new ClientHandler());


			}
		});
		
		ChannelFuture cf1 = b.connect("127.0.0.2", 8551).sync();

		//����Server�˵�����һ���˽ӿ�
		ChannelFuture cf2 = b.connect("127.0.0.2", 8552).sync();
		
		//buf ����������ַ� �������޷����͵�
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("client1 1a&".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("client1 1b&".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("client1 1c&".getBytes()));

		cf2.channel().writeAndFlush(Unpooled.copiedBuffer("client2 2a&".getBytes()));
		cf2.channel().writeAndFlush(Unpooled.copiedBuffer("client2 2b&".getBytes()));
		cf2.channel().writeAndFlush(Unpooled.copiedBuffer("client2 2c&".getBytes()));
		
		cf1.channel().closeFuture().sync();
		cf2.channel().closeFuture().sync();
		workgroup.shutdownGracefully();
		
	}
}

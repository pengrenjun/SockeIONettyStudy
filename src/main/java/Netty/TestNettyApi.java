package Netty;

import io.netty.buffer.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class TestNettyApi {


    public static void main(String[] args) {
        //衍生的缓冲区Derived buffers
        testA();

        //测试Nettty提供的ByteBuf的工具类
        testB();


    }
    //测试Nettty提供的ByteBuf的工具类
    private static void testB() {

        ByteBufAllocator byteBufAllocator= new NioSocketChannel().alloc();

        //通过byteBufAllocator建立不同类型的ByteBuf 其余方法参照 CompositeByteBuf接口API
        CompositeByteBuf compositeByteBuf = byteBufAllocator.compositeBuffer();

        ByteBuf buf = byteBufAllocator.directBuffer();

        ByteBuf buf1 = byteBufAllocator.heapBuffer();


        //Unpooled 也是Netty提供的创建各种缓冲区的工具类 里面包含很多方法
        CompositeByteBuf compositeByteBuf1 = Unpooled.compositeBuffer();



    }

    //衍生的缓冲区Derived buffers
    public static   void testA(){
        Charset utf8=Charset.forName("UTF-8");
        // get a ByteBuf  
        ByteBuf buf=Unpooled.copiedBuffer("qwertyuiozxcfghasdsdfgssdfgbv ",utf8);
        // slice  
        ByteBuf sliced=buf.slice(0,14);
        // copy  
        ByteBuf copy = buf.copy(0, 14);

        // print "“Netty in Action rocks!“"  
        System.out.println(buf.toString(utf8));
        // print "Netty in Act"  
        System.out.println(sliced.toString(utf8));
        // print "Netty in Act"  
        System.out.println(copy.toString(utf8));

    }
}

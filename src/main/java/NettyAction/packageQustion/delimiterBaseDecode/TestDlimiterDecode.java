package NettyAction.packageQustion.delimiterBaseDecode;

import NettyAction.TimeServerAndClient.democlient.NettyClient;
import NettyAction.TimeServerAndClient.demoserver.NettyServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;


/**
 * @Description 测试DlimiterDecode进行解析演示
 * @Date 2019/9/19 0019 下午 7:39
 * @Created by Pengrenjun
 */
public class TestDlimiterDecode {

    ByteBuf delimiterBuf= Unpooled.copiedBuffer(DlimiterClientHandler.DELIMITER_FLAG.getBytes());

    @Test
    public void startDlimiterServer(){


        DelimiterBasedFrameDecoder delimiterHandler= new DelimiterBasedFrameDecoder(1024,delimiterBuf);


        new NettyServer()
                .initServerAndBindPort(8000)
                .addChildHandlers(delimiterHandler,new StringDecoder(),new DlimiterServerHandler())
                .start();

    }
    @Test
    public void startDlimiterClient(){

        DelimiterBasedFrameDecoder delimiterHandler= new DelimiterBasedFrameDecoder(1024,delimiterBuf);


        new NettyClient()
                .connect("localhost",8000)
                .addHandlers(delimiterHandler,new StringDecoder(),new DlimiterClientHandler())
                .start();
    }
}

package NettyAction.packageQustion.fixlengthBaseDecode;

import NettyAction.TimeServerAndClient.democlient.NettyClient;
import NettyAction.TimeServerAndClient.demoserver.NettyServer;
import NettyAction.packageQustion.delimiterBaseDecode.DlimiterClientHandler;
import NettyAction.packageQustion.delimiterBaseDecode.DlimiterServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;


/**
 * @Description 测试FixedLengthFrameDecoder进行解析演示
 * @Date 2019/9/19 0019 下午 7:39
 * @Created by Pengrenjun
 */
public class TestFixLenDecode {


    @Test
    public void startFixLenServer(){


        FixedLengthFrameDecoder fixLenHandler= new FixedLengthFrameDecoder(FixLenClientHandler.MSG_FIX_LEN);


        new NettyServer()
                .initServerAndBindPort(8000)
                .addChildHandlers(fixLenHandler,new StringDecoder(),new FixLenServerHandler())
                .start();

    }
    @Test
    public void startFixLenClient(){


        new NettyClient()
                .connect("localhost",8000)
                .addHandlers(new FixLenClientHandler())
                .start();
    }
}

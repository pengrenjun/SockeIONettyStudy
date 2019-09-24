package NettyAction.DecodeAndEncode.JavaObjectCode;

import NettyAction.TimeServerAndClient.democlient.NettyClient;
import NettyAction.TimeServerAndClient.demoserver.NettyServer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.junit.Test;

/**
 * 测试基于java序列化的数据的编码解码
 */
public class TestJavaObjectCode {

    //启动服务端
    @Test
    public void startMeterServer(){

        //客户端与服务器端的对象的序列化和反序列化都由netty的对象解码器处理

        ObjectDecoder objectDecoder=new ObjectDecoder(1024*1024,
                ClassResolvers.weakCachingResolver(this.getClass().getClassLoader()));

        new NettyServer()
                .initServerAndBindPort(8005)
                .addChildHandlers(objectDecoder,new ObjectEncoder(),new RtuMeterServerHandler())
                .start();

    }

    //启动设备
    @Test
    public void startMeterClient(){

        ObjectDecoder objectDecoder=new ObjectDecoder(1024,
                ClassResolvers.cacheDisabled(this.getClass().getClassLoader()));

        new NettyClient()
                .connect("localhost",8005)
                .addHandlers(objectDecoder,new ObjectEncoder(),new RtuMeterClientHandler())
                .start();
    }
}
